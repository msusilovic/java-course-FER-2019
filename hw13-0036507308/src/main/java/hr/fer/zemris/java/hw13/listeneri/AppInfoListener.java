package hr.fer.zemris.java.hw13.listeneri;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener used to update app duration info.
 * 
 * @author Martina
 *
 */
@WebListener
public class AppInfoListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().removeAttribute("startTime");
		
	}
}
