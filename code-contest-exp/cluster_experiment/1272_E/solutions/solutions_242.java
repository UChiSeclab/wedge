import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.Collection;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.Queue;
import java.util.ArrayDeque;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Rustam Musin (t.me/musin_acm)
 */
public class Main {
  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    OutputWriter out = new OutputWriter(outputStream);
    EBlizhaishayaProtivopolozhnayaChetnost solver = new EBlizhaishayaProtivopolozhnayaChetnost();
    solver.solve(1, in, out);
    out.close();
  }

  static class EBlizhaishayaProtivopolozhnayaChetnost {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
      int n = in.readInt();
      int[] a = in.readIntArray(n);
      List<Integer>[] g = new List[n];
      for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        for (int m = -1; m <= 1; m++) {
          int to = i + a[i] * m;
          if (0 <= to && to < n) {
            g[to].add(i);
          }
        }
      }
      int[][] dist = {
          distances(a, g, 0),
          distances(a, g, 1)
      };
      for (int i = 0; i < n; i++) {
        out.print(dist[a[i] & 1 ^ 1][i] + " ");
      }
    }

    int[] distances(int[] a, List<Integer>[] g, int starts) {
      int n = a.length;
      int[] dist = new int[n];
      Arrays.fill(dist, -1);
      Queue<Integer> q = new ArrayDeque<>();
      for (int i = 0; i < n; i++)
        if (a[i] % 2 == starts) {
          q.add(i);
          dist[i] = 0;
        }
      while (!q.isEmpty()) {
        int cur = q.poll();
        for (int to : g[cur]) {
          if (dist[to] == -1) {
            dist[to] = dist[cur] + 1;
            q.add(to);
          }
        }
      }
      return dist;
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
        if (i != 0) {
          writer.print(' ');
        }
        writer.print(objects[i]);
      }
    }

    public void close() {
      writer.close();
    }

  }

  static class InputReader {
    private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    private InputReader.SpaceCharFilter filter;

    public InputReader(InputStream stream) {
      this.stream = stream;
    }

    public int[] readIntArray(int size) {
      int[] array = new int[size];
      for (int i = 0; i < size; i++) {
        array[i] = readInt();
      }
      return array;
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

    public int readInt() {
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

    public boolean isSpaceChar(int c) {
      if (filter != null) {
        return filter.isSpaceChar(c);
      }
      return isWhitespace(c);
    }

    public static boolean isWhitespace(int c) {
      return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }

    public interface SpaceCharFilter {
      public boolean isSpaceChar(int ch);

    }

  }
}

