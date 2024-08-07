// upsolve with rainboy
import java.io.*;
import java.util.*;

public class CF1101F {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int[] aa = new int[n];
		for (int i = 0; i < n; i++)
			aa[i] = Integer.parseInt(st.nextToken());
		int[] ss = new int[m];
		int[] ff = new int[m];
		int[] cc = new int[m];
		int[] rr = new int[m];
		Integer[] ii = new Integer[m];
		for (int h = 0; h < m; h++) {
			st = new StringTokenizer(br.readLine());
			ss[h] = Integer.parseInt(st.nextToken()) - 1;
			ff[h] = Integer.parseInt(st.nextToken()) - 1;
			cc[h] = Integer.parseInt(st.nextToken());
			rr[h] = Integer.parseInt(st.nextToken());
			ii[h] = h;
		}
		Arrays.sort(ii, (i, j) -> { return rr[i] - rr[j]; });
		int[][] dp = new int[n][n];
		int[][] dq = new int[n][n];
		long v = 0;
		for (int s = 0; s < n; s++)
			for (int f = s + 1; f < n; f++)
				dp[s][f] = aa[f] - aa[s];
		for (int r = 0, h_ = 0, h; r <= n; r++) {
			while (h_ < m && rr[h = ii[h_]] == r) {
				v = Math.max(v, (long) dp[ss[h]][ff[h]] * cc[h]);
				h_++;
			}
			for (int s = 0; s < n; s++) {
				int i = s;
				for (int f = s + 1; f < n; f++) {
					while (i < f && dp[s][i] < aa[f] - aa[i])
						i++;
					dq[s][f] = Math.min(dp[s][i], aa[f] - aa[i - 1]);
				}
			}
			int[][] tmp = dp; dp = dq; dq = tmp;
		}
		System.out.println(v);
	}
}
