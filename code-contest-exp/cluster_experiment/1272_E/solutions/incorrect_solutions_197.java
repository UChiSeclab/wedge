import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NearestOppositeParty {
	static int mod = 1000000007;

	static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;

		public Reader() {
			din = new DataInputStream(System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public Reader(String file_name) throws IOException {
			din = new DataInputStream(new FileInputStream(file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public String readLine() throws IOException {
			byte[] buf = new byte[10005]; // line length
			int cnt = 0, c;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, cnt);
		}

		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (neg)
				return -ret;
			return ret;
		}

		public long nextLong() throws IOException {
			long ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();

			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (c == '.') {
				while ((c = read()) >= '0' && c <= '9') {
					ret += (c - '0') / (div *= 10);
				}
			}

			if (neg)
				return -ret;
			return ret;
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}

	static List<Integer> g[];
	static int jump[][];
	static boolean visited[][];
	static int arr[];
	static int n;

	public static void main(String[] args) throws IOException {
		Reader in = new Reader();
		int i, t;
		n = in.nextInt();
		arr = new int[n + 1];
		g = new ArrayList[n + 1];
		jump = new int[2][n + 1];
		Arrays.fill(jump[0], 0);
		Arrays.fill(jump[1], 0);
		visited = new boolean[2][n + 1];
		for (i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
			g[i] = new ArrayList<Integer>();
		}
		for (i = 1; i <= n; i++) {
			if (i - arr[i] >= 1)
				g[i].add(i - arr[i]);
			if (i + arr[i] <= n)
				g[i].add(i + arr[i]);
		}
		for (i = 1; i <= n; i++) {
			int parity = arr[i] & 1;
			if (!visited[arr[i] & 1][i]) {
				Set<Integer> s = new HashSet<>();
				s.add(i);
//				System.out.println(i + " p " + parity + " m ");
				if (i - arr[i] >= 1 && i + arr[i] <= n) {
					jump[parity][i] = Math.min(dfs(i - arr[i], parity, s), dfs(i + arr[i], parity, s));
				} else if (i - arr[i] >= 1) {
					jump[parity][i] = dfs(i - arr[i], parity, s);
				} else if (i + arr[i] <= n) {
					jump[parity][i] = dfs(i + arr[i], parity, s);
				}
				visited[parity][i] = true;
			}
		}
		StringBuilder ans = new StringBuilder();
		for (i = 1; i <= n; i++) {
			int x = (arr[i] & 1);
			ans.append((jump[x][i] == 0 ? -1 : jump[x][i]) + " ");
		}
//		System.out.println(Arrays.toString(jump[0]) + "\n" + Arrays.toString(jump[1]));
//		System.out.println(Arrays.toString(visited[0]) + "\n" + Arrays.toString(visited[1]));
		System.out.println(ans);
	}

	private static int dfs(int i, int parity, Set<Integer> src) {
//		System.out.println(i + " p " + parity + " m ");
		if (visited[parity][i] == true)
			return jump[parity][i] + 1;
		if ((arr[i] & 1) == (1 - parity)) {
//			System.out.println("Asdasd");
			return 1;
		}
		visited[parity][i] = true;
//		for (int x : g[i]) {
//			if((arr[i]&1)==(1-parity))
//				return moves+1;
//		}
		src.add(i);
		if (!src.contains(i - arr[i]) && i - arr[i] >= 1 && i + arr[i] <= n && !src.contains(i + arr[i])) {
//			System.out.println("Sddfs");
			jump[parity][i] = Math.min(dfs(i - arr[i], parity, src), dfs(i + arr[i], parity, src));
			return 1 + jump[parity][i];
		} else if (i - arr[i] >= 1 && !src.contains(i - arr[i])) {
//			System.out.println("one");
			jump[parity][i] = dfs(i - arr[i], parity, src);
			return 1 + jump[parity][i];
		} else if (i + arr[i] <= n && !src.contains(i + arr[i])) {
//			System.out.println("tow");
			jump[parity][i] = dfs(i + arr[i], parity, src);
			return 1 + jump[parity][i];
		}
		return -1;
	}

}
