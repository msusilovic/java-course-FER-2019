package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class used to initialize connection-pool and destroy it.
 * 
 * @author Martina
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		String propertiesPath =	sce.getServletContext().getRealPath(
													"/WEB-INF/dbsettings.properties");
													
		Properties konfiguracija = new Properties();
		try {
			konfiguracija.load(new FileInputStream(propertiesPath));
		} catch (IOException e2) {

			e2.printStackTrace();
		}
		
		String connectionURL = "jdbc:derby://" + konfiguracija.getProperty("host") + ":" 
								+ konfiguracija.getProperty("port") + "/" + konfiguracija.getProperty("name");

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	
		
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(konfiguracija.getProperty("user"));
		cpds.setPassword(konfiguracija.getProperty("password"));

		Connection c = null;

		try {
			c = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		createTables(c);
		
		try {
			if (isTableEmpty("Polls", c)) {
				initPolls(sce, c);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * Sets initial values to PollOptions table in database.
	 * 
	 * @param sce		context event
	 * @param c			connection	
	 * @param defPath1	path to file containing options data
	 * @param pollId	id of a poll there options refer to
	 */
	private void initPollOptions(ServletContextEvent sce, Connection c, String defPath1, long pollId) {
		
		List<String> values = new ArrayList<>();

		try {
			values = Files.readAllLines(Paths.get(defPath1));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PreparedStatement p = null;
		try {
			p = c.prepareStatement("INSERT into PollOptions ("
									+ "optionTitle, optionLink, pollId, votesCount) "
									+ "values (?, ?, ?, ?)");
			
			for (String b : values) {
				String[] parts = b.split("\t");
				p.setString(1, parts[0]);
				p.setString(2, parts[1]);
				p.setLong(3, pollId);
				p.setInt(4, 0);
				
				p.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used to initialize polls. Creates two initial polls to be put in this table.
	 * 
	 * @param sce servlet context event
	 * @param c	  connection
	 */
	private void initPolls(ServletContextEvent sce, Connection c) {
		PreparedStatement p = null;
		
		try {
			p = c.prepareStatement("INSERT into Polls (title, message) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			p.setString(1, "Glasovanje sa omiljeni bend:");
			p.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasovali!");
			p.executeUpdate();

			ResultSet rs = p.getGeneratedKeys();
			String defPath1 =	sce.getServletContext().getRealPath("/WEB-INF/bendovi-definicija.txt");
			rs.next();
			long pollId = rs.getLong(1);
			initPollOptions(sce, c, defPath1, pollId);
			
			p = c.prepareStatement("INSERT into Polls (title, message) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			p.setString(1, "Glasovanje za omiljenu Domaćicu!");
			p.setString(2, "Od sljedećih vrsta Domaćice, koja vam je najdraža? Kliknite na link kako biste glasovali!");
			p.executeUpdate();
			
			rs = p.getGeneratedKeys();
			rs.next();
			String defPath2 =	sce.getServletContext().getRealPath("/WEB-INF/domacice-definicija.txt");
			initPollOptions(sce, c, defPath2, rs.getLong(1));
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if some table from database is empty.
	 * 
	 * @param name	name of table
	 * @param c		connection
	 * @return		<code>true</code> if table is empty,
	 * 				<code>false</code> otherwise
	 * @throws SQLException	if some problem occurs while reading data from database
	 */
	private boolean isTableEmpty(String name, Connection c) throws SQLException {
		
		PreparedStatement s = c.prepareStatement("Select * From " + name);

		ResultSet set = s.executeQuery();
		
		if (!set.next() ) {
		    return true;
		} 
		return false;
	}

	/**
	 * Method used to try to create Poll and PollOptions tables in database if they don't exist already.
	 * 
	 * @param c	connection
	 */
	private void createTables(Connection c) {
		PreparedStatement pst = null;
		
		try {
			pst = c.prepareStatement("CREATE TABLE Polls " + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
					+ " title VARCHAR(150) NOT NULL, " + " message CLOB(2048) NOT NULL " + ")");
			pst.execute();
		} catch (SQLException ignorable) {
		}

		try {
			pst = c.prepareStatement("CREATE TABLE PollOptions "
					+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " + " optionTitle VARCHAR(100) NOT NULL, "
					+ " optionLink VARCHAR(150) NOT NULL, " + " pollID BIGINT, " + " votesCount BIGINT, "
					+ " FOREIGN KEY (pollID) REFERENCES Polls(id) " + ")");
			pst.execute();
		} catch (SQLException ignorable) {
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce
									 .getServletContext()
									 .getAttribute("hr.fer.zemris.dbpool");
		
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}