import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.text.*;



public class Codeforces {
	
	static int mod=998244353 ;
	
	public static void main(String[] args) throws Exception {
		PrintWriter out=new PrintWriter(System.out);
	    FastScanner fs=new FastScanner();
	    int t=fs.nextInt();
	    outer : while(t-->0) {
	    	int n=fs.nextInt();
	    	char arr[]=fs.next().toCharArray();
	    	Map<Long,Integer> map=new HashMap<>();
	    	int ans[]=new int[n];
	    	int d=0, k=0;
	    	for(int i=0;i<n;i++) {
	    		if(arr[i]=='K') k++;
	    		else d++;
	    		int g=gcd(d,k);
	    		int a=d/g, b=k/g;
	    		long r= a*1000000 + b;
	    		if(map.containsKey(r)) {
	    			ans[i]=1+ans[map.get(r)];
	    		}
	    		else ans[i]=1;
	    		map.put(r, i);
	    	}
	    	for(int ele: ans) out.print(ele+" ");
	    	out.println();
	    }
	    out.close();
    }

	static int gcd(int  a,int  b) {
		if(b==0) return a;
		return gcd(b,a%b);
	}
    static void sort(int[] a) {
		//suffle
		int n=a.length;
		Random r=new Random();
		for (int i=0; i<a.length; i++) {
			int oi=r.nextInt(n);
			int temp=a[i];
			a[i]=a[oi];
			a[oi]=temp;
		}
		
		//then sort
		Arrays.sort(a);
	}
	// Use this to input code since it is faster than a Scanner
	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens())
				try {
					st=new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
	}
 
}