import java.util.*;
import java.io.*;
import java.lang.Math;


public class DK{
	public static int gcd(int a, int b)
	{
		if (a<b){
			int temp =a;
			a = b;
			b=temp;
		}
		if (b==0) {
			return a;
		}
		else{
			return gcd(b,a%b);
		}
	}
	 
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
 
		int t = console.nextInt();

		for (int o=0;o< t ;o++ ) {
			int n=console.nextInt();

			int[] vals = new int[n];
			String s = console.next();

			Ratio[] subs = new Ratio[n];
			int k=0;
			int d=0;

			for (int i=0;i<n ;i++ ) {
				char ch = s.charAt(i);
		        int a = ch - 'D';				
		        if(a ==0){
		        	vals[i]=0;
		        }
		        if(a>0){
		        	vals[i]=1;
		        }
		        if(a<0){
		        	System.out.println("DEBUGGG");
		        }

				if(vals[i]==0){
					d++;
				}
				else{
					k++;
				}
				int ooo=0;

				int dif = (k+d)/gcd(k,d);
				for (int j=i-dif;j>=0 ;j-=dif ) {
					if(subs[j].k*d == subs[j].d*k){
						ooo = subs[j].occ+1;
						break;
					}
				}
				System.out.print((ooo+1)+" ");
				Ratio pepe = new Ratio(k,d,ooo);
				subs[i]=pepe;		        
			}

			System.out.println();

		}
	}
}

class Ratio {
	public int k;
	public int d;
	public int occ;

	public Ratio(int k, int d, int occ) {
		this.k=k;
		this.d=d;
		this.occ = occ;
	}
}
