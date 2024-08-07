import java.lang.*;
import java.util.*;
import java.io.*;

public class Main {
    static FastScanner in = new FastScanner();

    static int n;
    static ArrayList<Integer>[] g;
    static int[] dist, a;
    static boolean[] vis;

    static int dijkstra(int start) {
        vis = new boolean[n + 1];
        dist = new int[n + 1];
        for (int i = 1; i <= n; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[start] = 0;

        PriorityQueue<PII> pq =
                new PriorityQueue<>(1, new PairComparator());
        pq.add(new PII(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Pair<Integer, Integer> curr = pq.remove();
            int node = curr.x, cost = curr.y;
            vis[node] = true;

            if ((a[node] & 1) != (a[start] & 1))
                return dist[node];

            for (int vecin : g[node]) {
                if (!vis[vecin]) {
                    int newDist = cost + 1;
                    if (newDist < dist[vecin])
                        dist[vecin] = newDist;
                    pq.add(new PII(vecin, dist[vecin]));
                }
            }
        }

        return -1;
    }

    static void solve() {
        n = in.nextInt();
        g = new ArrayList[n + 1];
        for (int i = 1; i <= n; ++i)
            g[i] = new ArrayList<>();
        a = new int[n + 1];

        for (int i = 1; i <= n; ++i) {
            a[i] = in.nextInt();
            if (i - a[i] >= 1)
                g[i].add(i - a[i]);
            if (i + a[i] <= n)
                g[i].add(i + a[i]);
        }

        for (int i = 1; i <= n; ++i)
            System.out.print(dijkstra(i) + " ");
    }

    public static void main(String[] args) {
        int T = 1;
        while (T-- > 0)
            solve();
    }

    static class PairComparator implements Comparator<PII> {
        public int compare(PII a, PII b) {
            return b.y - a.y;
        }
    }

    static class PII extends Pair {
        int x, y;

        public PII(int x, int y) {
            super(x, y);
        }
    }

    static class Pair<X, Y> {
        X x;
        Y y;

        public Pair(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
}

class FastScanner {
    BufferedReader br;
    StringTokenizer st;

    public FastScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}