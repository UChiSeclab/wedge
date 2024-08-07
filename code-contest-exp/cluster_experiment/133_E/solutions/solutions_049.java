import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class E133 {
	BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	StringTokenizer st = null;

	private void solution() throws IOException {
		String s = nextToken();
		int n = nextInt();
		int nap[] = new int[2];
		nap[0] = -1;
		nap[1] = 1;
		int dp[][][] = new int[s.length() + 1][n + 1][2];
		for (int i = 0; i <= s.length(); i++) {
			for (int j = 0; j <= n; j++) {
				for (int k = 0; k < 2; k++) {
					dp[i][j][k] = -100;
				}
			}
		}
		dp[0][0][0] = dp[0][0][1] = 0;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j <= n; j++) {
				for (int k = 0; k < 2; k++) {
					if (s.charAt(i - 1) == 'F') {
						dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j][k]
								+ nap[k]);
						if (j > 0) {
							dp[i][j][k] = Math.max(dp[i][j][k],
									dp[i - 1][j - 1][1 - k]);
						}
					} else {
						if (j > 0) {
							dp[i][j][k] = Math.max(dp[i][j][k],
									dp[i - 1][j - 1][k] + nap[k]);
						}
						dp[i][j][k] = Math
								.max(dp[i][j][k], dp[i - 1][j][1 - k]);
					}
				}
			}
		}
		int ans = 0;
		for (int j = 0; j <= n; j++) {
			if (j % 2 != n % 2)
				continue;
			for (int k = 0; k < 2; k++) {
				ans = Math.max(ans, dp[s.length()][j][k]);
			}
		}
		System.out.println(ans);
	}

	String nextToken() throws IOException {
		if (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(bf.readLine());
		}
		return st.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	public void print(int a[]) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
	}

	public static void main(String args[]) throws IOException {
		new E133().solution();
	}
}