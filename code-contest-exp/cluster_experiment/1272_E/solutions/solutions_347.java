import java.io.*;
import java.util.*;

public class F {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner();
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] a = new int[n];
		ArrayList<Integer>[] from = new ArrayList[n];
		ArrayList<Integer>[] to = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			to[i] = new ArrayList();
			from[i] = new ArrayList();
		}
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
			int v = i + a[i];
			if (v < n) {
				from[v].add(i);
				to[i].add(v);
			}
			v = i - a[i];
			if (v >= 0) {
				from[v].add(i);
				to[i].add(v);

			}
		}
		Queue<Integer> q = new LinkedList();
		int[] ans = new int[n];
		Arrays.fill(ans, -1);
		for (int i = 0; i < n; i++)
			for (int j : to[i])
				if (a[j] % 2 != a[i] % 2) {
					q.add(i);
					ans[i] = 1;
				}
		while (!q.isEmpty()) {
			int u = q.poll();
			for (int v : from[u]) {
				if (ans[v] == -1) {
					ans[v] = ans[u] + 1;
					q.add(v);
				}
			}
		}
		for (int x : ans)
			out.print(x + " ");

		out.close();

	}

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static class Scanner {
		BufferedReader br;
		StringTokenizer st;

		Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		Scanner(String fileName) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(fileName));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws NumberFormatException, IOException {
			return Double.parseDouble(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}

	}

	static void sort(int[] a) {
		shuffle(a);
		Arrays.sort(a);
	}

	static void shuffle(int[] a) {
		int n = a.length;
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			int tmpIdx = rand.nextInt(n);
			int tmp = a[i];
			a[i] = a[tmpIdx];
			a[tmpIdx] = tmp;
		}
	}

}