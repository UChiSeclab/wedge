import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
	static final boolean ONLINE_JUDGE = System.getProperty("ONLINE_JUDGE") != null;

	public long gcd(long a, long b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	List<Integer>[] graph;
	boolean[] used;
	int[] depth;

	ArrayList<Integer> f(int k) {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		for (int i = 0; i < (1 << 14); i++) {
			int d = Integer.bitCount(i);
			int f = i;
			while (f > 0) {
			//	d += f % 2;
				f /= 2;
			}
			if (d == k) {
				ans.add(i);
			}
		}
		return ans;
	}

	void solve() throws IOException {
		int n = rI();
		int k = rI();
		ArrayList<Integer> it = f(k);
		HashMap<Integer, Long> map = new HashMap<Integer, Long>();
		long ans = 0;
		if (k == 0) {
			while (n-- > 0) {
				int next = rI();
				ans += map.getOrDefault(next, 0L);
				map.put(next, map.getOrDefault(next, 0L) + 1);
			}
			out.println(ans);
			return;
		}
		while (n-- > 0) {
			int next = rI();
			map.put(next, map.getOrDefault(next, 0L) + 1);
		}

		for (int i = 0; i < 100001; i++) {
			if (map.get(i) == null) {
				continue;
			}
			int val = 0;
			for (int j : it) {
				if ((i ^ j) < 100001) {
					int c = (i ^ j) ^ i;
					if (Integer.bitCount(c) == k) {
						val += map.getOrDefault(i ^ j, 0L);
					}
				} else {
					//break;
				}
			}
			ans += val;
			map.remove(i);
		}
		out.println(ans);

	}

	int bit(int n) {
		int ans = 0;
		while (n > 0) {
			ans += n % 2;
			n /= 2;
		}
		return ans;
	}

	// ===========================================================================
	long minLong(long... values) {
		long min = Integer.MAX_VALUE;
		for (long value : values) {
			min = Math.min(min, value);
		}
		return min;
	}

	long maxLong(long... values) {
		long max = Integer.MIN_VALUE;
		for (long value : values) {
			max = Math.max(max, value);
		}
		return max;
	}

	int maxInt(int... values) {
		int max = Integer.MIN_VALUE;
		for (int value : values) {
			max = Math.max(max, value);
		}
		return max;
	}

	int minInt(int... values) {
		int min = Integer.MAX_VALUE;
		for (int value : values) {
			min = Math.min(min, value);
		}
		return min;
	}

	// ==============================================================================

	public static void main(String[] args) {
		new Main().run();
	}

	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok;

	long maxA(int[] ans) {
		long max = Integer.MIN_VALUE;
		for (int i = 0; i < ans.length; i++) {
			if (ans[i] > max) {
				max = ans[i];

			}
		}
		return max;
	}

	long minA(long[] a) {
		long min = Long.MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			if (a[i] < min) {
				min = a[i];
			}
		}
		return min;
	}

	void init() throws FileNotFoundException {

		if (ONLINE_JUDGE) {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
		} else {
			in = new BufferedReader(new FileReader("input.txt"));
			out = new PrintWriter("output.txt");
		}

		tok = new StringTokenizer("");
	}

	void run() {
		try {
			long timeStart = System.currentTimeMillis();

			init();
			solve();

			out.close();

			long timeEnd = System.currentTimeMillis();
			System.err.println("Time = " + (timeEnd - timeStart) + " COMPILED");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	long memoryTotal, memoryFree;

	void memory() {
		memoryFree = Runtime.getRuntime().freeMemory();
		System.err.println("Memory = " + ((-memoryTotal + memoryFree) >> 10) + " KB");
	}

	String readLine() throws IOException {
		return in.readLine();
	}

	String delimiter = " ";

	String rS() throws IOException {
		while (!tok.hasMoreTokens()) {
			String nextLine = readLine();
			if (null == nextLine)
				return null;

			tok = new StringTokenizer(nextLine);
		}

		return tok.nextToken(delimiter);
	}

	int[] rA(int b) {
		int a[] = new int[b];
		for (int i = 0; i < b; i++) {
			try {
				a[i] = rI();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	int rI() throws IOException {
		return Integer.parseInt(rS());
	}

	long rL() throws IOException {
		return Long.parseLong(rS());
	}

	void sort(int[] a) {
		Integer arr[] = new Integer[a.length];
		for (int i = 0; i < a.length; i++) {
			arr[i] = a[i];
		}
		Arrays.sort(arr);
		for (int i = 0; i < a.length; i++) {
			a[i] = arr[i];
		}
	}

}