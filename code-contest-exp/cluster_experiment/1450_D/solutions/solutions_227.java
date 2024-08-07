import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in Actual solution is at the top
 *
 * @author dauom
 */
public class Main {
  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    DRatingCompression solver = new DRatingCompression();
    int testCount = Integer.parseInt(in.next());
    for (int i = 1; i <= testCount; i++) solver.solve(i, in, out);
    out.close();
  }

  static class DRatingCompression {
    public final void solve(int testNumber, InputReader in, PrintWriter out) {
      int n = in.nextInt();
      final int[] leftest = new int[n + 1];
      final int[] rightest = new int[n + 1];
      int[] a = in.nextIntArray(n);
      char[] ans = new char[n];
      for (int i = 0; i < n; i++) {
        if (leftest[a[i]] == 0) {
          leftest[a[i]] = rightest[a[i]] = i + 1;
        } else {
          rightest[a[i]] = i + 1;
        }
      }

      Arrays.fill(ans, '0');
      for (int i = 1; i <= n; i++) {
        if (leftest[i] != 0) {
          ans[n - i] = '1';
        } else {
          break;
        }
      }

      int[][] st = new int[19][n];
      for (int[] strow : st) {
        Arrays.fill(strow, Constants.INF32);
      }

      for (int i = 0; i < n; i++) {
        st[0][i] = a[i];
      }
      for (int i = 1; (1 << i) <= n; i++) {
        for (int j = 0; j + (1 << i) <= n; j++) {
          st[i][j] = Math.min(st[i - 1][j], st[i - 1][j + (1 << (i - 1))]);
        }
      }

      int[] bad = new int[n + 2];
      for (int i = 0; i < n; i++) {
        int L = i, R = i;

        int lo = 0, hi = i;
        while (lo <= hi) {
          int mid = (lo + hi) >> 1;
          if (get(st, mid, i) == a[i]) {
            L = mid;
            hi = mid - 1;
          } else {
            lo = mid + 1;
          }
        }
        lo = i;
        hi = n - 1;
        while (lo <= hi) {
          int mid = (lo + hi) >> 1;
          if (get(st, i, mid) == a[i]) {
            R = mid;
            lo = mid + 1;
          } else {
            hi = mid - 1;
          }
        }

        if (L != i && R != i) {
          bad[2] += 1;
          bad[R - L + 1] -= 1;
        }
      }
      for (int i = 1; i <= n; i++) {
        bad[1] += 1;
        bad[rightest[i] - leftest[i] + 1] -= 1;
      }

      for (int i = 1; i <= n; i++) {
        bad[i] += bad[i - 1];
        if (bad[i] != 0) {
          ans[i - 1] = '0';
        }
      }

      out.println(ans);
    }

    private final int get(int[][] st, int i, int j) {
      int k = 0;
      int d = j - i + 1;
      while ((1 << k) <= d) {
        ++k;
      }
      --k;
      return Math.min(st[k][i], st[k][j - (1 << k) + 1]);
    }
  }

  static final class InputReader {
    private final InputStream stream;
    private final byte[] buf = new byte[1 << 18];
    private int curChar;
    private int numChars;

    public InputReader() {
      this.stream = System.in;
    }

    public InputReader(final InputStream stream) {
      this.stream = stream;
    }

    private int read() {
      if (this.numChars == -1) {
        throw new UnknownError();
      } else {
        if (this.curChar >= this.numChars) {
          this.curChar = 0;

          try {
            this.numChars = this.stream.read(this.buf);
          } catch (IOException ex) {
            throw new InputMismatchException();
          }

          if (this.numChars <= 0) {
            return -1;
          }
        }

        return this.buf[this.curChar++];
      }
    }

    public final int nextInt() {
      int c;
      for (c = this.read(); isSpaceChar(c); c = this.read()) {}

      byte sgn = 1;
      if (c == 45) { // 45 == '-'
        sgn = -1;
        c = this.read();
      }

      int res = 0;

      while (c >= 48 && c <= 57) { // 48 == '0', 57 == '9'
        res *= 10;
        res += c - 48; // 48 == '0'
        c = this.read();
        if (isSpaceChar(c)) {
          return res * sgn;
        }
      }

      throw new InputMismatchException();
    }

    public final String next() {
      int c;
      while (isSpaceChar(c = this.read())) {}

      StringBuilder result = new StringBuilder();
      result.appendCodePoint(c);

      while (!isSpaceChar(c = this.read())) {
        result.appendCodePoint(c);
      }

      return result.toString();
    }

    private static boolean isSpaceChar(final int c) {
      return c == 32 || c == 10 || c == 13 || c == 9
          || c == -1; // 32 == ' ', 10 == '\n', 13 == '\r', 9 == '\t'
    }

    public final int[] nextIntArray(final int n) {
      int[] arr = new int[n];
      for (int i = 0; i < n; i++) {
        arr[i] = nextInt();
      }
      return arr;
    }
  }

  static class Constants {
    public static final int INF32 = 0x3f3f3f3f;
  }
}
