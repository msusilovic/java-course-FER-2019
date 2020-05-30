package hr.fer.zemris.hw16.servlets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.hw16.util.Picture;
import hr.fer.zemris.hw16.util.PictureDB;

/**
 * Servlet implementation class ThumbnailServlet used for displaying thumbnails for requested tag.
 */
@WebServlet("/servleti/thumbnail/*")
public class ThumbnailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getPathInfo().substring(1);

		checkDirectory(request);

		Picture p = PictureDB.getPictureForName(name);
		Path thumbPath = Paths.get(request.getServletContext().getRealPath("/WEB-INF/thumbnails/") + p.getName());

		if (!Files.exists(thumbPath)) {
			Path picPath = Paths.get(request.getServletContext().getRealPath("/WEB-INF/slike/") + p.getName());

			BufferedImage img = ImageIO.read(picPath.toFile());
            BufferedImage thumb = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
            thumb.createGraphics().drawImage(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH), 0, 0, null);
            
            ImageIO.write(thumb, "jpg", thumbPath.toFile());
		}

		response.setContentType("image/jpg");
		
		OutputStream os = response.getOutputStream();
		ImageIO.write(ImageIO.read(thumbPath.toFile()), "jpg", os);

	}

	/**
	 * Checks if thumbnails directory already exists.
	 * @param request 
	 */
	private void checkDirectory(HttpServletRequest request) {
		Path path = Paths.get(request.getServletContext().getRealPath("/WEB-INF/thumbnails"));

		if (!path.toFile().exists()) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
