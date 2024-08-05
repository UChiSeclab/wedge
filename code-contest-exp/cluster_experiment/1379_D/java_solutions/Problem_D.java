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
		int K = in.nextInt() - 1;
		Pair[] P = new Pair[N];
		for (int i = 0; i < N; i++) {
			in.nextInt();
			P[i] = new Pair(i + 1, in.nextInt() % (M / 2));
		}
		Arrays.sort(P);
		Queue<Pair> q = new LinkedList<>();
		int opt_t = 0;
		int opt_cnt = INF;
		int i = 0;
		for (; i < N && P[i].y < K; i++) {
			q.offer(P[i]);
		}
		opt_cnt = q.size();
		for (; i < N; i++) {
			int x = P[i].y;
			while (!q.isEmpty() && x - q.peek().y > K) {
				q.poll();
			}
			if (opt_cnt > q.size()) {
				opt_cnt = q.size();
				opt_t = x - K;
			}
			while (!q.isEmpty() && x - q.peek().y == K) {
				q.poll();
			}
			q.offer(P[i]);
			while (i < N - 1 && P[i].y == P[i + 1].y) {
				q.offer(P[++i]);
			}
			if (opt_cnt > q.size()) {
				opt_cnt = q.size();
				opt_t = x - K + 1;
			}
		}
		out.append(opt_cnt).append(' ').append(opt_t).append('\n');

		i = 0;
		for (; i < N && P[i].y < K; i++) {
			q.offer(P[i]);
		}
		opt_cnt = q.size();
		for (; i < N; i++) {
			int x = P[i].y;
			if (opt_t < x - K) {
				break;
			}
			while (!q.isEmpty() && x - q.peek().y > K) {
				q.poll();
			}
			if (opt_cnt > q.size()) {
				opt_cnt = q.size();
				opt_t = x - K;
			}
			if (opt_t < x - K + 1) {
				break;
			}
			while (!q.isEmpty() && x - q.peek().y == K) {
				q.poll();
			}
			q.offer(P[i]);
			while (i < N - 1 && P[i].y == P[i + 1].y) {
				q.offer(P[++i]);
			}
			if (opt_cnt > q.size()) {
				opt_cnt = q.size();
				opt_t = x - K + 1;
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
