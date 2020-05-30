package hr.fer.zemris.java.hw15.web.servlets;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet used for redirecting to blog servlet.
 * @author Martina
 *
 */
@WebServlet("/index.jsp")
public class BlogServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = "";
		String sadrzaj = "";
		
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
	
}