import java.io.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;
import java.lang.management.*;
import static java.lang.Math.*;

@SuppressWarnings("unchecked")
public class P1203E {

  public void run() throws Exception {
    int n = nextInt(), k = 0, a [] = sort(readInt(n));
    boolean [] au = new boolean [150_002];

    for (int i = 0; i < n; i++) {
      for (int d = (a[i] > 1) ? -1 : 0; d <= 1; d++) {
        if (!au[a[i] + d]) {
          k++;
          au[a[i] + d] = true;
          break;
        }
      }
    }

    println(k);
  }

  public static void main(String... args) throws Exception {
    br = new BufferedReader(new InputStreamReader(System.in));
    pw = new PrintWriter(new BufferedOutputStream(System.out));
    new P1203E().run();
    br.close();
    pw.close();
    System.err.println("\n[Time : " + (System.currentTimeMillis() - startTime) + " ms]");
    long gct = 0, gcc = 0;
    for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
      gct += garbageCollectorMXBean.getCollectionTime();
      gcc += garbageCollectorMXBean.getCollectionCount();
    }
    System.err.println("[GC time : " + gct + " ms, count = " + gcc + "]");
  }

  static long startTime = System.currentTimeMillis();
  static BufferedReader br;
  static PrintWriter pw;
  StringTokenizer stok;

  String nextToken() throws IOException {
    while (stok == null || !stok.hasMoreTokens()) {
      String s = br.readLine();
      if (s == null) { return null; }
      stok = new StringTokenizer(s);
    }
    return stok.nextToken();
  }

  void print(byte b) { print("" + b); }
  void print(int i) { print("" + i); }
  void print(long l) { print("" + l); }
  void print(double d) { print("" + d); }
  void print(char c) { print("" + c); }
  void print(Object o) {
    if (o instanceof int[]) { print(Arrays.toString((int [])o));
    } else if (o instanceof long[]) { print(Arrays.toString((long [])o));
    } else if (o instanceof char[]) { print(Arrays.toString((char [])o));
    } else if (o instanceof byte[]) { print(Arrays.toString((byte [])o));
    } else if (o instanceof short[]) { print(Arrays.toString((short [])o));
    } else if (o instanceof boolean[]) { print(Arrays.toString((boolean [])o));
    } else if (o instanceof float[]) { print(Arrays.toString((float [])o));
    } else if (o instanceof double[]) { print(Arrays.toString((double [])o));
    } else if (o instanceof Object[]) { print(Arrays.toString((Object [])o));
    } else { print("" + o); }
  }
  void printsp(int [] a) { for (int i = 0, n = a.length; i < n; print(a[i] + " "), i++); }
  void print(String s) { pw.print(s); }
  void println() { println(""); }
  void println(byte b) { println("" + b); }
  void println(int i) { println("" + i); }
  void println(long l) { println("" + l); }
  void println(double d) { println("" + d); }
  void println(char c) { println("" + c); }
  void println(Object o) { print(o); println(); }
  void println(String s) { pw.println(s); }
  int nextInt() throws IOException { return Integer.parseInt(nextToken()); }
  long nextLong() throws IOException { return Long.parseLong(nextToken()); }
  double nextDouble() throws IOException { return Double.parseDouble(nextToken()); }
  char nextChar() throws IOException { return (char) (br.read()); }
  String next() throws IOException { return nextToken(); }
  String nextLine() throws IOException { return br.readLine(); }
  int [] readInt(int size) throws IOException {
    int [] array = new int [size];
    for (int i = 0; i < size; i++) { array[i] = nextInt(); }
    return array;
  }
  long [] readLong(int size) throws IOException {
    long [] array = new long [size];
    for (int i = 0; i < size; i++) { array[i] = nextLong(); }
    return array;
  }
  double [] readDouble(int size) throws IOException {
    double [] array = new double [size];
    for (int i = 0; i < size; i++) { array[i] = nextDouble(); }
    return array;
  }
  String [] readLines(int size) throws IOException {
    String [] array = new String [size];
    for (int i = 0; i < size; i++) { array[i] = nextLine(); }
    return array;
  }

  int gcd(int a, int b) {
    if (a == 0) return Math.abs(b); if (b == 0) return Math.abs(a);
    a = Math.abs(a); b = Math.abs(b);
    int az = Integer.numberOfTrailingZeros(a), bz = Integer.numberOfTrailingZeros(b);
    a >>>= az; b >>>= bz;
    while (a != b) {
      if (a > b) { a -= b; a >>>= Integer.numberOfTrailingZeros(a); }
            else { b -= a; b >>>= Integer.numberOfTrailingZeros(b); }
    }
    return (a << Math.min(az, bz));
  }

  long gcd(long a, long b) {
    if (a == 0) return Math.abs(b); if (b == 0) return Math.abs(a);
    a = Math.abs(a); b = Math.abs(b);
    int az = Long.numberOfTrailingZeros(a), bz = Long.numberOfTrailingZeros(b);
    a >>>= az; b >>>= bz;
    while (a != b) {
      if (a > b) { a -= b; a >>>= Long.numberOfTrailingZeros(a); }
            else { b -= a; b >>>= Long.numberOfTrailingZeros(b); }
    }
    return (a << Math.min(az, bz));
  }

  void shuffle(int [] a) { // RANDOM shuffle
    Random r = new Random();
    for (int i = a.length - 1, j, t; i >= 0; j = r.nextInt(a.length), t = a[i], a[i] = a[j], a[j] = t, i--);
  }

  void shuffle(int [] a, int m) { // QUICK shuffle
    for (int i = 0, n = a.length, j = m % n, t; i < n; t = a[i], a[i] = a[j], a[j] = t, i++, j = (i * m) % n);
  }

  void shuffle(long [] a) {
    Random r = new Random();
    for (int i = a.length - 1; i >= 0; i--) {
      int j = r.nextInt(a.length);
      long t = a[i]; a[i] = a[j]; a[j] = t;
    }
  }

  void shuffle(Object [] a) {
    Random r = new Random();
    for (int i = a.length - 1; i >= 0; i--) {
      int j = r.nextInt(a.length);
      Object t = a[i]; a[i] = a[j]; a[j] = t;
    }
  }

  int [] sort(int [] a) {
    final int SHIFT = 16, MASK = (1 << SHIFT) - 1, SIZE = (1 << SHIFT) + 1;
    int n = a.length, ta [] = new int [n], ai [] = new int [SIZE];
    for (int i = 0; i < n; ai[(a[i] & MASK) + 1]++, i++);
    for (int i = 1; i < SIZE; ai[i] += ai[i - 1], i++);
    for (int i = 0; i < n; ta[ai[a[i] & MASK]++] = a[i], i++);
    int [] t = a; a = ta; ta = t;
    ai = new int [SIZE];
    for (int i = 0; i < n; ai[(a[i] >> SHIFT) + 1]++, i++);
    for (int i = 1; i < SIZE; ai[i] += ai[i - 1], i++);
    for (int i = 0; i < n; ta[ai[a[i] >> SHIFT]++] = a[i], i++);
    return ta;
  }

  void flush() {
    pw.flush();
  }

  void pause() {
    flush(); System.console().readLine();
  }
}