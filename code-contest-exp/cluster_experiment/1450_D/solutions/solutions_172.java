import java.util.*;
import java.io.*;

public class D1450 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			int[] arr = sc.nextIntArr(n);
			int[] before = new int[n];
			int[] after = new int[n];

			Stack<Integer> st = new Stack<Integer>();
			for (int i = 0; i < n; i++) {
				while (!st.isEmpty() && arr[st.peek()] > arr[i])
					st.pop();
				if (st.isEmpty()) {
					before[i] = -1;
				} else {
					before[i] = st.peek();
				}
				st.add(i);
			}
			st = new Stack<Integer>();
			for (int i = n - 1; i >= 0; i--) {
				while (!st.isEmpty() && arr[st.peek()] >= arr[i])
					st.pop();
				if (st.isEmpty()) {
					after[i] = n;
				} else {
					after[i] = st.peek();
				}
				st.add(i);
			}
			ArrayList<Integer>[] g = new ArrayList[n + 1];
			for (int i = 0; i < g.length; i++) {
				g[i] = new ArrayList<Integer>();
			}
			for (int i = 0; i < n; i++) {
				int idx = after[i] - before[i] - 1;
				g[idx].add(arr[i]);
			}
			TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
			for (int x : arr) {
				tm.put(x, tm.getOrDefault(x, 0) + 1);
			}
			int[] ans = new int[n];
			for (int i = 0; i < n; i++) {
//				pw.println(tm);
				if (tm.size() == n - i && tm.lastKey() == n - i) {
					ans[i] = 1;
				}
				for (int x : g[i + 1]) {
					int cnt = tm.get(x);
					if (cnt == 1) {
						tm.remove(x);
					} else {
						tm.put(x, cnt - 1);
					}
				}
			}
			for (int x : ans) {
				pw.print(x);
			}
			pw.println();
		}
		pw.close();

	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public Scanner(FileReader f) {
			br = new BufferedReader(f);
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public int[] nextIntArr(int n) throws IOException {
			int[] arr = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = Integer.parseInt(next());
			}
			return arr;
		}

	}

}
