package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for setting color.
 * 
 * @author Martina
 *
 */
public class SetColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String color = req.getParameter("pickedBgCol");
		req.getSession().setAttribute("pickedBgCol", color);
		req.getRequestDispatcher("colors.jsp").forward(req, resp);
	}
}
