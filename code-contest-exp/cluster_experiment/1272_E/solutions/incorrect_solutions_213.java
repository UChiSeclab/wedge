import java.util.*;
import java.io.*;
import java.math.*;
 
public class Main {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        //Scanner sc = new Scanner();
        Reader in = new Reader();
        Main solver = new Main();
        solver.solve(out, in);
        out.flush();
        out.close();
 
    }
 
        
    static int INF = (int)1e9;
    static int maxn = (int)1e5+5;
    static int mod  = 998244353;
    static int n,m,k,t,q,d,p;
    
    static boolean[][] vis;
    static int[] arr;
    static int[][] dp;
    
    void solve(PrintWriter out, Reader in) throws IOException{
        n = in.nextInt();
        
        arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = in.nextInt();
        
        vis = new boolean[n][2];
        dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            dp[i][0] = INF; dp[i][1] = INF;
        }
        
        for (int i = 0; i < n; i++) {
            if (!vis[i][0]) DFS(i, 0);
            if (!vis[i][1]) DFS(i, 1);
        }
        for (int i = 0; i < n; i++) {
            if (dp[i][0] == INF) dp[i][0] = -1;
            if (dp[i][1] == INF) dp[i][1] = -1;
        }
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 0) out.print(dp[i][1]+" ");
            else                 out.print(dp[i][0]+" ");
        }
        out.println();
        
    }
    
    static int DFS(int s, int tp) {
        if (arr[s]%2 == tp) {
            dp[s][tp] = 0;
            vis[s][tp] = true;
            return 0;
        } else if (vis[s][tp]) return dp[s][tp];
        vis[s][tp] = true;
        
        int left = INF, right = INF;
        if (s+arr[s] < n) left = 1 + DFS(s+arr[s], tp);
        if (s-arr[s] >= 0) right = 1 + DFS(s-arr[s], tp);
        return dp[s][tp] = Math.min(dp[s][tp], Math.min(left, right));
    }
    
    static class Reader {
 
    private InputStream mIs;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
 
    public Reader() {
        this(System.in);
    }
 
    public Reader(InputStream is) {
        mIs = is;
    }
 
    public int read() {
        if (numChars == -1) {
            throw new InputMismatchException();
 
    }
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = mIs.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0) {
                return -1;
            }
        }
        return buf[curChar++];
    }
 
    public String nextLine() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isEndOfLine(c));
        return res.toString();
    }
 
    public String next() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }
 
    double nextDouble()
    {
        return Double.parseDouble(next());
    }
 
    public long nextLong() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        long res = 0;
        do {
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
 
    public int nextInt() {
        int c = read();
        while (isSpaceChar(c)) {
            c = read();
        }
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9') {
                throw new InputMismatchException();
            }
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
 
    public boolean isSpaceChar(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }
 
    public boolean isEndOfLine(int c) {
        return c == '\n' || c == '\r' || c == -1;
    }
 
    }
}