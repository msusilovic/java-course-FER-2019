package hr.fer.zemris.java.hw13.servleti;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet implementation class PieChartServlet used for displaying a pie chart containing 
 * data about OS usage.
 */
@WebServlet("/reportImage")
public class PieChartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("image/png");

		OutputStream outputStream = response.getOutputStream();

		JFreeChart chart = getChart();
		int width = 500;
		int height = 500;
		
		ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
	}

	/**
	 * Returns pie chart containing data about OS usage.
	 * @return pie chart about OS usage
	 */
	private JFreeChart getChart() {
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Windows", 90);
		dataset.setValue("Linux", 2);
		dataset.setValue("iOS", 8);

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("OS", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.BLUE);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}


}
