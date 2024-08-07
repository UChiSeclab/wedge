// javac e.java && java _

import java.io.*;
import java.util.*;

public class e {
	public static void main(String[] args) { new e(); }
	FS in = new FS();
	PrintWriter out = new PrintWriter(System.out);
	
	int n, trash = 1 << 20;
	int[] pd, l, r;
	int[][] dp;
	boolean[][] on, done;
	
	e() {
		n = in.nextInt();
		pd = new int[n];
		l = new int[n];
		r = new int[n];
		for (int i = 0; i < n; i++) {
			int a = in.nextInt();
			l[i] = i - a;
			if (l[i] < 0)
				l[i] = trash;
			r[i] = i + a;
			if (r[i] >= n)
				r[i] = trash;
			pd[i] = (a & 1);
		}

		dp = new int[2][n];
		Arrays.fill(dp[0], trash);
		Arrays.fill(dp[1], trash);

		on = new boolean[2][n];
		done = new boolean[2][n];
		ArrayDeque<Integer> q;
		for (int i = 0; i < n; i++)
			if (!done[pd[i] ^ 1][i])
				dp(i, pd[i] ^ 1);

		for (int i = 0; i < n; i++)
			if (dp[pd[i] ^ 1][i] == trash)
				dp[pd[i] ^ 1][i] = -1;
		for (int i = 0; i < n; i++)
			out.print(dp[pd[i] ^ 1][i] + " ");
		out.println();
		out.close();
	}
	
	int dp(int i, int p) {
		on[p][i] = true;
		if (pd[i] == p)
			return dp[p][i] = 0;
		if (dp[p][i] != trash)
			return dp[p][i];
		int ans = trash;
		if (l[i] != trash) {
			if (!on[p][l[i]])
				ans = min(ans, 1 + dp(l[i], p));
			else if (pd[l[i]] == p)
				ans = 1;
		}
		if (r[i] != trash) {
			if (!on[p][r[i]])
				ans = min(ans, 1 + dp(r[i], p));
			else if (pd[r[i]] == p)
				ans = 1;
		}
		on[p][i] = false;
		done[p][i] = true;
		return dp[p][i] = ans;
	}

	int min(int a, int b) { if (a < b) return a; return b; }	
	int max(int a, int b) { if (a > b) return a; return b; }	
	long min(long a, long b) { if (a < b) return a; return b; }	
	long max(long a, long b) { if (a > b) return a; return b; }	

	boolean z(int x) { if (x == 0) return true; return false; }
	boolean z(long x) { if (x == 0) return true; return false; }
	
	class FS {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens()) {
				try { st = new StringTokenizer(br.readLine()); }
				catch (Exception e) {}
			} return st.nextToken();
		}
		int nextInt() { return Integer.parseInt(next()); }
		long nextLong() { return Long.parseLong(next()); }
		double nextDouble() { return Double.parseDouble(next()); }
	}
}

