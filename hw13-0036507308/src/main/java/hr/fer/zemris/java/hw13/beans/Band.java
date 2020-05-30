package hr.fer.zemris.java.hw13.beans;

/**
 * Java-bean class representing some band with it's id, name and some song.
 * 
 * @author Martina
 *
 */
public class Band {
	
	/**
	 * ID value
	 */
	Integer id;
	/**
	 * Band name
	 */
	private String name;
	/**
	 * Link to some song
	 */
	private String song;
	
	/**
	 * Constructs one Band
	 * @param id	id value
	 * @param name	band name
	 * @param song	song link
	 */
	public Band(int id, String name, String song) {
		this.id = id;
		this.name = name;
		this.song = song;
	}
	
	/**
	 * Returns id value.
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Sets id value.
	 * @param id	id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns name.
	 * @return	name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets name.
	 * @param name	name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns song url.
	 * 
	 * @return song url
	 */
	public String getSong() {
		return song;
	}
	
	/**
	 * Sets song url
	 * @param song url to some song
	 */
	public void setSong(String song) {
		this.song = song;
	}
	
}
