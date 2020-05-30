package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

public class SlagalicaMain {
	public static void main(String[] args) {
		char[] arrayChars = args[0].toCharArray();
		int[] arrayInts = new int [arrayChars.length];
		
		List<Integer> list =helpList();
		
		boolean bfsv = false;
		
		
		
			for (int i = 0; i < arrayChars.length; i++) {
				arrayInts[i] = arrayChars[i]-48;
				if (list.contains(arrayInts[i])) {
					list.remove(Integer.valueOf(arrayInts[i]));
				}else {
					bfsv = true;
				}
			}
			if (arrayInts.length != 9) {
				bfsv = true;
			}
		
		Slagalica slagalica =
				new Slagalica(new KonfiguracijaSlagalice(arrayInts));
		Node<KonfiguracijaSlagalice> rješenje;

		if (bfsv) {
			rješenje= SearchUtil.bfsv(slagalica, slagalica, slagalica);
		}else {
			rješenje = SearchUtil.bfs(slagalica, slagalica, slagalica);
		}
				
		if(rješenje==null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println(
						"Imam rješenje. Broj poteza je: " + rješenje.getCost());
		List<KonfiguracijaSlagalice> lista = new ArrayList<>();
		Node<KonfiguracijaSlagalice> trenutni = rješenje;
		while(trenutni != null) {
		lista.add(trenutni.getState());
		trenutni = trenutni.getParent();
		}
		Collections.reverse(lista);
		lista.stream().forEach(k -> {
		System.out.println(k);
		System.out.println();
		});
		SlagalicaViewer.display(rješenje);
		}
		}
		
		

	private static List<Integer> helpList() {
		List<Integer> help = new ArrayList<>();
		help.add(0);
		help.add(1);
		help.add(2);
		help.add(3);
		help.add(4);
		help.add(5);
		help.add(6);
		help.add(7);
		help.add(8);
		
		return help;
		
	}
}
