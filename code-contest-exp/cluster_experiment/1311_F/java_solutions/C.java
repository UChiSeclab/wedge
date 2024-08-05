import java.io.*;
import java.util.*;

public class C {

	static int a, b, c;

	static int solve(int[] ans, int min, int i, int d) {
		int curr = Math.abs(i - b);

		curr += Math.abs(d - a);
		int r = c % i;
		if (c < i)
			curr += i - c;
		else
			curr += Math.min(r, i - r);
		if (curr < min) {
			ans[0] = d;
			ans[1] = i;
			if (c < i)
				ans[2] = i;
			else
				ans[2] = r < i - r ? c - r : c - r + i;

		}
		return curr;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[][] a = new int[n][2];
		TreeMap<Integer, Integer> map = new TreeMap();
		for (int i = 0; i < n; i++) {
			a[i][0] = sc.nextInt();

		}
		for (int i = 0; i < n; i++) {

			a[i][1] = sc.nextInt();
			map.put(a[i][1], 1);

		}
		int id = 1;
		for (int x : map.keySet())
			map.put(x, id++);
		FenwickTree ft = new FenwickTree(map.size());
		Arrays.sort(a, (x, y) -> x[0] - y[0]);
		long ans = 0;
		for (int[] pair : a) {
			int x = pair[0], v = pair[1];
			long[] less = ft.query(map.get(v));
			long cnt = less[0];
			ans += x * cnt - less[1];
			ft.udpate(map.get(v), new int[] { 1, x });

		}
		out.println(ans);
		out.close();

	}

	static class FenwickTree {
		long[][] bit;

		FenwickTree(int n) {
			bit = new long[n + 1][2];
		}

		void udpate(int idx, int[] a) {
			while (idx < bit.length) {
				for (int j = 0; j < 2; j++)
					bit[idx][j] += a[j];

				idx += idx & -idx;
			}
		}

		long[] query(int idx) {
			long[] ans = new long[2];
			while (idx > 0) {
				for (int j = 0; j < 2; j++)
					ans[j] += bit[idx][j];
				idx -= idx & -idx;
			}
			return ans;
		}
	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		Scanner(String fileName) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(fileName));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws NumberFormatException, IOException {
			return Double.parseDouble(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}

	}

	static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	static void shuffle(int[] a) {
		int n = a.length;
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			int tmpIdx = rand.nextInt(n);
			int tmp = a[i];
			a[i] = a[tmpIdx];
			a[tmpIdx] = tmp;
		}
	}

}