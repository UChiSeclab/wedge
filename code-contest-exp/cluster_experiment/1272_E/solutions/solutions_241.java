import java.io.*;
import java.util.*;
 
 
 
public class Main {
	
	static InputReader in = new InputReader(System.in);
	static PrintWriter out = new PrintWriter(System.out);
	
	static int oo = (int)1e9;
//	static long oo = (long)1e15;
	static int mod = 1_000_000_007;
	
	static int[] di = {1, 0, 0, -1};
	static int[] dj = {0, -1, 1, 0};
	
	static int M = 40;
	static double EPS = 1e-13;
	
	
	public static void main(String[] args) throws IOException {

		int n = in.nextInt();
		int[] a = in.nextIntArray(n);
		ArrayList<Integer>[] adj = new ArrayList[n];
		for(int i = 0; i < n; ++i)
			adj[i] = new ArrayList<>();
		
		for(int i = 0; i < n; ++i) {
			int j = i + a[i];
			if(j >= 0 && j < n)
				adj[j].add(i);
			j = i - a[i];
			if(j >= 0 && j < n)
				adj[j].add(i);
		}
		boolean[] visit = new boolean[n];
		Queue<Pair> q = new ArrayDeque<>();
		
		int[] ans = new int[n];
		for(int i = 0; i < n; ++i) {
			if(a[i] % 2 == 0) {
				q.add(new Pair(i, 0));
				visit[i] = true;
			}
		}
		while(!q.isEmpty()) {
			int u = q.peek().first;
			int d = q.peek().second;
			q.poll();
			if(d != 0)
				ans[u] = d;
			for(int v : adj[u]) {
				if(!visit[v]) {
					q.add(new Pair(v, d+1));
					visit[v] = true;
				}
			}
		}
		Arrays.fill(visit, false);
		for(int i = 0; i < n; ++i) {
			if(a[i] % 2 == 1) {
				q.add(new Pair(i, 0));
				visit[i] = true;
			}
		}
		while(!q.isEmpty()) {
			int u = q.peek().first;
			int d = q.peek().second;
			q.poll();
			if(d != 0)
				ans[u] = d;
			for(int v : adj[u]) {
				if(!visit[v]) {
					q.add(new Pair(v, d+1));
					visit[v] = true;
				}
			}
		}
		for(int i = 0; i < n; ++i)
			out.print( (ans[i] == 0 ? -1 : ans[i]) + " ");
		
		
		out.close();
	}
	
	static int find(int[] g, int x) {
		return g[x] = g[x] == x ? x : find(g, g[x]);
	}
	
	static void union(int[] g, int[] size, int x, int y) {
		x = find(g, x); y = find(g, y);
		if(x == y)
			return;
		if(size[x] < size[y]) {
			g[x] = y;
			size[y] += size[x];
		}
		else {
			g[y] = x;
			size[x] += size[y];
		}
	}
	
	static class Segment {
		Segment left, right;
		int size, val;
		int time, lazy;
		
		public Segment(int[] a, int l, int r) {
			super();
			if(l == r) {
				return;
			}
			int mid = (l + r) / 2;
			left = new Segment(a, l, mid);
			right = new Segment(a, mid+1, r);
		}
		
		boolean covered(int ll, int rr, int l, int r) {
			return ll <= l && rr >= r;
		}
		
		boolean noIntersection(int ll, int rr, int l, int r) {
			return ll > r || rr < l;
		}
		
		void lazyPropagation() {
			if(lazy != 0) {
				if(left != null) {
					left.setLazy(this.time, this.lazy);
					right.setLazy(this.time, this.lazy);
				}
				else {
					val = lazy;
				}
			}
		}
		
		void setLazy(int time, int lazy) {
			if(this.time != 0 && this.time <= time)
				return;
			this.time = time;
			this.lazy = lazy;
		}
		
		int query(int ll, int rr, int l, int r) {
			lazyPropagation();
			if(noIntersection(ll, rr, l, r))
				return 0;
			if(covered(ll, rr, l, r))
				return val;
			int mid = (l + r) / 2;
			int leftSum = left.query(ll, rr, l, mid);
			int rightSum = right.query(ll, rr, mid+1, r);
			return leftSum + rightSum;
		}
		
		int query(int k, int l, int r) {
			Segment trace = this;
			while(l < r) {
				int mid = (l + r) / 2;
				if(trace.left.size > k) {
					trace = trace.left;
					r = mid;
				}
				else {
					k -= trace.left.size;
					trace = trace.right;
					l = mid + 1;
				}
			}
			return l;
		}
		
		void update(int ll, int rr, int time, int knight, int l, int r) {
			lazyPropagation();
			if(noIntersection(ll, rr, l, r))
				return;
			if(covered(ll, rr, l, r)) {
				setLazy(time, knight);
				return;
			}
			int mid = (l + r) / 2;
			left.update(ll, rr, time, knight, l, mid);
			right.update(ll, rr, time, knight, mid+1, r);
		}
	}
	
	static long pow(long a, long n, long mod) {
		if(n == 0)
			return 1;
		if(n % 2 == 1)
			return a * pow(a, n-1, mod) % mod;
		long x = pow(a, n / 2, mod);
		return x * x % mod;
	}
	
	
	static int[] getPi(char[] a) {
		int m = a.length;
		int j = 0;
		int[] pi = new int[m];
		for(int i = 1; i < m; ++i) {
			while(j > 0 && a[i] != a[j])
				j = pi[j-1];
			if(a[i] == a[j]) {
				pi[i] = j + 1;
				j++;
			}
		}
		return pi;
	}
	
	static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}
	
	static boolean nextPermutation(int[] a) {
		for(int i = a.length - 2; i >= 0; --i) {
			if(a[i] < a[i+1]) {
				for(int j = a.length - 1; ; --j) {
					if(a[i] < a[j]) {
						int t = a[i];
						a[i] = a[j];
						a[j] = t;
						for(i++, j = a.length - 1; i < j; ++i, --j) {
							t = a[i];
							a[i] = a[j];
							a[j] = t;
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	static void shuffle(Object[] a) {
		Random r = new Random();
		for(int i = a.length - 1; i > 0; --i) {
			int si = r.nextInt(i);
			Object t = a[si];
			a[si] = a[i];
			a[i] = t;
		}
	}
	
	static void shuffle(int[] a) {
		Random r = new Random();
		for(int i = a.length - 1; i > 0; --i) {
			int si = r.nextInt(i);
			int t = a[si];
			a[si] = a[i];
			a[i] = t;
		}
	}
	
	static void shuffle(long[] a) {
		Random r = new Random();
		for(int i = a.length - 1; i > 0; --i) {
			int si = r.nextInt(i);
			long t = a[si];
			a[si] = a[i];
			a[i] = t;
		}
	}
	
	static int lower_bound(int[] a, int n, int k) {
		int s = 0;
		int e = n;
		int m;
		while (e - s > 0) {
			m = (s + e) / 2;
			if (a[m] < k)
				s = m + 1;
			else
				e = m;
		}
		return e;
	}
	static int lower_bound(long[] a, int n, long k) {
		int s = 0;
		int e = n;
		int m;
		while (e - s > 0) {
			m = (s + e) / 2;
			if (a[m] < k)
				s = m + 1;
			else
				e = m;
		}
		return e;
	}
	
	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}
	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}
	
	static class Pair implements Comparable<Pair> {
		int first, second;
 
		public Pair(int first, int second) {
			super();
			this.first = first;
			this.second = second;
		}
 
		@Override
		public int compareTo(Pair o) {
			return this.first != o.first ? this.first - o.first : this.second - o.second;
		}
		
//		@Override
//		public int compareTo(Pair o) {
//			return this.first != o.first ? o.first - this.first : o.second - this.second;
//		}
 
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + first;
			result = prime * result + second;
			return result;
		}
 
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (first != other.first)
				return false;
			if (second != other.second)
				return false;
			return true;
		}
	}
}
 
 
 
class InputReader {
 
	private final InputStream stream;
	private final byte[] buf = new byte[8192];
	private int curChar, snumChars;
 
	public InputReader(InputStream st) {
		this.stream = st;
	}
 
	public int read() {
		if (snumChars == -1)
			throw new InputMismatchException();
		if (curChar >= snumChars) {
			curChar = 0;
			try {
				snumChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (snumChars <= 0)
				return -1;
		}
		return buf[curChar++];
	}
 
	public int nextInt() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}
 
	public long nextLong() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		long res = 0;
		do {
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}
 
	public int[] nextIntArray(int n) {
		int a[] = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = nextInt();
		}
		return a;
	}
 
	public String readString() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isSpaceChar(c));
		return res.toString();
	}
 
	public String nextLine() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isEndOfLine(c));
		return res.toString();
	}
 
	public boolean isSpaceChar(int c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}
 
	private boolean isEndOfLine(int c) {
		return c == '\n' || c == '\r' || c == -1;
	}
 
}