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
		for (int i = 0; i <= 10000; i++) {
			int d = 0;
			int f = i;
			while (f > 0) {
				d += f % 2;
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
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		long ans = 0;
		while (n-- > 0) {
			int next = rI();
			for (int i : it) {
				if (i < next * 2) {

					ans += map.getOrDefault(next - i, 0);
					if (i != 0) {
						ans += map.getOrDefault(next + i, 0);
					}
				}
			}
			map.put(next, map.getOrDefault(next, 0) + 1);
		}
		out.println(ans);

	}

	class GraphDirected {
		List<Integer>[] graph;
		boolean[] used;
		int[] depth;
		ArrayList<Integer> topSort;
		int n;

		GraphDirected(int n) {
			this.n = n;
			this.graph = new ArrayList[n];
			for (int i = 0; i < n; i++) {
				graph[i] = new ArrayList<Integer>();
			}
			this.used = new boolean[n];
			this.depth = new int[n];
			this.topSort = new ArrayList<Integer>();
		}

		void dfs(int from) {
			used[from] = true;
			for (int i : graph[from]) {
				if (!used[i]) {
					dfs(i);
				}
			}
		}

		int[] bfs(int start) {
			int[] depth = new int[graph.length];
			Arrays.fill(depth, Integer.MAX_VALUE);
			Queue<Integer> queue = new ArrayDeque<>();
			depth[start] = 0;
			queue.add(start);
			while (queue.size() > 0) {
				int from = queue.poll();
				for (int to : graph[from]) {
					if (depth[to] > depth[from] + 1) {
						depth[to] = depth[from] + 1;
						queue.add(to);
					}
				}
			}
			return depth;
		}

		void add(int from, int to) {
			graph[from].add(to);
		}

		// -------------------------------
		void dfsTopSort(int from, ArrayList<Integer> top) {
			used[from] = true;
			for (int i : graph[from]) {
				if (!used[i])
					dfsTopSort(i, top);
			}
			top.add(from);
		}

		ArrayList<Integer> top_Sort() {
			ArrayList<Integer> top = new ArrayList<Integer>();
			Arrays.fill(used, false);
			top.clear();
			for (int i = 0; i < n; ++i)
				if (!used[i])
					dfsTopSort(i, top);
			Collections.reverse(top);
			return top;
		}

		// -----------------------------
		List<Integer> getChild(int from) {
			return graph[from];
		}

	}

	long sum(int[] a) {
		long ans = 0;
		for (int i : a) {
			ans += i;
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