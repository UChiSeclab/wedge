//package codeforces;
import java.io.PrintWriter;
import java.util.*;
public class solution { 
    public static void main(String args[]){ 
    	Scanner s=new Scanner(System.in);
		PrintWriter out=new PrintWriter(System.out);
		int t=1;
        for(int tt=0;tt<t;tt++) {
        	int n=s.nextInt();
        	int a[]=new int[n];
        	HashSet<Integer> set=new HashSet<>();
          	for(int i=0;i<n;i++) {
        		a[i]=s.nextInt();
        	}
          	sort(a);
          	for(int i=0;i<n;i++) {
          		if(a[i]!=1) {
          			if(!set.contains(a[i]-1)) {
          				set.add(a[i]-1);
          			}else if(!set.contains(a[i])) {
          				set.add(a[i]);
          			}else {
          				set.add(a[i]+1);
          			}
          		}else {
          			if(set.contains(a[i])) {
          				set.add(a[i]+1);
          			}else {
          				set.add(a[i]);
          			}
          			
          		}
          		
          	}
          	out.print(set.size());
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
			if(x[c]!=y[c])
			 return (int)( x[c] - y[c]);
			else {
				return (int)(x[1]-y[1]);
			}
		});
	}
    public static void printb(boolean ans) {
    	if(ans) {
    		System.out.println("YES");
    	}else {
    		System.out.println("NO");
    	}
    }
    
}
class pair implements Comparable<pair>{
	int a , b ; 
	pair(int x , int y){
		a=x;
		b=y;
	}
	public int compareTo(pair o) {
		return a != o.a ? a - o.a : b - o.b;
	}
}