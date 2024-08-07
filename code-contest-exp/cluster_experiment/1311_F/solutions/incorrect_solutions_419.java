//package psa.minrazdalja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class minrazdalja {

	/**
	 * Uvozena abstraktna funkcija za klic Paid<L, R>
	 */
	public static class Pair<L,R> {

		private final L left;
		private final R right;
	  
		public Pair(L left, R right) {
		  assert left != null;
		  assert right != null;
	  
		  this.left = left;
		  this.right = right;
		}
	  
		public L getLeft() { return left; }
		public R getRight() { return right; }
	  
		@Override
		public int hashCode() { return left.hashCode() ^ right.hashCode(); }
	  
		@Override
		public boolean equals(Object o) {
		  if (!(o instanceof Pair)) return false;
		  Pair pairo = (Pair) o;
		  return this.left.equals(pairo.getLeft()) &&
				 this.right.equals(pairo.getRight());
		}
	  
	  }

	/**?
	 * glavna funkcija racunanja:
	 * sprejme list tipa ArrayList<Pair<Integer, Integer>>, potem st vseh elementov in seveda listo vseh elementov.
	 * 
	 * nato izracunamo maksimalno distance, ki je sestevek vseh distanc med posameznimi elementi, kar lahko storimo, ker je sortirana tabela.
	 * V drugem delu odstejemo od maksimalnega sestevka distanc, posamezne ki se sekajo v prihodnosti
	 * ostane nam samo tocni sestevek minimalnih distanc
	 */
	public static long elele(ArrayList<Pair<Integer,Integer>> elementi, int s, Integer[] x_el) {

		long skupek = 0;
		Long ml;
		HashMap<Integer, Integer> pos = new HashMap<Integer, Integer>();

		for(int i=0; i<s; ++i) {
			skupek += x_el[i] * i + (-1 * x_el[i]) * (s - 1 - i);
			pos.put(x_el[i], i);
		}

		if(s == 161) 
			System.out.println(skupek);

		for(int i=0; i<s; ++i){
			skupek -= elementi.get(i).getRight() *  (pos.get(elementi.get(i).getRight())-i);
		}
			//2 1 4 3 5
			//2 2 2 3 4
		return skupek;
	}


	public static void main(String[] args) throws IOException {
		int st_el;
		// uporabimo bufferedreader zaradi 1000x vecje hitrosti kot pa skener
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Vnesi stevilo elementov: ");
		st_el = Integer.parseInt(br.readLine());

		//System.out.println(st_el);
		Integer[] x_el = new Integer[st_el];
		Integer[] v_el = new Integer[st_el];
		//System.out.println("Vnesi elemente: ");
		String x = br.readLine();
		String[] strs = x.trim().split("\\s+");
		for(int i=0; i<st_el; ++i) {
			x_el[i] = Integer.parseInt(strs[i]);
		}

		//System.out.println("Vnesi tezo elementov: ");
		String y = br.readLine();
		String[] strs2 = y.trim().split("\\s+");
		for(int i=0; i<st_el; ++i) {
			v_el[i] = Integer.parseInt(strs2[i]);
		}

		br.close();

		//pospravimo skupej v par oblike <integer, integer> oz <hitrost, element>
		ArrayList<Pair<Integer,Integer>> listt = new ArrayList<Pair<Integer, Integer>>(st_el);
		for(int i=0; i<st_el; i++) {
			Pair<Integer, Integer> pair = new Pair<Integer, Integer>(v_el[i],x_el[i]);
			listt.add(pair);
		}

		/*sortiramo listt od najmanjsega do najvecjega
		*	- sortiramo prvo po hitrostih (to je glavno najbolj vazno)
		*	- ce je hitrost ista potem pa sortiramo se glede na element
		*/
		Collections.sort(listt, new Comparator<Pair<Integer, Integer>>() {
			@Override
			public int compare(final Pair<Integer, Integer> o1, final Pair<Integer, Integer> o2) {
				if(o1.getLeft()<o2.getLeft()) {
					return -1;
				} else if(o1.getLeft() == o2.getLeft()) {
					if(o1.getRight() < o2.getRight()){
						return -1;
					}else{
						return 1;
					}
				} else {
					return 1;
				}
			}	
		});

		//sortiramo se elemente
		Arrays.sort(x_el);

		//izpisemo elemente
		System.out.println(elele(listt,st_el,x_el));
	}
}
