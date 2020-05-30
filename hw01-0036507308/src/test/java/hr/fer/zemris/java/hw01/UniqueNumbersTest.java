package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

class UniqueNumbersTest {

	@Test
	public void isValueAddedRight() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 3);
		head = UniqueNumbers.addNode(head, 1);
		head = UniqueNumbers.addNode(head, 4);
		head = UniqueNumbers.addNode(head, 4);

		assertEquals(head.value, 3);
		assertEquals(head.left.value, 1);
		assertEquals(head.right.value, 4);

	}

	@Test
	void noDuplicateNumbersSize() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 5);
		head = UniqueNumbers.addNode(head, -13);
		head = UniqueNumbers.addNode(head, 9);
		int size = UniqueNumbers.treeSize(head);

		assertEquals(size, 3);
	}

	@Test
	void emptyTreeSize() {
		TreeNode head = null;
		int size = UniqueNumbers.treeSize(head);
		assertEquals(size, 0);
	}

	@Test
	void duplicateNumbersSize() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 5);
		head = UniqueNumbers.addNode(head, 6);
		head = UniqueNumbers.addNode(head, 5);

		int size = UniqueNumbers.treeSize(head);
		assertEquals(size, 2);
	}

	@Test
	void donesntContainValue() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 5);

		assertFalse(UniqueNumbers.containsValue(head, 0));
	}

	@Test
	void treeContainsValue() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 5);
		head = UniqueNumbers.addNode(head, 17);

		assertTrue(UniqueNumbers.containsValue(head, 5));
	}

}
