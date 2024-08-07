import java.lang.*;
import java.util.*;
import java.io.*;

public class Main {
    static FastScanner in = new FastScanner();

    static int n;
    static ArrayList<Integer>[] g;
    static int[] a, ans;

    static void BFS(ArrayList<Integer> start, ArrayList<Integer> stop) {
        int[] d = new int[n];
        Arrays.fill(d, Integer.MAX_VALUE);
        Queue<Integer> q = new LinkedList<>();
        for (int el : start) {
            d[el] = 0;
            q.add(el);
        }

        while (!q.isEmpty()) {
            int node = q.remove();
            for (int vecin : g[node]) {
                if (d[vecin] == Integer.MAX_VALUE) {
                    d[vecin] = d[node] + 1;
                    q.add(vecin);
                }
            }
        }

        for (int el : stop) {
            if (d[el] != Integer.MAX_VALUE)
                ans[el] = d[el];
        }
    }

    static void solve() {
        n = in.nextInt();
        a = new int[n];
        g = new ArrayList[n];
        for (int i = 0; i < n; ++i)
            g[i] = new ArrayList<>();
        ArrayList<Integer> even = new ArrayList<>();
        ArrayList<Integer> odd = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            a[i] = in.nextInt();
            if ((a[i] & 1) != 0)
                odd.add(i);
            else
                even.add(i);
        }
        for (int i = 0; i < n; ++i) {
            int l = i - a[i], r = i + a[i];
            if (0 <= l) g[l].add(i);
            if (r < n) g[r].add(i);
        }

        ans = new int[n];
        Arrays.fill(ans, -1);
        BFS(odd, even);
        BFS(even, odd);

        for (int i = 0; i < n; ++i)
            System.out.print(ans[i] + " ");
    }

    public static void main(String[] args) {
        int T = 1;
        while (T-- > 0)
            solve();
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