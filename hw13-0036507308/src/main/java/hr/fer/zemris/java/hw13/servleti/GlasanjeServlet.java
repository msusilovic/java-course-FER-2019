package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.beans.Band;

/**
 * Servlet implementation class GlasanjeServlet used for loading data for band voting.
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		
		List<Band> bands = new ArrayList<>();
		
		for (String line : lines) {
			String[] parts = line.split("\t");
			
			bands.add(new Band(Integer.parseInt(parts[0]), parts[1], parts[2]));
		}
		
		Collections.sort(bands, new Comparator<Band>() {
			@Override
			public int compare(Band o1, Band o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		request.setAttribute("bands", bands);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(request, response);
	}


}
