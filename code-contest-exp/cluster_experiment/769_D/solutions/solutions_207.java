import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    OutputWriter out = new OutputWriter(outputStream);
    Task solver = new Task();
    solver.solve(1, in, out);
    out.close();
  }

  static class Task {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
      int n = in.nextInt();
      int k = in.nextInt();
      int M = 1 << 14;
      long[] a = new long[M];
      for (int i = 0; i < n; i++) {
        int x = in.nextInt();
        a[x]++;
      }

      List<Integer> masks = new ArrayList<>();
      for (int i = 0; i < M; i++) {
        if (Integer.bitCount(i) == k) {
          masks.add(i);
        }
      }

      long answer = 0;
      for (int i = 0; i < M; i++) {
        for (int mask : masks) {
          if ((i ^ mask) < i) {
            answer += a[i] * a[i ^ mask];
          }
          if ((i ^ mask) == i) {
            answer += a[i] * (a[i] - 1) / 2;
          }
        }
      }

      out.println(answer);
    }

  }

  static class OutputWriter extends PrintWriter {
    public OutputWriter(String fileName) throws FileNotFoundException {
      super(fileName);
    }

    public OutputWriter(OutputStream outputStream) {
      super(outputStream);
    }

    public OutputWriter(Writer writer) {
      super(writer);
    }

  }

  static class InputReader {
    BufferedReader br;
    StringTokenizer in;

    public InputReader(String fileName) {
      try {
        br = new BufferedReader(new FileReader(fileName));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    public InputReader(InputStream inputStream) {
      br = new BufferedReader(new InputStreamReader(inputStream));
    }

    boolean hasMoreTokens() {
      while (in == null || !in.hasMoreTokens()) {
        String s = nextLine();
        if (s == null) {
          return false;
        }
        in = new StringTokenizer(s);
      }
      return true;
    }

    public String nextString() {
      return hasMoreTokens() ? in.nextToken() : null;
    }

    public String nextLine() {
      try {
        in = null;
        return br.readLine();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    public int nextInt() {
      return Integer.parseInt(nextString());
    }
    
  }
}