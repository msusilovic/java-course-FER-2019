package hr.fer.zemris.java.webserver.workers;

import java.util.Map.Entry;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker used for simply iterating collection of parameters and print them
 * in a form of a table.
 *
 * @author Martina
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		context.setMimeType("text/html");
		try {
			context.write("<html>");
			context.write("<head>");
			context.write("<title>Popis parametara</title>");
			context.write("</head>");
			context.write("<body>");
			context.write("<table>");
			context.write("<thead>");
			context.write("<tr><th>Ime</th><th>Parametar</th></tr>");
			context.write("</thead>");
			context.write("<tbody>");
			
			for (Entry<String, String> entry : context.getParameters().entrySet()) {
				context.write("<tr><td>"+ entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>");
			}
			context.write("</tbody>");
			context.write("</table>");
			context.write("</body>");
			context.write("</html>");

			
		}catch (Exception e) {

		}
	}

	
}
