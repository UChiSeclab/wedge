import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.io.InputStream;

/**
 * Built using CHelper plug-in Actual solution is at the top
 *
 * @author MaxHeap
 */
public class Main {

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    FMovingPoints solver = new FMovingPoints();
    solver.solve(1, in, out);
    out.close();
  }

  static class FMovingPoints {

    public void solve(int testNumber, InputReader in, PrintWriter out) {
      int n = in.nextInt();
      int[] x = in.nextIntArray(n);
      int[] v = in.nextIntArray(n);
      // v = Arr.compress(v);
      int[] so = v.clone();
      Sort.sort(so);
      int ne = 0;
      HashMap<Integer, Integer> map = new HashMap<>();
      for (int i : so) {
        if (i < 0) {
          ne++;
        }
      }
      int st = -ne;
      for (int i : so) {
        if (st == 0) {
          ++st;
        }
        if (!map.containsKey(i)) {
          map.put(i, st++);
        }
      }
      for (int i = 0; i < n; ++i) {
        v[i] = map.get(v[i]);
      }
      int pt = 0;
      IntPair[] pts = new IntPair[n];
      for (int i = 0; i < n; ++i) {
        pts[i] = new IntPair(x[i], v[i]);
      }
      Arrays.sort(pts);
      long ans = 0;
      // <- ->
      // out.println(Arrays.toString(pts));
      long sum = 0, cnt = 0;
      for (int i = 0; i < n; ++i) {
        if (pts[i].y < 0) {
          sum += pts[i].x;
          ++cnt;
        } else {
          ans += cnt * pts[i].x - sum;
        }
      }
      // same direction different speed
      List<IntPair> pos = new ArrayList<>();
      List<IntPair> neg = new ArrayList<>();
      for (int i = 0; i < n; ++i) {
        if (pts[i].y < 0) {
          neg.add(pts[i]);
        } else {
          pos.add(pts[i]);
        }
      }
      FMovingPoints.FenTree tree = new FMovingPoints.FenTree(2 * n);
      FMovingPoints.FenTree treeCount = new FMovingPoints.FenTree(2 * n);
      for (int i = pos.size() - 1; i >= 0; --i) {
        IntPair cur = pos.get(i);
        long has = tree.get(cur.y, 2 * n - 1);
        long count = treeCount.get(cur.y, 2 * n - 1);
        ans += has - count * cur.x;
        tree.update(cur.y, cur.x);
        treeCount.update(cur.y, 1);
      }
      tree = new FMovingPoints.FenTree(2 * n);
      treeCount = new FMovingPoints.FenTree(2 * n);
      for (int i = 0; i < neg.size(); ++i) {
        IntPair cur = neg.get(i);
        cur.y *= -1;
        long has = tree.get(cur.y, 2 * n - 1);
        long count = treeCount.get(cur.y, 2 * n - 1);
        ans += count * cur.x - has;
        tree.update(cur.y, cur.x);
        treeCount.update(cur.y, 1);
      }
      out.println(ans);
    }

    static class FenTree {

      long[] values;
      int n;

      public FenTree(int n) {
        values = new long[n];
        this.n = n;
      }

      void update(int index, long value) {
        while (index < n) {
          values[index] += value;
          index += (index & -index);
        }
      }

      long get(int index) {
        long res = 0;
        while (index > 0) {
          res += values[index];
          index -= (index & -index);
        }
        return res;
      }

      long get(int l, int r) {
        return get(r) - get(l - 1);
      }

    }

  }

  static final class Sort {

    final static Random rand = new Random();

    public static void shuffle(int[] arr) {
      for (int i = 0; i < arr.length; ++i) {
        int index = rand.nextInt(arr.length);
        swap(arr, index, i);
      }
    }

    public static void sort(int[] arr) {
      shuffle(arr);
      Arrays.sort(arr);
    }

    public static void swap(int[] arr, int i, int j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }

  }

  static class IntPair implements Comparable<IntPair> {

    public int x;
    public int y;

    public IntPair(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public IntPair(IntPair that) {
      this.x = that.x;
      this.y = that.y;
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      IntPair intPair = (IntPair) o;
      return x == intPair.x &&
          y == intPair.y;
    }

    public int hashCode() {
      return Objects.hash(x, y);
    }

    public String toString() {
      return "IntPair{" +
          "x=" + x +
          ", y=" + y +
          '}';
    }

    public int compareTo(IntPair o) {
      int c = Integer.compare(x, o.x);
      if (c == 0) {
        return Integer.compare(y, o.y);
      }
      return c;
    }

  }

  static class InputReader {

    private InputStream stream;
    private static final int DEFAULT_BUFFER_SIZE = 1 << 16;
    private static final int EOF = -1;
    private byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
    private int curChar;
    private int numChars;

    public InputReader(InputStream stream) {
      this.stream = stream;
    }

    public int read() {
      if (this.numChars == EOF) {
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
            return EOF;
          }
        }

        return this.buf[this.curChar++];
      }
    }

    public int nextInt() {
      int c;
      for (c = this.read(); isSpaceChar(c); c = this.read()) {
      }

      byte sgn = 1;
      if (c == 45) {
        sgn = -1;
        c = this.read();
      }

      int res = 0;

      while (c >= 48 && c <= 57) {
        res *= 10;
        res += c - 48;
        c = this.read();
        if (isSpaceChar(c)) {
          return res * sgn;
        }
      }

      throw new InputMismatchException();
    }

    public static boolean isSpaceChar(int c) {
      return c == 32 || c == 10 || c == 13 || c == 9 || c == EOF;
    }

    public int[] nextIntArray(int n) {
      int[] arr = new int[n];
      for (int i = 0; i < n; i++) {
        arr[i] = nextInt();
      }
      return arr;
    }

  }
}

