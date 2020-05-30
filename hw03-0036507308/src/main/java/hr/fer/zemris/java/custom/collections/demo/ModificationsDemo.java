package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class ModificationsDemo {

	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		//System.out.println("Jedan element: " + getter.getNextElement());
		
		System.out.println();

		Collection col2 = new LinkedListIndexedCollection();
		col2.add("Ivo");
		col2.add("Ana");
		col2.add("Jasna");
		ElementsGetter getter2 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		col2.clear();
		System.out.println("Jedan element: " + getter2.getNextElement());
	}

}
