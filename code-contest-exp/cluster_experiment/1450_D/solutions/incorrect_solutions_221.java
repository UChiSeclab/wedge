import java.io.*;
import java.util.*;

public class D {

	public static void main(String[] args) {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        int t = in.nextInt();
        while(t-->0) { 
        	int n = in.nextInt();
        	int a[] = in.readArray(n);
        	int f[] = new int[n+1];
        	for(int i : a){
            	f[i]++;
        	}
        	int stop = 0;
        	for(int i=1;i<=n;i++){
        		if(f[i]==0||f[i]>1){
        			stop = i; break;
        		}
        	}
        	int ans[] = new int[n];
        	if(stop==0) ans[0] = 1;
        	if(stop==0){
        		int valid[] = new int[n+1];
        		for(int i=0;i<n-1;i++){
        			if(a[i]<a[i+1]) valid[a[i]] = 1;
        		}
        		valid[a[n-1]] = 1;
        		int min = n-1;
        		for(int i=1;i<=n;i++){
        			if(valid[i]==0){
        				min = i-1; break;
        			}
        		}
        		for(int i=1;i<=min;i++) ans[n-i] = 1;
        	}
         	else{
         		int min = 0;
         		if(f[stop]==0) min = stop-1;
         		else min = stop;
         		for(int i=1;i<=min;i++) ans[n-i] = 1;
         	}
         for(int i : ans) out.print(i);
         out.println();
        }
        out.flush();
	}
	
	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		
		String next() {
			while(!st.hasMoreTokens())
				try { st = new StringTokenizer(br.readLine()); }
				catch(IOException e) {}
			return st.nextToken();
		}
		
		String nextLine(){
			try{ return br.readLine(); } 
			catch(IOException e) { } return "";
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		int[] readArray(int n) {
			int a[] = new int[n];
			for(int i=0;i<n;i++) a[i] = nextInt();
			return a;
		}
	}
}
