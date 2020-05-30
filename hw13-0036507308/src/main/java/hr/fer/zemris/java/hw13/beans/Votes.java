package hr.fer.zemris.java.hw13.beans;

/**
 * Class contaiing info about band name and number of votes that band received.
 * 
 * @author Martina
 *
 */
public class Votes {

	/**
	 * Name of the band.
	 */
	private String bandName;
	
	/**
	 * NUmber of votes.
	 */
	private Integer count;
	
	/**
	 * Constructs one Votes object.
	 * 
	 * @param bandName	band name to set
	 * @param count		number of votes to set
	 */
	public Votes(String bandName, Integer count) {
		super();
		this.bandName = bandName;
		this.count = count;
	}

	/**
	 * Returns band name.
	 * @return name
	 */
	public String getBandName() {
		return bandName;
	}

	/**
	 * Sets band name.
	 * @param bandName	name to set
	 */
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	/**
	 * Returns number of votes.
	 * @return number of votes
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * Sets number of votes.
	 * @param count	number of votes to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
}
