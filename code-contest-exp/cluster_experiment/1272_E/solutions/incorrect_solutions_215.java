import java.io.*;
import java.security.cert.Certificate;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Solver {
    static int a[], n;
    static long ans[];
    static boolean vis[];

    static boolean check(int x, int y) {
        if (y >= 0 && y < n) {
            if (a[x] % 2 != a[y] % 2)
                ans[x] = 1;
            else if (!vis[y])
                dfs(y);
            if (ans[x] > ans[y] + 1) {
                ans[x] = ans[y] + 1;
                return true;
            }
        }
        return false;
    }

    static boolean dfs(int v) {
        vis[v] = true;
        return check(v, v - a[v]) | check(v, v + a[v]);
    }

    static boolean better() {
        fill(vis, false);
        boolean good = false;
        for (int i = 0; i < n; ++i) {
            if (!vis[i])
                good |= dfs(i);
        }
        return good;
    }

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        n = in.nextInt();
        a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = in.nextInt();
        ans = new long[n];
        fill(ans, n + 1);
        vis = new boolean[n];
        while (better()) ;
        for (int i = 0; i < n; i++) {
            out.print((ans[i] > n ? -1 : ans[i]) + " ");
        }
        out.close();
    }
}

class FastScanner {
    BufferedReader br;
    StringTokenizer st;

    FastScanner(File f) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(f));
    }

    FastScanner(InputStream is) {
        br = new BufferedReader(new InputStreamReader(is));
    }

    String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}