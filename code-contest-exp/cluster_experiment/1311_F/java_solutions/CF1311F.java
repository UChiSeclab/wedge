import java.io.*;
import java.util.*;

public class CF1311F extends PrintWriter {
	CF1311F() { super(System.out, true); }
	Scanner sc = new Scanner(System.in);
	public static void main(String[] $) {
		CF1311F o = new CF1311F(); o.main(); o.flush();
	}

	int[] ll, rr, zz, key, val, cnt; long[] sum; int l_, r_, __ = 1;
	Random rand = new Random();
	void init(int n) {
		ll = new int[1 + n]; rr = new int[1 + n]; zz = new int[1 + n];
		key = new int[1 + n]; val = new int[1 + n];
		cnt = new int[1 + n]; sum = new long[1 + n];
	}
	int node(int v, int x) {
		zz[__] = rand.nextInt();
		key[__] = v; val[__] = x; cnt[__] = 1; sum[__] = x;
		return __++;
	}
	void pul(int u) {
		int l = ll[u], r = rr[u];
		cnt[u] = cnt[l] + 1 + cnt[r];
		sum[u] = sum[l] + val[u] + sum[r];
	}
	void split(int u, int k) {
		if (u == 0) {
			l_ = r_ = 0;
			return;
		}
		if (key[u] <= k) {
			split(rr[u], k);
			rr[u] = l_; l_ = u;
		} else {
			split(ll[u], k);
			ll[u] = r_; r_ = u;
		}
		pul(u);
	}
	int merge(int u, int v) {
		if (u == 0)
			return v;
		if (v == 0)
			return u;
		if (zz[u] < zz[v]) {
			rr[u] = merge(rr[u], v);
			pul(u);
			return u;
		} else {
			ll[v] = merge(u, ll[v]);
			pul(v);
			return v;
		}
	}
	static class P {
		int x, v;
	}
	void main() {
		int n = sc.nextInt();
		P[] pp = new P[n];
		for (int i = 0; i < n; i++)
			pp[i] = new P();
		for (int i = 0; i < n; i++)
			pp[i].x = sc.nextInt();
		for (int i = 0; i < n; i++)
			pp[i].v = sc.nextInt();
		Arrays.sort(pp, (p, q) -> p.x - q.x);
		init(n);
		long ans = 0;
		int u_ = 0;
		for (int i = 0; i < n; i++) {
			int x = pp[i].x, v = pp[i].v;
			split(u_, v);
			ans += (long) cnt[l_] * x - sum[l_];
			u_ = merge(l_, merge(node(v, x), r_));
		}
		println(ans);
	}
}
