import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Main {

	InputStream is;
	PrintWriter out;
	String INPUT = "";

	private static final long M = 1000000007L;

	List<Integer> g[];
	int[] a;
	int dp[][];

	void solve() {
		StringBuffer sb = new StringBuffer();
		int N = ni();
		a = na(N);
		dp = new int[N][2];
		for (int i = 0; i < N; i++) {
			dp[i][a[i] % 2] = 0;
			dp[i][1 - a[i] % 2] = Integer.MAX_VALUE / 2;
		}
		vis = new boolean[N];
		for (int i = 0; i < N; i++) {
			if (!vis[i]) {
				dfs(i);
			}
			int ans = Math.max(dp[i][0], dp[i][1]);
			if (ans >= Integer.MAX_VALUE / 2)
				ans = -1;
			sb.append(ans).append(' ');
		}
		out.println(sb);
	}

	private void dfs(int i) {
		if (i < 0 || i >= a.length || vis[i])
			return;
		vis[i] = true;
		if (i + a[i] < a.length) {
			dfs(i + a[i]);
			dp[i][0] = Math.min(dp[i][0], dp[i + a[i]][0] + 1);
			dp[i][1] = Math.min(dp[i][1], dp[i + a[i]][1] + 1);
		}
		if (i - a[i] >= 0) {
			dfs(i - a[i]);
			dp[i][0] = Math.min(dp[i][0], dp[i - a[i]][0] + 1);
			dp[i][1] = Math.min(dp[i][1], dp[i - a[i]][1] + 1);
		}

	}

	private List<Integer>[] getG(int N, int M) {
		List<Integer>[] ret = new List[N];
		for (int i = 0; i < N; i++)
			ret[i] = new ArrayList<Integer>();
		for (int i = 0; i < M; i++) {
			int u = ni() - 1;
			int v = ni() - 1;
			ret[u].add(v);
		}
		return ret;
	}

	private long gcd(long a, long b) {
		while (a != 0) {
			long tmp = b % a;
			b = a;
			a = tmp;
		}
		return b;
	}

	void run() throws Exception {
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		out = new PrintWriter(System.out);

		long s = System.currentTimeMillis();
		solve();
		out.flush();
		if (!INPUT.isEmpty())
			tr(System.currentTimeMillis() - s + "ms");
	}

	public static void main(String[] args) throws Exception {
		new Main().run();
	}

	private byte[] inbuf = new byte[1024];
	public int lenbuf = 0, ptrbuf = 0;
	private boolean vis[];

	private int readByte() {
		if (lenbuf == -1)
			throw new InputMismatchException();
		if (ptrbuf >= lenbuf) {
			ptrbuf = 0;
			try {
				lenbuf = is.read(inbuf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (lenbuf <= 0)
				return -1;
		}
		return inbuf[ptrbuf++];
	}

	private boolean isSpaceChar(int c) {
		return !(c >= 33 && c <= 126);
	}

	private int skip() {
		int b;
		while ((b = readByte()) != -1 && isSpaceChar(b))
			;
		return b;
	}

	private double nd() {
		return Double.parseDouble(ns());
	}

	private char nc() {
		return (char) skip();
	}

	private String ns() {
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != '
									// ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}

	private char[] ns(int n) {
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while (p < n) {
			if (!(isSpaceChar(b)))
				buf[p++] = (char) b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}

	private char[][] nm(int n, int m) {
		char[][] map = new char[n][];
		for (int i = 0; i < n; i++)
			map[i] = ns(m);
		return map;
	}

	private int[] na(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		return a;
	}

	private Integer[] na2(int n) {
		Integer[] a = new Integer[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		return a;
	}

	private int[][] na(int n, int m) {
		int[][] a = new int[n][];
		for (int i = 0; i < n; i++)
			a[i] = na(m);
		return a;
	}

	private Integer[][] na2(int n, int m) {
		Integer[][] a = new Integer[n][];
		for (int i = 0; i < n; i++)
			a[i] = na2(m);
		return a;
	}

	private int ni() {
		int num = 0, b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}

		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private long nl() {
		long num = 0;
		int b;
		boolean minus = false;
		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = readByte();
		}

		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private static void tr(Object... o) {
		System.out.println(Arrays.deepToString(o));
	}
}