import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.*;

public class Main {
    int[] a;
    long[] ans;
    boolean[] used;
    ArrayList<Integer> graph[];

    void dfs(int v, int chet) {
        used[v] = true;
        for (Integer c : graph[v]) {
            if (a[c] % 2 == chet) ans[v] = 1;
            else if (!used[c]) dfs(c, chet);
            ans[v] = min(ans[v], ans[c] + 1);
        }
    }

    void run() throws IOException {
        int n = nextInt();
        a = new int[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = nextInt();
        }
        ans = new long[n];
        graph = new ArrayList[n];
        used = new boolean[n];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        Arrays.fill(ans, Integer.MAX_VALUE);
        for (int i = 0; i < a.length; i++) {
            if (a[i] + i < n) graph[i].add(a[i] + i);
            if (i - a[i] >= 0) graph[i].add(i - a[i]);
        }
        for (int i = 0; i < ans.length; i++) {
           dfs(i, (a[i] % 2) ^ 1);
        }
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] >= Integer.MAX_VALUE) pw.print(-1 + " ");
            else pw.print(ans[i] + " ");
        }
        pw.close();
    }

    class point implements Comparable<point> {
        int x, y;

        public point(int a, int b) {
            x = a;
            y = b;
        }

        @Override
        public int compareTo(point o) {
            if (o.y == this.y) return Integer.compare(o.x, this.x);
            return -Integer.compare(o.y, this.y);
        }

    }

    long mod = (long) 1e9 + 7;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //BufferedReader br = new BufferedReader(new FileReader("qual.in"));

    StringTokenizer st = new StringTokenizer("");
    PrintWriter pw = new PrintWriter(System.out);
    //PrintWriter pw = new PrintWriter("qual.out");

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    String next() throws IOException {
        if (!st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}