import java.io.*;
import java.util.*;

public class Main {

//	static boolean[] prime = new boolean[200000];

	public static void main(String[] args) {
//		sieve();
		InputReader in = new InputReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = in.nextInt();
		while (t-- > 0) {
			int n = in.nextInt();
			String str = in.next();
			double d = str.charAt(0) == 'D' ? 1 : 0;
			double k = str.charAt(0) == 'K' ? 1 : 0;
			HashMap<Double, Integer> map = new HashMap<>();
			out.print(1 + " ");
			for(int i = 1; i < n; i++) {
				if(str.charAt(i) == 'D')
					d++;
				else
					k++;
				if(k == 0 || d == 0) {
					out.print((int)Math.max(k, d) + " ");
				}else {
					map.put(d/k, map.getOrDefault(d/k, 0) + 1);
					out.print(map.get(d/k) + " ");
				}
			}
			out.println();
		}
		out.flush();
	}

	static long total(long n) {
		return (n * (n + 1)) / 2;
	}

	static long gcd(long a, long b) {
		if (a % b == 0) {
			return b;
		} else {
			return gcd(b, a % b);
		}
	}

	static void reverseArray(int[] a) {
		for (int i = 0; i < (a.length >> 1); i++) {
			int temp = a[i];
			a[i] = a[a.length - 1 - i];
			a[a.length - 1 - i] = temp;
		}
	}

	static int[] intInput(int n, InputReader in) {
		int[] a = new int[n];
		for (int i = 0; i < a.length; i++)
			a[i] = in.nextInt();
		return a;
	}

	static long[] longInput(int n, InputReader in) {
		long[] a = new long[n];
		for (int i = 0; i < a.length; i++)
			a[i] = in.nextLong();
		return a;
	}

	static String[] strInput(int n, InputReader in) {
		String[] a = new String[n];
		for (int i = 0; i < a.length; i++)
			a[i] = in.next();
		return a;
	}

//	static void sieve() {
//		for (int i = 2; i * i < prime.length; i++) {
//			if (prime[i])
//				continue;	
//			for (int j = i * i; j < prime.length; j += i) {
//				prime[j] = true;
//			}
//		}
//	}

}

class Data {
	int i, v;

	Data(int i, int v) {
		this.i = i;
		this.v = v;
	}
}

class compare implements Comparator<Data> {

	@Override
	public int compare(Data o1, Data o2) {
		return o1.v - o2.v;
	}

}

class InputReader {
	public BufferedReader reader;
	public StringTokenizer tokenizer;

	public InputReader(InputStream stream) {
		reader = new BufferedReader(new InputStreamReader(stream), 32768);
		tokenizer = null;
	}

	public String next() {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			try {
				tokenizer = new StringTokenizer(reader.readLine());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return tokenizer.nextToken();
	}

	public int nextInt() {
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
			str = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

}