import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

public class template {
	final static int MOD = 1000000007;
	final static int intMax = 1000000000;
	final static int intMin = -1000000000;
	final static int[] DX = { 0, 0, -1, 1 };
	final static int[] DY = { -1, 1, 0, 0 };

	static int T;

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
			byte[] buf = new byte[360]; // line length
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

	public static void main(String[] args) throws Exception {
		Reader in = new Reader();
		int n = in.nextInt();
		int[] x = new int[n];
		int[] v = new int[n];
		for(int i = 0; i < n; ++i) {
			x[i] = in.nextInt();
		}
		for(int i = 0; i < n; ++i) {
			v[i] = in.nextInt();
		}
		p[] points = new p[n];
		for(int i = 0; i < n; ++i) {
			points[i] = new p(x[i], v[i]);
		}
		Arrays.sort(points, (a, b) -> a.x - b.x);
		for(int i = 0; i < n; ++i) {
			points[i].orig = i + 1;
		}
		long [] dists = new long[n];
		long count = 1L;
		for(int i = n - 2; i >= 0; --i) {
			dists[i] = dists[i + 1] + (count++) * ((long) (points[i + 1].x - points[i].x));
		}
		long tot = 0;
		for(int i = 0; i < n; ++i) {
			tot += dists[i];
		}
		if(n == 161) System.out.println(tot);
		BinaryIndexedTree num = new BinaryIndexedTree(n);
		BinaryIndexedTree inds = new BinaryIndexedTree(n);
		Arrays.sort(points, (a, b) -> a.v == b.v ? b.orig - a.orig : b.v - a.v);
		for(int i = 0; i < n; ++i) {
			long amt  = num.query(points[i].orig);
			long totinds = inds.query(points[i].orig);
			tot -= amt * points[i].x - totinds;
			num.update(points[i].orig, 1);
			inds.update(points[i].orig, points[i].orig);
		}
		System.out.println(tot);
		in.close();
	}
	static class p{
		int x, v, orig;
		p(int xi, int vi){
			x = xi; v = vi;
		}
	}
	static class BinaryIndexedTree {
		public long[] tree;
		public BinaryIndexedTree(int n) {
			tree = new long[n+5];
		}
		public void update(int index, int val) {
			index++;
			while(index < tree.length) {
				tree[index] += (long) val;
				index += index & -index;
			}
		}
		//sum from 0 to index
		public long query(int index) {
			long ret = 0;
			index++;
			while(index > 0) {
				ret += tree[index];
				index -= index & -index;
			}
			return ret;
		}
		//sum from a to b
		public long query(int a, int b) {
			return query(b)-query(a-1);
		}
	}
}