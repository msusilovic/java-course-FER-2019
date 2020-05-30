package hr.fer.zemris.java.hw14.servleti;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Servlet implementation class GlasanjeGlasajServlet used for registering votes to database.
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		DAO dao = DAOProvider.getDao();
		
		dao.updateOption(id);
		
		response.sendRedirect(request.getContextPath() + "/servleti/glasanje-rezultati");
	}

}
