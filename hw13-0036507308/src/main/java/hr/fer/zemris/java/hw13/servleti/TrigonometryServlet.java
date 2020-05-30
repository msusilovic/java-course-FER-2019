package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet used for calculating sine and cosine values for given range of angles.
 * 
 * @author Martina
 *
 */
@WebServlet (name = "t", urlPatterns = "/trigonometric")
public class TrigonometryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int a;
		int b;
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
		}catch (NullPointerException | NumberFormatException e) {
			a = 0;
		}
		try{
			b = Integer.parseInt(req.getParameter("b"));
		}catch (NullPointerException | NumberFormatException e) {
			b = 360;
		}
		if (b > a + 720) {
			b = a + 720;
		}
		
		Map<Integer, Double> sinValues = new LinkedHashMap<>();
		Map<Integer, Double> cosValues = new LinkedHashMap<>();
		
		for (int i = a; i <= b; i ++) {
			sinValues.put(i, Math.sin(Math.toRadians(i)));
			cosValues.put(i, Math.cos(Math.toRadians(i)));
		}
		
		req.setAttribute("sinValues", sinValues);
		req.setAttribute("cosValues", cosValues);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}
