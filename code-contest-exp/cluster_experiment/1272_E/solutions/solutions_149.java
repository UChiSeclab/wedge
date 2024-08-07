

import java.util.*;

import java.io.*;
import java.text.*;

public class E1272 {

	static ArrayList<Integer>[] adjList;
	static int[] arr, ans;
	static int INF = (int) 1e9;

	static void bfs(int h, int n) {
		Queue<Integer> q = new LinkedList<Integer>();
		int[] dist = new int[n];
		Arrays.fill(dist, INF);
		for (int i = 0; i < n; i++) {
			if (arr[i] % 2 == h) {
				q.add(i);
				dist[i] = 0;
			}
		}
		while (!q.isEmpty()) {
			int cur = q.poll();
			for (int x : adjList[cur]) {
				if (dist[x] == INF) {
					dist[x] = dist[cur] + 1;
					q.add(x);
				}
			}
		}
		for (int i = 0; i < n; i++) {
			if (arr[i] % 2 != h) {
				ans[i] = dist[i] == INF ? -1 : dist[i];
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();

		arr = new int[n];
		ans = new int[n];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = sc.nextInt();
		}
		adjList = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < n; i++) {
			if (i - arr[i] >= 0) {
				adjList[i - arr[i]].add(i);
			}
			if (i + arr[i] < n) {
				adjList[i + arr[i]].add(i);
			}
		}
		bfs(1, n);
		bfs(0, n);
		for (int i = 0; i < n; i++)
			pw.print(ans[i] + " ");
		pw.close();

	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public Scanner(FileReader r) {
			br = new BufferedReader(r);
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

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public double nextDouble() throws IOException {
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

	}
}
