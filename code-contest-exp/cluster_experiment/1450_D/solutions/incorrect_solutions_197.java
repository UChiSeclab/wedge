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
        	boolean valid[] = new boolean[n+1];
        	for(int i=0;i<n;i++){
        		int x = a[i];
        		int j = i;
        		int cnt = 0;
        		while(j<n&&x<=a[j]){
        			cnt++; j++;
        		}
        		j = i-1;
        		while(j>-1&&x<=a[j]){
        			cnt++; j--;
        		}
        		if(cnt>=n-x+1) valid[x] = true;
        		//out.println(x+" "+cnt);
        	}
        	valid[a[n-1]] = true;
        	int min = n;
        	for(int i=1;i<=n;i++){
        		if(!valid[i]){
        			//out.println(" "+i);
        			min = i; break;
        		}
        		//out.print(valid[i]+" ");
        	}
        	//out.println();
        	for(int i=1;i<min;i++) ans[n-i] = 1;
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
