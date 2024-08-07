import java.io.*;
import java.util.*;

public class CF1450D extends PrintWriter {
	CF1450D() { super(System.out); }
	Scanner sc = new Scanner(System.in);
	public static void main(String[] $) {
		CF1450D o = new CF1450D(); o.main(); o.flush();
	}

	void main() {
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			int[] aa = new int[n + 2];
			aa[0] = aa[n + 1] = 0;
			for (int i = 1; i <= n; i++)
				aa[i] = sc.nextInt();
			int[] ll = new int[n + 1];
			int[] rr = new int[n + 1];
			int[] qu = new int[n + 1]; int cnt;
			cnt = 0; qu[cnt++] = 0;
			for (int i = 1; i <= n; i++) {
				int a = aa[i];
				while (aa[qu[cnt - 1]] >= a)
					cnt--;
				ll[i] = qu[cnt - 1];
				qu[cnt++] = i;
			}
			cnt = 0; qu[cnt++] = n + 1;
			for (int i = n; i >= 1; i--) {
				int a = aa[i];
				while (aa[qu[cnt - 1]] >= a)
					cnt--;
				rr[i] = qu[cnt - 1];
				qu[cnt++] = i;
			}
			int[] kk = new int[n + 1];
			for (int i = 1; i <= n; i++) {
				int a = aa[i];
				int k = rr[i] - ll[i] - 1;
				kk[a] = Math.max(kk[a], k);
			}
			int[] dd = new int[n + 2];
			for (int a = 1; a <= n; a++) {
				int k = kk[a];
				int b = n + 1 - k;
				if (a > b)
					k -= a - b;
				dd[k]++;
			}
			char[] cc = new char[n];
			for (int k = n; k >= 1; k--) {
				int d = dd[k] += dd[k + 1];
				cc[k - 1] = d == n + 1 - k ? '1' : '0';
			}
			println(cc);
		}
	}
}
