import java.util.Arrays;
import java.util.Scanner;

public class e {
	
	static int n, oo = 1_000_000_000;
	static int[] arr;
	static int[][] dp;
	
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		
		n = stdin.nextInt();
		
		arr = new int[n];
		dp = new int[n][2];
		for (int[] i : dp) Arrays.fill(i, -1);
		
		for (int i = 0; i < n; i ++) arr[i] = stdin.nextInt();
		
		int[] ans = new int[n];
		
		for (int i = 0; i < n; i ++) {
			int odd = arr[i] % 2 == 0 ? 0 : 1;
			ans[i] = go(i, odd, new boolean[n]);
		}
		
		for (int i = 0; i < n; i ++) System.out.print(ans[i] >= oo ? -1 + " " : ans[i] + " ");
	}
	
	static int go (int indx, int odd, boolean[] vis) {
		
		if (indx < 0 || indx >= n) return oo;
		if (vis[indx]) return oo;
		if (arr[indx] % 2 == 0 && odd == 1) return 0;
		if (arr[indx] % 2 != 0 && odd == 0) return 0;
		if (dp[indx][odd] != -1) return dp[indx][odd];
		
		vis[indx] = true;
		
		int plus = 1 + go (indx + arr[indx], odd, vis);
		int minus = 1 + go (indx - arr[indx], odd, vis);
		
		return dp[indx][odd] = Math.min(plus, minus);
	}
}
