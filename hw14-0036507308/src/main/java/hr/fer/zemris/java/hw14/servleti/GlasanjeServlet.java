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
import hr.fer.zemris.java.hw14.model.PollOptions;

/**
 * Servlet implementation class VoteServlet
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long pollId = Long.parseLong(request.getParameter("pollID"));
		
		DAO dao = DAOProvider.getDao();
		
		List<PollOptions> options = dao.getPollOptions(pollId);
		
		request.setAttribute("poll", dao.getPoll(pollId));
		getServletContext().setAttribute("pollId", dao.getPoll(pollId).getId());
		request.setAttribute("options", options);
		
		request.getRequestDispatcher("/WEB-INF/pages/voteIndex.jsp").forward(request, response);
	}
	
	
}
