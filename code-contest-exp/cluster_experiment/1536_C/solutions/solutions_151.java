import java.util.*;
import java.io.*;

public class Main {
	static final int M = 1000000007;

	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");

		String next() {
			while (!st.hasMoreTokens())
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long[] readArrayLong(int n) {
			long[] a = new long[n];
			for (int i = 0; i < n; i++)
				a[i] = nextLong();
			return a;
		}

		int[] readArrayInt(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}
	}

	static class Pair<F, S> {
		F first;
		S second;

		Pair(F first, S second) {
			this.first = first;
			this.second = second;
		}

		public F getFirst() {
			return this.first;
		}

		public S getSecond() {
			return this.second;
		}
		// Sort on basis of first term, reverse p1.first and p2.first to sort in desc
		// Collections.sort(a, (p1, p2) -> (p1.first - p2.first));

		// Sort on the basis of second term, reverse p1.second and p2.second to sort
		// in
		// desc
		// Collections.sort(a, (p1, p2) -> (p1.second - p2.second));
	}

	static long __gcd(long a, long b) {
		if (b == 0)
			return a;
		return __gcd(b, a % b);
	}

	/* Iterative Function to calculate (x^y) in O(log y) */
	static long power(long x, long y) {
		long res = 1;

		x = x % M;
		if (x == 0)
			return 0;
		while (y > 0) {
			if ((y & 1) != 0)
				res = (res * x) % M;
			y = y >> 1;
			x = (x * x) % M;
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		FastScanner fs = new FastScanner();
		int t = fs.nextInt();
		// int t = 1;
		StringBuffer res = new StringBuffer();
		for (int tt = 1; tt <= t; tt++) {
			int n = fs.nextInt();
			String s = fs.next();
			HashMap<String, Integer> hm = new HashMap<>();
			int d = 0, k = 0;
			for (int i = 0; i < n; i++) {
				if (s.charAt(i) == 'D')
					d++;
				else
					k++;
				int a = d, b = k;
				if (d == 0) {
					b = 1;
				} else if (k == 0)
					a = 1;
				else {
					int gcd = (int) __gcd(d, k);
					a = a / gcd;
					b = b / gcd;
				}
				if (hm.containsKey(a + ":" + b)) {
					hm.put(a + ":" + b, hm.get(a + ":" + b) + 1);
				} else
					hm.put(a + ":" + b, 1);
				res.append(hm.get(a + ":" + b)).append(" ");
			}
			res.append("\n");
		}
		System.out.println(res);
	}

}
