// 07-Jun-2021
import java.util.*;
import java.io.*;

public class A {
	static class FastReader {

		BufferedReader br;
		StringTokenizer st;

		private FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
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

		int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}

		int[] nextIntArrayOne(int n) {
			int[] a = new int[n + 1];
			for (int i = 1; i < n + 1; i++)
				a[i] = nextInt();
			return a;
		}

		long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < n; i++)
				a[i] = nextLong();
			return a;
		}

		long[] nextLongArrayOne(int n) {
			long[] a = new long[n + 1];
			for (int i = 1; i < n + 1; i++)
				a[i] = nextLong();
			return a;
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

	public static void main(String[] args) {
		FastReader s = new FastReader();
		StringBuilder str = new StringBuilder();

		int t = s.nextInt();

		while (t-- > 0) {
			int n = s.nextInt();
			char arr[] = s.nextLine().toCharArray();
			Map<String,Integer> map = new HashMap<>();
			
			int d = 0, k = 0;
			for(char c : arr) {
				if(c =='D') d++; else k++;
				
				int g = (int)gcd(d, k);
				
				int a = d / g;
				int b = k / g;
				Ratio obj = new Ratio(a,b);
				if(map.containsKey(obj.toString())) {
					map.put(obj.toString(), map.get(obj.toString()) + 1);
				}else {
					map.put(obj.toString(), 1);
				}
				
				int ans = map.get(obj.toString());
				str.append(ans + " ");
				
				
			}
			
			str.append("\n");
			
			
			
		}
		System.out.println(str);
	}
	
	private static long gcd(long a, long b) {
		if(b==0) return a;
		return gcd(b,a%b);
	}
	
	static class Ratio{
		int a,b;
		
		Ratio(int a,int b){
			this.a = a;
			this.b = b;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "{" + this.a +":" + this.b +"} ";
		}
		
	}

}
