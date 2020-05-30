package hr.fer.zemris.java.hw14.servleti;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOptions;

/**
 * Servlet implementation class GlasanjeGrafikaServlet used for displaying pie chart
 * with data about voting results.
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DAO dao = DAOProvider.getDao();
		
		List<PollOptions> results = dao.getPollOptions((long) getServletContext().getAttribute("pollId"));
		int sum = 0;
		
		for (PollOptions o : results) {
			sum+= o.getCount();
		}
		
		OutputStream os = response.getOutputStream();
		
		JFreeChart chart = getChart(results, sum);
		int width = 500;
		int height = 500;
		ChartUtilities.writeChartAsPNG(os, chart, width, height);
	}
	
	/**
	 * Returns chart for given voting results.
	 * @param results	results to be shown
	 * @param sum		sum of all votes
	 * @return			chart containing all data about voting
	 */
	private JFreeChart getChart(List<PollOptions> results, int sum) {

		DefaultPieDataset dataset = new DefaultPieDataset();

		for (PollOptions e : results) {

			dataset.setValue(e.getOptionTitle(), e.getCount());
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
