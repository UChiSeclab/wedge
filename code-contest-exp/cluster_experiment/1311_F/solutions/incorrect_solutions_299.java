//package psa.minrazdalja;

import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class

public class minrazdalja {

	static class MinStruktua {
		private int x;
		private int v;

		public MinStruktua(int x, int v) {
			this.x = x;
			this.v = v;
		}

		public void dodajx(int x) {
			this.x = x;
		}

		public void dodajv(int v) {
			this.v = v;
		}
 	}



	public static void elele(ArrayList<MinStruktua> elementi) {

		int skupek = 0;
		for(int i=0; i<elementi.size(); i++) {
			for(int j=0;j<elementi.size();j++) {
				if(i != j) {
					int ix = elementi.get(i).x;
					int jx = elementi.get(j).x;

					if(jx > ix) {
						int vx = elementi.get(i).v;
						int vy = elementi.get(j).v;

						//System.out.println(ix + " " + jx);
						skupek += vrniMax(ix, jx, vx, vy);
					}

				}
			}
			//2 1 4 3 5
			//2 2 2 3 4
		}
		System.out.println("IZPIS elementov: ");
		System.out.println("STEVC : " + skupek);
	}
	//							2      3        3      2
	public static int vrniMax(int m, int n, int i, int j) {
		//najdemo max 
		//nardimo primerjave in vrnemo ustrezno
		
		int x = 0;
		int absol = Math.abs(m-n);
		if(m < n) {
			if(j < 0 && i >= 0) {
				x =+ 0;
			}else if(j >= 0 && i < 0){
				x =+ absol;
			}else if(j < 0 && i < 0) {
				if(j > i){
					x =+ absol;
				} else {
					x =+ 0;
				}
			}else{
				if(j != i) {
					if(j > i){
						x =+ absol;
					}else{
						x =+ 0;
					}
				}else{
					x =+ absol;
				}
				
			}
		};
		System.out.println(m + " " + n + " " + i + " " + j + " " + x);
		return x;
	}


	public static void main(String[] args) {
		int st_el;

		Scanner skener = new Scanner(System.in);  // Create a Scanner object
		//System.out.println("Vnesi stevilo elementov: ");
		st_el = skener.nextInt();

		//System.out.println(st_el);
		ArrayList<MinStruktua> elementi = new ArrayList<>(st_el);

		//System.out.println("Vnesi elemente: ");
		for(int i=0; i<st_el; i++) {
			int x = skener.nextInt();
			elementi.add(new MinStruktua(x, 0));
		}

		//System.out.println("Vnesi tezo elementov: ");
		for(int i=0; i<st_el; i++) {
			int x = skener.nextInt();
			elementi.get(i).dodajv(x);
		}
		skener.close();
		System.out.println();
		System.out.println();
		elele(elementi);
		//vrniMax(1, 2, -100, 3);
	}
}
