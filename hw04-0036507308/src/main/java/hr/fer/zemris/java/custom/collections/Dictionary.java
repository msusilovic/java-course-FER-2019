package hr.fer.zemris.java.custom.collections;


/**
 * An object that maps keys to values. Keys can't be null, but values can.
 * Does not contain duplicate keys, if user tries to store some value at 
 * the key that was already in the dictionary, old value will be replaced 
 * with new value.
 * 
 * @author Martina
 *
 * @param <K>	type of keys maintained by this map
 * @param <V>	type of values stored
 */
public class Dictionary<K, V> {
	
	/**
	 * Class which represents one object to be mapped. Consists of key to map the 
	 * value by and value.
	 * 
	 * @author Martina
	 *
	 * @param <K>	type of key
	 * @param <V>	type of value
	 */
	private static class Node<K, V> {
		
		/**
		 * Key at which value is stored.
		 */
		K key; 
		
		/**
		 * Stored value.
		 */
		V value; 
	}
	
	ArrayIndexedCollection<Node<K, V>> dictionary = new ArrayIndexedCollection<>();
	
	/**
	 * Checks if this dictionary contains any key-value pairs. 
	 * 
	 * @return <code>true</code> if dictionary is empty, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return dictionary.isEmpty();
	}
	
	
	/**
	 * Returns number of key-value pairs stored in this dictionary.
	 * 
	 * @return number of key-value pairs stored in this dictionary
	 */
	public int size() {
		return dictionary.size();
	}
	
	/**
	 * Removes all the mappings from this dictionary.
	 */
	void clear() {
		dictionary.clear();
	}
	
	/**
	 * Method to add a new mapping into this dictionary. Since duplicate keys are
	 * not allowed, if some value is already mapped with given key, it is replaced
	 * with new value.
	 * 
	 * @param key	key of a new mapping
	 * @param value	value to be mapped
	 */
	void put(K key, V value) {
		Node<K, V> newNode = new Node<>();
		newNode.value = value;
		newNode.key = key;
		int i;
		Node<K, V> tempNode;
		
		for (i = 0; i < dictionary.size() ; i++) {
			tempNode = dictionary.get(i);
			if (tempNode.key.equals(key)) {
				dictionary.remove(i);
				break;
			}
		}
		dictionary.insert(newNode, i);
	}
	
	/**
	 * Searches the dictionary for given key and return value mapped with it if
	 * key is found. If key is not found, returns null value.
	 * 
	 * @param key	key to search for
	 */
	public V get(Object key) {
		Node<K, V> tempNode; 
		
		for (int i = 0;  i< dictionary.size(); i++) {
			tempNode = dictionary.get(i);
			if (tempNode.key.equals(key)) {
				return tempNode.value;
			}
		}
		return null;
	}
}
