package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class GlasanjeRezultatiServlet used for displaying results of
 * voting.
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long id = (long) getServletContext().getAttribute("pollId");
		
		DAO dao = DAOProvider.getDao();
		
		List<PollOptions> options = dao.getPollOptions(id);
		request.setAttribute("options", options);
		
		int max = 0;
		for (PollOptions o : options) {
			if (o.getCount() > max) {
				max = o.getCount();
			}
		}
		
		List<PollOptions> winners = new ArrayList<>();
		
		for (PollOptions o : options) {
			if (o.getCount() == max) {
				winners.add(o);
			}
		}
		
		request.setAttribute("winners", winners);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(request, response);
	}

}
