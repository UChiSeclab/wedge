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
 * @author NMouad21
 */
public class Main {

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    ENearestOppositeParity solver = new ENearestOppositeParity();
    solver.solve(1, in, out);
    out.close();
  }

  static class ENearestOppositeParity {

    public final void solve(int testNumber, InputReader in, PrintWriter out) {
      int n = in.nextInt();
      int[] a = in.nextIntArrayOneBased(n);
      int[][] e = new int[n << 1][];
      int pt = 0;
      for (int i = 1; i <= n; i++) {
        if (i - a[i] >= 1) {
          e[pt++] = new int[]{i - a[i], i};
        }
        if (i + a[i] <= n) {
          e[pt++] = new int[]{i + a[i], i};
        }
      }
      e = Arrays.copyOf(e, pt);
      int[][] t = GraphUtils.packDirectedUnweighted(e, n);

      int[] ans = new int[n + 1];
      Arrays.fill(ans, Constants.INF32);

      for (int j = 0; j < 2; j++) {
        int[] queue = new int[n + 1];
        int[] dist = new int[n + 1];
        int addPt = 0, pollPt = 0;
        for (int i = 1; i <= n; i++) {
          if (a[i] % 2 != j) {
            queue[addPt++] = i;
          }
        }
        while (addPt > pollPt) {
          int u = queue[pollPt++];
          for (int v : t[u]) {
            if (a[v] % 2 == j && dist[v] == 0) {
              ans[v] = dist[v] = dist[u] + 1;
              queue[addPt++] = v;
            }
          }
        }
      }

      for (int i = 1; i <= n; i++) {
        if (ans[i] == Constants.INF32) {
          out.print("-1");
        } else {
          out.print(ans[i]);
        }
        out.print(" ");
      }
    }

  }

  static final class GraphUtils {

    private GraphUtils() {
      throw new RuntimeException("DON'T");
    }

    public static final int[][] packDirectedUnweighted(int[][] edges, int n) {
      int[][] g = new int[n + 1][];
      int[] size = new int[n + 1];
      for (int[] edge : edges) {
        ++size[edge[0]];
      }
      for (int i = 0; i <= n; i++) {
        g[i] = new int[size[i]];
      }
      for (int[] edge : edges) {
        g[edge[0]][--size[edge[0]]] = edge[1];
      }
      return g;
    }

  }

  static class Constants {

    public static final int INF32 = 0x3f3f3f3f;

  }

  static final class InputReader {

    private final InputStream stream;
    private final byte[] buf = new byte[1 << 16];
    private int curChar;
    private int numChars;

    public InputReader() {
      this.stream = System.in;
    }

    public InputReader(final InputStream stream) {
      this.stream = stream;
    }

    private final int read() {
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
      for (c = this.read(); isSpaceChar(c); c = this.read()) {
      }

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

    private static final boolean isSpaceChar(final int c) {
      return c == 32 || c == 10 || c == 13 || c == 9
          || c == -1; // 32 == ' ', 10 == '\n', 13 == '\r', 9 == '\t'
    }

    public final int[] nextIntArrayOneBased(final int n) {
      int[] ret = new int[n + 1];
      for (int i = 1; i <= n; i++) {
        ret[i] = nextInt();
      }
      return ret;
    }

  }
}

