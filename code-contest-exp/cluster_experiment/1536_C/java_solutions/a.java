import java.io.*;
import java.math.*;
import java.util.*;
public class a {
	static class FastReader {
		BufferedReader br;
		StringTokenizer st;
		
		public FastReader() {
			br = new BufferedReader(new
					InputStreamReader(System.in));
		}
		
		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
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
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
	
	static FastReader sc = new FastReader();
	static PrintWriter out = new PrintWriter(System.out);
	
	private static int[] rai(int n) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}
		return arr;
	}
	
	private static int[][] rai(int n, int m) {
		int[][] arr = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				arr[i][j] = sc.nextInt();
			}
		}
		return arr;
	}
	
	private static long[] ral(int n) {
		long[] arr = new long[n];
		for (int i = 0; i < n; i++) {
			arr[i] = sc.nextLong();
		}
		return arr;
	}
	
	private static long[][] ral(int n, int m) {
		long[][] arr = new long[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				arr[i][j] = sc.nextLong();
			}
		}
		return arr;
	}
	
	private static int ri() {
		return sc.nextInt();
	}
	
	private static long rl() {
		return sc.nextLong();
	}
	
	private static String rs() {
		return sc.next();
	}
	
	static void solve()
	{
		int n=sc.nextInt();
		String str=sc.next();
		int d=0,k=0;
		for(int i=0;i<n;i++)
		{
			if(str.charAt(i)=='D')
				d++;
			else
				k++;
			if(d==0||k==0)
				System.out.print(d+k+" ");
			else if(d==1||k==1)
				System.out.print(Math.min(d, k)+" ");
			else if((d>=k&&(double)d/((double)d/k)==k)||(k>=d&&(double)k/((double)k/d)==d)||d==k)
			{
				System.out.print(Math.min(d,k)+" ");
//				System.out.println(d+" "+k);
			}
			else
				System.out.print(Math.min(d, k)-1+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		int t;
		t=a.ri();
		while(t-->0)
			solve();
		
	}
}