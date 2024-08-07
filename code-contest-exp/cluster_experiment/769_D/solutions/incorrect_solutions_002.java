import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

		int arr[] = new int[10001];

		for (int i = 0; i < n; ++i) {
			int curr = jin.nextInt();
			arr[curr]++;
		}

		int total = 0;

		for (int i = 0; i < arr.length; ++i) {
			for (int j = i; j < arr.length; ++j) {
				if (arr[i] != 0 && arr[j] != 0 && count(i ^ j) == k) {
					if (j == i && arr[i] > 1)
						total += arr[i];
					else
						total += arr[i] * arr[j];
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
