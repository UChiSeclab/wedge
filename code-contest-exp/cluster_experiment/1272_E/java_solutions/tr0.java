import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class tr0 {
	static PrintWriter out;
	static StringBuilder sb;
	static final double EPS = 1e-9;
	static long mod = (int) 998244353;
	static int inf = (int) 1e9 + 2;
	static long[] fac;
	static int[] a;
	static int[] si;
	static ArrayList<Integer> primes;
	static ArrayList<Integer>[] ad;
	static ArrayList<pair>[] d;
	static edge[] ed;
	static int[][] vis;
	static int[] l, ch;
	static int[] occ;
	static Queue<Integer>[] can;
	static String s;
	static long[][] memo;
	static int n, m;

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		out = new PrintWriter(System.out);
		n = sc.nextInt();
		a = new int[n + 1];
		memo = new long[n + 1][2];
		for (long[] y : memo)
			Arrays.fill(y, -2);
		for (int i = 1; i <= n; i++)
			a[i] = sc.nextInt();

		for (int i = 1; i <= n; i++) {
			if (dp(i, a[i] % 2) == Long.MAX_VALUE)
				out.print(-1 + " ");
			else
				out.print((dp(i, a[i] % 2)) + " ");
		}
		out.flush();
	}

	static long dp(int i, int p) {
		if (memo[i][p] != -2)
			return memo[i][p];
		memo[i][p] =  Long.MAX_VALUE;
		if (a[i] % 2 != p)
			return memo[i][p] = 0;
		long ans = Long.MAX_VALUE;
		long ans1 = Long.MAX_VALUE;
		if (a[i] + i <= n) {
			long o = dp(a[i] + i, p);
			if (o != Long.MAX_VALUE)
				ans = o + 1;
		}
		if (i - a[i] >= 1) {
			long o = dp(i - a[i], p);
			if (o != Long.MAX_VALUE) {
				ans1 = o + 1;
			}
		}
		return memo[i][p] = Math.min(ans1, ans);
	}

	static class qu implements Comparable<qu> {
		int id, val;

		qu(int i, int v) {
			id = i;
			val = v;
		}

		public String toString() {
			return id + " " + val;
		}

		public int compareTo(qu o) {
			return id - o.id;
		}
	}

	static class seg implements Comparable<seg> {
		int a;
		int b;

		seg(int s, int e) {
			a = s;
			b = e;
		}

		public String toString() {
			return a + " " + b;
		}

		public int compareTo(seg o) {
			// if(a==o.a)
			return o.b - b;
			// return
		}
	}

	static class pair {
		int to;
		int number;

		pair(int t, int n) {
			number = n;
			to = t;
		}

		public String toString() {
			return to + " " + number;
		}
	}

	static int modPow(int a, int e)

	{
		int res = 1;
		while (e > 0) {
			if ((e & 1) == 1)
				res = (res * a);
			a = (a * a);
			e >>= 1;
		}
		return res;
	}

	static long inver(long x) {
		int a = (int) x;
		long e = (mod - 2);
		long res = 1;
		while (e > 0) {
			if ((e & 1) == 1) {
				res = (int) ((1l * res * a) % mod);
			}
			a = (int) ((1l * a * a) % mod);
			e >>= 1;
		}
		return res % mod;
	}

	static class edge implements Comparable<edge> {
		int from;
		int to;
		int number;

		edge(int f, int t, int n) {
			from = f;
			to = t;
			number = n;
		}

		public String toString() {
			return from + " " + to + " " + number;
		}

		public int compareTo(edge f) {
			return f.number - number;
		}
	}

	static void seive(int N) {
		si = new int[N];
		primes = new ArrayList<>();
		si[1] = 1;
		for (int i = 2; i < N; i++) {
			if (si[i] == 0) {
				si[i] = i;
				primes.add(i);
			}
			for (int j = 0; j < primes.size() && primes.get(j) <= si[i] && (i * primes.get(j)) < N; j++)
				si[primes.get(j) * i] = primes.get(j);

		}
	}

	static long fac(int n) {
		if (n == 0)
			return fac[n] = 1;
		if (n == 1)
			return fac[n] = 1;
		long ans = 1;
		for (int i = 1; i <= n; i++)
			fac[i] = ans = (i % mod * ans % mod) % mod;
		return ans % mod;
	}

	static long gcd(long a, long b) {

		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	static class unionfind {
		int[] p;
		int[] size;
		int[] max;
		int num;

		unionfind(int n) {
			p = new int[n];
			size = new int[n];
			max = new int[n];
			for (int i = 0; i < n; i++) {
				p[i] = i;
				max[i] = i;
			}
			Arrays.fill(size, 1);
			num = 0;
		}

		int findSet(int v) {
			if (v == p[v])
				return v;

			max[v] = Math.max(max[v], max[p[v]]);

			p[v] = findSet(p[v]);

			max[v] = Math.max(max[v], max[p[v]]);

			return p[v];
		}

		boolean sameSet(int a, int b) {
			a = findSet(a);
			b = findSet(b);
			if (a == b)
				return true;
			return false;
		}

		int max() {
			int max = 0;
			for (int i = 0; i < size.length; i++)
				if (size[i] > max)
					max = size[i];
			return max;
		}

		boolean combine(int a, int b) {
			a = findSet(a);
			b = findSet(b);
			if (a == b)
				return true;
			if (size[a] > size[b]) {
				p[b] = a;
				max[a] = Math.max(max[a], max[b]);
				size[a] += size[b];

			} else {
				p[a] = b;
				max[b] = Math.max(max[a], max[b]);
				size[b] += size[a];
			}
			return false;
		}
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream system) {
			br = new BufferedReader(new InputStreamReader(system));
		}

		public Scanner(String file) throws Exception {
			br = new BufferedReader(new FileReader(file));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public char nextChar() throws IOException {
			return next().charAt(0);
		}

		public Long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

		public void waitForInput() throws InterruptedException {
			Thread.sleep(3000);
		}
	}
}