package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Program used for searching documents. 
 * 
 * @author Martina
 *
 */
public class Konzola {
	
	public static void main(String[] args) {
		String pathString = args[0];
		
		Path p = Paths.get(pathString);
		
		if (!Files.exists(p) || !Files.isDirectory(p)) {
			System.out.println("Invalid path!");
			return;
		}

		/**
		 * Set of stopwords.
		 */
		HashSet<String> stopwords = getStopwords();
		
		/**
		 * Set containing all the words that are not stopwords.
		 */
		LinkedHashSet<String> vocabulary = new LinkedHashSet<String>();
		
		/**
		 * List of objects representing documents.
		 */
		List<Document> documents = new ArrayList<>();
	
		try {
			initVocabulary(vocabulary, stopwords, p);
			System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.");
			initDocuments(vocabulary, p, documents);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Vector idf = caculateTfidf(vocabulary, documents);
		
		Scanner sc = new Scanner(System.in);
		
		List<Document> results = new ArrayList<Document>();
		
		while(true) {
			System.out.println("Enter command > ");
			
			String input = sc.nextLine();
			String command = input.split(" ")[0];
			
			switch(command) {
				case "query":
					findResults(input, vocabulary, documents, results, idf);
					break;
				case "results":
					getResults(results);
					break;
				case "type":
				try {
					getType(input, results);
				} catch (IOException e) {
					e.printStackTrace();
				}
					break;
				case "exit":
					sc.close();
					return;
				default:
					System.out.println("Nepoznata naredba.");
			}	
		}
	}

	private static void getResults(List<Document> results) {
		
		if (results.isEmpty()) {
			System.out.println("Nedopuštena naredba.");
			return;
		}
		
		printResults(results);
	}

	private static void getType(String input, List<Document> results) throws IOException {

		if (results.isEmpty()) {
			System.out.println("Nedopuštena naredba.");
			return;
		}
		int index = 0;

		try {
			index = Integer.parseInt(input.split(" ")[1]);
		} catch (ClassCastException e) {
			System.out.println("Illegal argument.");
			return;
		}

		if (index < 0 || index > results.size() - 1) {
			System.out.println("Illegal argument.");
			return;
		}
		
		Document document = results.get(index);

		System.out.println("Dokument: " + document.getPath());
		System.out.println("----------------------------------------------------------------");
		
		BufferedReader br = new BufferedReader(
							new InputStreamReader(
							new BufferedInputStream(
						    Files.newInputStream(document.getPath())),"UTF-8"));
	
		String line = br.readLine();
		while (line != null) {
			System.out.println(line);
			line = br.readLine();
		}
	}

	/**
	 * Method to find documents that best match given query.
	 * 
	 * @param input 		words to find in a document
	 * @param vocabulary	set containing all words
	 * @param documents		set of documents to search
	 * @param stopwords		set of stopwords
	 * @param results		list of results matching 
	 * @param idf 			idf vector
	 */
	private static void findResults(String input, LinkedHashSet<String> vocabulary, List<Document> documents,
								    List<Document> results, Vector idf) {
		
		results.clear();
		List<String> words = new ArrayList<String>();
		String[] queryParts = input.split(" ");
		
		for (String s : queryParts) {
			if (vocabulary.contains(s)) {
				words.add(s);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("Query is: [");
		for (String s : words) {
			sb.append(s);
			if (words.indexOf(s) != words.size()-1) sb.append(", ");
		}
		
		sb.append("]");
		System.out.println(sb.toString());
		
		Map<String, Integer> wordsMap = new HashMap<String, Integer>();
		for (String s : words) {
			wordsMap.put(s, 1);
		}
		
		double[] tfidf = new double[vocabulary.size()];
		int index = 0;
		for (String w : vocabulary) {
			if (words.contains(w)) {
				tfidf[index] = idf.getValues()[index];
			}
			index++;
		}
		
		Document d = new Document(wordsMap, null);
		d.setTfidf(new Vector(tfidf));
		
		for (Document doc : documents) {
			doc.compareDocument(d);
			if (doc.similarity > 0) {
				results.add(doc);
			}
		}	
		Collections.sort(results, (d1,d2) -> -Double.compare(d1.getSimilarity(), d2.getSimilarity()));
		System.out.println("Najboljih 10 rezultata:");
		printResults(results);
	}

	private static void printResults(List<Document> results) {
		int i = 0;
		for (Document doc : results) {
			if (i > 9) break;
			DecimalFormat formatter = new DecimalFormat("#0.0000");
			System.out.println("[" + i + "] (" + formatter.format(doc.similarity) + ") " + doc.getPath());
			i++;
		}
	}

	/**
	 * Method used to calculate tfidf value for each word in a vocabulary.
	 * 
	 * @param vocabulary	set containing all the vocabulary words
	 * @param documents		set of all documents
	 * @return				Vector containing all the tfidf values
	 */
	private static Vector caculateTfidf(LinkedHashSet<String> vocabulary, List<Document> documents) {

		double[] idfArray = new double[vocabulary.size()];
		
		int index=0;
		for (String word : vocabulary) {
			
			double count = 0;
			for (Document d : documents) {
				if (d.getWords().containsKey(word)) count++;
			}

			idfArray[index] = Math.log((double)documents.size()/count);
			index++;
		}
		
		for (Document d : documents) {
			index = 0;
			double[] tfidf = new double[vocabulary.size()];
			
			for (String w : vocabulary) {
				if (d.getWords().containsKey(w)) {
					tfidf[index] = (double)d.getWords().get(w)*idfArray[index];
				}else {
					tfidf[index] = 0;
				}	
				index++;
			}

			Vector v = new Vector(tfidf);
			d.setTfidf(v);
		}
		
		return new Vector(idfArray);
	}

	/**
	 * Method to create documents and count words occurring in them.
	 * 
	 * @param vocabulary	vocabulary containing all words 
	 * @param p				path to folder containing all documents
	 * @param documents		set of documents
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws IOException	if reading from files fails
	 */
	private static void initDocuments(LinkedHashSet<String> vocabulary, Path p,
			List<Document> documents) throws UnsupportedEncodingException, IOException {
		
		File[] files = p.toFile().listFiles();
		
		for (File f : files) {
			
			Map<String, Integer> words = new HashMap<>();
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
					new BufferedInputStream(
					Files.newInputStream(f.toPath())),"UTF-8"));
			
			String line = br.readLine();
			
			while (line!= null) {
				StringBuilder sb = new StringBuilder();
				
				if (line.isEmpty()) {
					line = br.readLine();
					continue;
				}
				
				for (char c : line.toCharArray()) {
					if (Character.isAlphabetic(c)) {
						sb.append(c);
					}
					else {
						String word = sb.toString().toLowerCase();
						sb = new StringBuilder();
				
						if (vocabulary.contains(word)) {
							if (!words.containsKey(word)) {
								words.put(word, 1);
							}else {
								words.put(word, words.get(word)+1);
							}
						}	
					}
				}
				line = br.readLine();
			}
			documents.add(new Document(words, f.toPath()));
		}
	}

	/**
	 * Visits all documents and generates vocabulary containing all words found in
	 * documents that are not stopwords.
	 * 
	 * @param vocabulary set to put vocabulary words to
	 * @param stopwords  words not to be added to document
	 * @param path       path to folder containing all documents
	 * @throws IOException if reading from files fails
	 */
	private static void initVocabulary(LinkedHashSet<String> vocabulary, HashSet<String> stopwords,
			Path path) throws IOException {
		
		File[] files = path.toFile().listFiles();
		
		for (File f : files) {
			BufferedReader br = new BufferedReader(
								new InputStreamReader(
								new BufferedInputStream(
								Files.newInputStream(f.toPath())),"UTF-8"));
			
			String line = br.readLine();
			
			while (line!= null) {
				StringBuilder sb = new StringBuilder();
				
				if (line.isEmpty()) {
					line = br.readLine();
					continue;
				}
				
				for (char c : line.toCharArray()) {
					if (Character.isAlphabetic(c)) {
						sb.append(c);
					}
					else {
						String word = sb.toString().toLowerCase();
						sb = new StringBuilder();
						if (!word.strip().isBlank() && !stopwords.contains(word)) {
							vocabulary.add(word);
						}	
					}
				}
				line = br.readLine();
			}
		}			
	}

	/**
	 * Method to load all stopwords.
	 * 
	 * @return	set of all stopwords
	 * @throws IOException 
	 */
	private static HashSet<String> getStopwords() {

		List<String> list = new ArrayList<>();
		HashSet<String> stopwords = new HashSet<>();
		
		try {
			list = Files.readAllLines(Paths.get("src/main/resources/hrvatski_stoprijeci.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String s : list) {
			stopwords.add(s.strip());
		}
		return stopwords;
	}
}
