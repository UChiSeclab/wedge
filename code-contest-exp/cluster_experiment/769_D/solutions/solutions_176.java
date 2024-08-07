import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class D {
	static int count(int n) {
		int count = 0;
		while (n > 0) {
			n &= (n - 1);
			count++;
		}
		return count;
	}

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader jin = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);

		int n = jin.nextInt();
		int k = jin.nextInt();

		long arr[] = new long[10001];

		for (int i = 0; i < n; ++i) {
			int curr = jin.nextInt();
			arr[curr]++;
		}

		long total = 0;

		for (int i = 0; i < arr.length; ++i) {
			for (int j = i; j < arr.length; ++j) {
				if (count(i ^ j) == k) {
					if (i != j)
						total += arr[i] * arr[j];
					else
						total += (arr[i] * (arr[i] - 1)) / 2;
				}
			}
		}

		out.println(total);
		out.close();
	}

	static class InputReader {
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

		public long nextLong() {
			return Long.parseLong(next());
		}
	}
}
