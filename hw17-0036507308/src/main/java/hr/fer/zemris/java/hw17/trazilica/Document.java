package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Document {

	/**
	 * Words that this document contains with number mapped with number of times they occurred.
	 */
	private Map<String, Integer> words = new HashMap<String, Integer>();
	
	/**
	 * Tfidf values of each word in the vocabulary for this document.
	 */
	private Vector tfidf;
	
	/**
	 * Path to this document.
	 */
	private Path path;

	/**
	 * Value determined by how much this document matches some given query.
	 */
	double similarity;
	
	public Document(Map<String, Integer> words, Path path) {
		
		Objects.requireNonNull(words);
		this.words = words;
		this.path = path;
		
	}

	public Map<String, Integer> getWords() {
		return words;
	}

	public void setWords(Map<String, Integer> words) {
		this.words = words;
	}

	public Vector getTfidf() {
		return tfidf;
	}

	public void setTfidf(Vector tfidf) {
		this.tfidf = tfidf;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public void compareDocument(Document d) {
		
		similarity = tfidf.compareVectors(d.tfidf);
	}



	
}
