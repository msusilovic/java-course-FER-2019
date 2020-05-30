package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

/**
 * Servlet implementation class PowerServlet.
 */
@WebServlet("/powers")
public class PowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int a;
		int b;
		int n;
		
		try {
			a = Integer.parseInt(request.getParameter("a"));
		}catch (Exception e){
			message(request, response, "Invalid input for a parameter.");
			return;
		}
		
		try {
			b = Integer.parseInt(request.getParameter("b"));
		}catch (Exception e){
			message(request, response, "Invalid input for b parameter.");
			return;
		}
		
		try {
			n = Integer.parseInt(request.getParameter("n"));
		}catch (Exception e){
			message(request, response, "Invalid input for n parameter.");
			return;
		}
		
		if (a > 100 || a < -100 || b > 100 || b < -100 || n > 5 || n < 1) {
			message(request, response, "Value out of range.");
		}
		
		try(HSSFWorkbook hwb = new HSSFWorkbook()){
			for (int i = 0; i < n; i ++) {
				HSSFSheet sheet =  hwb.createSheet("sheet_" + Integer.toString((i+1)));
				
				int rowcount = 0;
				Row row0 = sheet.createRow(rowcount++);
				row0.createCell(0).setCellValue("Vrijednost");
				row0.createCell(1).setCellValue("Potencija");
				for (int j = a; j <= b; j++) {
					Row row = sheet.createRow(rowcount++);
					row.createCell(0).setCellValue(j);
					row.createCell(1).setCellValue(Math.pow(j, i+1));
				}
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
			OutputStream outputStream = response.getOutputStream();
			
			hwb.write(outputStream);

		}
	}

	/**
	 * Method used for sending message to used in case of error.
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @throws ServletException
	 * @throws IOException
	 */
	private void message(HttpServletRequest request, HttpServletResponse response, String message) 
			throws ServletException, IOException {
		request.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
	}

}
