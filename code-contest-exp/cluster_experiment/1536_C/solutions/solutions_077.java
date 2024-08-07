import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.io.*;
import java.math.*;

public class C{
    static class InputReader {
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int read() {
			if (numChars == -1)
				throw new InputMismatchException();
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}

		public int Int() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public String String() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public boolean isSpaceChar(int c) {
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String next() {
			return String();
		}

		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	}

	static class OutputWriter {
		private final PrintWriter writer;

		public OutputWriter(OutputStream outputStream) {
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
		}

		public OutputWriter(Writer writer) {
			this.writer = new PrintWriter(writer);
		}

		public void print(Object... objects) {
			for (int i = 0; i < objects.length; i++) {
				if (i != 0)
					writer.print(' ');
				writer.print(objects[i]);
			}
		}

		public void printLine(Object... objects) {
			print(objects);
			writer.println();
		}

		public void close() {
			writer.close();
		}

		public void flush() {
			writer.flush();
		}
	}

	static InputReader in = new InputReader(System.in);
	static OutputWriter out = new OutputWriter(System.out);

    public static int i() {
		return in.Int();
	}

	public static long l() {
		String s = in.String();
		return Long.parseLong(s);
	}

	public static String s() {
		return in.String();
	}

	public static int[] readArray(int n) {
		int A[] = new int[n];
		for (int i = 0; i < n; i++) {
			A[i] = i();
		}
		return A;
	}

    public static int gcd(int x, int y) {
		if(x > y)
        {
            int t = x;
            x = y;
            y = t;
        }
		if (x == 0)
			return y;
		else
			return gcd(y % x, x);
	}

    private static void solve(String str, int n) {

		StringBuilder sb = new StringBuilder();
        HashMap<String, Integer> hm = new HashMap<>();
        int dCount = 0, kCount = 0;
        // int[] ans = new int[n];
        for(int i = 0; i < n; i++) {
            if(str.charAt(i) == 'D') dCount++;
            else kCount++;

            String curr = "";
            if(kCount == 0 ) {
                curr = "1_0";
            } else if(dCount == 0) {
                curr = "0_1";
            } else {
                int g = gcd(dCount, kCount);
                int num = dCount / g, den = kCount / g;
                curr = num + "_" + den;  
            }

            int t = 1;
            if(hm.containsKey(curr)) {
                t += hm.get(curr);
            }
            hm.put(curr, t);
            // ans[i] = t;
			sb.append(t + " ");
        }

		System.out.println(sb);
        // for(int i = 0; i < n; i++) {
        //     System.out.print(ans[i] + " ");
        // }
        // System.out.println();

    }

    public static void main(String[] args) {
		int test = i();
		while (test-- > 0) {
            int n = i();
            String str = s();
			solve(str, n);
		}

	}
}