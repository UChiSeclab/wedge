//package psa.minrazdalja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

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
	public static BigInteger elele(ArrayList<Pair<BigInteger,BigInteger>> elementi, int s, BigInteger[] x_el) {

        BigInteger skupek = new BigInteger("0");
        
        HashMap<BigInteger, Integer> pos = new HashMap<BigInteger, Integer>();
        for(int i=0; i<s; ++i) {
			skupek = skupek.add( (x_el[i].multiply( new BigInteger(Integer.toString(i) ))).add( (x_el[i].negate()).multiply(new BigInteger(Integer.toString(s - 1 - i)))));
            pos.put(x_el[i], i);
        }

        for(int i=0; i<s; ++i){
            skupek = skupek.subtract( (elementi.get(i).getRight()).multiply( new BigInteger( Integer.toString( (pos.get(elementi.get(i).getRight()) - i) ) ) ) );
        }
        //    //2 1 4 3 5
        //    //2 2 2 3 4
    	return skupek;
    }


	public static void main(String[] args) throws IOException {
		int st_el;
		// uporabimo bufferedreader zaradi 1000x vecje hitrosti kot pa skener
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		//System.out.println("Vnesi stevilo elementov: ");
		st_el = Integer.parseInt(br.readLine());

		//System.out.println(st_el);
		BigInteger[] x_el = new BigInteger[st_el];
		BigInteger[] v_el = new BigInteger[st_el];
		//System.out.println("Vnesi elemente: ");
		String x = br.readLine();
		String[] strs = x.trim().split("\\s+");
		for(int i=0; i<st_el; ++i) {
			x_el[i] = new BigInteger(strs[i]);
		}

		//System.out.println("Vnesi tezo elementov: ");
		String y = br.readLine();
		String[] strs2 = y.trim().split("\\s+");
		for(int i=0; i<st_el; ++i) {
			v_el[i] = new BigInteger(strs2[i]);
		}

		br.close();

		//pospravimo skupej v par oblike <integer, integer> oz <hitrost, element>
		ArrayList<Pair<BigInteger,BigInteger>> listt = new ArrayList<Pair<BigInteger, BigInteger>>(st_el);
		for(int i=0; i<st_el; i++) {
			Pair<BigInteger, BigInteger> pair = new Pair<BigInteger, BigInteger>(v_el[i],x_el[i]);
			listt.add(pair);
		}

		/*sortiramo listt od najmanjsega do najvecjega
		*	- sortiramo prvo po hitrostih (to je glavno najbolj vazno)
		*	- ce je hitrost ista potem pa sortiramo se glede na element
		*	- O(n log n)
		*/
		Collections.sort(listt, new Comparator<Pair<BigInteger, BigInteger>>() {
			@Override
			public int compare(final Pair<BigInteger, BigInteger> o1, final Pair<BigInteger, BigInteger> o2) {
				int oo = o1.getLeft().compareTo(o2.getLeft());
				if(oo == -1) {
					return -1;
				} else if(oo == 0) {
					int ooo = o1.getRight().compareTo(o2.getRight());
					if(ooo == -1){
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
