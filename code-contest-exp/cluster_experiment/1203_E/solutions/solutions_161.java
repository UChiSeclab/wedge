import java.util.*;
import java.io.*;
import java.io.FileWriter; 

// Solution

public class Main 
{    
  public static void main (String[] argv)
  {
	  new Main();
  } 
     
    
  
  boolean test = false;  
  
  final int MOD = 998244353;  //1000000007;
  
 
  
  public Main()  {
	FastReader in = new FastReader(new BufferedReader(new InputStreamReader(System.in)));
	
     
    //FastReader in = new FastReader(new BufferedReader(new FileReader("Main.in")));
    
    int n = in.nextInt();
        
    Integer[] a = new Integer[n];
    for (int i = 0; i < n; i++) a[i] = in.nextInt();
    
    Arrays.sort(a);
    int N = 150010;
    boolean[] visited = new boolean[N];
    int cnt = 0;
    for (int v : a)
    {
        if (v - 1 > 0 && !visited[v-1]) {
            cnt++;
            visited[v-1] = true;
        }else if (!visited[v]) {
            cnt++;
            visited[v] = true;
        }else if (!visited[v+1]) {
            cnt++;
            visited[v+1] = true;
        }
        
        
    }
    System.out.println(cnt);
  }
  
  

  
 
  private int mod_add(int a, int b) {
      int v = a + b;
      if (v >= MOD) v -= MOD;
      return v;
  }
  
  
  private long overlap(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
      if (x1 > x3) return overlap(x3, y3, x4, y4, x1, y1, x2, y2);
      
      if (x3 > x2 || y4 < y1 || y3 > y2) return 0L;
      
      //(x3, ?, x2, ?)
      int yL = Math.max(y1, y3);
      int yH = Math.min(y2, y4); 
      int xH = Math.min(x2, x4);
      
      return f(x3, yL, xH, yH);
  }
  //return #black cells in rectangle
  private long f(int x1, int y1, int x2, int y2) {
      long dx = 1L + x2 - x1;
      long dy = 1L + y2 - y1;
      if (dx % 2 == 0 || dy % 2 == 0 || (x1 + y1) % 2 == 0) 
          return 1L * dx * dy / 2;
      return 1L * dx * dy / 2 + 1;
  }
  
  private int dist(int x, int y, int xx, int yy) {
      return abs(x - xx) + abs(y - yy);
  }
  
  private boolean less(int x, int y, int xx, int yy) {
      return x < xx || y > yy;
  }
  
  private int mul(int x, int y) {
      return (int)(1L * x * y % MOD);
  }
  
  private int add(int x, int y) {
      return (x + y) % MOD;
  }
  
 
  
  private int nBit1(int v) {
      int v0 = v;
      int c = 0;
      while (v != 0) {
          ++c;
          v = v & (v - 1);
      }
      return c;
  }
  
  private long abs(long v) {
      return v > 0 ? v : -v;
  }
  
  private int abs(int v) {
      return v > 0 ? v : -v;
  }
  
  private int common(int v) {
      int c = 0;
      while (v != 1) {
          v = (v >>> 1);
          ++c;
      }
      
      return c;
  }
  
  private void reverse(char[] a, int i, int j) {
      while (i < j) {
          swap(a, i++, j--);
      }
  }
  
  private void swap(char[] a, int i, int j) {
      char t = a[i];
      a[i] = a[j];
      a[j] = t;
  }
  
  private int gcd(int x, int y) {
      if (y == 0) return x;
      return gcd(y, x % y);
  }
  private long gcd(long x, long y) {
      if (y == 0) return x;
      return gcd(y, x % y);
  }
  private int max(int a, int b) {
      return a >  b ? a : b;
  }
  
  private long max(long a, long b) {
      return a >  b ? a : b;
  }
  
  private int min(int a, int b) {
      return a >  b ? b : a;
  }
  
  private long min(long a, long b) {
      return a >  b ? b : a;
  }
  
  static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;
 
        public FastReader(BufferedReader in)
        {            
            br = in;
        }
 
        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    String line = br.readLine();
                    if (line == null || line.length() == 0) return "";
                    st = new StringTokenizer(line);
                }
                catch (IOException  e)
                {
                    return "";
                    //e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt()
        {
            return Integer.parseInt(next());
        }
 
        long nextLong()
        {
            return Long.parseLong(next());
        }
 
        double nextDouble()
        {
            return Double.parseDouble(next());
        }
 
        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                return null;
                //e.printStackTrace();
            }
            return str;
        }
    }
}