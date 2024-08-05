import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class LogoTurtle {

	static char[] s;
	static int[][][] dp;

	static int solve(int i, int n, int dir) {
		if (i == s.length)
			return n == 0 ? 0 : -999999;
		if (dp[i][n][dir] != -1)
			return dp[i][n][dir];
		int ans = (s[i] == 'F' ? (dir == 0 ? 1 : -1) : 0)
				+ solve(i + 1, n, s[i] != 'F' ? 1 - dir : dir);
		for (int j = 1; j <= n; j++) {
			if (dir == 0) {
				if ((s[i] == 'F' && j % 2 == 0) || (s[i] == 'T' && j % 2 == 1))
					ans = Math.max(ans, 1 + solve(i + 1, n - j, 0));
				else
					ans = Math.max(ans, solve(i + 1, n - j, 1));
			} else {
				if ((s[i] == 'F' && j % 2 == 0) || (s[i] == 'T' && j % 2 == 1))
					ans = Math.max(ans, -1 + solve(i + 1, n - j, 1));
				else
					ans = Math.max(ans, solve(i + 1, n - j, 0));
			}
		}
		return dp[i][n][dir] = ans;
	}

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		sc = new StringTokenizer("");
		s = nxtCharArr();
		int n = nxtInt();
		dp = new int[s.length][n + 1][2];
		for (int[][] a : dp)
			for (int[] b : a)
				Arrays.fill(b, -1);
		out.println(Math.max(solve(0, n, 0), solve(0, n, 1)));
		br.close();
		out.close();
	}

	static BufferedReader br;
	static StringTokenizer sc;
	static PrintWriter out;

	static String nxtTok() throws IOException {
		while (!sc.hasMoreTokens()) {
			String s = br.readLine();
			if (s == null)
				return null;
			sc = new StringTokenizer(s.trim());
		}
		return sc.nextToken();
	}

	static int nxtInt() throws IOException {
		return Integer.parseInt(nxtTok());
	}

	static long nxtLng() throws IOException {
		return Long.parseLong(nxtTok());
	}

	static double nxtDbl() throws IOException {
		return Double.parseDouble(nxtTok());
	}

	static int[] nxtIntArr(int n) throws IOException {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = nxtInt();
		return a;
	}

	static long[] nxtLngArr(int n) throws IOException {
		long[] a = new long[n];
		for (int i = 0; i < n; i++)
			a[i] = nxtLng();
		return a;
	}

	static char[] nxtCharArr() throws IOException {
		return nxtTok().toCharArray();
	}
}