import java.util.*;
import java.io.*;

public class CFD {
  BufferedReader br;
  PrintWriter out;
  StringTokenizer st;
  boolean eof;
  private static final long MOD = 1000L * 1000L * 1000L + 7;
  private static final int[] dx = {0, -1, 0, 1};
  private static final int[] dy = {1, 0, -1, 0};
  private static final String yes = "Yes";
  private static final String no = "No";

  void solve() {
    int T = nextInt();
//    int T = 1;
    for (int i = 0; i < T; i++) {
      helper();
    }
  }

  int n;
  int[] arr;
  void helper() {
    n = nextInt();
    arr = nextIntArr(n);
    boolean[] res = new boolean[n];
    res[0] = isPerm(find(1));
    if (n == 1) {
      print(res);
      return;
    }
    res[1] = isPerm(find(2));
    if (res[1]) {
      Arrays.fill(res, true);
      print(res);
      return;
    }

    int left = 1;
    int right = n;
    while (left < right) {
      int mid = (left + right) / 2;
      List<Integer> tmp = find(mid + 1);
      boolean valid = isPerm(tmp);
      if (valid) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }

    for (int i = 1; i < left; i++) {
      res[i] = false;
    }
    for (int i = left; i < n; i++) {
      res[i] = true;
    }
    print(res);
  }

  void print(boolean[] res) {
    for (boolean b : res) {
      out(b ? 1 : 0);
    }
    outln("");
  }

  List<Integer> find(int k) {
    List<Integer> res = new ArrayList<>();
    TreeMap<Integer, Integer> tm = new TreeMap<>();
    for (int i = 0; i < k - 1; i++) {
      add(tm, arr[i]);
    }
    for (int i = k - 1; i < n; i++) {
      add(tm, arr[i]);
      // check
      res.add(tm.firstKey());
      remove(tm, arr[i - (k - 1)]);
    }
    return res;
  }

  void add(TreeMap<Integer, Integer> tm, int val) {
    tm.put(val, tm.getOrDefault(val, 0) + 1);
  }

  void remove(TreeMap<Integer, Integer> tm, int val) {
    if (!tm.containsKey(val)) {
      return;
    }
    tm.put(val, tm.getOrDefault(val, 0) - 1);
    if (tm.get(val) == 0) {
      tm.remove(val);
    }
  }


  boolean isPerm(List<Integer> ls) {
    int n = ls.size();
    int[] hist = new int[n];
    for (int v : ls) {
      if (v < 1 || v > n) {
        return false;
      }
      hist[v - 1]++;
    }
    for (int v : hist) {
      if (v != 1) {
        return false;
      }
    }
    return true;
  }

  void shuffle(int[] a) {
    int n = a.length;
    for(int i = 0; i < n; i++) {
      int r = i + (int) (Math.random() * (n - i));
      int tmp = a[i];
      a[i] = a[r];
      a[r] = tmp;
    }
  }
  long gcd(long a, long b) {
    while(a != 0 && b != 0) {
      long c = b;
      b = a % b;
      a = c;
    }
    return a + b;
  }
  private void outln(Object o) {
    out.println(o);
  }
  private void out(Object o) {
    out.print(o);
  }
  private void formatPrint(double val) {
    outln(String.format("%.9f%n", val));
  }
  public CFD() {
    br = new BufferedReader(new InputStreamReader(System.in));
    out = new PrintWriter(System.out);
    solve();
    out.close();
  }
  public static void main(String[] args) {
    new CFD();
  }

  public long[] nextLongArr(int n) {
    long[] res = new long[n];
    for(int i = 0; i < n; i++)
      res[i] = nextLong();
    return res;
  }
  public int[] nextIntArr(int n) {
    int[] res = new int[n];
    for(int i = 0; i < n; i++)
      res[i] = nextInt();
    return res;
  }
  public String nextToken() {
    while (st == null || !st.hasMoreTokens()) {
      try {
        st = new StringTokenizer(br.readLine());
      } catch (Exception e) {
        eof = true;
        return null;
      }
    }
    return st.nextToken();
  }
  public String nextString() {
    try {
      return br.readLine();
    } catch (IOException e) {
      eof = true;
      return null;
    }
  }
  public int nextInt() {
    return Integer.parseInt(nextToken());
  }
  public long nextLong() {
    return Long.parseLong(nextToken());
  }
  public double nextDouble() {
    return Double.parseDouble(nextToken());
  }
}
