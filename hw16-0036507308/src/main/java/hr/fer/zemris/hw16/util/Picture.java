package hr.fer.zemris.hw16.util;

import java.util.HashSet;

/**
 * Class representing some picture.
 * 
 * @author Martina
 *
 */
public class Picture {

	/**
	 * Picture's description.
	 */
	private String description;
	
	/**
	 * Tags for this picture.
	 */
	private HashSet<String> tags = new HashSet<>();
	
	/**
	 * Picture name.
	 */
	private String name;	

	/**
	 * Constructs Picture.
	 * 
	 * @param name			picture name
	 * @param description	picture description
	 * @param tagsString	string containing tags split by ','
	 */
	public Picture(String name, String description, String tagsString) {
		this.description = description;
		this.name = name;
		
		String[] tagArray = tagsString.split(",");
		
		for (String s : tagArray) {
			tags.add(s.strip());
		}
	}

	/**
	 * Returns description.
	 * 
	 * @return	description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description.
	 * 
	 * @param description	description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns all the tags for this picture.
	 * 
	 * @return	tags
	 */
	public HashSet<String> getTags() {
		return tags;
	}
	
	/**
	 * Sets tags.
	 * 
	 * @param tags tags to set
	 */
	public void setTags(HashSet<String> tags) {
		this.tags = tags;
	}

	/**
	 * Returns name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 * 
	 * @param name name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	
}
