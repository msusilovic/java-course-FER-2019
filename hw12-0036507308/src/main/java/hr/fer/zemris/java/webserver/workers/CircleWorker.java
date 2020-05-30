package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;


/**
 * Worker used for displaying an image of circle to user.
 * 
 * @author Martina
 *
 */
public class CircleWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		
		context.setMimeType("image/png");
		Graphics2D g2d = bim.createGraphics();
		
		g2d.setColor(Color.MAGENTA);
		g2d.fillOval(0, 0, 200, 200);
		g2d.dispose();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		ImageIO.write(bim, "png", bos);
		byte[] data = bos.toByteArray();
		context.write(data);
		
	}
	
	
}
