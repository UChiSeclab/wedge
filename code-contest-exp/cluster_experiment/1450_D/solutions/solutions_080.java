import java.io.*;
import java.util.*;

public class D {

	static long m = (long) (1e9 + 7);

	public static void main(String[] args) throws IOException {
		Scanner scn = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int T = scn.nextInt(), tcs = 0;
		D: while (tcs++ < T) {
			int n = scn.nextInt();
			int a[] = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = scn.nextInt();
			int l[] = new int[n], r[] = new int[n];
			Stack<Integer> st = new Stack<>();
			for (int i = n - 1; i >= 0; --i) {
				while (!st.isEmpty() && a[st.peek()] >= a[i])
					st.pop();
				r[i] = st.isEmpty() ? n - i : st.peek() - i;
				st.push(i);
			}
			st = new Stack<>();
			for (int i = 0; i < n; i++) {
				while (!st.isEmpty() && a[st.peek()] >= a[i])
					st.pop();
				l[i] = st.isEmpty() ? i + 1 : i - st.peek();
				st.push(i);
			}
			HashMap<Integer, Integer> hm = new HashMap<>();
			for (int i = 0; i < n; i++)
				hm.put(a[i], Math.max(hm.getOrDefault(a[i], 0), l[i] + r[i] - 1));

			int minl[] = new int[n + 1];
			minl[0] = n + 2;
			for (int i = 1; i <= n; i++)
				minl[i] = Math.min(minl[i - 1], hm.getOrDefault(i, -1));

			for (int i = n, ll = 1; i >= 1; --i, ll++)
				if (minl[i] >= ll)
					sb.append(1);
				else
					sb.append(0);

			sb.append("\n");
		}
		System.out.print(sb);
	}

	static class Scanner {

		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
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
	}
}