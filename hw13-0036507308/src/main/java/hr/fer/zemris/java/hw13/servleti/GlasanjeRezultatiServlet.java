package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.beans.Band;
import hr.fer.zemris.java.hw13.beans.Votes;
import hr.fer.zemris.java.hw13.beans.Winners;

/**
 * Servlet implementation class GlasanjeRezultatiServlet used for displaying results of
 * voting.
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//getting data about all  bands
		String bandFile = request.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Map<Integer, Band> bands = getBandsMap(Paths.get(bandFile));
		
		//getting voting results
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<Votes> result = new ArrayList<>();
		int max = 0;
		
		for (String l : lines) {
			String[] parts = l.split("\t");
			int id = Integer.parseInt(parts[0]);
			int votes = Integer.parseInt(parts[1]);
			
			if (votes > max) max = votes;
			Band b = bands.get(id);
			
			result.add(new Votes(b.getName(), votes));
		}
		
		Collections.sort(result, (v1, v2) -> -v1.getCount().compareTo(v2.getCount()));

		request.setAttribute("results", result);
		
		List<Winners> winners = new ArrayList<>();
		for (String l : lines) {
			String[] parts = l.split("\t");
			int value = Integer.parseInt(parts[1]);
			if (value == max) {
				Band winner = bands.get(Integer.parseInt(parts[0]));
				winners.add(new Winners(winner.getName(), winner.getSong()));
			}
		}
		
		request.setAttribute("winners", winners);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(request, response);
	}

	
	/**
	 * Returns map of all defined bands.
	 * 
	 * @param bandPath		path of file where bands are defined
	 * @return				map of id values and bands
	 * @throws IOException	if reading from file fails
	 */
	public static Map<Integer, Band> getBandsMap(Path bandPath) throws IOException {

		if (!Files.exists(bandPath, LinkOption.NOFOLLOW_LINKS)) {
			Files.createFile(bandPath);
		}

		Map<Integer, Band> bands = new HashMap<>();
		List<String> bandLines = Files.readAllLines(bandPath);

		for (String l : bandLines) {
			String[] parts = l.split("\t");
			int id = Integer.parseInt(parts[0]);
			bands.put(id, new Band(id, parts[1], parts[2]));
		}

		return bands;
	}
}
