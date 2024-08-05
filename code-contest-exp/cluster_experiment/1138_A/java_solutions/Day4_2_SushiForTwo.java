
import java.io.*;
import java.util.*;

public class Day4_2_SushiForTwo {

	public static void main(String[] args) {
		InputReader reader = new InputReader(System.in);
		int n = reader.nextInt();
		int count = 0, countOne = Integer.MAX_VALUE, countTwo = Integer.MAX_VALUE;
		int currentNum = -1;
		int[] pieces = new int[n];
		for (int i = 0; i < n; i++) {
			pieces[i] = reader.nextInt();
			if (pieces[i] == currentNum) {
				count++;
			} else {
				if (currentNum == 1)
					countOne = Math.min(count, countOne);
				else if (currentNum == 2)
					countTwo = Math.min(countTwo, count);

				currentNum = pieces[i];
				count = 1;
				if (i == n - 1) {
					if (currentNum == 1)
						countOne = Math.min(count, countOne);
					else if (currentNum == 2)
						countTwo = Math.min(countTwo, count);
				}
			}
		}
		int result = Math.max(countOne, countTwo);
		System.out.print(result * 2);
	}

	static class InputReader {
		StringTokenizer tokenizer;
		BufferedReader reader;
		String token;
		String temp;

		public InputReader(InputStream stream) {
			tokenizer = null;
			reader = new BufferedReader(new InputStreamReader(stream));
		}

		public InputReader(FileInputStream stream) {
			tokenizer = null;
			reader = new BufferedReader(new InputStreamReader(stream));
		}

		public String nextLine() throws IOException {
			return reader.readLine();
		}

		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					if (temp != null) {
						tokenizer = new StringTokenizer(temp);
						temp = null;
					} else {
						tokenizer = new StringTokenizer(reader.readLine());
					}
				} catch (IOException e) {
				}
			}
			return tokenizer.nextToken();
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}
