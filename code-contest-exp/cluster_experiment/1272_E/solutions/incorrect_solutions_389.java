import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Solver {
    static int a[];
    static int n;
    static int ans[];
    static boolean rt[];
    static boolean same(int a, int b){
        return ((a ^ b) & 1) == 1;
    }
    static int clc(int id, int c) {
        if (same(a[id],c)) return 0;
        if(rt[id])return ans[id];
        rt[id] = true;
        if (id + a[id] < n){
            if(!rt[id + a[id]])ans[id] = min(ans[id], clc(id + a[id], c) + 1);
            else {
                if(same(c,a[id + a[id]]))ans[id] = 1;
                else ans[id] = min(ans[id],ans[id + a[id]] + 1);
            }
        }
        if (id - a[id] >= 0) {
            if(!rt[id - a[id]])ans[id] = min(ans[id], clc(id - a[id], c) + 1);
            else{
                if(same(c,a[id - a[id]]))ans[id] = 1;
                else ans[id] = min(ans[id],ans[id - a[id]] + 1);
            }
        }

        return ans[id];
    }

    public static void main(String[] args) throws IOException {
        FastScanner in = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        n = in.nextInt();
        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        ans = new int[n];
        fill(ans, (int) 1e6);
        rt = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (ans[i] >= (int) 1e6) clc(i, a[i]);
        }
        for (int i = 0; i < n; i++) {
            if(ans[i] >= (int)1e6)ans[i] = -1;
            out.print(ans[i] + " ");
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