package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw06.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.LoginData;

/**
 * Servlet implementation class MainServletrepresenting used for showing main blog bage.
 * 
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LoginData ld = new LoginData();
		List<BlogUser> authors = DAOProvider.getDAO().getListOfUsers();
		
		request.setAttribute("zapis", ld);
		request.setAttribute("authors", authors);
		
		request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BlogUser user =  new BlogUser();
		List<BlogUser> authors = DAOProvider.getDAO().getListOfUsers();
		request.setAttribute("authors", authors);
		
		LoginData data = new LoginData();
		data.popuniIzHttpRequesta(request);
		data.validiraj();
		
		if (data.imaPogresaka()) {
			request.setAttribute("zapis", data);
			
			request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
		}else {
			user = DAOProvider.getDAO().getUserForNick(data.getNick());
			
			if (user == null) {
				data.setPassword("");
				data.setError("nick", "Nepostojeć nick!");
				request.setAttribute("zapis", data);
				request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
				
			}else if (!user.getPasswordHash().equals(Crypto.calcSha(data.getPassword()))){
				data.setError("password", "Invalid password!");
				request.setAttribute("zapis", data);
				request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
				
			}else {
				request.getSession().setAttribute("current.user.id", user.getId());
				request.getSession().setAttribute("current.user.fn", user.getFirstName());
				request.getSession().setAttribute("current.user.ln", user.getLastName());
				request.getSession().setAttribute("current.user.nick", user.getNick());
				request.getSession().setAttribute("current.user.email", user.getEmail());
				
				response.sendRedirect(request.getContextPath() + "/servleti/author/" + user.getNick());
			}
		}
		
		
	}

}
