import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class Problem_E {
	static final int INF = Integer.MAX_VALUE / 2;
	static int N;
	static int[] A;
	static List<Integer>[] G;

	public static void chk(int x, int[] d, Queue<Integer> q) {
		if (d[x] == -1) {
			d[x] = 1;
			q.offer(x);
		}
	}

	public static int[] bfs() {
		int[] d = new int[N];
		Arrays.fill(d, -1);
		Queue<Integer> q = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			if (i - A[i] >= 0 && Math.abs(A[i] - A[i - A[i]]) % 2 == 1) {
				chk(i, d, q);
			}
			if (i + A[i] < N && Math.abs(A[i] + A[i + A[i]]) % 2 == 1) {
				chk(i, d, q);
			}
		}
		while (!q.isEmpty()) {
			int x = q.poll();
			for (int y : G[x]) {
				if (d[y] == -1) {
					d[y] = d[x] + 1;
					q.offer(y);
				}
			}
		}
		return d;
	}

	public static void main(String[] args) {
		InputReader in = new InputReader();
		StringBuilder out = new StringBuilder();

		N = in.nextInt();
		A = new int[N];
		for (int i = 0; i < N; i++) {
			A[i] = in.nextInt();
		}
		G = new List[N];
		for (int i = 0; i < N; i++) {
			G[i] = new ArrayList<>();
		}
		for (int i = 0; i < N; i++) {
			if (i - A[i] >= 0) {
				G[i - A[i]].add(i);
			}
			if (i + A[i] < N) {
				G[i + A[i]].add(i);
			}
		}
		int[] d = bfs();
		for (int i = 0; i < N; i++) {
			out.append(d[i]).append(' ');
		}
		out.setCharAt(out.length() - 1, '\n');

		System.out.print(out);
	}

	static class InputReader {
		public BufferedReader reader;
		public StringTokenizer st;

		public InputReader() {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}

		public String next() {
			while (st == null || !st.hasMoreTokens()) {
				st = new StringTokenizer(nextLine());
			}
			return st.nextToken();
		}

		public String nextLine() {
			try {
				return reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}
	}
}
