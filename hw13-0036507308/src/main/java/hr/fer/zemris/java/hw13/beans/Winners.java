package hr.fer.zemris.java.hw13.beans;

/**
 * Class representing bands that won most votes. Contains band name and 
 * url to some song.
 * 
 * @author Martina
 *
 */
public class Winners {

	/**
	 * Name of band.
	 */
	private String name;
	
	/**
	 * Song url.
	 */
	private String song;
	
	/**
	 * Constructs Winners object.
	 * @param name	band name
	 * @param song	song url
	 */
	public Winners(String name, String song) {
		super();
		this.name = name;
		this.song = song;
	}
	
	/**
	 * Returns band name.
	 * @return band name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets band name to given value.
	 * @param name name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns song url.
	 * @return	song url
	 */
	public String getSong() {
		return song;
	}
	
	/**
	 * Sets song url.
	 * @param song	son url to set
	 */
	public void setSong(String song) {
		this.song = song;
	}
	
	
}
