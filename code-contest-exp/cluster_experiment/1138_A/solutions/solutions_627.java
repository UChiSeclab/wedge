import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class shushi_for_2 {
	private static Reader fr;
	private static OutputStream out;
	private static final int delta = (int) 1e9 + 7;

	private static class Reader {
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

		public int[] nextArray(int n) throws IOException {
			int arr[] = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = nextInt();
			}
			return arr;
		}
	}

	private static void print(Object str) {
		try {
			out.write(str.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void println(Object str) {
		try {
			out.write((str.toString() + "\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printArray(int arr[]) {
		for (int val : arr) {
			print(val + " ");
		}
		println("");
	}

	private static long add(long a, long b) {
		return add(a, b, delta);
	}

	private static long add(long a, long b, long mod) {
		return ((a % mod) + (b % mod)) % mod;
	}

	private static long multiply(long a, long b) {
		return ((a % delta) * (b % delta)) % delta;
	}

	public static double multiply(long a, double b) {
		return ((a % delta) * (b % delta)) % delta;
	}

	private static long pow(int base, int pow) {
		if (pow == 0)
			return 1;
		if (pow == 1)
			return base;
		long halfPow = pow(base, pow / 2);
		if ((pow & 1) == 0)
			return multiply(halfPow, halfPow);
		return multiply(halfPow, multiply(base, halfPow));
	}

	private static int gcd(int a, int b) {
		if (a == 0 || b == 0)
			return a == 0 ? b : a;
		return gcd(b, a % b);
	}

	private static long kadane(int[] arr) {
		int size = arr.length;
		long max_so_far = Integer.MIN_VALUE, max_ending_here = 0;

		for (int anArr : arr) {
			max_ending_here = max_ending_here + anArr;
			if (max_so_far < max_ending_here)
				max_so_far = max_ending_here;
			if (max_ending_here < 0)
				max_ending_here = 0;
		}
		return max_so_far;
	}

	public static void main(String args[]) throws IOException {
		run();
	}

	private static void run() throws IOException {
		fr = new Reader();
		out = new BufferedOutputStream(System.out);
		solve();
		out.flush();
		out.close();
	}

	private static void solve() throws IOException {
		int n = fr.nextInt();
		int[] arr = fr.nextArray(n);
		ArrayList<Integer> size = new ArrayList<Integer>();
		int co = 0;
		int ct = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] == 1) {
				co++;
				if (ct != 0) {
					size.add(ct);
					ct = 0;
				}

			} else {
				ct++;
				if (co != 0) {
					size.add(co);
					co = 0;
				}
			}
		}
		if (arr[n - 1] == 1) {
			size.add(co);
		} else {
			size.add(ct);
		}
		int ele = arr[0];
		int ans = 0;
		for (int i = 1; i < size.size(); i++) {
			int temp1 = size.get(i - 1);
			int temp2 = size.get(i);
			int temp = Math.min(temp1, temp2);
			if(temp > ans) {
				ans = temp;
			}
		}
		println(ans * 2);
	}
}
