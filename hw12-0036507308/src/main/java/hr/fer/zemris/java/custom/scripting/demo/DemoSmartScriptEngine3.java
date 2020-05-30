package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class DemoSmartScriptEngine3 {
	
	public static void main(String[] args) {
		String docBody = null;
		try {
			docBody = Files.readString(Paths.get("test3.txt"));
		}catch(IOException o) {
			System.out.println("Error reading file");
			return;
		}
		
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
		cookies);
		new SmartScriptEngine(
		new SmartScriptParser(docBody).getDocumentNode(), rc
		).execute();
		
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
	}
}
