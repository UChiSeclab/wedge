// practice with kaiboy
import java.io.*;
import java.util.*;

public class CF769D extends PrintWriter {
	CF769D() { super(System.out, true); }
	static class Scanner {
		Scanner(InputStream in) { this.in = in; } InputStream in;
		byte[] bb = new byte[1 << 15]; int i, n;
		byte getc() {
			if (i >= n) {
				try { n = in.read(bb); } catch (IOException e) { n = 0; }
				if (n <= 0) return -1;
				i = 0;
			}
			return bb[i++];
		}
		int nextInt() {
			byte c = 0; while (c <= ' ') c = getc();
			int a = 0; while (c > ' ') { a = a * 10 + c - '0'; c = getc(); }
			return a;
		}
	}
	Scanner sc = new Scanner(System.in);
	public static void main(String[] $) {
		CF769D o = new CF769D(); o.main(); o.flush();
	}

	static final int A = 1 << 14;
	int[] kk = new int[A + 1];
	void sort(int[] aa, int n) {
		for (int i = 0; i < n; i++) {
			int a = aa[i];
			kk[a]++;
		}
		n = 0;
		for (int a = 0; a <= A; a++) {
			int k = kk[a];
			if (k > 0) {
				while (k-- > 0)
					aa[n++] = a;
				kk[a] = 0;
			}
		}
	}
	int choose(int n, int k) {
		return k == 0 ? 1 : choose(n - 1, k - 1) * n / k;
	}
	long solve(int[] aa, int n, int[] bb, int m) {
		sort(aa, n);
		sort(bb, m);
		long ans = 0;
		for (int i = 0, j = 0; i < n && j < m; ) {
			int a = aa[i], b = bb[j];
			if (a < b)
				i++;
			else if (a > b)
				j++;
			else {
				int i_ = i + 1;
				while (i_ < n && aa[i_] == a)
					i_++;
				int j_ = j + 1;
				while (j_ < m && bb[j_] == b)
					j_++;
				ans += (long) (i_ - i) * (j_ - j);
				i = i_;
				j = j_;
			}
		}
		return ans;
	}
	void main() {
		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] aa = new int[n];
		for (int i = 0; i < n; i++)
			aa[i] = sc.nextInt();
		if (k == 0) {
			sort(aa, n);
			long ans = 0;
			for (int i = 0, j; i < n; i = j) {
				j = i + 1;
				while (j < n && aa[j] == aa[i])
					j++;
				ans += (long) (j - i) * (j - i - 1) / 2;
			}
			println(ans);
			return;
		}
		int[][] pp = new int[8][]; int[] np = new int[8];
		int[][] qq = new int[8][]; int[] nq = new int[8];
		int l = Math.max(k - 7, 0), r = Math.min(k, 7);
		for (int h = l; h <= r; h++) {
			int m = n * choose(7, h);
			pp[h] = new int[m];
			qq[h] = new int[m];
		}
		for (int b = 0; b < 1 << 7; b++) {
			int h = 0;
			for (int a = b; a > 0; a &= a - 1)
				h++;
			if (h < l || h > r)
				continue;
			for (int i = 0; i < n; i++) {
				int a = aa[i];
				pp[h][np[h]++] = a ^ b;
				qq[h][nq[h]++] = a ^ b << 7;
			}
		}
		long ans = 0;
		for (int h = l; h <= r; h++)
			ans += solve(pp[h], np[h], qq[k - h], nq[k - h]);
		println(ans / 2);
	}
}
