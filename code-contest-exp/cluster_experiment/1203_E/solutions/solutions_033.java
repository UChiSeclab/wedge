import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main1 {
	static PrintWriter out = new PrintWriter(System.out, true);

	public static void main(String[] args) throws IOException {
		FastReader input = new FastReader();
		int n = input.nextInt();
		int freq[] = new int[150000 + 10];
		int trans[] = new int[freq.length];
		for (int i = 0; i < n; i++) {
			int x = input.nextInt();
			freq[x]++;
		}

		for (int i = 1; i <= 150000; i++) {

			if (i > 1 && trans[i - 1] == 0 && freq[i] > 0) {
				freq[i]--;
				trans[i - 1] = 1;
			}

			if (trans[i] == 0 && freq[i] > 0) {
				freq[i]--;
				trans[i] = 1;
			}

			if (trans[i + 1] == 0 && freq[i] > 0) {
				trans[i + 1] = 1;
				freq[i]--;
			}
		}
		int cnt = 0;
		for (int x : trans) {
			if (x >= 1) {
				cnt++;
			}
		}

		out.println(cnt);

		out.flush();
	}

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			// try {
			// br = new BufferedReader(new FileReader(new File("army.in")));
			// } catch (FileNotFoundException e) {
			// e.printStackTrace();
			// }
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreElements()) { st = new StringTokenizer(br.readLine()); }
			return st.nextToken();
		}

		int nextInt() throws NumberFormatException, IOException { return Integer.parseInt(next()); }

		long nextLong() throws NumberFormatException, IOException { return Long.parseLong(next()); }

		double nextDouble() throws NumberFormatException, IOException { return Double.parseDouble(next()); }

		String nextLine() throws IOException {
			String str = "";
			str = br.readLine();
			return str;
		}

		boolean hasNext() throws IOException { return br.ready(); }
	}

	static class con {
		static int IINF = (int) 1e9;
		static int _IINF = (int) -1e9;
		static long LINF = (long) 1e15;
		static long _LINF = (long) -1e15;
		static double EPS = 1e-9;
	}

	static class Triple implements Comparable<Triple> {
		int x;
		int y;

		int z;

		Triple(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public int compareTo(Triple o) {
			if (x == o.x && y == o.y)
				return z - o.z;
			if (x == o.x)
				return y - o.y;
			return x - o.x;
		}

		@Override
		public String toString() { return "(" + x + ", " + y + ", " + z + ")"; }
	}

	static class Pair implements Comparable<Pair> {
		int x;
		int y;

		Pair(int x, int y) {
			this.x = x;
			this.y = y;

		}

		@Override
		public int compareTo(Pair o) {
			if (x == o.x)
				return y - o.y;
			return x - o.x;
		}

		@Override
		public String toString() {

			return "(" + x + ", " + y + ")";
		}

	}

	static void shuffle(long[] a) {
		for (int i = 0; i < a.length; i++) {
			int r = i + (int) (Math.random() * (a.length - i));
			long tmp = a[r];
			a[r] = a[i];
			a[i] = tmp;
		}
	}

	static int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }

	static class DSU {

		int[] p, rank, setSize;
		int numSets;

		DSU(int n) {
			p = new int[n];
			rank = new int[n];
			setSize = new int[n];
			numSets = n;

			for (int i = 0; i < n; i++) {
				p[i] = i;
				setSize[i] = 1;
			}

		}

		int findSet(int n) { return p[n] = p[n] == n ? n : findSet(p[n]); }

		boolean isSameSet(int n, int m) { return findSet(n) == findSet(m); }

		void mergeSet(int n, int m) {

			if (!isSameSet(n, m)) {
				numSets--;
				int p1 = findSet(n);
				int p2 = findSet(m);

				if (rank[p1] > rank[p2]) {
					p[p2] = p1;
					setSize[p1] += setSize[p2];
				} else {
					p[p1] = p2;
					setSize[p2] += setSize[p1];
					if (rank[p1] == rank[p2])
						rank[p1]++;
				}
			}

		}

		int size() { return numSets; }

		int setSize(int n) { return setSize[findSet(n)]; }

	}

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
			byte[] buf = new byte[1024];
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
			do { ret = ret * 10 + c - '0'; } while ((c = read()) >= '0' && c <= '9');
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
			do { ret = ret * 10 + c - '0'; } while ((c = read()) >= '0' && c <= '9');
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
			do { ret = ret * 10 + c - '0'; } while ((c = read()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
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

}