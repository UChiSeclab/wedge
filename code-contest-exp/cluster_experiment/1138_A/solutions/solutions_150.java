import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class cf1138a {

	public static void main(String[] args) {
		Scanner sc=  new Scanner(System.in);
//		BufferedReader bu = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		int n = sc.nextInt();
		
		int[] a = new int[n];
		

		
//		int[] cnt = new int[n+1];
		
//		int[] last = new int[2*n+5];
		for (int i =0; i < n; i++) {
			a[i] = sc.nextInt();
		}
		
		int best = 1;
		int type = a[0];
		int streak = 1;
		int lstreak = 0;
		for (int i =1; i < n; i++) {
			if (type == a[i]) {
				streak++;
			}else {
				if (lstreak != 0) {
					best = Math.max(2*Math.min(streak, lstreak), best);
				}
				lstreak = streak;

				streak = 1;
				type = a[i];
			}
		}
		best = Math.max(2*Math.min(streak, lstreak), best);
//		Arrays.fill(last, -1);
//		int best = 1;
//		cnt[0] = 0;
//		last[cnt[0]+n] = 0;
//		for (int i =1; i <= n; i++) {
//			if (i > 0) {
//				cnt[i] += cnt[i-1];
//			}
//			if (a[i-1] == 1) {
//				cnt[i]--;
//			}else {
//				cnt[i]++;
//			}
//
//			if (last[cnt[i]+n] != -1) {
//				best = Math.max(best, i-last[cnt[i]+n]);
//				last[cnt[i]+n] = i;
//			}else {
//				last[cnt[i]+n] = i;
//			}
//		}
		out.println(best);
		out.close();
	}

}

/*

5
1 1 1 2 1

2
1 2

*/
