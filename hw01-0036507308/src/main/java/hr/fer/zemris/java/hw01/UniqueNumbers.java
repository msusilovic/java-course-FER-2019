package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program UniqueNumbers is used for creating a binary tree and adding only
 * integers that are not duplicate to it.
 * 
 * @author Martina
 *
 */

public class UniqueNumbers {

	/**
	 * TreeNode class represents one node in a binary tree.
	 */
	public static class TreeNode {
		
		/**
		 * Integer value stored in a node.
		 */
		int value;
		
		/**
		 * First node of this node's left subtree.
		 */
		TreeNode left;
		
		/**
		 * First node of this node's right subtree.
		 */
		TreeNode right;
	}

	/**
	 * Asks user to specify numbers to store in a binary tree. Adds values to the
	 * tree and then shows values stored in ascending and descending order.
	 * 
	 * @param args	command line arguments.
	 */
	public static void main(String[] args) {

		/**
		 * Binary tree head, first element in a tree.
		 */
		TreeNode head = null;

		Scanner sc = new Scanner(System.in);
		
		System.out.println("Unesite broj >");

		while (sc.hasNext()) {
			
			String nextString = sc.next().toLowerCase();
			if (nextString.equals("kraj")) {
				break;
			}
			
			try {
				int value = Integer.parseInt(nextString);

				if (containsValue(head, value)) {
					System.out.println("Broj već postoji. Preskačem.");
				}else {
					head = addNode(head, value);
					System.out.println("Dodano.");
				}
			}catch (NumberFormatException e) {
				System.out.println("'" + nextString + "' nije cijeli broj");
			}
			
			System.out.print("Unesite broj >");
		}

		sc.close();

		System.out.print("Ispis od najmanjeg: ");
		printInOrder(head);
		System.out.println("");

		System.out.print("Ispis od najvećeg: ");
		printReverseOrder(head);

	}

	/**
	 * Adds a node with requested value to a tree if not duplicate.
	 * 
	 * @param head  	current head node of a tree
	 * @param value 	value to add
	 * @return binary	 tree head
	 */

	public static TreeNode addNode(TreeNode head, int value) {

		if (head == null) {
			
			TreeNode newNode = new TreeNode();
			newNode.value = value;
			newNode.left = null;
			newNode.right = null;
			return newNode;
		}

		if (value < head.value) {
			head.left = addNode(head.left, value);
		}

		if (value > head.value) {
			head.right = addNode(head.right, value);
		}
		
		return head;
	}

	/**
	 * Counts the nodes in a binary tree.
	 * 
	 * @param head 	current tree head
	 * @return number of nodes in a tree
	 */
	public static int treeSize(TreeNode head) {

		if (head == null) {
			return 0;
		}
		return (1 + treeSize(head.left) + treeSize(head.right));
	}

	/**
	 * Checks if a binary tree contains specified value.
	 * 
	 * @param head  current tree head
	 * @param value value to search for in a tree
	 * @return true if requested value is found, false otherwise
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}
		if (head.value == value) {
			return true;
		}

		return value < head.value ? containsValue(head.left, value) : containsValue(head.right, value);

	}

	/**
	 * Prints values in binary tree in descending order.
	 * 
	 * @param head current tree head
	 */
	private static void printInOrder(TreeNode head) {
		if (head != null) {
			printInOrder(head.left);
			System.out.print(head.value + " ");
			printInOrder(head.right);
		}

	}

	/**
	 * Prints values in binary tree in ascending order.
	 * 
	 * @param head current tree head
	 */

	private static void printReverseOrder(TreeNode head) {
		if (head != null) {
			printReverseOrder(head.right);
			System.out.print(head.value + " ");
			printReverseOrder(head.left);
		}

	}
}
