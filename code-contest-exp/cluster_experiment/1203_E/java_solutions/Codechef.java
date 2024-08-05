/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.BigInteger; 

/* Name of the class has to be "Main" only if the class is public. */
public class Codechef
{
	public static void main (String[] args) throws java.lang.Exception
	{
	    FastReader fr = new FastReader();
	    int n = fr.nextInt();
	    int a[] = new int[n];
	    for(int i=0;i<n;i++){
	        a[i] = fr.nextInt();
	    }
	    
	    
	    HashSet<Integer> h = new HashSet<>();
	    
	    int min = Integer.MAX_VALUE;
	    
	    for(int i=n-1;i>=0;i--){
	        if(!h.contains(a[i]+1)){
	            h.add(a[i]+1);
	        }else if(!h.contains(a[i])){
	            h.add(a[i]);
	        }else if(!h.contains(a[i]-1)&&(a[i]-1>0)){
	            h.add(a[i]-1);
	        }
	    }
	    
	    System.out.println(h.size());
	    
	}
}
class FastReader {
 
    BufferedReader bf;
    StringTokenizer st;
 
    public FastReader() {
        bf = new BufferedReader(new InputStreamReader(System.in));
    }
 
    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(bf.readLine());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return st.nextToken();
    }
 
    int nextInt() {
        return Integer.parseInt(next());
    }
 
    long nextLong() {
        return Long.parseLong(next());
    }
 
    double nextDouble() {
        return Double.parseDouble(next());
    }
 
    String nextLine() {
        String str = "";
        try {
            str = bf.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return str;
    }
}
