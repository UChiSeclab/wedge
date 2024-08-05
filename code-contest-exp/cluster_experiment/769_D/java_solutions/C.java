import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class C {
  static PrintWriter out;
  static BufferedReader in;
  static StringTokenizer tok = new StringTokenizer("");

  public static void main(String[] args) throws FileNotFoundException {
    in = new BufferedReader(new InputStreamReader(System.in));
    out = new PrintWriter(System.out);
    solve();
    out.close();
  }

  private static String readString() {
    while (!tok.hasMoreTokens()) {
      try {
        tok = new StringTokenizer(in.readLine());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return tok.nextToken();
  }

  static int readInt() {
    return Integer.parseInt(readString());
  }

  static long readLong() {
    return Long.parseLong(readString());
  }

  static double readDouble() {
    return Double.parseDouble(readString());
  }


  public static void solve() {
    int n = readInt();
    int k = readInt();
    int MAX = 10001;
    long[] counts = new long[MAX];
    for (int i = 0; i < n; i++) {
      counts[readInt()]++;
    }
    long ans = 0;
    for (int i = 0; i < MAX; i++) {
      for (int j = 0; j < MAX; j++) {
        if (counts[i] > 0 && counts[j] > 0 && Integer.bitCount(i ^ j) == k) {
          if (k == 0) {
            ans += (counts[i]) * (counts[i] - 1);
          } else {
            ans += counts[i] * counts[j];
          }
        }
      }
    }
    out.print(ans / 2);

  }


}
