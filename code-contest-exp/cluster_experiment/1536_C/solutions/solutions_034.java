/*
 * Author:- Tanmay
 */
import java.io.*;
import java.util.*;
public class Main {
	static int gcd(int a,int b){
        if(b==0)return a;
        return gcd(b,a%b);
    }
	public static void main(String[] args) {
		FastScanner sc = new FastScanner();
		PrintWriter out = new PrintWriter(System.out);
//		int t=1;	
		int t=sc.nextInt();
//		outer:	
		while(t-- >0) {
			int n = sc.nextInt();
			String s = sc.next();
			char c[] = s.toCharArray();
			int a[] = new int[n];
			Map<String,Integer> map = new HashMap<>();
			int d=0,k=0;
			for(int i=0 ; i<n ; i++) {
				if(c[i] == 'D') d++;
				else k++;
				int gcd = gcd(d,k);
				String str = d/gcd+","+k/gcd;
				map.put(str,map.getOrDefault(str, 0)+1);
				out.print(map.get(str)+" ");
			}
			out.println();
		}
		out.flush();
		out.close();
	}
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
		int [] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
		long [] longArray(int n) {
			long[] a=new long[n];
			for(int i=0 ; i<n ; i++) a[i]=nextLong();
			return a;
		}
		double nextDouble() {
			return Double.parseDouble(next());
		}
	}
} 