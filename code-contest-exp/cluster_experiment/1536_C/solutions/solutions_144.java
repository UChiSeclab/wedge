//Utilities
import java.io.*;
import java.util.*;

public class Main {
	static int t;
	static int n;
	static char[] ch;
	static int[] psaD;
	static int cntD, cntK;
	static HashMap<Pair, Integer> lastLeftOff;
	static HashMap<Pair, Integer> cntLeftOff;
	
	public static void main(String[] args) throws IOException {
		t = in.iscan();
		while (t-- > 0) {
			n = in.iscan(); ch = in.sscan().toCharArray();
			lastLeftOff = new HashMap<Pair, Integer>();
			cntLeftOff = new HashMap<Pair, Integer>();
			psaD = new int[n];
			for (int i = 0; i < n; i++) {
				psaD[i] = ch[i] == 'D' ? 1 : 0;
				if (i > 0) {
					psaD[i] += psaD[i-1];
				}
			}
			cntD = 0; cntK = 0;
			for (int i = 0; i < n; i++) {
				if (ch[i] == 'D') {
					cntD++;
				}
				else {
					cntK++;
				}
				if (cntD == 0 || cntK == 0) {
					out.print((i+1) + " ");
				}
				else {
					out.print(attack(cntD, cntK, i) + " ");
				}
			}
			out.println();
		}
		out.close();
	}
	
	static int attack(int cntD, int cntK, int r) {
		int gcd = UTILITIES.gcd(cntD, cntK);
		cntD /= gcd; cntK /= gcd;
		int leng = cntD + cntK;
		Pair tmp = new Pair(cntD, cntK);
		int ret = 0;
		int start = leng-1;
		if (lastLeftOff.containsKey(tmp)) {
			start = lastLeftOff.get(tmp) + leng;
			ret = cntLeftOff.get(tmp);
		}
		int curD, curK;
		for (int i = start; i <= r; i+= leng) {
			curD = psaD[i]; curK = i + 1 - psaD[i];
			if (curD % cntD == 0 && curK % cntK == 0 && curD / cntD == curK / cntK) {
				ret++;
			}
		}
		lastLeftOff.put(tmp, r);
		cntLeftOff.put(tmp, ret);
		return ret;
	}
	
	static int sumRangeD(int l, int r) {
		if (l > 0) {
			return psaD[r] - psaD[l-1];
		}
		return psaD[r];
	}
	
	static class Pair {
		int d, k;
		Pair(int d, int k){
			this.d = d; this.k = k;
		}
		public int hashCode() { return Objects.hash(d, k); }
		public boolean equals(Object o) {
			Pair p = (Pair)o;
			return d == p.d && k == p.k;
		}
	}
	
	static INPUT in = new INPUT(System.in);
	static PrintWriter out = new PrintWriter(System.out);
	private static class INPUT {

		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar, numChars;

		public INPUT (InputStream stream) {
			this.stream = stream;
		}

		public INPUT (String file) throws IOException {
			this.stream = new FileInputStream (file);
		}

		public int cscan () throws IOException {
			if (curChar >= numChars) {
				curChar = 0;
				numChars = stream.read (buf);
			}
			
			if (numChars == -1)
				return numChars;

			return buf[curChar++];
		}

		public int iscan () throws IOException {
			int c = cscan (), sgn = 1;
			
			while (space (c))
				c = cscan ();

			if (c == '-') {
				sgn = -1;
				c = cscan ();
			}

			int res = 0;

			do {
				res = (res << 1) + (res << 3);
				res += c - '0';
				c = cscan ();
			}
			while (!space (c));

			return res * sgn;
		}

		public String sscan () throws IOException {
			int c = cscan ();
			
			while (space (c))
				c = cscan ();

			StringBuilder res = new StringBuilder ();

			do {
				res.appendCodePoint (c);
				c = cscan ();
			}
			while (!space (c));

			return res.toString ();
		}

		public double dscan () throws IOException {
			int c = cscan (), sgn = 1;
			
			while (space (c))
				c = cscan ();

			if (c == '-') {
				sgn = -1;
				c = cscan ();
			}

			double res = 0;

			while (!space (c) && c != '.') {
				if (c == 'e' || c == 'E')
					return res * UTILITIES.fast_pow (10, iscan ());
				
				res *= 10;
				res += c - '0';
				c = cscan ();
			}

			if (c == '.') {
				c = cscan ();
				double m = 1;

				while (!space (c)) {
					if (c == 'e' || c == 'E')
						return res * UTILITIES.fast_pow (10, iscan ());

					m /= 10;
					res += (c - '0') * m;
					c = cscan ();
				}
			}

			return res * sgn;
		}

		public long lscan () throws IOException {
			int c = cscan (), sgn = 1;
			
			while (space (c))
				c = cscan ();

			if (c == '-') {
				sgn = -1;
				c = cscan ();
			}

			long res = 0;

			do {
				res = (res << 1) + (res << 3);
				res += c - '0';
				c = cscan ();
			}
			while (!space (c));

			return res * sgn;
		}

		public boolean space (int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	}

	public static class UTILITIES {

		static final double EPS = 10e-6;

		public static int lower_bound (int[] arr, int x) {
			int low = 0, high = arr.length, mid = -1;

			while (low < high) {
				mid = (low + high) / 2;

				if (arr[mid] >= x)
					high = mid;
				else
					low = mid + 1;
			}

			return low;
		}

		public static int upper_bound (int[] arr, int x) {
			int low = 0, high = arr.length, mid = -1;

			while (low < high) {
				mid = (low + high) / 2;

				if (arr[mid] > x)
					high = mid;
				else
					low = mid + 1;
			}

			return low;
		}

		public static int gcd (int a, int b) {
			return b == 0 ? a : gcd (b, a % b);
		}

		public static int lcm (int a, int b) {
			return a * b / gcd (a, b);
		}

		public static long fast_pow_mod (long b, long x, int mod) {
			if (x == 0) return 1;
			if (x == 1) return b;
			if (x % 2 == 0) return fast_pow_mod (b * b % mod, x / 2, mod) % mod;

			return b * fast_pow_mod (b * b % mod, x / 2, mod) % mod;
		}

		public static int fast_pow (int b, int x) {
			if (x == 0) return 1;
			if (x == 1) return b;
			if (x % 2 == 0) return fast_pow (b * b, x / 2);

			return b * fast_pow (b * b, x / 2);
		}

		public static long choose (long n, long k) {
			k = Math.min (k, n - k);
			long val = 1;

			for (int i = 0; i < k; ++i)
				val = val * (n - i) / (i + 1);

			return val;
		}

		public static long permute (int n, int k) {
			if (n < k) return 0;
			long val = 1;

			for (int i = 0; i < k; ++i)
				val = (val * (n - i));

			return val;
		}
	}
}
