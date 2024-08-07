import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

	public static void main(final String[] args) {
		new Main();
	}

	public Main() {
		final FastScanner fs = new FastScanner();
		final java.io.PrintWriter out = new java.io.PrintWriter(System.out);
		solve(fs, out);
		out.flush();
	}

	public void solve(final FastScanner fs, final java.io.PrintWriter out) {
		int t = fs.nextInt();
		while(t --> 0) {
			final int n = fs.nextInt();
			final int[] a = new int[n];
			for (int i = 0;i < n;++ i) a[i] = fs.nextInt();
			final ArrayList<ArrayList<Integer>> point = new ArrayList<>();
			for (int i = 0;i <= n;++ i) point.add(new ArrayList<>());
			for (int i = 0;i < n;++ i) point.get(a[i]).add(i);
			final TreeSet<Integer> set = new TreeSet<>();
			final TreeMap<Integer, Integer> map = new TreeMap<>();
			for (int i = -1;i <= n;++ i) set.add(i);
			final ArrayList<ArrayList<Integer>> query = new ArrayList<>();
			for (int i = 0;i <= n;++ i) query.add(new ArrayList<>());
			for (int i = n;i > 0;-- i) {
				set.removeAll(point.get(i));
				for (final int j : point.get(i)) {
					final int rem = set.higher(j) - set.lower(j) - 1;
					query.get(rem).add(i);
					map.merge(i, 1, (l, r) -> l + r);
				}
			}
			final char[] ans = new char[n];
			for (int i = 0;i < n;++ i) {
				for (final int j : query.get(i)) {
					map.compute(j, (k, v) -> v == 1 ? null : v - 1);
				}
				ans[i] = map.size() == n - i && map.lastKey() == n - i ? '1' : '0';
			}
			out.println(ans);
		}
	}

	static class Query implements Comparable<Query> {
		int rem;
		int x, y;
		Query(final int rem, final int x, final int y) {
			this.rem = rem;
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(final Query o) {
			return Integer.compare(rem, o.rem);
		}

	}
}

class FastScanner {

	private final java.io.InputStream in = System.in;
	private final byte[] buffer = new byte[8192];
	private int ptr = 0;
	private int buflen = 0;

	private boolean hasNextByte() {
		if (ptr < buflen) return true;
		ptr = 0;
		try {
			buflen = in.read(buffer);
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		}
		return buflen > 0;
	}

	private byte readByte() {
		return hasNextByte() ? buffer[ptr++ ] : -1;
	}

	private static boolean isPrintableChar(final byte c) {
		return 32 < c || c < 0;
	}

	private static boolean isNumber(final int c) {
		return '0' <= c && c <= '9';
	}

	public boolean hasNext() {
		while (hasNextByte() && !isPrintableChar(buffer[ptr]))
			ptr++ ;
		return hasNextByte();
	}

	public String next() {
		if (!hasNext()) throw new java.util.NoSuchElementException();
		final StringBuilder sb = new StringBuilder();
		byte b;
		while (isPrintableChar(b = readByte()))
			sb.appendCodePoint(b);
		return sb.toString();
	}

	public final char nextChar() {
		if (!hasNext()) throw new java.util.NoSuchElementException();
		return (char)readByte();
	}

	public final long nextLong() {
		if (!hasNext()) throw new java.util.NoSuchElementException();
		long n = 0;
		try {
			byte b = readByte();
			if (b == '-') {
				while (isNumber(b = readByte()))
					n = n * 10 + '0' - b;
				return n;
			} else if (!isNumber(b)) throw new NumberFormatException();
			do
				n = n * 10 + b - '0';
			while (isNumber(b = readByte()));
		} catch (final java.util.NoSuchElementException e) {}
		return n;
	}

	public final int nextInt() {
		if (!hasNext()) throw new java.util.NoSuchElementException();
		int n = 0;
		try {
			byte b = readByte();
			if (b == '-') {
				while (isNumber(b = readByte()))
					n = n * 10 + '0' - b;
				return n;
			} else if (!isNumber(b)) throw new NumberFormatException();
			do
				n = n * 10 + b - '0';
			while (isNumber(b = readByte()));
		} catch (final java.util.NoSuchElementException e) {}
		return n;
	}

	public double nextDouble() {
		return Double.parseDouble(next());
	}
}

class Arrays {

	public static void sort(final int[] array) {
		int l, min = 0xFFFFFFFF, max = 0;
		for (l = 0; l < array.length; ++l) {
			final int i = array[l];
			min &= i;
			max |= i;
			if ((i & 0x80000000) == 0) break;
		}
		for (int r = l + 1; r < array.length; ++r) {
			final int i = array[r];
			min &= i;
			max |= i;
			if ((i & 0x80000000) != 0) {
				array[r] = array[l];
				array[l++ ] = i;
			}
		}
		final int use = min ^ max, bit = Integer.highestOneBit(use & 0x7FFFFFFF);
		if (bit == 0) return;
		sort(array, 0, l, use, bit);
		sort(array, l, array.length, use, bit);
	}

	private static void sort(final int[] array, final int left, final int right, final int use, int digit) {
		if (right - left <= 96) {
			for (int i = left + 1; i < right; ++i) {
				final int tmp = array[i];
				int tmp2, j;
				for (j = i; j > left && (tmp2 = array[j - 1]) > tmp; --j)
					array[j] = tmp2;
				array[j] = tmp;
			}
			return;
		}
		int l = left;
		while (l < right && (array[l] & digit) == 0)
			++l;
		for (int r = l + 1; r < right; ++r) {
			final int i = array[r];
			if ((i & digit) == 0) {
				array[r] = array[l];
				array[l++ ] = i;
			}
		}
		if ((digit = Integer.highestOneBit(use & digit - 1)) == 0) return;
		sort(array, left, l, use, digit);
		sort(array, l, right, use, digit);
	}

	public static void sort(final long[] array) {
		int l;
		long min = 0xFFFFFFFFFFFFFFFFL, max = 0;
		for (l = 0; l < array.length; ++l) {
			final long i = array[l];
			min &= i;
			max |= i;
			if ((i & 0x8000000000000000L) == 0) break;
		}
		for (int r = l + 1; r < array.length; ++r) {
			final long i = array[r];
			min &= i;
			max |= i;
			if ((i & 0x8000000000000000L) != 0) {
				array[r] = array[l];
				array[l++ ] = i;
			}
		}
		final long use = min ^ max, bit = Long.highestOneBit(use & 0x7FFFFFFFFFFFFFFFL);
		if (bit == 0) return;
		sort(array, 0, l, use, bit);
		sort(array, l, array.length, use, bit);
	}

	private static void sort(final long[] array, final int left, final int right, final long use, long digit) {
		if (right - left <= 96) {
			for (int i = left + 1, j; i < right; ++i) {
				final long tmp = array[i];
				long tmp2;
				for (j = i; j > left && (tmp2 = array[j - 1]) > tmp; --j)
					array[j] = tmp2;
				array[j] = tmp;
			}
			return;
		}
		int l = left;
		while (l < right && (array[l] & digit) == 0)
			++l;
		for (int r = l + 1; r < right; ++r) {
			final long i = array[r];
			if ((i & digit) == 0) {
				array[r] = array[l];
				array[l++ ] = i;
			}
		}
		if ((digit = Long.highestOneBit(use & digit - 1)) == 0) return;
		sort(array, left, l, use, digit);
		sort(array, l, right, use, digit);
	}
}

class IntMath {

	public static int gcd(int a, int b) {
		while (a != 0)
			if ((b %= a) != 0) a %= b;
			else return a;
		return b;
	}

	public static int gcd(final int... array) {
		int ret = array[0];
		for (int i = 1; i < array.length; ++i)
			ret = gcd(ret, array[i]);
		return ret;
	}

	public static long gcd(long a, long b) {
		while (a != 0)
			if ((b %= a) != 0) a %= b;
			else return a;
		return b;
	}

	public static long gcd(final long... array) {
		long ret = array[0];
		for (int i = 1; i < array.length; ++i)
			ret = gcd(ret, array[i]);
		return ret;
	}

	public static long lcm(final long a, final long b) {
		return a / gcd(a, b) * b;
	}

	public static int pow(final int a, int b) {
		int ans = 1;
		for (int mul = a; b > 0; b >>= 1, mul *= mul)
			if ((b & 1) != 0) ans *= mul;
		return ans;
	}

	public static long powLong(final long a, long b) {
		long ans = 1;
		for (long mul = a; b > 0; b >>= 1, mul *= mul)
			if ((b & 1) != 0) ans *= mul;
		return ans;
	}

	public static int pow(final int a, int b, final int mod) {
		if (b < 0) b = b % (mod - 1) + mod - 1;
		long ans = 1;
		for (long mul = a; b > 0; b >>= 1, mul = mul * mul % mod)
			if ((b & 1) != 0) ans = ans * mul % mod;
		return (int)ans;
	}

	public static int floorsqrt(final long n) {
		return (int)Math.sqrt(n + 0.1);
	}

	public static int ceilsqrt(final long n) {
		return n <= 1 ? (int)n : (int)Math.sqrt(n - 0.1) + 1;
	}
}