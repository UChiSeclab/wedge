import java.util.*;
import java.io.*;
import java.math.*;

public class cf {
	static PrintWriter pw = new PrintWriter(System.out);

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			arr = new int[n];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = sc.nextInt();
			}
			sp = new SparseTable(arr, n);
			int a = bs();
			pw.print(chk(1) ? 1 : 0);
			for (int i = 2; i < a; i++) {
				pw.print(0);
			}
			for (int i = a; i <= n; i++) {
				pw.print(1);
			}
			pw.println();
		}
		pw.close();
	}

	static int[] arr;

	static int bs() {
		int start = 2, end = arr.length, ans = arr.length + 1;
		while (start <= end) {
			int mid = (start + end) / 2;
			if (chk(mid)) {
				end = mid - 1;
				ans = mid;
			} else {
				start = mid + 1;
			}
		}
		return ans;
	}

	static boolean chk(int k) {
		HashSet<Integer> hs = new HashSet<Integer>();
//		pw.println(k);
		for (int i = 0; i <= arr.length - k; i++) {
//			pw.print(sp.query(i, i + k - 1) + " ");
			hs.add(sp.query(i, i + k - 1));
		}
//		pw.println(hs);
		for (int i = 1; i <= arr.length - k + 1; i++) {
			if (!hs.contains(i)) {
				return false;
			}
		}
		return true;
	}

	static SparseTable sp;

	static class SparseTable {
		int[] log, min[], arr;
		int inf = (int) 1e9;

		SparseTable(int[] in, int n) {
			arr = in;
			log = new int[n + 1];
			for (int i = 2; i <= n; i++)
				log[i] = log[i / 2] + 1;

			min = new int[n][22];
			for (int i = 0; i < n; i++) {
				min[i][0] = i;
			}
			for (int i = 1, len = 2; len <= n; i++, len <<= 1) {
				for (int j = 0; j < n; j++) {
					int u = min[j][i - 1], v = j + (len >> 1) >= n ? inf : min[j + (len >> 1)][i - 1];
					if (v == inf) {
						min[j][i] = u;
					} else
						min[j][i] = (arr[u] < arr[v] ? u : v);
				}
			}
		}

		int query(int l, int r) {
			int len = r - l + 1;
			int lg = log[len];
			int u = min[l][lg];
			int v = min[r - (1 << lg) + 1][lg];
			int bestIdx = arr[u] < arr[v] ? u : v;
			return arr[bestIdx];// or return bestIdx if we want the best index
		}

	}

	public static class pair implements Comparable<pair> {
		int x;
		int y;

		public pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return x + " " + y;
		}

		public boolean equals(Object o) {
			if (o instanceof pair) {
				pair p = (pair) o;
				return p.x == x && p.y == y;
			}
			return false;
		}

		public int hashCode() {
			return new Double(x).hashCode() * 31 + new Double(y).hashCode();
		}

		public int compareTo(pair other) {
			if (this.x == other.x) {
				return Long.compare(this.y, other.y);
			}
			return Long.compare(this.x, other.x);
		}
	}

	public static class tuble implements Comparable<tuble> {
		int x;
		int y;
		int z;

		public tuble(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public String toString() {
			return x + " " + y + " " + z;
		}

		public int compareTo(tuble other) {
			if (this.x == other.x) {
				if (this.y == other.y)
					return this.z - other.z;
				return this.y - other.y;
			} else {
				return this.x - other.x;
			}
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
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public long[] nextlongArray(int n) throws IOException {
			long[] a = new long[n];
			for (int i = 0; i < n; i++)
				a[i] = nextLong();
			return a;
		}

		public Long[] nextLongArray(int n) throws IOException {
			Long[] a = new Long[n];
			for (int i = 0; i < n; i++)
				a[i] = nextLong();
			return a;
		}

		public int[] nextIntArray(int n) throws IOException {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}

		public Integer[] nextIntegerArray(int n) throws IOException {
			Integer[] a = new Integer[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

	}

}