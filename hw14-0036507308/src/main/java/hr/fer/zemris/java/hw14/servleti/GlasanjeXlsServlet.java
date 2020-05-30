package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOptions;

/**
 * Servlet implementation class GlasanjeXlsServlet used for creating .xls file containing all 
 * data about band voting.
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DAO dao = DAOProvider.getDao();
		
		List<PollOptions> results = dao.getPollOptions((long) getServletContext().getAttribute("pollId"));
		
		try (HSSFWorkbook hwb = new HSSFWorkbook()) {
			HSSFSheet sheet = hwb.createSheet("rezultati");

			int rowcount = 0;
			
			Row row = sheet.createRow(rowcount++);
			row.createCell(0).setCellValue("Ime");
			row.createCell(1).setCellValue("Broj glasova");
			for (PollOptions e : results) {
				Row row1 = sheet.createRow(rowcount++);
				row1.createCell(0).setCellValue(e.getOptionTitle());
				row1.createCell(1).setCellValue(e.getCount());
			}

			response.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
			OutputStream outputStream = response.getOutputStream();

			hwb.write(outputStream);
		}
	}
}
