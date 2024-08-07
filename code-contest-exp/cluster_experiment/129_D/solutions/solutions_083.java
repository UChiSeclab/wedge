import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
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
    DStroka solver = new DStroka();
    solver.solve(1, in, out);
    out.close();
  }

  static class DStroka {
    long[] lenPS;
    int n;
    char[] s;
    int[] order;
    int[] type;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
      String s = in.next() + "$";
      this.s = s.toCharArray();
      n = s.length();
      initSuffixArray();

      int toFind = in.readInt() - 1;
      if (toFind >= n * (n + 1L) / 2) {
        out.print("No such line.");
        return;
      }

      lenPS = new long[n + 1];
      for (int i = 0; i < n; i++) lenPS[i + 1] = lenPS[i] + (n - 1 - order[i]);

      IntIntPair p = find(toFind);
      String answer = s.substring(order[p.first], order[p.first] + p.second + 1);
      out.print(answer);
    }

    IntIntPair find(int toFind) {
      int l = 0;
      int r = n - 1;
      int at = -1;
      while (true) {
        if (order[l] + at + 1 == n - 1) l++;
        int last = findLast(l, r, at + 1);
        if (l + toFind <= last) return IntIntPair.makePair(l + toFind, at + 1);
        int len = last - l + 1;
        long lenAdd = (lenPS[last + 1] - lenPS[l]) - len * (at + 1L);
        if (toFind < lenAdd) {
          toFind -= len;
          r = last;
          at++;
          continue;
        }
        toFind -= lenAdd;
        l = last + 1;
      }
    }

    int findLast(int l, int r, int at) {
      r++;
      while (r - l > 1) {
        int m = l + r >> 1;
        if (s[order[m] + at] == s[order[l] + at]) l = m;
        else r = m;
      }
      return l;
    }

    void updateTypes(int len) {
      int[] type2 = new int[n];
      type2[order[0]] = 0;
      for (int i = 1, j = 0; i < n; i++) {
        int left1 = type[order[i]];
        int left2 = type[order[i - 1]];
        int right1 = type[(order[i] + len) % n];
        int right2 = type[(order[i - 1] + len) % n];
        if (left1 != left2 || right1 != right2) j++;
        type2[order[i]] = j;
      }
      System.arraycopy(type2, 0, type, 0, n);
    }

    void countSort(int typeCount, int len) {
      int[] count = new int[typeCount];
      for (int t : type) count[t]++;
      for (int i = 0, j = 0; i < typeCount; i++) {
        int x = count[i];
        count[i] = j;
        j += x;
      }
      int[] order2 = new int[n];
      for (int x : order) order2[count[type[x]]++] = x;
      System.arraycopy(order2, 0, order, 0, n);
      updateTypes(len);
    }

    void initSuffixArray() {
      order = new int[n];
      type = new int[n];
      for (int i = 0; i < n; i++) order[i] = i;
      for (int i = 0; i < n; i++) type[i] = s[i];
      countSort(1 << 8, 0);
      for (int len = 1, typeCount; (typeCount = type[order[n - 1]] + 1) < n; len <<= 1) {
        for (int i = 0; i < n; i++) order[i] = (order[i] - len + n) % n;
        countSort(typeCount, len);
      }
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

    public String readString() {
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

    public String next() {
      return readString();
    }

    public interface SpaceCharFilter {
      public boolean isSpaceChar(int ch);

    }

  }

  static class IntIntPair implements Comparable<IntIntPair> {
    public final int first;
    public final int second;

    public static IntIntPair makePair(int first, int second) {
      return new IntIntPair(first, second);
    }

    public IntIntPair(int first, int second) {
      this.first = first;
      this.second = second;
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      IntIntPair pair = (IntIntPair) o;

      return first == pair.first && second == pair.second;
    }

    public int hashCode() {
      int result = first;
      result = 31 * result + second;
      return result;
    }

    public String toString() {
      return "(" + first + "," + second + ")";
    }

    public int compareTo(IntIntPair o) {
      int value = Integer.compare(first, o.first);
      if (value != 0) {
        return value;
      }
      return Integer.compare(second, o.second);
    }

  }
}

