import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author El Mehdi ASSALI
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
    int n;
    int[] arr;
    int[][] memo;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
      n = in.nextInt();
      arr = new int[n];
      for (int i = 0; i < n; i++) {
        arr[i] = in.nextInt();
      }
      memo = new int[n][2];
      for (int[] line : memo) {
        Arrays.fill(line, -1);
      }
      for (int i = 0; i < n; i++) {
        dfs(i);
      }
      for (int i = 0; i < n; i++) {
        out.print(memo[i][(arr[i] & 1) ^ 1] + " ");
      }
    }

    void dfs(int i) {
      if (0 <= i && i < n && memo[i][arr[i] & 1] == -1) {
        memo[i][arr[i] & 1] = 0;
        dfs(i + arr[i]);
        dfs(i - arr[i]);
        if (i + arr[i] < n) {
          if (memo[i][0] == -1 && memo[i + arr[i]][0] != -1) {
            memo[i][0] = 1 + memo[i + arr[i]][0];
          }
          if (memo[i][1] == -1 && memo[i + arr[i]][1] != -1) {
            memo[i][1] = 1 + memo[i + arr[i]][1];
          }
        }
        if (i - arr[i] >= 0) {
          if (memo[i][0] == -1 && memo[i - arr[i]][0] != -1) {
            memo[i][0] = 1 + memo[i - arr[i]][0];
          }
          if (memo[i][1] == -1 && memo[i - arr[i]][1] != -1) {
            memo[i][1] = 1 + memo[i - arr[i]][1];
          }
        }
      }
    }

  }

  static class InputReader {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    public InputReader(InputStream inputStream) {
      reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public String next() {
      while (tokenizer == null || !tokenizer.hasMoreTokens()) {
        try {
          tokenizer = new StringTokenizer(reader.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return tokenizer.nextToken();
    }

    public int nextInt() {
      return Integer.parseInt(next());
    }

  }
}

