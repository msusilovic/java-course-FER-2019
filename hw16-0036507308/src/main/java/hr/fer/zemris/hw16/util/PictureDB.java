package hr.fer.zemris.hw16.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

/**
 * Class representing 
 * @author Martina
 *
 */
public class PictureDB {

	/**
	 * Set containing all tags.
	 */
	private static Set<String> tagSet = new TreeSet<>();
	
	/**
	 * List of all pictures.
	 */
	private static Picture[] pictures = {};
	
	
	/**
	 * Returns all pictures that have given tag.
	 * 
	 * @param tag	
	 */
	public static List<Picture> getPicturesForTag(String tag) {
		
		List<Picture> list = new ArrayList<>();
		
		for (int i = 0; i < pictures.length; i++) {
			if (pictures[i].getTags().contains(tag)) {
				list.add(pictures[i]);
			}
		}
		
		return list;
	}

	/**
	 * Returns a list of all tags.
	 * 
	 * @return	list of all tags
	 */
	public static List<String> getTags() {
		return new ArrayList<String>(tagSet);
	}
	
	/**
	 * Loads pictures info from file.
	 * @param servletContext 
	 * 
	 * @return	array of pictures
	 */
	public static void getPicturesFromFile(ServletContext servletContext) {
		
		List<String> list = new ArrayList<>();
		
		List<Picture> pictureList = new ArrayList<>();
		Picture[] picturesArray = {};
		try {
			list = Files.readAllLines(Paths.get(servletContext.getRealPath("/WEB-INF/opisnik.txt")));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < list.size(); i++) {
			String name = list.get(i);
			String description = list.get(++i);
			String tagString =list.get(++i);
			addToTagSet(tagString);
			Picture p = new Picture(name, description, tagString);
			pictureList.add(p);
		}
		pictures = pictureList.toArray(picturesArray);
	}


	/**
	 * Adds some picture's tags to set of tags.
	 * 
	 * @param tagString	string containing tags
	 */
	private static void addToTagSet(String tagString) {
		
		String[] parts = tagString.split(",");
		for (String s : parts) {
			tagSet.add(s.strip());
		}
	}
	
	public static Picture getPictureForName(String name) {
		for (Picture p : pictures) {
			if (p.getName().equals(name)) return p;
		}
		
		return null;
	}
}