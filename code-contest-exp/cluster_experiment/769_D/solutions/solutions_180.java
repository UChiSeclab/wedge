import java.io.*;
import java.util.*;

public class D{

	public static Reader sc = new Reader();
	//public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
	static long ans = 0;
	static long[] bits = new long[1<<14];
	static int max;
	static int k;
	public static void main(String[] args) throws IOException {
		//BufferedReader br = new BufferedReader(new FileReader("input.in"));
		int n=  sc.nextInt(), k = sc.nextInt();
		while (n-->0) {
			int x = sc.nextInt();
			bits[x]++;
			max = Integer.max(max, x);
		}
		for (int i=  0; i <= max; i++) {
			for (int j = i; j <= max; j++) {
				if (Integer.bitCount(i^j) == k) {
					if (i == j) ans += bits[i] * (bits[j]-1)/2;
					else ans += bits[i] * bits[j];
				}
			}
		}


		out.println(ans);
		out.close();
	}

	static void mergeSort(long[] a) {
		if (a.length < 2)
			return;
		long[] x = new long[a.length / 2];
		long[] y = new long[a.length - a.length / 2];
		for (int i = 0; i < x.length; i++)
			x[i] = a[i];
		for (int i = 0; i < y.length; i++)
			y[i] = a[i + x.length];
		mergeSort(x);
		mergeSort(y);
		int i = 0, j = 0, k = 0;
		while (j < x.length && k < y.length) {
			if (x[j] < y[k]) {
				a[i] = x[j];
				j++;
			} else {
				a[i] = y[k];
				k++;
			}
			i++;
		}
		while (j < x.length) {
			a[i] = x[j];
			j++;
			i++;
		}
		while (k < y.length) {
			a[i] = y[k];
			k++;
			i++;
		}
	}

	static long ceil(long a, long b) {
		return (a + b - 1) / b;
	}

	static long gcd(long a, long b) {
		while (b != 0) {
			long t = a;
			a = b;
			b = t % b;
		}
		return a;
	}

	static long powMod(long base, long exp, long mod) {
		if (base == 0 || base == 1)
			return base;
		if (exp == 0)
			return 1;
		if (exp == 1)
			return base % mod;
		long R = powMod(base, exp / 2, mod) % mod;
		R *= R;
		R %= mod;
		if ((exp & 1) == 1) {
			return base * R % mod;
		} else
			return R % mod;
	}

	static long pow(long base, long exp) {
		if (base == 0 || base == 1)
			return base;
		if (exp == 0)
			return 1;
		if (exp == 1)
			return base;
		long R = pow(base, exp / 2);
		if ((exp & 1) == 1) {
			return R * R * base;
		} else
			return R * R;
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
			byte[] buf = new byte[64]; // line length 
			int cnt = 0, c;
			while ((c = read()) != -1) {
				if (c == '\n') {
					break;
				}
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, Integer.max(cnt - 1, 0));
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

}
