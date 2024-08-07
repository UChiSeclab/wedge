import java.util.*;
import java.io.*;

public class A {
  public static void main(String[] args) throws IOException {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    Task solver = new Task();
    solver.solve(1, in, out);
    out.close();
  }
  
  static class Task {
    public void solve(int testNumber, InputReader in, PrintWriter out) {
      final int n = in.nextInt();
      int count[] = new int[2];
      int ans = 2;
      int prev = -1;
      for (int i = 0; i < n; i++) {
        int cur = in.nextInt() - 1;
        if (prev != -1 && cur != prev) {
          count[cur] = 0;
        }
        count[cur]++;
        ans = Math.max(ans, 2 * Math.min(count[cur], count[1 - cur]));
        prev = cur;
      }
      out.println(ans);
    }
  }
  
  static class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
      reader = new BufferedReader(new InputStreamReader(stream), 32768);
      tokenizer = null;
    }

    public String next() {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      return tokenizer.nextToken();
    }

    public int nextInt() {
      return Integer.parseInt(next());
    }

    public long nextLong() {
      return Long.parseLong(next());
    }
  }
}