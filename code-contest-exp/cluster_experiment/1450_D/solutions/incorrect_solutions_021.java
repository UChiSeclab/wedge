import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;



public class D {

	
	
    public static void main(String[] args) {
    	FastScanner scan = new FastScanner();
    	int t = scan.nextInt();
    	for(int tt=0; tt<t; tt++) {
    		String str = "";
    		int n = scan.nextInt();
    		int [] arr = scan.readArray(n);
    		int k = 1;
    		
    		while(k <= n) {
    			int [] ans = new int[n-k+1];
    			boolean [] used = new boolean[n-k+1];
    			boolean perm = true;
    			
    			int lastMin = 0;
    			for(int i=0; i<ans.length; i++) {
    				int min = arr[i];
    				if(i != 0 && arr[i-1] != lastMin) {
    					min = Math.min(min, arr[i+k-1]);
    				} else {
    				for(int j=i+1; j<i+k; j++) {
    					min = Math.min(min, arr[j]);
    				}
    			}	
    				ans[i] = min;
    				if(min-1 >= used.length) perm = false;
    				else used[min-1]=true;
    				lastMin = min;
    			}
    			
    			
    			for(int i=0; i<used.length && perm; i++) {
    				if(!used[i]) perm = false;
    			}
    		 
    			if(perm) str += "1";
    			else str += "0";
    			k++;
    		}
    		System.out.println(str);
    	}		
    }
    
   
   
    
   
   
	public static void sort(int [] a) {
    	ArrayList<Integer> b = new ArrayList<>();
    	for(int i: a) b.add(i);
    	Collections.sort(b);
    	for(int i=0; i<a.length; i++) a[i]= b.get(i);
    }
    
    static class FastScanner{
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st = new StringTokenizer("");
    	String next() {
    		while(!st.hasMoreTokens())
    			try {
    				st = new StringTokenizer(br.readLine());
    			} catch (IOException e){
    				e.printStackTrace();
    			}
    		return st.nextToken();
    	}
    	
    	int nextInt() {
    		return Integer.parseInt(next());
    	}
    	
    	int [] readArray(int n) {
    		int [] a = new int[n];
    		for(int i=0; i<n ; i++) a[i] = nextInt();
    		return a;
    	}
    	
    	long nextLong() {
    		return Long.parseLong(next());
    	}
    	
    	
    }
    
   
}

