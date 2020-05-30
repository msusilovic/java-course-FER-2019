package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;



/**
 * Collection LinkedListIndexedCollection is a representation of a linked list-backed
 * collection.
 * 
 * @author Martina
 *
 */

public class LinkedListIndexedCollection implements List {
	
	//represents one object in a linked list
	private static class ListNode {
		Object value; //reference to a value stored in a node
		ListNode previous;  //previous node reference
		ListNode next; //next node reference
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	long modificationCount = 0;
	
	/**
	 * Default list constructor.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = null;
		last = null;
	}
	
	/**
	 * Makes a new list by adding all elements of the other collection to this collection.
	 * 
	 * @param other	other collection from which the elements are to be copied to this collection
	 */
	public LinkedListIndexedCollection (Collection other) {
		size = 0;
		addAll (other);	
	}

	/**
	 * Adds the given object to the end of this list. Newly added element becomes the 
	 * "last" element of the linked-list.
	 * <p>Average complexity of this method is O(1)</p>
	 * 
	 * @param value object to be added to the end of the linked-list
	 * @throws NullPointerException if given value is null reference
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		
		modificationCount++;
		
		ListNode newNode = new ListNode();
		newNode.value = value;
		
		if (first == null) {
			newNode.previous = null;
			newNode.next = null;
			first = newNode;
			last = newNode;
		}
		
		else {
			newNode.previous = last;
			newNode.next = null;
			last.next = newNode;
			last = newNode;
		}
		size++;
		
		
	}
	
	/**
	 * Returns the number of elements stored in a linked list.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if this linked-list contains the given object.
	 * 
	 * @param value	an object to look for
	 * @return true if the value is found, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		ListNode temporaryNode = first;
		for (int i = 0; i < size; i++) {
			if (temporaryNode.value.equals(value)) {
				return true;
			}
			temporaryNode = temporaryNode.next;
		}
		return false;
	}
	
	/**
	 * Removes the given value from a list.
	 * 
	 * @param value the value to remove
	 * @return true if value is found and removes, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		int index =  indexOf (value);
		if (index == -1) {
			return false;
		}
		remove (index);
		return true;
	}
	
	 /**
	  * Allocates new array with size equals to the size of this collections
	  * and fills it with objects from a collection.
	  * 
	  * @return	new array with same content as found in collection
	  */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode temporaryNode = first;
		for (int i = 0; i < size; i++) {
			array [i] = temporaryNode.value;
			temporaryNode = temporaryNode.next;
		}
		return array;
	}
	

	/**
	 * Returns the object that is stored in linked list at position index.
	 * <p>This method never has complexity greater than n/2+1</p>
	 * 
	 * @param index index of an object to be returned
	 * @throws IndexOutOfBoundsException if given index is negative or too large for this collection
	 * @return	object stored at given index in this linked-list
	 * 
	 */
	public Object get(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode found; 
		if (index <= size/2) {
			found = findFromFirst(index);
		}
		else {
			found = findFromLast(index);
		}

		return found.value;
	}

	/**
	 * Removes all elements from a linked list by setting references to the
	 * first and last element to null.
	 */
	@Override
	public void clear () {
		modificationCount++;
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Inserts given value to the given position in this linked-list.
	 * <p>The average complexity of this method is never greater than n/2+1 </p>
	 * 
	 * @param value		value to be inserted
	 * @param position	position to insert the value to
	 * @throws IndexOutOfBoundsException if given position is not valid
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode newNode = new ListNode();
		newNode.value = value;
		
		modificationCount++;
		
		if (position == 0) {
			newNode.next = first;
			newNode.previous = null;
			first.previous = newNode;
			first = newNode;
			size++;
			return;
		}
		if (position == size) {
			last.next = newNode;
			newNode.previous = last;
			newNode.next = null;
			last = newNode;
			size++;
			return;
		}
		
		ListNode previousNode = findNode(position-1);
		newNode.previous = previousNode;
		newNode.next = previousNode.next;
		
		previousNode.next = newNode;
		size++;
	}
	
	/**
	 * Returns the  index of the first occurrence of the given value.
	 * 
	 * @param value of which an index is to be returned
	 * @return index of the first occurrence if value found, -1 otherwise
	 */
	public int indexOf(Object value) {
		ListNode temporaryNode = first;
		for (int i = 0; i < size; i++) {
			if (temporaryNode.value.equals(value)) {
				return i;
			}
			temporaryNode = temporaryNode.next;
		}
		return -1;
	}
	
	/**
	 * Removes the value at given index.
	 * 
	 * @param index	index at which a value is to be removed
	 * @throws IndexOutOfBoundsException if given index is negative or too large for this collection
	 */
	public void remove(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		modificationCount++;
		
		ListNode nodeToRemove;
		
		nodeToRemove = findNode(index); 
		
		
		if ((index != 0) && (index != (size-1))){
			nodeToRemove.next.previous = nodeToRemove.previous;
			nodeToRemove.previous.next = nodeToRemove.next;
		}
		else if (index==0 && index == size-1) {
			last = null;
			first = null;
		}
		
		else if (index == (size-1)) {
		
			last = last.previous;
			nodeToRemove.previous.next = null;
			
			
		}
		
		else if (index == 0) {
			first = first.next;
			nodeToRemove.next.previous = null;
		}
	
		size--;
	}
	
	
	/**
	 * Checks if given index is in first or second half of the collection and returns it.
	 * @param index index of requested value
	 * @return found node
	 */
	private ListNode findNode(int index) {
		ListNode node = null;
		
		if (index <= size/2) {
			node= findFromFirst(index);
		}
		else {
			node = findFromLast(index);
		}
		return node;
	}

	/**
	 * Returns the node found at requested index if index is in the second half of the collection.
	 * @param index	an index of a requested node
	 * @return	node found at given index
	 */
	private ListNode findFromLast(int index) {
		ListNode node = last;
		for (int i = size-1; i > index; i--) {
			node = node.previous;
		}
		return node;
	}

	/**
	 * Returns the value at given index if index is smaller than size/2.
	 * @param index	an index of a requested node
	 * @return node found at given index
	 */
	private ListNode findFromFirst(int index) {
		ListNode node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		
		return new ElementsGetterForLinkedList(this);
	
	}
			
		
	/**
	 * An implementation of a {@link ElementsGetter} interface used for {@link LinkedListIndexedCollection} objects.
	 * 
	 * @author Martina
	 *
	 */
	private static class ElementsGetterForLinkedList implements ElementsGetter {
		
		LinkedListIndexedCollection collection;
		LinkedListIndexedCollection.ListNode nextNode;
		long savedModificationCount = 0;
		
		/**
		 * Constructor method for creating a {new {@link ElementsGetterForLinkedList} object.
		 * Receives a collection as an argument because a static class {@link ElementsGetterForLinkedList} can't
		 * access it's methods and attributes otherwise.
		 * 
		 * @param collection	collection on which methods from this class are to be used
		 */
		public ElementsGetterForLinkedList(LinkedListIndexedCollection collection) {
			this.collection = collection;
			this.nextNode = collection.first;
			this.savedModificationCount = collection.modificationCount;
		}

		
		/**
		 * {@inheritDoc}
		 * @throws {@link ConcurrentModificationException} if collection gets modified while elements getter is being used.
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			return nextNode != null;
		}

		/**
		 * {@inheritDoc}
		 * @throws ConcurrentModificationException if collection gets modified while elements getter is being used.
		 */
		@Override
		public Object getNextElement() {
			
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (nextNode == null) {
				throw new NoSuchElementException();
			}
			
			Object returnValue = nextNode.value;
			nextNode = nextNode.next;
			
			return returnValue;
		}
	}
	
}
