package hr.fer.zemris.java.hw13.servleti;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw13.beans.Band;

/**
 * Servlet implementation class GlasanjeGrafikaServlet used for displaying pie chart
 * with data about voting results.
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String bandFile = request.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Map<Integer, Band> bandNames = GlasanjeRezultatiServlet.getBandsMap(Paths.get(bandFile));
		
		String resultFile = request.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		List<String> lines = Files.readAllLines(Paths.get(resultFile));
		
		Map<String, Integer> results = new HashMap<>();
		int sum = 0;
		
		for (String s : lines) {
			String[] parts = s.split("\t");
			try {
				String name = bandNames.get(Integer.parseInt(parts[0])).getName();
				results.put(name, Integer.parseInt(parts[1]));
				sum+=Integer.parseInt(parts[1]);
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
				
		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();

		JFreeChart chart = getChart(results, sum);
		int width = 500;
		int height = 500;
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
	}
	
	/**
	 * Returns chart for given voting results.
	 * @param results	results to be shown
	 * @param sum		sum of all votes
	 * @return			chart containing all data about voting
	 */
	private JFreeChart getChart(Map<String, Integer> results, int sum) {

		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Entry<String, Integer> e : results.entrySet()) {

			dataset.setValue(e.getKey(), e.getValue());
		}

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Rezultat glasovanja", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.BLUE);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}

}
