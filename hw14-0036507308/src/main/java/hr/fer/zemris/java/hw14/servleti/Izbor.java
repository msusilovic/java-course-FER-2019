package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Servlet implementation class VoteServlet used to  obtain a list of defined polls
 * and render it to user as a list of clickable links.
 * 
 */
@WebServlet("/servleti/index.html")
public class Izbor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DAO dao = DAOProvider.getDao();
		
		List<Poll> polls = dao.getPolls();
		
		request.setAttribute("polls", polls);
		
		request.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(request, response);
	}

}
