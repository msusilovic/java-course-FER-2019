package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw06.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.RegisterData;

/**
 * Servlet implementation class RegisterServlet used for registering new users.
 * 
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RegisterData data = new RegisterData();
		
		request.setAttribute("zapis", data);
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BlogUser user =  new BlogUser();
		
		RegisterData data = new RegisterData();
		data.fillFromHttpRequest(request);
		data.validate();
		
		if (data.hasErrors()) {
			request.setAttribute("zapis", data);
			request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
		}else {
			user = DAOProvider.getDAO().getUserForNick(data.getNick());
			
			if (user!=null) {
				data.setError("nick", "Korisničko ime se već koristi.");
				data.setNick("");
				data.setPassword("");
				request.setAttribute("zapis", data);
				request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
			}else {
				BlogUser newUser =  new BlogUser();
				newUser.setEmail(data.getEmail());
				newUser.setFirstName(data.getFirstName());
				newUser.setLastName(data.getLastName());
				newUser.setNick(data.getNick());
				newUser.setPasswordHash(Crypto.calcSha(data.getPassword()));
				
				DAOProvider.getDAO().addNewUser(newUser);
				
				response.sendRedirect(request.getContextPath() + "/servleti/main");
			}
		}
	}

}
