//package codeforces;
import java.io.PrintWriter;
import java.util.*;
public class solution { 
    public static void main(String args[]){ 
    	Scanner s=new Scanner(System.in);
		PrintWriter out=new PrintWriter(System.out);
		int t=s.nextInt();
		for(int tt=0;tt<t;tt++) {
			int n=s.nextInt();
			String a=s.next();
			Pair c[]=new Pair[n];
			HashMap<Character,Integer> map=new HashMap<>();
			outer:for(int i=0;i<n;i++) {
				map.put(a.charAt(i),1+map.getOrDefault(a.charAt(i), 0));
				int k=map.getOrDefault('D', 0);
				int d=map.getOrDefault('K', 0);
				c[i]=new Pair(d,k);
				if(k==0) {
					out.print(d+" ");
				}else if(d==0) {
					out.print(k+" ");
				}else {
					int f=Math.min(k, d);
					if(Math.max(k, d)%f!=0) {
						out.print(1+" ");
						continue outer;
					}
					ArrayList<Integer> factors=factors(f);
					for(int j=factors.size()-1;j>=0;j--) {
						boolean ans=true;
						for(int l=2*((i+1)/factors.get(j))-1;l<=i;l+=(i+1)/factors.get(j)) {
							double maxi=Math.max(c[l].d, c[l].k);
							double mixi=Math.min(c[l].d, c[l].k);
							double maxa=Math.max(c[l-(i+1)/factors.get(j)].d, c[l-(i+1)/factors.get(j)].k);
							double mixa=Math.min(c[l-(i+1)/factors.get(j)].d, c[l-(i+1)/factors.get(j)].k);
							//out.println(i+" "+maxi+" "+maxa+" "+mixi+" "+mixa+" "+(l-i/factors.get(j))+" "+l+" "+(i/factors.get(j)));
							
							if((maxa/mixa)!=(maxi/mixi)) {
								ans=false;
								break;
							}
						}
						if(ans) {
							out.print(factors.get(j)+" ");
							continue outer;
						}
					}
					out.print(1+" ");
				}
			}
			out.println();
		}
	    s.close();
	    out.close();
    } 
    
    static void sort(long [] a) {
		ArrayList<Long> l=new ArrayList<>();
		for (long i:a) l.add(i);
		Collections.sort(l);
		for (int i=0; i<a.length; i++) a[i]=l.get(i);
	}
    static void sort(int [] a) {
		ArrayList<Integer> l=new ArrayList<>();
		for (int i:a) l.add(i);
		Collections.sort(l);
		for (int i=0; i<a.length; i++) a[i]=l.get(i);
	}
    static int gcd(int a, int b)
    {
      if (b == 0)
        return a;
      return gcd(b, a % b);
    }
    static void sortcol(long a[][],int c) {
		Arrays.sort(a, (x, y) -> {
			 return (int)( x[c] - y[c]);
		});
	}
    public static void printb(boolean ans) {
    	if(ans) {
    		System.out.println("YES");
    	}else {
    		System.out.println("NO");
    	}
    }
    static class Pair /*implements Comparable<Pair>*/ {
    	int d, k;
    	public Pair(int index, int dur) {
    		this.d=index;
    		this.k=dur;
    	}
    	
//    	public int compareTo(Pair o) {
//    		return Long.compare(in, o.in);
//    	}
    }
    public static ArrayList<Integer> factors(int f){
    	ArrayList<Integer> ans=new ArrayList<>();
    	for(int i=1;i<=f;i++) {
    		if(f%i==0) {
    			ans.add(i);
    		}
    	}
    	return ans;
    }
}
