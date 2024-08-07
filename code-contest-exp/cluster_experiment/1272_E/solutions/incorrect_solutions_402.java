import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter out = new PrintWriter(System.out);
    static Scanner sc = new Scanner(System.in);
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    long mod = 998244353;
    long[] debt;
    int[] a;
    int n;
    long[][] dp;
    boolean[][] vis;
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.solve();
//        out.println(main.solve());
        out.flush();
    }
    void solve() throws IOException {
        n = sc.nextInt();
        a = new int[n];
        for(int i=0; i<n; i++) a[i] = sc.nextInt();
        dp = new long[n][2];
        for(int i=0; i<n; i++) Arrays.fill(dp[i],-2);
        vis = new boolean[n][2];
        for(int i=0; i<n; i++){
            int t = 1-a[i]%2;
            long cur = dfs(i, t);
            out.print(cur); out.print(' ');
        }
    }
    long dfs(int idx, int parity){
        if(vis[idx][parity]) return dp[idx][parity];
        vis[idx][parity] = true;
        if(a[idx]%2==parity) return dp[idx][parity] = 0;
        long ans = -1;
        if(idx>=a[idx]){
            int t = idx-a[idx];
            if(vis[t][parity]&&dp[t][parity]==-2) return -1; // cycle
            long temp = dfs(t, parity);
            if(temp>=0) ans = temp+1;
        }
        if(idx+a[idx]<n){
            int t = idx+a[idx];
            if(vis[t][parity]&&dp[t][parity]==-2) return -1; // cycle
            long temp = dfs(t, parity);
            if(temp>=0){
                if(ans<0) ans = temp+1;
                else ans = Math.min(ans, temp+1);
            }
        }
        return dp[idx][parity] = ans;
    }
}