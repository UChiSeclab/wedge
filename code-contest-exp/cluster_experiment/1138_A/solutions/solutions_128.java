import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class A {
	public static void main(String[] args) throws IOException {
		FastScanner in= new FastScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int n = in.nextInt();
		int [] a = new int[n];
		ArrayList<Integer> c = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			a[i] = in.nextInt();
		}
		int curt = a[0];
		int curs = 1;
		for (int i = 1; i < a.length; i++) {
			if(curt != a[i]) {
				c.add(curs);
				curt = a[i];
				curs = 1;
			}
			else {
				curs++;
			}
		}
		c.add(curs);
		int best = 0;
		for (int i = 0; i < c.size()-1; i++) {
			best = Math.max(best, Math.min(c.get(i), c.get(i+1)));
		}
		System.out.println(best*2);
	}
	
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(InputStream in) {
			br = new BufferedReader(new InputStreamReader(in));
			st = new StringTokenizer("");
		}

		public String next() throws IOException {
			if (!st.hasMoreTokens()) {
				st = new StringTokenizer(br.readLine());
				return next();
			}
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
		public double nextDouble() throws NumberFormatException, IOException {
			return Double.parseDouble(next());
		}
		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}