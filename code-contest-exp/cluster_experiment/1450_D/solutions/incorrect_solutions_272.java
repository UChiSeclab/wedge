import java.util.*;
import java.io.*;

public class Rating_Compression {

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

	}

	public static void shuffle(int[] a) {
		Random r = new Random();

		for (int i = 0; i <= a.length - 2; i++) {
			int j = i + r.nextInt(a.length - i);

			swap(a, i, j);
		}
	}

	public static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastReader t = new FastReader();
		PrintWriter o = new PrintWriter(System.out);
		int test = t.nextInt();

		while (test-- > 0) {
			int n = t.nextInt();
			int[] a = new int[n];
			int[] maxDif = new int[n];
			int[] left = new int[n];
			int[] right = new int[n];

			for (int i = 0; i < n; ++i)
				a[i] = t.nextInt() - 1;

			Stack<int[]> stack = new Stack<>();

			for (int i = 0; i < n; i++) {
				while (!stack.empty() && stack.peek()[0] >= a[i]) {
					stack.pop();
				}

				if (stack.empty()) {
					left[i] = i + 1;
				} else {
					left[i] = i - stack.peek()[1];
				}

				stack.push(new int[] { a[i], i });
			}

			stack = new Stack<>();
			HashMap<Integer, Integer> map = new HashMap<>();

			stack.push(new int[] { a[0], 0 });

			for (int i = 1; i < n; i++) {
				if (stack.empty()) {
					stack.push(new int[] { a[i], i });
					continue;
				}

				while (stack.empty() == false && stack.peek()[0] > a[i]) {
					map.put(stack.peek()[1], i);
					stack.pop();
				}

				stack.push(new int[] { a[i], i });
			}

			while (stack.empty() == false) {
				map.put(stack.peek()[1], -1);
				stack.pop();
			}

			for (HashMap.Entry<Integer, Integer> m : map.entrySet()) {
				right[m.getKey()] = (m.getValue() == -1 ? n : m.getValue()) - m.getKey();
			}

			List<Integer>[] list = new ArrayList[n];

			for (int i = 0; i < n; ++i)
				list[i] = new ArrayList<>();

			for (int i = 0; i < n; ++i) {
				list[a[i]].add(i);
			}

			for (int i = 0; i < n; ++i) {
				if (list[i].size() > 0) {
					int max = 0;

					for (int idx : list[i]) {
						max = Math.max(left[idx] + right[idx] - 1, max);
					}

					maxDif[i] = max;
				}
			}

			StringBuilder sb = new StringBuilder();
			int cur = 0;

			while (cur < n) {
				if (maxDif[cur] > 0) {
					if (n - cur <= maxDif[cur])
						sb.append("1");
					else
						sb.append("0");
				} else {
					break;
				}

				++cur;
			}

			while (cur++ < n)
				sb.append("0");

			o.println(sb.reverse());
		}
		o.flush();
		o.close();
	}
}