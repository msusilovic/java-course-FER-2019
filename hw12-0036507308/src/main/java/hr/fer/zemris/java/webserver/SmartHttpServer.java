package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class representing a server used for receiving HTTP requests and processing them. 
 * 
 * @author Martina
 *
 */
public class SmartHttpServer {

	/**
	 * Address for this server.
	 */
	private String address;
	
	/**
	 * Domain name for this server.
	 */
	private String domainName;
	
	/**
	 * Port this server listens to.
	 */
	private int port;
	
	/**
	 * Number of threads.
	 */
	private int workerThreads;
	
	/**
	 * Number of milliseconds used as session timeout.
	 */
	private int sessionTimeout;
	
	/**
	 * Map of mime type names and values.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * Map binding worker names and workers.
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * Map of session names and entries.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/**
	 * Used to generate session id values.
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Thread executing server work.
	 */
	private ServerThread serverThread;
	
	/**
	 * Thread pool for this server.
	 */
	private ExecutorService threadPool;
	
	/**
	 * Root document for this server.
	 */
	private Path documentRoot;

	/**
	 * Value used when thread needs to stop.
	 */
	private volatile boolean stop;
	
	
	/**
	 * Constructs SmartHttpServer by initializing properties to values from
	 * given configuration properties file.
	 * 
	 * @param configFileName	name of properties file
	 */
	public SmartHttpServer(String configFileName) {
		
		Properties prop = new Properties();
		
		try (FileInputStream in = new FileInputStream(configFileName)) {
			prop.clear();
			prop.load(in);
			
			address = prop.getProperty("server.address");
			domainName = prop.getProperty("server.domainName");
			port = Integer.parseInt(prop.getProperty("server.port"));
			workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
			documentRoot = Paths.get(".").resolve(
						   Paths.get(prop.getProperty("server.documentRoot").substring(1)));
			sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String mimePath = Paths.get(".").resolve(
						  Paths.get(prop.getProperty("server.mimeConfig").substring(1))).toString();
		try(FileInputStream in2 = new FileInputStream(mimePath)) {
			prop.load(in2);
			prop.forEach((k, v) -> mimeTypes.put((String)k, (String)v));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String workPath = Paths.get(".").resolve(
						  Paths.get(prop.getProperty("server.workers").substring(1))).toString();
		try(FileInputStream in3 = new FileInputStream(workPath)) {
			prop.clear();
			prop.load(in3);
			prop.forEach((k, v) -> {	

				Path workerPath = documentRoot.resolve(Paths.get(((String)k).substring(1)));
				String pathName = workerPath.toString();
			
				Class<?> referenceToClass;
				Object newObject = null;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass((String) v);
					newObject = referenceToClass.getDeclaredConstructor().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
				IWebWorker iww = (IWebWorker)newObject;
				if (workersMap.containsKey(pathName)) {
					throw new RuntimeException("Multiple mappings for same path name");
				}
				workersMap.put(pathName, iww);
			});
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		serverThread = new ServerThread();
	}

	/**
	 * Method used to start server work.
	 */
	protected synchronized void start() {
		
		threadPool = Executors.newFixedThreadPool(workerThreads);
		if (!serverThread.isAlive()) {
			serverThread.run();
		}
		
		Thread cleaner = new CleanerThread();
		cleaner.setDaemon(true);
		cleaner.start();
	}

	/**
	 * Method used to terminate server work.
	 */
	protected synchronized void stop() {
		
		stop = true;
		threadPool.shutdown();
	}

	/**
	 * Model of a server thread used in this server.
	 * 
	 * @author Martina
	 *
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {
			
			ServerSocket serverSocket;

			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(
						new InetSocketAddress(InetAddress.getByName(address), port));
				
				while(!stop) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Thread used for periodically cleaning expires sessions from list of sessions to reduce 
	 * memory consumption. 
	 * 
	 * @author Martina
	 *
	 */
	protected class CleanerThread extends Thread {
		
		@Override
		public void run() {
			
			while(true) {
				List<String> toRemove = new ArrayList<>();
				for (Entry<String, SessionMapEntry> e : sessions.entrySet()) {
					if (e.getValue().validUntil < System.currentTimeMillis()/1000) {
						toRemove.add(e.getKey());
					}
				}
				for (String s : toRemove) {
					sessions.remove(s);
				}
				try {
	                 Thread.sleep(300000);
	             } catch (InterruptedException e) {
	                 e.printStackTrace();
	             }
			}
		}
	}
	
	
	/**
	 * Model of a client worker used in this server.
	 * 
	 * @author Martina
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Socket for each worker.
		 */
		private Socket csocket;
		
		/**
		 * Input stream for currently used socket.
		 */
		private PushbackInputStream istream;
		
		/**
		 * Output stream for currently used socket.
		 */
		private OutputStream ostream;
		
		/**
		 * Request version.
		 */
		private String version;
		
		/**
		 * Request method.
		 */
		private String method;
		
		/**
		 * Request host.
		 */
		private String host;
		
		/**
		 * Parameters for request.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		
		/**
		 * Temporary request parameters.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		
		/**
		 * Permanent request parameters.
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		
		/**
		 * List of cookies used in request.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/**
		 * Current session id.
		 */
		private String SID;
		
		/**
		 * Request context used in this server.
		 */
		RequestContext context = null;

		/**
		 * Session map entry for current worker.
		 */
		SessionMapEntry entry;
		
		/**
		 * Consctructs ClientWorker with given Socket.
		 * 
		 * @param csocket	socket to be set
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Then read complete request header from your client in separate method...
			List<String> request = new ArrayList<>();
			try {
				request = readRequest(istream);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			if (request.size() < 1) {
				sendError(ostream, 400, "Bad request");
		
				return;
			}
			
			String firstLine = request.get(0);
			
			String[] firstLineSplit = request.isEmpty() ? 
					null : request.get(0).split(" ");
			
			if(firstLine==null || firstLineSplit.length != 3) {
				sendError(ostream, 400, "Bad request");
				return;
			}
			
			method = firstLineSplit[0].toUpperCase();
			if(!method.equals("GET")) {
				sendError(ostream, 405, "Method Not Allowed");
				return;
			}
			
			String requestedPathString = firstLineSplit[1];
			
			version = firstLineSplit[2].toUpperCase();
			if(!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
				sendError(ostream, 505, "HTTP Version Not Supported");
				return;
			}
			
			for (String s : request) {
				if (s.toUpperCase().startsWith("HOST:")) {
					if (s.contains(":")) {
						host = s.substring(5).trim().split(":")[0];
						break;
					}
				}
			}
			if (host == null) {
				host = domainName;
			}
			
			//generates session id for current worker
			checkSession(request);

			String path;
			String paramString = null;
			
			// (path, paramString) = split requestedPath to path and parameterString
			if (requestedPathString.contains("?")) {
				String[] pathParts = requestedPathString.split("\\?");
				 path = pathParts[0];
				 paramString = pathParts[1];
			}else {
				path = requestedPathString;
			}
			
			if (paramString != null) {
					params = parseParameters(paramString);
			}		
			
			try {
				internalDispatchRequest(path, true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	}

		/**
		 * Method used to check if sesion id already exists. If not, generates new 
		 * entry, SID and cookie.
		 * 
		 * @param request	string containing header for this request
		 */
		private synchronized void checkSession(List<String> request) {
			
			String sidCandidate = null;
			
			for (String line : request) {
				
				if (line.startsWith("Cookie:")) {
					String l = line.substring(8).trim();
					String[] cookies = l.split(";");
					String name;
					String value;

					for (String cookie : cookies) {
						String[] parts = cookie.split("=");
						name = parts[0].trim();
						value = parts[1].trim();
						value = value.substring(1, value.length() - 1);
						if (name.equals("sid")) {
							sidCandidate = cookie;
							break;
						}
					}
				}
				if (sidCandidate != null)
					break;
			}
			
			if (sidCandidate == null) {
				setNewSid();
				return;
			}
			String[] parts = sidCandidate.split("=");
			String value = parts[1].trim();
			value = value.substring(1, value.length() - 1);
			
			SessionMapEntry entry = sessions.get(value);
			 if (entry == null) {
				 setNewSid();
					return;
			 }
			
			if (!entry.host.equals(host)) {
				setNewSid();
				return;
			}
			if (entry.validUntil < System.currentTimeMillis()/1000) {
				sessions.remove(value);
				setNewSid();
				return;
				
			}else {
				entry.validUntil = System.currentTimeMillis()/1000 + sessionTimeout;
				permParams = entry.map;
			}
		}
		
		/**
		 * Sets session ID to new value, creates entry and cookie.
		 */
		private void setNewSid() {
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				sb.append((char) (sessionRandom.nextInt(26) + 'A'));
			}
			
			SID = sb.toString();
			
			entry = new SessionMapEntry(SID,
										host,
										System.currentTimeMillis()/1000 + sessionTimeout,
										new ConcurrentHashMap<>());
			sessions.put(SID, entry);
			
			RCCookie cookie = new RCCookie("sid", SID, null, host, "/");
			cookie.setHttpOnly();
			outputCookies.add(cookie);
		}

		/**
		 * Method used to parse parameters from given string.
		 * 
		 * @param paramString	string to extract the parameters from
		 * @return	map of parameter names and values
		 */
		private Map<String, String> parseParameters(String paramString) {
			Map<String, String> map = new HashMap<>();
			
			String[] parts = paramString.split("&");
			
			for (String s : parts) {
				String[] nameAndValue = s.split("=");
				map.put(nameAndValue[0], nameAndValue[1]);
			}
			return map;
		}

		/**
		 * Method used to send errors.
		 * 
		 * @param ostream	output stream to send errors to
		 * @param code		status code
		 * @param text		status text
		 */
		private void sendError(OutputStream ostream, int i, String string) {
			try {
				ostream.write(
					("HTTP/1.1 "+i+" "+string+"\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: text/plain;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII));
				
				ostream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		/**
		 * Method used to read request parts from input stream.
		 * @param is	input stream to read from
		 * @return		list of strings containing request details
		 * @throws IOException	if reading from input stream fails
		 */
		private List<String> readRequest(InputStream is) throws IOException {

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int state = 0;
		l:		while(true) {
					int b = is.read();
					if(b==-1) return null;
					if(b!=13) {
						bos.write(b);
					}
					switch(state) {
					case 0: 
						if(b==13) { state=1; } else if(b==10) state=4;
						break;
					case 1: 
						if(b==10) { state=2; } else state=0;
						break;
					case 2: 
						if(b==13) { state=3; } else state=0;
						break;
					case 3: 
						if(b==10) { break l; } else state=0;
						break;
					case 4: 
						if(b==10) { break l; } else state=0;
						break;
					}
				}
				byte[] bytes =  bos.toByteArray();
				
				if(bytes==null) {
					sendError(ostream, 400, "Bad request");
					return null;
				}
				String requestStr = new String(
					bytes,
					StandardCharsets.US_ASCII
				);
				
				return extractHeaders(requestStr);
			}

		/**
		 * Method used to extract headers fromm a request string.
		 * 
		 * @param requestStr	string containing headers
		 * @return				list of headers
		 */
		private List<String> extractHeaders(String requestStr) {
			
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestStr.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
			
		}

		/**
		 * Internal method used for dispatching server requests.
		 * 
		 * @param urlPath		path used in this server
		 * @param directCall	value determining if internal dispatching is called directly or 
		 * 						from some other class		
		 * @throws Exception	if any problem occurs
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			checkPrivate(urlPath, directCall);
			
			String pathName = documentRoot.resolve((Paths.get(urlPath.substring(1)))).toString();
			
			if (urlPath.contains("/ext/")) {
				
				if (context == null) {
					createContext();
				}
				String name = urlPath.substring(urlPath.lastIndexOf("/") + 1);
				Class<?> referenceToClass = this.getClass()
												.getClassLoader()
												.loadClass("hr.fer.zemris.java.webserver.workers." + name);
				Object newObject = referenceToClass
								   .getDeclaredConstructor()
								   .newInstance();
				
				IWebWorker iww = (IWebWorker)newObject;
				iww.processRequest(context);
				close();
				return;
			}
			if (workersMap.containsKey(pathName)) {
				IWebWorker worker = workersMap.get(pathName);
				if (context == null) {
					createContext();
				}
				worker.processRequest(context);
				close();
				return;
			}
			
			// requestedPath = resolve path with respect to documentRoot
			Path requestedPath = documentRoot.resolve(Paths.get(urlPath.substring(1)));
			
			// if requestedPath is not below documentRoot, return response status 403 forbidden
			if (!requestedPath.toAbsolutePath().startsWith(documentRoot.toAbsolutePath())) {
				sendError(ostream, 403, "Requested path is not below document root.");
			}
			
			// check if requestedPath exists, is file and is readable; if not, return status 404
			if (!Files.exists(requestedPath, LinkOption.NOFOLLOW_LINKS) ||
				Files.isDirectory(requestedPath, LinkOption.NOFOLLOW_LINKS) ||
				!Files.isReadable(requestedPath)) {
				sendError(ostream, 404, "Requested path is not a readable file.");
			}

			// else extract file extension
			String filename = requestedPath.getFileName().toString();
			String[] parts = filename.split("\\.");
			String ext = parts[1];
			
			// find in mimeTypes map appropriate mimeType for current file extension
			String type = mimeTypes.get(ext);
			
			if (context == null) {
				createContext();
			}
			
			context.setMimeType(type);
			context.setStatusCode(200);
			
			if (ext.equals("smscr")) {
				String docBody = new String( Files.readAllBytes(requestedPath),StandardCharsets.UTF_8);
				context.setMimeType("text/plain");
				
				new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), context).execute();
				
				close();
				return;
			}
			
			// if no mime type found, assume application/octet-stream
			if (type == null) {
				type = "application/octet-stream";
			}
			
			InputStream str;
			
			try {
				str = new BufferedInputStream(Files.newInputStream(requestedPath));
				byte[] buf = new byte[1024];
	            while (true) {
	                int r = str.read(buf);
	                if (r < 1) break;
	                context.write(buf, 0, r);
	            }
	            str.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
            close();
            return;
		}

		/**
		 * Called to close output stream and socket.
		 */
		private void close() {
			try {
				ostream.flush();
				ostream.close();
				csocket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		/**
		 * Checks if method internalDispatchRequest was called internally or from some
		 * other class and sends error if path is /private.
		 * 
		 * @param urlPath		path to check if private
		 * @param directCall	boolean value to check if method was called internally
		 */
		private void checkPrivate(String urlPath, boolean directCall) {
			if (urlPath.equals("/private") || urlPath.contains("/private/")) {
				if (directCall) {
					sendError(ostream, 404, "Direct call of private");
				}
			}
		}

		/**
		 * Initializes context if null.
		 * This method is only called when context is first needed.
		 */
		private void createContext() {
			context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
			
		}
	}

	/**
	 * Represents a session map entrycontaining session id, host, map of parameters and 
	 * time until this session is valid.
	 * 
	 * @author Martina
	 *
	 */
	private static class SessionMapEntry {
		
		/**
		 * Session id.
		 */
		@SuppressWarnings("unused")
		String sid;
		
		/**
		 * Host value.
		 */
		String host;
		
		/**
		 * Time until when valid.
		 */
		long validUntil;
		
		/**
		 * 
		 */
		Map<String,String> map;
		
		/**
		 * Consctructs map entry with given session id, host, map for parameters and 
		 * time until this session is valid.
		 * 
		 * @param sid			session id
		 * @param host			host path value
		 * @param validUntil	time until this session is valid
		 * @param map			map for parameters
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}
	
	
	/**
	 * Main method to start the server.
	 * 
	 * @param args	command line arguments, unused in this program
	 */
	public static void main(String[] args) {
		new SmartHttpServer("./config/server.properties").start();
	}
}
