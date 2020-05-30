package hr.fer.zemris.java.custom.collections;

/**
 * LinkedListIndexedCollection is a representation of a linked list-backed
 * collection.
 * 
 * @author Martina
 *
 */

public class LinkedListIndexedCollection extends Collection {

	/**
	 * One object in a linked list.
	 * 
	 */
	private static class ListNode {

		/**
		 * Reference to a value stored in a node.
		 */
		Object value;

		/**
		 * Reference to previous node.
		 */
		ListNode previous;

		/**
		 * Reference to next node.
		 */
		ListNode next;
	}

	/**
	 * List size.
	 */
	private int size;

	/**
	 * First element in the list.
	 */
	private ListNode first;

	/**
	 * Last element in the list.
	 */
	private ListNode last;

	/**
	 * Default constructor.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = null;
		last = null;
	}

	/**
	 * Constructs a list by adding all elements of the other collection to this
	 * collection.
	 * 
	 * @param other other collection from which the elements are to be copied to
	 *              this collection
	 */
	public LinkedListIndexedCollection(Collection other) {

		size = 0;
		addAll(other);
	}

	/**
	 * Adds the given object to the end of this list. Newly added element becomes
	 * the "last" element of the linked-list.
	 * <p>Average complexity of this method is O(1).
	 * 
	 * @param value object to be added to the end of the linked-list
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}

		ListNode newNode = new ListNode();
		newNode.value = value;

		if (first == null) {
			newNode.previous = null;
			newNode.next = null;
			first = newNode;
			last = newNode;

		} else {
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
	 * @param value an object to search for
	 * @return <code>true</code> if the value is found, <code>false</code> otherwise
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
	 * @return <code>true</code> if value is found and removed, <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	/**
	 * Allocates new array with size equals to the size of this collection and fills
	 * it with objects from a collection.
	 * 
	 * @return new array with same content as found in collection
	 */
	@Override
	public Object[] toArray() {

		Object[] array = new Object[size];
		ListNode temporaryNode = first;
		int i = 0;

		while (temporaryNode != null) {
			array[i++] = temporaryNode.value;
			temporaryNode = temporaryNode.next;
		}
		return array;
	}

	/**
	 * Method calls the process method from a processor for each element of this
	 * linked-list.
	 * 
	 * @param processor instance of a Processor class that can process a given value
	 */
	@Override
	public void forEach(Processor processor) {

		ListNode temporaryNode = first;

		while (temporaryNode != null) {
			processor.process(temporaryNode.value);
			temporaryNode = temporaryNode.next;
		}
	}

	/**
	 * Returns the object that is stored in linked list at position index.
	 * <p>
	 * This method never has complexity greater than n/2+1
	 * </p>
	 * 
	 * @param index index of an object to be returned
	 * @return object stored at given index in this linked-list
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}

		ListNode found = null;
		if (index <= size / 2) {
			found = findFromFirst(index);

		} else {
			found = findFromLast(index);
		}

		return found.value;
	}

	/**
	 * Removes all elements from a linked list by setting references to the first
	 * and last element to <code>null</code> and size to 0.
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Inserts given value to the given position in this linked-list.
	 * <p>
	 * The average complexity of this method is never greater than n/2+1
	 * </p>
	 * 
	 * @param value    value to be inserted
	 * @param position position to insert the value to
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}

		ListNode newNode = new ListNode();
		newNode.value = value;
		if (position == 0) {
			newNode.next = first.next;
			newNode.previous = first;
			first = newNode;

		} else if (position == size) {
			last.next = newNode;
			newNode.previous = last;
			newNode.next = null;
			last = newNode;

		} else {

			ListNode previousNode;

			if (position <= size / 2) {
				previousNode = findFromFirst(position - 1);
			} else {
				previousNode = findFromLast(position - 1);
			}

			newNode.previous = previousNode;
			newNode.next = previousNode.next;

			previousNode.next = newNode;
		}
		size++;
	}

	/**
	 * Returns the index of the first occurrence of the given value.
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
	 * @param index index at which a value is to be removed
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}

		ListNode nodeToRemove = null;

		if (index <= size / 2) {
			nodeToRemove = findFromFirst(index);
		} else {
			nodeToRemove = findFromLast(index);
		}

		if ((index != 0) && (index != (size - 1))) {
			nodeToRemove.next.previous = nodeToRemove.previous;
			nodeToRemove.previous.next = nodeToRemove.next;

		} else if (index == 0 && index == size - 1) {
			last = null;
			first = null;

		} else if (index == (size - 1)) {
			last = last.previous;
			nodeToRemove.previous.next = null;

		} else if (index == 0) {
			first = first.next;
			nodeToRemove.next.previous = null;
		}

		size--;
	}

	/**
	 * Method to find node at specified index by iterating from the end of the list.
	 * 
	 * @param index index of a node to be returned
	 * @return node at specified index
	 */
	private ListNode findFromLast(int index) {
		ListNode node = last;
		for (int i = size - 1; i > index; i--) {
			node = node.previous;
		}
		return node;
	}

	/**
	 * Method to find node at specified index by iterating from the start of the
	 * list.
	 * 
	 * @param index index of a node to be returned
	 * @return node at specified index
	 */
	private ListNode findFromFirst(int index) {
		ListNode node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}

}
