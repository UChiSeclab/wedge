import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class Main implements Runnable {
  static Scanner in;
  // static FastReader in;
  static PrintWriter out;
  static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};
  static long INF = (long) 1e15 + 7;
  static int n, m, sum, max, res, k, p, s, count, T;
  static long min;
  static int[][] E;
  static List<int[]>[] adj;
  static int[] a, idx;
  static long[][] dp;
  static int[] dpp;
  static boolean[][] vis;
  static int rows, cols;
  static int[] curRes;
  static Map<String, Integer> map;
  static String str;
  static String[] w;

  public static void main(String[] args) throws Exception{

    in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    // in = new FastReader();
    out = new PrintWriter(System.out);

    Thread t = new Thread(null, new Main(), "Main", 1 << 28);
    t.start();
    t.join();
    out.flush();
    out.close();

    // int T = in.nextInt();
    // for (int t = 1; t <= T; t++) {
      




    
    // out.flush();
    // out.close();
  }

  static long fill(int idx, int p) {
    // System.out.println(idx + " " + p);
    if (vis[idx][p]) return INF;
    vis[idx][p] = true;
    // if (idx < 0 || idx >= n) return INF;
    if (dp[idx][p] != 0) {vis[idx][p] = false; return dp[idx][p];}
    if (idx + a[idx] < n && (a[idx + a[idx]] % 2 == p)) {vis[idx][p] = false; return 1l;}
    if (idx - a[idx] >= 0 && (a[idx - a[idx]] % 2 == p)) {vis[idx][p] = false;return 1l;}
    long left = INF, right = INF;
    if (idx + a[idx] < n && (a[idx + a[idx]] % 2 != p)) {right = 1 + fill(idx + a[idx], p);}
    if (idx - a[idx] >= 0 && (a[idx - a[idx]] % 2 != p)) {left = 1 + fill(idx - a[idx], p);}
    dp[idx][p] = Math.min(left, right);
    vis[idx][p] = false;

    return dp[idx][p];

  }  

  static void solve() {
      n = in.nextInt();
      a = new int[n];
      for (int i = 0; i < n; i++) {
        a[i] = in.nextInt();
      }

      dp = new long[n][2];
      // dp[i][0] min jump to an even
      // dp[i][1] min jump to an odd
      vis = new boolean[n][2];
      for (int i = 0; i < n; i++) {
        dp[i][0] = fill(i, 0);
        dp[i][1] = fill(i, 1);
      }

      // out.println(INF);
      long[] res = new long[n];
      for (int i = 0; i < n; i++) {
        // out.println(vis[i][1 - a[i] % 2] + " " + vis[i][1]);
        res[i] = dp[i][1 - a[i] % 2];
        if (res[i] >= INF) res[i] = -1;
      }
      
      for (long i : res) {out.print(i + " ");}
      out.println();
    // }
  }

  @Override
  public void run() {
    // out.println("h");
    solve();
    // out.flush();
    // out.close();
  }

  

 

  static class FastReader {

    public BufferedReader br; 
    StringTokenizer st; 

    public FastReader() {br = new BufferedReader(new InputStreamReader(System.in));} 

    String next() { 
      while (st == null || !st.hasMoreElements()) { 
          try {st = new StringTokenizer(br.readLine());} 
          catch (IOException e) {e.printStackTrace();} 
      } 
      return st.nextToken(); 
    } 

    int nextInt() {return Integer.parseInt(next());} 

    long nextLong() {return Long.parseLong(next());} 

    double nextDouble() {return Double.parseDouble(next());} 

    String nextLine() {
      String str = ""; 
        try {str = br.readLine();} 
        catch (IOException e) {e.printStackTrace();} 
        return str; 
    }
  }

} 

  



  
  


  












