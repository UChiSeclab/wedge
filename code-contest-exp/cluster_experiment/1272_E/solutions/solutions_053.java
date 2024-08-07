import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Solver {
    static int a[], n;
    static long ans[];
    static boolean vis[];

    static void check(int x, int y) {
        if(y >= 0 && y < n) {
            if (a[x] % 2 != a[y] % 2)
                ans[x] = 1;
            else if (!vis[y])
                dfs(y);
            ans[x] = min(ans[x], ans[y] + 1);
        }
    }

    static void dfs(int v) {
        vis[v] = true;
        check(v, v - a[v]);
        check(v, v + a[v]);
    }

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        n = in.nextInt();
        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        ans = new long[n];
        fill(ans, (long) 1e6);
        for (int i = 0; i < 100; i++) {
            vis = new boolean[n];
            for (int j = 0; j < n; j++) {
                dfs(j);
            }
        }
        for (int i = 0; i < n; i++) {
            out.print((ans[i] >= (long) 1e6 ? -1 : ans[i]) + " ");
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