import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import java.lang.*;

public class Practice {
	public static long mod = (long) Math.pow(10, 9) + 7;
	public static long tt = 0;
	public static int[] ttt = new int[2];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		// int c = 1;
		int t = Integer.parseInt(br.readLine());
		while (t-- > 0) {

			int n = Integer.parseInt(br.readLine());
			int[] ans = new int[n];
			String str = br.readLine();
			int[] arrk = new int[n];
			int[] arrd = new int[n];
			for (int i = 0; i < n; i++) {
				if (str.charAt(i) == 'D') {
					if (i == 0) {
						arrd[i]++;
					} else {
						arrd[i] = 1 + arrd[i - 1];
						arrk[i] = arrk[i - 1];
					}
				} else {
					if (i == 0) {
						arrk[i]++;
					} else {
						arrk[i] = 1 + arrk[i - 1];
						arrd[i] = arrd[i - 1];
					}
				}
				// ans[i] = 1;
			}

			// StringBuilder pre = new StringBuilder();
			for (int i = 0; i < n; i++) {
				// pre.append(str.charAt(i));
				// System.out.println(pre);
				if (ans[i] != 0) {
					continue;
				}
				int last = 1;
				ans[i] = Math.max(ans[i], 1);
				int a = (int) getGCD(arrd[i], arrk[i]);
				// System.out.println(a);
				int add = arrd[i] / a + arrk[i] / a;
				for (int j = i + add; j < n; j += add) {
					// System.out.println((long) (arrd[i] / ans[i]) * (arrk[j] - arrk[i]));
					// System.out.println(str.substring(j - i, j + 1) + " " + pre);
					if ((long) (arrd[i] / a) * (arrk[j] - arrk[i]) == (long) (arrk[i] / a) * (arrd[j] - arrd[i])) {
						ans[j] = Math.max(ans[j], last + 1);
						last++;
					}
//					if ((long) (arrd[j - i - 1] / ans[j - i - 1])
//							* (arrk[j] - arrk[j - i - 1]) == (long) (arrk[j - i - 1] / ans[j - i - 1])
//									* (arrd[j] - arrd[j - i - 1])) {
//						ans[j] = Math.max(ans[j], ans[j - i - 1] + 1);
//					}
				}
//				for (int j = 0; j < n; j++) {
//					System.out.print(ans[j] + " ");
//				}
//				System.out.println();
			}

			StringBuilder tt = new StringBuilder();
			for (int i = 0; i < n; i++) {
				tt.append(ans[i] + " ");
			}
			pw.println(tt.toString());
		}
		pw.close();

	}

	private static long getGCD(long l, long m) {
		// TODO Auto-generated method stub
		if (l == 0 || m == 0) {
			return Math.max(l, m);
		}
		long t1 = Math.min(l, m);
		long t2 = Math.max(l, m);
		while (true) {
			long temp = t2 % t1;
			if (temp == 0) {
				return t1;
			}
			t2 = t1;
			t1 = temp;
		}
	}

}

//	private static long getGCD(long l, long m) {
//// TODO Auto-generated method stub
//
//long t1 = Math.min(l, m);
//long t2 = Math.max(l, m);
//while (true) {
//	long temp = t2 % t1;
//	if (temp == 0) {
//		return t1;
//	}
//	t2 = t1;
//	t1 = temp;
//}
//}
