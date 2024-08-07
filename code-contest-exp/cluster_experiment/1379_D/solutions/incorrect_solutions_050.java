import java.io.*;
import java.util.*;

public class Problem_D {
	static final int INF = Integer.MAX_VALUE / 2;

	static class Pair implements Comparable<Pair> {
		int x, y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int compareTo(Pair p) {
			return y - p.y;
		}
	}

	public static void main(String[] args) {
		InputReader in = new InputReader();
		StringBuilder out = new StringBuilder();

		int N = in.nextInt();
		in.nextInt();
		int M = in.nextInt();
		int K = in.nextInt();
		Pair[] P = new Pair[N];
		for (int i = 0; i < N; i++) {
			in.nextInt();
			P[i] = new Pair(i + 1, in.nextInt() % (M / 2));
		}
		Arrays.sort(P);

		Queue<Pair> q = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			if (P[i].y > M / 2 - K) {
				q.offer(new Pair(P[i].x, P[i].y - M / 2));
			}
		}
		int opt_size = q.size();
		int opt_t = 0;
		for (int i = 0; i < N; i++) {
			q.offer(P[i]);
			while (i < N - 1 && P[i].y == P[i + 1].y) {
				q.offer(P[++i]);
			}
			while (P[i].y - q.peek().y >= K - 1) {
				q.poll();
			}
			if (opt_size > q.size()) {
				opt_size = q.size();
				opt_t = P[i].y + 1;
			}
		}
		out.append(opt_size).append(' ').append(opt_t).append('\n');

		q.clear();
		for (int i = 0; i < N; i++) {
			if (P[i].y > M / 2 - K) {
				q.offer(new Pair(P[i].x, P[i].y - M / 2));
			}
		}
		for (int i = 0; i < N; i++) {
			if (opt_t == 0) {
				break;
			}
			q.offer(P[i]);
			while (i < N - 1 && P[i].y == P[i + 1].y) {
				q.offer(P[++i]);
			}
			while (P[i].y - q.peek().y >= K - 1) {
				q.poll();
			}
			if (opt_t == P[i].y + 1) {
				break;
			}
		}
		for (Pair p : q) {
			out.append(p.x).append(' ');
		}
		if (q.isEmpty()) {
			out.append('\n');
		} else {
			out.setCharAt(out.length() - 1, '\n');
		}

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
