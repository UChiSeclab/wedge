import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

	public static void main(String[] args) throws IOException {
		Reader in = new Reader();
		int n, i, t;
		n = in.nextInt();
		int[] arr = new int[n + 1];
		List<Integer>[] g = new ArrayList[n + 1];

		int ans[] = new int[n + 1];
		Arrays.fill(ans, -1);
		Queue<Integer> q = new LinkedList<>();
		for (i = 1; i <= n; i++) {
			arr[i] = in.nextInt();
			g[i] = new ArrayList<>();
		}
		for (i = 1; i <= n; i++) {
			t = arr[i] & 1;
			if (i - arr[i] >= 1) {
				g[i - arr[i]].add(i);
				if (arr[i - arr[i]] % 2 != t)
					ans[i] = 1;
			}
			if (i + arr[i] <= n) {
				g[i + arr[i]].add(i);
				if (arr[i + arr[i]] % 2 != t)
					ans[i] = 1;
			}
			if (ans[i] == 1)
				q.add(i);
		}
		while (!q.isEmpty()) {
			t = q.poll();
			for (int to : g[t]) {
				if (ans[to] == -1 && arr[t] % 2 == arr[to] % 2) {
					ans[to] = 1 + ans[t];
					q.add(to);
				}
			}
		}
		StringBuilder s = new StringBuilder();
		for (i = 1; i <= n; i++) {
			s.append(ans[i] + " ");
		}
		System.out.println(s);
	}

}
