package hr.fer.zemris.hw16.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/servleti/image/*")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getPathInfo().substring(1);

		Path path = Paths.get(request.getServletContext().getRealPath("/WEB-INF/slike/") + name);

		BufferedImage img = ImageIO.read(path.toFile());

		 OutputStream out = response.getOutputStream();
		if (name.endsWith("jpg")) {
			response.setContentType("image/jpg");
			ImageIO.write(img, "jpg", out);
		}else {
			response.setContentType("image/png");
			ImageIO.write(img, "png", out);
		}
       
        out.close();
	}

}
