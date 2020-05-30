package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.CommentForm;
import hr.fer.zemris.java.hw15.model.EntryForm;

/**
 * Servlet implementation class AuthorsServlet.
 */
@WebServlet("/servleti/author/*")
public class AuthorsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String info = request.getPathInfo().substring(1);
		
		String[] parts = info.split("/");
		
		BlogUser author = DAOProvider.getDAO().getUserForNick(parts[0]);
		if (author == null) {
			request.setAttribute("message", "Nepostojeći autor!");
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
			return;
		}
		
		request.setAttribute("author", author);
		
		if (parts.length == 1) {
			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(author);
			request.setAttribute("entries", entries);
			request.getRequestDispatcher("/WEB-INF/pages/list.jsp").forward(request, response);
			return;
		}
		
		if (parts.length == 2) {
			String value = parts[1];
	
			try {
				long id = Long.parseLong(value);
				callForIdValue(request, response,  id, author);
				return;
			}catch (NumberFormatException e) {
			}

			if (parts[1].equals("new")) {
				EntryForm form = new EntryForm();
				request.setAttribute("form", form);
				request.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(request, response);
				return;
			}
			else if(parts[1].equals("edit")) {
				
				EntryForm form = new EntryForm();
				
				long id = Long.parseLong(request.getParameter("id"));
				BlogEntry e = DAOProvider.getDAO().getBlogEntry(id);
				
				form.setText(e.getText());
				form.setTitle(e.getTitle());
				
				request.setAttribute("form", form);
				request.setAttribute("id", id);
				request.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(request, response);
				return;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String info = request.getPathInfo().substring(1);

		String[] parts = info.split("/");

		BlogUser author = DAOProvider.getDAO().getUserForNick(parts[0]);
		
		if (author == null) {
			request.setAttribute("message", "Nepostojeći autor!");
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
			return;
		}

		request.setAttribute("author", author);
		
		if (parts.length == 1) {
			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(author);
			request.setAttribute("entries", entries);
			request.getRequestDispatcher("/WEB-INF/pages/list.jsp").forward(request, response);
			return;
		}
		
		if (parts.length == 2) {
			String value = parts[1];
	
			try {
				long id = Long.parseLong(value);
				CommentForm form = new CommentForm();
				form.fillFromHttpRequest(request);
				
				if (form.getEmail().equals("") && request.getSession().getAttribute("current.user.id") != null){
					form.setEmail((String)request.getSession().getAttribute("current.user.email"));
				}
				
				form.validate();
				
				if (form.hasErrors()) {
					request.setAttribute("form", form);
					request.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(request, response);
					return;
				}

				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				
				BlogComment comment = new BlogComment();
				comment.setBlogEntry(entry);
				comment.setMessage(form.getMessage());
				comment.setPostedOn(new Date());
				comment.setUsersEMail(form.getEmail());
				
				DAOProvider.getDAO().addComment(comment);
				entry.getComments().add(comment);
				
				response.sendRedirect(request.getContextPath() + "/servleti/author" + "/" + author.getNick() + "/"+ id);
				return;
			}catch (NumberFormatException e) {
			}
			
			if (parts[1].equals("new")) {
				EntryForm form = new EntryForm();
				form.fillFromHttpRequest(request);
				form.validate();
				
				if (form.hasErrors()) {
					request.setAttribute("form", form);
					request.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(request, response);
					return;
				}
			
				BlogEntry entry = new BlogEntry();
				entry.setText(form.getText());
				entry.setTitle(form.getTitle());
				entry.setCreatedAt(new Date());
				entry.setCreator(author);
				DAOProvider.getDAO().addEntry(entry);
				
				request.setAttribute("form", form);
				response.sendRedirect(request.getContextPath() + "/servleti/author" + "/" + author.getNick());
				
			}
			else if(parts[1].equals("edit")) {
				long id = Long.parseLong(request.getParameter("id"));
				
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

				if (entry == null) {
					request.setAttribute("message", "Nepostojeći zapis!");
					request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
					return;
				}

				EntryForm form = new EntryForm();
				form.setText(request.getParameter("text"));
				form.setTitle(request.getParameter("title"));
				
				if (form.hasErrors()) {
					request.setAttribute("form", form);
					request.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(request, response);
					return;
				}
				
				DAOProvider.getDAO().updateEntry(form, entry);
				
				request.setAttribute("form", form);
				response.sendRedirect(request.getContextPath() + "/servleti/author" + "/" + author.getNick());
				return;
			}else {

			}
		}
		
	}

	private void callForIdValue(HttpServletRequest request, HttpServletResponse response, long id, BlogUser author)
			throws ServletException, IOException {
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		if (entry == null) {
			request.setAttribute("message", "Nepostojeći zapis!");
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
			return;
		}
		
		long eId = entry.getId();
		request.setAttribute("blogEntry", entry);
		request.setAttribute("id", eId);
		request.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(request, response);
	}
}
