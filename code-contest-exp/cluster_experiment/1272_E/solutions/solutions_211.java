import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Solver {
    static int a[], n;
    static long ans[];
    static boolean vis[];

    static void dfs(int v) {
        vis[v] = true;
        if (v - a[v] >= 0) {
            if (((a[v - a[v]] ^ a[v]) & 1) == 1) ans[v] = 1;
            else if (!vis[v - a[v]]) dfs(v - a[v]);
            ans[v] = min(ans[v], ans[v - a[v]] + 1);
        }
        if (v + a[v] < n) {
            if (((a[v + a[v]] ^ a[v]) & 1) == 1) ans[v] = 1;
            else if (!vis[v + a[v]]) dfs(v + a[v]);
            ans[v] = min(ans[v], ans[v + a[v]] + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        n = in.nextInt();
        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        int log = 80;
        ans = new long[n];
        fill(ans, (long) 1e6);
        for (int i = 0; i < log; i++) {
            vis = new boolean[n];
            for (int j = 0; j < n; j++) {
                dfs(j);
            }
        }
        for(int i = 0; i < n; i++) {
            out.print((ans[i] >= (long)1e6 ? -1 : ans[i]) + " ");
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
