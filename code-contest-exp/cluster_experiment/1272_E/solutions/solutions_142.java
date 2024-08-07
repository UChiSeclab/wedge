import java.io.*;
import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Main {
    static int distOdd[], distEven[];
    static int n;
    static int ans[], a[];
    static ArrayList<Integer> g[];

    static void dijkstraEven() {
        TreeSet<Integer> dj = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                if (distEven[integer] != distEven[t1]) return Integer.compare(distEven[integer], distEven[t1]);
                return Integer.compare(integer, t1);
            }
        });
        for (int i = 0; i < n; i++) {
            if ((a[i] & 1) == 0) {
                dj.add(i);
                distEven[i] = 0;
            }
        }
        while (!dj.isEmpty()) {
            int v = dj.pollFirst();
            for (int to : g[v]) {
                if (distEven[to] > distEven[v] + 1) {
                    dj.remove(to);
                    distEven[to] = distEven[v] + 1;
                    dj.add(to);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if ((a[i] & 1) == 1) ans[i] = distEven[i];
        }
    }

    static void dijkstraOdd() {
        TreeSet<Integer> dj = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                if (distOdd[integer] != distOdd[t1]) return Integer.compare(distOdd[integer], distOdd[t1]);
                return Integer.compare(integer, t1);
            }
        });
        for (int i = 0; i < n; i++) {
            if ((a[i] & 1) != 0) {
                dj.add(i);
                distOdd[i] = 0;
            }
        }
        while (!dj.isEmpty()) {
            int v = dj.pollFirst();
            for (int to : g[v]) {
                if (distOdd[to] > distOdd[v] + 1) {
                    dj.remove(to);
                    distOdd[to] = distOdd[v] + 1;
                    dj.add(to);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if ((a[i] & 1) == 0) ans[i] = distOdd[i];
        }
    }

    static void buildGraph() {
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            if (i - a[i] >= 0) g[i - a[i]].add(i);
            if (i + a[i] < n) g[i + a[i]].add(i);
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
        distOdd = new int[n];
        distEven = new int[n];
        ans = new int[n];
        fill(distEven, n);
        fill(distOdd, n);
        fill(ans, n);
        buildGraph();
        dijkstraEven();
        dijkstraOdd();
        for (int i = 0; i < n; i++) {
            if (ans[i] == n) ans[i] = -1;
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