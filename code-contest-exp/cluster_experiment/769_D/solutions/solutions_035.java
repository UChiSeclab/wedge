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
 
 
  static final int MAX = 10001;
  static final int maxBits = 14;
  static final int maxMask = 1 << maxBits;
 
  public static void solve() {
    int n = readInt();
    int k = readInt();
    long[] counts = new long[MAX];
    for (int i = 0; i < n; i++) {
      counts[readInt()]++;
    }
    long ans = 0;
    if (k == 0) {
      for (int i = 0; i < MAX; i++) {
        if (counts[i] > 1) {
          ans += (counts[i] * (counts[i] - 1)) / 2;
        }
      }
      out.println(ans);
      return;
    }
    for (int mask = 0; mask < maxMask; mask++) {
      if (Integer.bitCount(mask) == k) {
        for (int i = 0; i < MAX; i++) {
          if (counts[i] != 0 && (mask ^ i) >= 0 && (mask ^ i) < MAX) {
            ans += counts[i] * counts[mask ^ i];
          }
        }
      }
    }
    out.print(ans / 2);
 
  }
 
  // D
  // L
  // R
  // U
 
 
}