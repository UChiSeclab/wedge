
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

	public Main() throws FileNotFoundException {
//
//		File file = Paths.get("input.txt").toFile();
//		if (file.exists()) {
//			System.setIn(new FileInputStream(file));
//		}
		long t = System.currentTimeMillis();

		FastReader reader = new FastReader();

		int n = reader.nextInt();

		Set<Integer> set = new HashSet<>();

		for (int i = 0; i < n; i++) {
			int val = reader.nextInt();
			if (val == 1) {
				if (set.contains(val)) {
					set.add(val + 1);
				}else {
					set.add(val);
				}
			} else {
				if (set.contains(val)) {
					if (set.contains(val + 1)) {
						set.add(val - 1);
					} else {
						set.add(val + 1);
					}
				} else {
					set.add(val);
				}
			}

		}
		System.out.println(set.size());

	}

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
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
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		new Main();
	}

}
