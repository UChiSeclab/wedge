
import java.util.*;
import java.io.*;
import java.text.*;

public class F1311 {

	public static long solve(ArrayList<Pair> arr) {
		FenwickTree cnt = new FenwickTree(arr.size());
		FenwickTree val = new FenwickTree(arr.size());
		TreeSet<Integer> ts = new TreeSet<Integer>();
		for (Pair x : arr)
			ts.add(x.v);
		TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
		while (!ts.isEmpty()) {
			tm.put(ts.pollFirst(), tm.size() + 1);
		}
//		System.out.println(tm);
		Collections.sort(arr, (a, b) -> a.x - b.x);
		long sum = 0;
		for (Pair p : arr) {
			int lim = tm.get(p.v);
			if (lim > 0) {
				int cntt = cnt.rsq(lim);
				int value = val.rsq(lim);
				sum += p.x * cntt - value;
			}
			cnt.point_update(lim, 1);
			val.point_update(lim, p.x);
//			System.out.println(sum);
		}
		return sum;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] x = sc.nextIntArr(n);
		int[] v = sc.nextIntArr(n);
		ArrayList<Pair> r = new ArrayList<Pair>();
		for (int i = 0; i < n; i++) {
			r.add(new Pair(x[i], v[i]));
		}
		pw.println(solve(r));
		pw.close();

	}

	static class FenwickTree { // one-based DS

		int n;
		int[] ft;

		FenwickTree(int size) {
			n = size;
			ft = new int[n + 1];
		}

		int rsq(int b) // O(log n)
		{
			int sum = 0;
			while (b > 0) {
				sum += ft[b];
				b -= b & -b;
			} // min?
			return sum;
		}

		int rsq(int a, int b) {
			return rsq(b) - rsq(a - 1);
		}

		void point_update(int k, int val) // O(log n), update = increment
		{
			while (k <= n) {
				ft[k] += val;
				k += k & -k;
			} // min?
		}

		int point_query(int idx) // c * O(log n), c < 1
		{
			int sum = ft[idx];
			if (idx > 0) {
				int z = idx ^ (idx & -idx);
				--idx;
				while (idx != z) {
					sum -= ft[idx];
					idx ^= idx & -idx;
				}
			}
			return sum;
		}

	}

	static class Pair {
		int x, v;

		public Pair(int a, int b) {
			x = a;
			v = b;
		}
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public Scanner(FileReader r) {
			br = new BufferedReader(r);
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public int[] nextIntArr(int n) throws IOException {
			int[] arr = new int[n];
			for (int i = 0; i < arr.length; i++)
				arr[i] = nextInt();
			return arr;
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

	}
}
