import java.util.*;

import java.io.*;
import java.math.*;
import java.awt.geom.*;

import static java.lang.Math.*;

public class Solution implements Runnable {

	long mod1 = (long) 1e9 + 7;
	int mod2 = 998244353;

	public void solve() throws Exception {
		int t = sc.nextInt();
		while (t-- > 0) {
			int n=sc.nextInt();
			char arr[] = sc.nextToken().toCharArray();
			double pref[] = new double[n];
			int result[] = new int[n];
			int max = Integer.MAX_VALUE/10;
			int d=0,k=0;
			HashMap<Double,Integer> map = new HashMap<>();
			for(int i=0;i<n;i++) {
				if(arr[i]=='D') {
					d++;
				}
				else {
					k++;
				}
				if(k==0) {
					pref[i] = max;
				}
				else {
					pref[i] = (double)d/(double)k;
				}
				map.put(pref[i],map.getOrDefault(pref[i], 0)+1);
				if(k==0) {
					result[i]=i+1;
				}
				else {
					result[i] = map.get(pref[i]);
				}
			}
			for(int i:result) {
				out.print(i+" ");
			}
			out.println();
		}
			

	}

	static long gcd(long a, long b) {
		if (a == 0)
			return b;
		return gcd(b % a, a);
	}

	static void sort(int[] a) {
		ArrayList<Integer> l = new ArrayList<>();
		for (int i : a)
			l.add(i);
		Collections.sort(l);
		for (int i = 0; i < a.length; i++)
			a[i] = l.get(i);
	}

	static long ncr(int n, int r, long p) {
		if (r > n)
			return 0l;
		if (r > n - r)
			r = n - r;

		long C[] = new long[r + 1];

		C[0] = 1;

		for (int i = 1; i <= n; i++) {

			for (int j = Math.min(i, r); j > 0; j--)
				C[j] = (C[j] + C[j - 1]) % p;
		}
		return C[r] % p;
	}

	void sieveOfEratosthenes(boolean prime[], int size) {
		for (int i = 0; i < size; i++)
			prime[i] = true;

		for (int p = 2; p * p < size; p++) {
			if (prime[p] == true) {
				for (int i = p * p; i < size; i += p)
					prime[i] = false;
			}
		}
	}

	static int LowerBound(int a[], int x) { // smallest index having value >= x
		int l = -1, r = a.length;
		while (l + 1 < r) {
			int m = (l + r) >>> 1;
			if (a[m] >= x)
				r = m;
			else
				l = m;
		}
		return r;
	}

	static int UpperBound(int a[], int x) {// biggest index having value <= x
		int l = -1, r = a.length;
		while (l + 1 < r) {
			int m = (l + r) >>> 1;
			if (a[m] <= x)
				l = m;
			else
				r = m;
		}
		return l + 1;
	}

	public long power(long x, long y, long p) {
		long res = 1;
		// out.println(x+" "+y);
		x = x % p;
		if (x == 0)
			return 0;

		while (y > 0) {
			if ((y & 1) == 1)
				res = (res * x) % p;
			y = y >> 1;
			x = (x * x) % p;
		}
		return res;
	}

	static Throwable uncaught;

	BufferedReader in;
	FastScanner sc;
	PrintWriter out;

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			sc = new FastScanner(in);
			solve();
		} catch (Throwable uncaught) {
			Solution.uncaught = uncaught;
		} finally {
			out.close();
		}
	}

	public static void main(String[] args) throws Throwable {
		Thread thread = new Thread(null, new Solution(), "", (1 << 26));
		thread.start();
		thread.join();
		if (Solution.uncaught != null) {
			throw Solution.uncaught;
		}
	}

}

class FastScanner {

	BufferedReader in;
	StringTokenizer st;

	public FastScanner(BufferedReader in) {
		this.in = in;
	}

	public String nextToken() throws Exception {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(in.readLine());
		}
		return st.nextToken();
	}

	public int nextInt() throws Exception {
		return Integer.parseInt(nextToken());
	}

	public int[] readArray(int n) throws Exception {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = nextInt();
		return a;
	}

	public long nextLong() throws Exception {
		return Long.parseLong(nextToken());
	}

	public double nextDouble() throws Exception {
		return Double.parseDouble(nextToken());
	}

}