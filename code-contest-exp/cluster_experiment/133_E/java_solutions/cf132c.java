import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;


public class cf132c {
  static int n,m,oo=987654321;;
  static String v;
  static Integer[][][] memo;
  public static void main(String[] args) {
    FastIO in = new FastIO(), out = in;
    v = in.next().trim();
    n = in.nextInt();
    m = v.length();
    memo = new Integer[2][m][n+1];
    out.println(Math.max(go(0,n,0),go(0,n,1)));
    out.close();
  }
  static int go(int pos, int left, int dir) {
    if(left < 0) return -oo;
    if(pos == m) {
      return left%2==0?0:-oo;
    }
    if(memo[dir][pos][left] != null)
      return memo[dir][pos][left];
    int ans = -oo;
    char comm = v.charAt(pos);
    ans = Math.max(ans, go(pos+1,left-(comm=='T'?0:1),1-dir));
    ans = Math.max(ans, 2*dir-1 + go(pos+1,left-(comm=='F'?0:1),dir));
    return memo[dir][pos][left] = ans;
  }
  static class FastIO extends PrintWriter {
    BufferedReader br;
    StringTokenizer st;
    
    public FastIO() {
      this(System.in,System.out);
    }
    public FastIO(InputStream in, OutputStream out) {
      super(new BufferedWriter(new OutputStreamWriter(out)));
      br = new BufferedReader(new InputStreamReader(in));
      scanLine();
    }
    public void scanLine() {
      try {
        st = new StringTokenizer(br.readLine().trim());
      } catch(Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
    public int numTokens() {
      if(!st.hasMoreTokens()) {
        scanLine();
        return numTokens();
      }
      return st.countTokens();
    }
    public String next() {
      if(!st.hasMoreTokens()) {
        scanLine();
        return next();
      }
      return st.nextToken();
    }
    public double nextDouble() {
      return Double.parseDouble(next());
    }
    public long nextLong() {
      return Long.parseLong(next());
    }
    public int nextInt() {
      return Integer.parseInt(next());
    }
  }
}
