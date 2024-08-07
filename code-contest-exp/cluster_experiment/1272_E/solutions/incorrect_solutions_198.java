
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;

/**
 * @author Mubtasim Shahriar
 */

public class OppPar {

	public static final long INFINITY = (long) 1e7;

	public static void main(String[] args) {

		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader sc = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Solver solver = new Solver();
		// int t = sc.nextInt();
		int t = 1;
		while (t-- != 0) {
			solver.solve(sc, out);
		}
		out.close();

	}

	static class Solver {
		public void solve(InputReader sc, PrintWriter out) {
			int n = sc.nextInt();
			int[] arr = sc.nextIntArray(n);
//			int[] arr = new int[n];
//			for (int i = 0; i < n; i++) {
//				arr[i] = (int) (Math.random() * (n - 1) + 1);
//			}
//			out.println(Arrays.toString(arr));
			long[] dp = new long[n];
			long[] dpreverse = new long[n];
			long ans[] = { 0 };
			for (int i = 0; i < n; i++) {
				HashSet<Integer> poss = new HashSet();
//				long now = search(arr, dp, i, i, ans, poss);
				if(dp[i]==0) dp[i] = search(arr,dp,i,i,ans,poss);
//				out.println(dp[84] + " " + dp[84-8] + " " + i + " " + arr[i]);
//				 System.out.println(Arrays.toString(dp));
//				out.println(poss.toString());
			}
			for (int i = n-1; i >= 0; i--) {
				HashSet<Integer> poss = new HashSet();
//				long now = search(arr, dp, i, i, ans, poss);
				if(dpreverse[i]==0) dpreverse[i] = search(arr,dpreverse,i,i,ans,poss);
//				out.println(dp[84] + " " + dp[84-8] + " " + i + " " + arr[i]);
//				 System.out.println(Arrays.toString(dp));
//				out.println(poss.toString());
			}
			for(int i = 0; i < n; i++) {
				if(dpreverse[i]!=-1) {
					if(dp[i]==-1) dp[i] = dpreverse[i];
					else dp[i] = Math.min(dp[i], dpreverse[i]);
				}
			}
			for (Long l : dp)
				out.print(l + " ");
			out.println();
//			out.println(ans[0]);
		}

		public long search(int[] arr, long[] dp, int pos, int abspos, long[] ans, HashSet<Integer> poss) {
			ans[0]++;
			int leftpos = pos - arr[pos];
			int rightpos = pos + arr[pos];
			long left = 0;
			long right = 0;
			if (pos >= 0 && pos < arr.length)
				poss.add(pos);
//			if(abspos==2) System.out.println(leftpos+1 + " " + (rightpos+1));
//			System.out.println(abspos);
			if (leftpos >= 0) {
				if (isopposite(arr[leftpos], arr[abspos]))
					left = 0;
				else if (poss.contains(leftpos))
					left = INFINITY;
				else {
					poss.add(leftpos);
					if (dp[leftpos] == 0) {
						dp[leftpos] = search(arr, dp, leftpos, abspos, ans, poss);
					}
					if (dp[leftpos] == -1)
						left = INFINITY;
					else
						left = dp[leftpos];
				}
			} else {
				left = INFINITY;
			}
			if (rightpos < arr.length) {
				if (isopposite(arr[rightpos], arr[abspos]))
					right = 0;
				else if (poss.contains(rightpos))
					right = INFINITY;
				else {
					poss.add(rightpos);
					if (dp[rightpos] == 0) {
						dp[rightpos] = search(arr, dp, rightpos, abspos, ans, poss);
					}
					if (dp[rightpos] == -1)
						right = INFINITY;
					else
						right = dp[rightpos];
				}
			} else {
				right = INFINITY;
			}
			long cnt = Math.min(left, right);
			cnt++;
//			System.out.println(pos + 1 + " " + cnt);
			dp[pos] = cnt >= INFINITY ? -1 : cnt;
			return dp[pos];
		}

		private boolean isopposite(int i, int j) {
			if (i % 2 == 0 && j % 2 != 0)
				return true;
			if (i % 2 != 0 && j % 2 == 0)
				return true;
			return false;
		}
	}

	static class InputReader {
		private boolean finished = false;

		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int read() {
			if (numChars == -1) {
				throw new InputMismatchException();
			}
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0) {
					return -1;
				}
			}
			return buf[curChar++];
		}

		public int peek() {
			if (numChars == -1) {
				return -1;
			}
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					return -1;
				}
				if (numChars <= 0) {
					return -1;
				}
			}
			return buf[curChar];
		}

		public int nextInt() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public long nextLong() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public String nextString() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			StringBuilder res = new StringBuilder();
			do {
				if (Character.isValidCodePoint(c)) {
					res.appendCodePoint(c);
				}
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public boolean isSpaceChar(int c) {
			if (filter != null) {
				return filter.isSpaceChar(c);
			}
			return isWhitespace(c);
		}

		public static boolean isWhitespace(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		private String readLine0() {
			StringBuilder buf = new StringBuilder();
			int c = read();
			while (c != '\n' && c != -1) {
				if (c != '\r') {
					buf.appendCodePoint(c);
				}
				c = read();
			}
			return buf.toString();
		}

		public String readLine() {
			String s = readLine0();
			while (s.trim().length() == 0) {
				s = readLine0();
			}
			return s;
		}

		public String readLine(boolean ignoreEmptyLines) {
			if (ignoreEmptyLines) {
				return readLine();
			} else {
				return readLine0();
			}
		}

		public BigInteger readBigInteger() {
			try {
				return new BigInteger(nextString());
			} catch (NumberFormatException e) {
				throw new InputMismatchException();
			}
		}

		public char nextCharacter() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			return (char) c;
		}

		public double nextDouble() {
			int c = read();
			while (isSpaceChar(c)) {
				c = read();
			}
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') {
				if (c == 'e' || c == 'E') {
					return res * Math.pow(10, nextInt());
				}
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') {
				c = read();
				double m = 1;
				while (!isSpaceChar(c)) {
					if (c == 'e' || c == 'E') {
						return res * Math.pow(10, nextInt());
					}
					if (c < '0' || c > '9') {
						throw new InputMismatchException();
					}
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			return res * sgn;
		}

		public boolean isExhausted() {
			int value;
			while (isSpaceChar(value = peek()) && value != -1) {
				read();
			}
			return value == -1;
		}

		public String next() {
			return nextString();
		}

		public SpaceCharFilter getFilter() {
			return filter;
		}

		public void setFilter(SpaceCharFilter filter) {
			this.filter = filter;
		}

		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}

		public int[] nextIntArray(int n) {
			int[] array = new int[n];
			for (int i = 0; i < n; ++i)
				array[i] = nextInt();
			return array;
		}

		public int[] nextSortedIntArray(int n) {
			int array[] = nextIntArray(n);
			Arrays.sort(array);
			return array;
		}

		public int[] nextSumIntArray(int n) {
			int[] array = new int[n];
			array[0] = nextInt();
			for (int i = 1; i < n; ++i)
				array[i] = array[i - 1] + nextInt();
			return array;
		}

		public long[] nextLongArray(int n) {
			long[] array = new long[n];
			for (int i = 0; i < n; ++i)
				array[i] = nextLong();
			return array;
		}

		public long[] nextSumLongArray(int n) {
			long[] array = new long[n];
			array[0] = nextInt();
			for (int i = 1; i < n; ++i)
				array[i] = array[i - 1] + nextInt();
			return array;
		}

		public long[] nextSortedLongArray(int n) {
			long array[] = nextLongArray(n);
			Arrays.sort(array);
			return array;
		}
	}

}
