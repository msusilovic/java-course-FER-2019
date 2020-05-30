package hr.fer.zemris.java.hw13.servleti;

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

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import hr.fer.zemris.java.hw13.beans.Band;

/**
 * Servlet implementation class GlasanjeXlsServlet used for creating .xls file containing all 
 * data about band voting.
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {
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
		
		for (String s : lines) {
			String[] parts = s.split("\t");
			try {
				String name = bandNames.get(Integer.parseInt(parts[0])).getName();
				results.put(name, Integer.parseInt(parts[1]));
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		try (HSSFWorkbook hwb = new HSSFWorkbook()) {
			HSSFSheet sheet = hwb.createSheet("rezultati");

			int rowcount = 0;
			
			Row row = sheet.createRow(rowcount++);
			row.createCell(0).setCellValue("Ime");
			row.createCell(1).setCellValue("Broj glasova");
			for (Entry<String, Integer> e : results.entrySet()) {
				Row row1 = sheet.createRow(rowcount++);
				row1.createCell(0).setCellValue(e.getKey());
				row1.createCell(1).setCellValue(e.getValue());
			}

			response.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
			OutputStream outputStream = response.getOutputStream();

			hwb.write(outputStream);
		}
	}
}
