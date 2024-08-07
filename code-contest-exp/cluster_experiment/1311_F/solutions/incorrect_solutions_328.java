//package psa.minrazdalja;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Main {
 
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
	public static long elele(ArrayList<Pair<Long,Long>> elementi, int s, Long[] x_el) {
 
        long skupek = 0L;
        
        HashMap<Long, Integer> pos = new HashMap<Long, Integer>();
        for(int i=0; i<s; ++i) {
            skupek += x_el[i] * Long.valueOf(i) + (-1L * x_el[i]) * Long.valueOf(s - 1 - i);
            pos.put(x_el[i], i);
        }
 
 
        for(int i=0; i<s; ++i){
            skupek -= elementi.get(i).getRight() *  (pos.get(elementi.get(i).getRight()) - i);
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
		Long[] x_el = new Long[st_el];
		Long[] v_el = new Long[st_el];
		//System.out.println("Vnesi elemente: ");
		String x = br.readLine();
		String[] strs = x.trim().split("\\s+");
		for(int i=0; i<st_el; ++i) {
			x_el[i] = Long.parseLong(strs[i]);
		}
 
		//System.out.println("Vnesi tezo elementov: ");
		String y = br.readLine();
		String[] strs2 = y.trim().split("\\s+");
		for(int i=0; i<st_el; ++i) {
			v_el[i] = Long.parseLong(strs2[i]);
		}
 
		br.close();
 
		//pospravimo skupej v par oblike <integer, integer> oz <hitrost, element>
		ArrayList<Pair<Long,Long>> listt = new ArrayList<Pair<Long, Long>>(st_el);
		for(int i=0; i<st_el; i++) {
			Pair<Long, Long> pair = new Pair<Long, Long>(v_el[i],x_el[i]);
			listt.add(pair);
		}
 
		/*sortiramo listt od najmanjsega do najvecjega
		*	- sortiramo prvo po hitrostih (to je glavno najbolj vazno)
		*	- ce je hitrost ista potem pa sortiramo se glede na element
		*	- O(n log n)
		*/
		Collections.sort(listt, new Comparator<Pair<Long, Long>>() {
			@Override
			public int compare(final Pair<Long, Long> o1, final Pair<Long, Long> o2) {
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
		// O(n log n)
		Arrays.sort(x_el);
 
		//izpisemo elemente
		System.out.println(elele(listt,st_el,x_el));
	}
}