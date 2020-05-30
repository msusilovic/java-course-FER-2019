package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class modeling some request's context.
 * @author Martina
 *
 */
public class RequestContext {
	
	
	public static class RCCookie {
		
		/**
		 * Name of this cookie.
		 */
		private String name;
		
		/**
		 * Value of this cookie.
		 */
		private String value;
		
		/**
		 * Domain value.
		 */
		private String domain;
		
		/**
		 * Path for this cookie.
		 */
		private String path;

		/**
		 * Maximum age value.
		 */
		private Integer maxAge;
		
		/**
		 * 
		 */
		boolean httpOnly;
		/**
		 * Constructor method. 
		 * 
		 * @param name		name for this cookie
		 * @param value		value for this cookie
		 * @param domain	domain for this cookie
		 * @param path		path for this cookie
		 * @param maxAge	max age for this cookie
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = false;
		}

		/**
		 * Returns name.
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns value.
		 * 
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns domain.
		 * 
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Retruns path.
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * Returns max age.
		 * 
		 * @return maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
		public void setHttpOnly() {
			this.httpOnly = true;
		}
		/**
		 * Returns boolean determining if cookie is http only cookie.
		 * @return	<code>true</code> if cookie is http only, <code>false</code> otherwise
		 */
		public boolean getHttpOnly() {
			return httpOnly;
		}
	}
	
	/**
	 * Output stream used for writing.
	 */
	private OutputStream outputStream;
	
	/**
	 * Charset currently used.
	 */
	private Charset charset;
	
	/**
	 * Endocing to be used in header.
	 * <p>Can only be modified before header is generated.
	 */
	private String encoding = "UTF-8";
	
	/**
	 * Status code to be used in a header.
	 * <p>Can only be modified before header is generated.
	 */
	private int statusCode = 200;
	
	/**
	 * Status text to be used in header.
	 * <p>Can only be modified before header is generated.
	 */
	private String statusText = "OK";
	
	/**
	 * Mime type to be used in a header.
	 * <p>Can only be modified before header is generated.
	 */
	private String mimeType = "text/html";
	
	/**
	 * Content length value to be used in a header.
	 * <p>Can only be modified before header is generated.
	 */
	private Long contentLength = null;
	
	/**
	 * IDispatcher object used for dispatching requests.
	 */
	private IDispatcher dispatcher;
	/**
	 * Map of parameter names and values.
	 */
	private Map<String,String> parameters;
	
	/**
	 * Map of temporary parameter names and values.
	 */
	private Map<String,String> temporaryParameters = new HashMap<>();
	
	/**
	 * Map of persistent parameter names and values.
	 */
	private  Map<String,String> persistentParameters;
	
	/**
	 * List of output cookie values.
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Value determining whether a header was already generated or not.
	 * <p>False by default.
	 */
	private  boolean headerGenerated = false;
	
	/**
	 * 
	 */
	private String sid;
	
	/**
	 * Constructs RequestCOntexts with given output stream and collections of parameters,
	 * persistent parameters and output cookies.
	 * 
	 * @param outputStream			stream for this object; must not be <code>null</code>
	 * @param parameters			map of parameters; if <code>null</code>, treated as 
	 * 								an empty collection
	 * @param persistentParameters	map of  persistent parameters; if <code>null</code>,
	 *  							treated as an empty collection
	 * @param outputCookies			list of output cookies; if <code>null</code>, treated
	 * 								as an empty collection
	 */
	public RequestContext(
			OutputStream outputStream, 
			Map<String,String> parameters, 
			Map<String,String> persistentParameters,
			List<RCCookie> outputCookies) { 
		
		Objects.requireNonNull(outputStream);
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new LinkedList<>() : outputCookies;
	}
	
	/**
	 * Constructs RequestCOntexts with given output stream and collections of parameters,
	 * persistent parameters, temporay parameters, output cookies and a dispatcher object.
	 * 
	 * @param outputStream			stream for this object; must not be <code>null</code>
	 * @param parameters			map of parameters; if <code>null</code>, treated as 
	 * 								an empty collection
	 * @param persistentParameters	map of  persistent parameters; if <code>null</code>,
	 *  							treated as an empty collection
	 * @param outputCookies			list of output cookies; if <code>null</code>, treated
	 * 								as an empty collection
	 * @param temporaryParameters	map of  temporary parameters; if <code>null</code>,
	 *  							treated as an empty collection
	 * @param dispatcher			dispatcher used for dispatching requests
	 * @param sid					session id
	 */
	public RequestContext(
			OutputStream outputStream, 
			Map<String,String> parameters, 
			Map<String,String> persistentParameters,
			List<RCCookie> outputCookies,
			Map<String,String> temporaryParameters,
			IDispatcher dispatcher,
			String sid) {
		
		this(outputStream, parameters, persistentParameters, outputCookies);
		
		Objects.requireNonNull(dispatcher);
		this.dispatcher = dispatcher;
		this.sid = sid;
		
		if (temporaryParameters != null) {
			this.temporaryParameters = temporaryParameters;
		}
	}

	/**
	 * Retreives parameter value mapped with given name.
	 * <p>Returns <code>null</code> if no association exists.
	 * 
	 * @param name	name to return the value for
	 * @return		value associated with given name
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Retrieves names of all parameters in persistent parameters map as a read-only set.
	 * 
	 * @return	 names of all persistent parameters
	 */
	public Set<String> getPersistentParameterNames(){
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Stores a value to map of persistent parameters.
	 * 
	 * @param name	name for a new mapping
	 * @param value value for a new mapping
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * Removes a value from map of persistent parameters.
	 * 
	 * @param name	name to find the value to remove
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Retreives temporary parameter value mapped with given name.
	 * <p>Returns <code>null</code> if no association exists.
	 * 
	 * @param name	name to return the value for
	 * @return		value associated with given name
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Retrieves names of all parameters in temporary parameters map as a read-only set.
	 * 
	 * @return   names of all temporary parameters
	 */
	public Set<String> getTemporaryParameterNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Retreives persistent parameter value mapped with given name.
	 * <p>Returns <code>null</code> if no association exists.
	 * 
	 * @param name	name to return the value for
	 * @return		value associated with given name
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Retrieves an identifier which is unique for current user session.
	 * <p>Implemented here to return an empty string.
	 * 
	 * @return identifier unique for current user session
	 */
	public String getSessionID() {
		return "";
	}
	
	/**
	 * Stores a value to map of temporary parameters.
	 * 
	 * @param name	name for a new mapping
	 * @param value value for a new mapping
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes a value from map of temporary parameters.
	 * 
	 * @param name	name to find the value to remove
	 * @return		current RequestContext object
	 * @throws IOException	if writing to output stream fails
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Adds a RCCookie to list of cookies.
	 * 
	 * @param cookie	RCCookie to add to list
	 */
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(cookie);
	}
	
	public RequestContext write(byte[] data) throws IOException {
		
		if (!headerGenerated) {
			writeHeader();
		}
		
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes data byte array to output stream with given offset and length.
	 * 
	 * @param data		data to be written
	 * @param offset	offset to use when writing
	 * @param len		length of data to be written
	 * @return			current RequestContext object
	 * @throws IOException	if writing to output stream fails
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		
		if (!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}
	
	/**
	 * Extracts bytes from given String value using current charset and writes them to 
	 * output stream.
	 * 
	 * @param text	String to be written
	 * @return		current RequestContext object
	 * @throws IOException	if writing to output stream fails
	 */
	public RequestContext write(String text) throws IOException {
		
		if (!headerGenerated) {
			writeHeader();
		}
		byte[] data = text.getBytes(charset);
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Writes header to output stream.
	 * <p>This method can only be called once, the first time someone tries to write to 
	 * output stream.
	 * 
	 * @throws IOException if writing can't be done
	 */
	private void writeHeader() throws IOException {
		
		charset = Charset.forName(encoding);
		
		StringBuilder sb = new StringBuilder();
		
		//first line:
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		
		//second line:
		sb.append("Content-Type: " + mimeType);
		if(mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		
		//third line:
		if (contentLength != null) {
			sb.append("Content-Length:" + contentLength);
			sb.append("\r\n");
		}
		
		//lines for all cookies:
		for (RCCookie cookie : outputCookies) {
			sb.append("Set-Cookie: " + cookie.name + "=\"" + cookie.value + "\"");
			if(cookie.domain != null) {
				sb.append("; Domain=" + cookie.domain);
			}
			if(cookie.path != null) {
				sb.append("; Path=" + cookie.path);
			}
			if (cookie.maxAge != null) {
				sb.append("; Max-Age=" + cookie.maxAge);
			}
			if (cookie.getHttpOnly()) {
				sb.append("; HttpOnly");
			}
			sb.append("\r\n");
		}
		sb.append("\r\n");
		
		//write heading to output stream:
		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
	
		headerGenerated = true;
	}

	/**
	 * Sets encoding  to given encoding value.
	 * 
	 * @param encoding	encoding to set
	 */
	public void setEncoding(String encoding) {
		checkHeaderGenerated();
		this.encoding = encoding;
	}

	/**
	 * Sets status code.
	 * 
	 * @param statusCode status code to set
	 */
	public void setStatusCode(int statusCode) {
		checkHeaderGenerated();
		this.statusCode = statusCode;
	}

	/**
	 * Sets status text.
	 * 
	 * @param statusText status text to set
	 */
	public void setStatusText(String statusText) {
		checkHeaderGenerated();
		this.statusText = statusText;
	}

	/**
	 * Sets mime type.
	 * 
	 * @param mimeType mime type to set
	 */
	public void setMimeType(String mimeType) {
		checkHeaderGenerated();
		this.mimeType = mimeType;
	}

	/**
	 * Sets content length value.
	 * 
	 * @param contentLength	content length value to set
	 */
	public void setContentLength(Long contentLength) {
		checkHeaderGenerated();
		this.contentLength = contentLength;
	}

	/**
	 * Checks if header had already been generated and if so, throws an exception.
	 * @throws RuntimeException if header had alerady been modified
	 */
	private void checkHeaderGenerated() {
		if (headerGenerated) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Returns map with temporary parameters.
	 * 
	 * @return	temporary parameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Sets map of temporary parameters to given map.
	 * 
	 * @param temporaryParameters	temporary parameters to set
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Returns map with persistent parameters.
	 * 
	 * @return	persistent parameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Sets map of persistent parameters to given map.
	 * 
	 * @param persistentParameters	persistent parameters to set
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}
	

	/**
	 * Retruns map with parameters.
	 * 
	 * @return	parameters
	 */
	public Map<String, String> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}
	
	
	/**
	 * Returns dispatcher.
	 * 
	 * @return	dispatcher
	 */
	public IDispatcher getDispatcher() {
		return this.dispatcher;
	}
	
	/**
	 * 
	 * Returns current session id.
	 * 
	 * @return	session id
	 */
	public String getSessionId() {
		return sid;
	}
	
}
