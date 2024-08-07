import java.io.*;
import java.util.*;

public class Main {
	static class Pair implements Comparable<Pair> {
		int x, v;

		Pair(int x, int v) {
			this.x = x;
			this.v = v;
		}

		public int compareTo(Pair p) {
			return x - p.x;
		}
	}

	static class SegmentTree {
		int n;
		long[] t;

		SegmentTree(int n) {
			this.n = n;
			int h = (int)(Math.ceil(Math.log(n) / Math.log(2)));
			t = new long[1 << (h + 1)];
		}

		void update(int idx, long diff) {
			update(1, 0, n - 1, idx, diff);
		}

		void update(int n, int s, int e, int idx, long diff) {
			if (idx < s || e < idx) {
				return;
			}
			t[n] += diff;
			if (s != e) {
				update(2 * n, s, (s + e) / 2, idx, diff);
				update(2 * n + 1, (s + e) / 2 + 1, e, idx, diff);
			}
		}

		long sum(int l, int r) {
			return sum(1, 0, n - 1, l, r);
		}

		long sum(int n, int s, int e, int l, int r) {
			if (r < s || e < l) {
				return 0;
			} else if (l <= s && e <= r) {
				return t[n];
			} else {
				return sum(2 * n, s, (s + e) / 2, l, r) + sum(2 * n + 1, (s + e) / 2 + 1, e, l, r);
			}
		}
	}

	public static void main(String[] args) {
		InputReader in = new InputReader();

		int N = in.nextInt();
		int[] x = new int[N];
		int[] v = new int[N];
		Pair[] p = new Pair[N];
		for (int i = 0; i < N; i++) {
			x[i] = in.nextInt();
		}
		for (int i = 0; i < N; i++) {
			v[i] = in.nextInt();
		}
		for (int i = 0; i < N; i++) {
			p[i] = new Pair(x[i], v[i]);
		}
		Arrays.sort(p, new Comparator<Pair>() {
			public int compare(Pair p1, Pair p2) {
				return Integer.compare(p1.v, p2.v);
			}
		});
		Map<Integer, Integer> map = new HashMap<>();
		int idx = 0;
		for (int i = 0; i < N; i++) {
			if (map.containsKey(p[i].v)) {
				continue;
			}
			map.put(p[i].v, idx++);
		}
		Arrays.sort(p);
		SegmentTree seg1 = new SegmentTree(N);
		SegmentTree seg2 = new SegmentTree(N);
		long ans = 0;
		for (int i = 0; i < N; i++) {
			ans += seg2.sum(0, map.get(p[i].v)) * p[i].x;
			ans -= seg1.sum(0, map.get(p[i].v));
			seg1.update(map.get(p[i].v), p[i].x);
			seg2.update(map.get(p[i].v), 1);
		}

		System.out.println(ans);
	}

	static class InputReader {
		public BufferedReader reader;
		public StringTokenizer st;

		public InputReader() {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}

		public String next() {
			while (st == null || !st.hasMoreTokens()) {
				st = new StringTokenizer(nextLine());
			}
			return st.nextToken();
		}

		public String nextLine() {
			try {
				return reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}
	}
}
