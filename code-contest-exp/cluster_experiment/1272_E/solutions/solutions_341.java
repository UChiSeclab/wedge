import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.*;

public class Main {
    ArrayList<Integer>[] graph;
    int[] dist;
    boolean[] used;

    void bfs(Queue<Integer> q) {
        while (!q.isEmpty()) {
            int to = q.poll();
            for (Integer c : graph[to]) {
                if (!used[c]) {
                    q.add(c);
                    used[c] = true;
                }
                dist[c] = min(dist[c], dist[to] + 1);
            }
        }
    }

    void run() throws IOException {
        int n = nextInt();
        int[] a = new int[n];
        dist = new int[n];
        graph = new ArrayList[n];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = nextInt();
        }
        Queue<Integer> chet = new ArrayDeque<>();
        Queue<Integer> nechet = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            if (i - a[i] > -1) graph[i - a[i]].add(i);
            if (i + a[i] < n) graph[i + a[i]].add(i);
            if (a[i] % 2 == 0) chet.add(i);
            else nechet.add(i);
        }
        Arrays.fill(dist, Integer.MAX_VALUE);
        used = new boolean[n];
        for (int i = 0; i < a.length; i++) {
            if (a[i] % 2 == 0) {
                dist[i] = 0;
                used[i] = true;
            }
        }
        bfs(chet);
        int[] ans = new int[n];
        for (int i = 0; i < dist.length; i++) {
            if (a[i] % 2 != 0) ans[i] = dist[i];
        }
        Arrays.fill(dist, Integer.MAX_VALUE);
        used = new boolean[n];
        for (int i = 0; i < a.length; i++) {
            if (a[i] % 2 == 1) {
                dist[i] = 0;
                used[i] = true;
            }
        }
        bfs(nechet);
        for (int i = 0; i < ans.length; i++) {
            if (a[i] % 2 != 1) ans[i] = dist[i];
        }
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == Integer.MAX_VALUE) pw.print(-1 + " ");
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