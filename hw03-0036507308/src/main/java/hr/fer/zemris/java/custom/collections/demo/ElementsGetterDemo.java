package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class ElementsGetterDemo {

	public static void main(String[] args) {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());

		System.out.println("");
		Collection coll = new ArrayIndexedCollection();
		coll.add("Ivo");
		coll.add("Ana");
		coll.add("Jasna");
		ElementsGetter getter11 = coll.createElementsGetter();
		ElementsGetter getter22 = coll.createElementsGetter();
		System.out.println("Jedan element: " + getter11.getNextElement());
		System.out.println("Jedan element: " + getter11.getNextElement());
		System.out.println("Jedan element: " + getter22.getNextElement());
		System.out.println("Jedan element: " + getter11.getNextElement());
		System.out.println("Jedan element: " + getter22.getNextElement());

		
		System.out.println();
		Collection col = new LinkedListIndexedCollection(); // npr. new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());


	}

}
