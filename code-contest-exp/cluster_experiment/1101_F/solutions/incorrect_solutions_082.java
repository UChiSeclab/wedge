import java.util.*;
import java.io.*;

public class P1101F {

  private static void solve() {
    int n = nextInt();
    int m = nextInt();

    long[] a = new long[n];

    for (int i = 0; i < n; i++) {
      a[i] = nextInt();
    }

    int[][] trucks = new int[m][4];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < 4; j++) {
        trucks[i][j] = nextInt();
      }
    }

    long min = 1, max = (long)1E18;

    long ans = Long.MAX_VALUE;

    while (min <= max) {
      long mid = (min + max) / 2;

      if (check(trucks, a, mid)) {
        max = mid - 1;
        ans = Math.min(ans, mid);
      } else {
        min = mid + 1;
      }
    }

    System.out.println(ans);
  }

  private static boolean check(int[][] trucks, long[] a, long mid) {

    boolean[][] store = new boolean[a.length][a.length];

    boolean good = true;
    for (int i = 0; i < trucks.length && good; i++) {
      int s = trucks[i][0] - 1;
      int f = trucks[i][1] - 1;

      if (store[s][f]) continue;

      boolean pos = true;
      long fuel = mid;
      int minRefill = 0;
      for (int j = s + 1; j <= f && pos; j++) {
        long req = (a[j] - a[j - 1]) * trucks[i][2];
        if (req <= fuel) {
          fuel -= req;
        } else if (req <= mid) {
          fuel = mid;
          fuel -= req;
          minRefill++;
        } else {
          pos = false;
        }
      }

      if (!pos || minRefill > trucks[i][3]) {
        good = false;
      }

      store[s][f] = true;
    }
    return good;
  }

  private static void run() {
    br = new BufferedReader(new InputStreamReader(System.in));
    out = new PrintWriter(System.out);

    solve();

    out.close();
  }

  private static StringTokenizer st;
  private static BufferedReader br;
  private static PrintWriter out;

  private static String next() {
    while (st == null || !st.hasMoreElements()) {
      String s;
      try {
        s = br.readLine();
      } catch (IOException e) {
        return null;
      }
      st = new StringTokenizer(s);
    }
    return st.nextToken();
  }

  private static int nextInt() {
    return Integer.parseInt(next());
  }

  private static long nextLong() {
    return Long.parseLong(next());
  }

  public static void main(String[] args) {
    run();
  }
}