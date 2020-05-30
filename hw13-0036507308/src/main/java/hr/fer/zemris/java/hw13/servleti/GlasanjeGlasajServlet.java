package hr.fer.zemris.java.hw13.servleti;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GlasanjeGlasajServlet used for registering votes.
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(fileName);
		if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			Files.createFile(path);
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		Map<Integer, Integer> helpMap = new TreeMap<>();
		boolean found = false;
		
		List<String> lines = Files.readAllLines(path);
		for (String l :lines) {
			String[] parts = l.split("\t");
			int currentId = Integer.parseInt(parts[0]);
			if (currentId == id) {
				found = true;
			}
			int value = Integer.parseInt(parts[1]);
			helpMap.put(currentId, id == currentId ? Integer.parseInt(parts[1])+1 : value);
			
			if (!found) {
				helpMap.put(id, 1);
			}
		}

		try(BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
		
			for (Entry<Integer, Integer> e : helpMap.entrySet()) {
				String value = e.getKey() + "\t" + e.getValue() + "\n";
				bw.write(value);
			}
		}
		response.sendRedirect(request.getContextPath() + "/glasanje-rezultati");
	}

}
