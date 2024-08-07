import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;
import static java.util.Arrays.sort;
import static java.util.Collections.sort;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;

public class Main {
    FastScanner in;
    PrintWriter out;
    ArrayList<Integer>[] graph;
    ArrayList<GraphPair>[] weightedGraph;
    long mod = 998_244_353L; // (long) 1e9 + 7 || (long) 1e9 + 9
    boolean multitests = false;

    void shuffleInt2(int[] a, int n) {
        Random random = new Random();
        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int swap = a[j];
            a[j] = a[i];
            a[i] = swap;
        }
    }

    private void solve() throws IOException {
        int n = in.nextInt(), h = in.nextInt(), m = in.nextInt() / 2, k = in.nextInt() - 1;
        int[] t = new int[n];
        for (int i = 0; i < n; i++) {
            in.next();
            t[i] = in.nextInt() % m;
        }

        int[] sortedT = new int[n * 2];
        System.arraycopy(t, 0, sortedT, 0, n);
        shuffleInt2(sortedT, n);
        sort(sortedT, 0, n);
        for (int i = 0; i < n; i++)
            sortedT[n + i] = sortedT[i] + m;

        int ma = n, ta = 0;
        for (int tk = 0, l = 0, r = 0; tk < m; tk++) {
            while (r < n * 2 && sortedT[r] < tk + k)
                r++;
            while (l < r && sortedT[l] < tk)
                l++;

            if (ma > r - l) {
                ta = tk;
                ma = r - l;
            }
        }

        out.println(ma + " " + (ta + k) % m);
        for (int i = 0; i < n; i++)
            if ((t[i] >= ta && t[i] < ta + k) || (t[i] + m >= ta && t[i] + m < ta + k))
                out.print(i + 1 + " ");
        out.println();
    }


    int binSearch(int[] a, int x) {
        int l = -1, mid, r = a.length;
        while (l + 1 < r) {
            mid = (l + r) / 2;
            if (a[mid] >= x)
                r = mid;
            else
                l = mid;
        }
        return r;
    }

    void shuffleInt(int[] a) {
        Random random = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int swap = a[j];
            a[j] = a[i];
            a[i] = swap;
        }
    }

    void shuffleLong(long[] a) {
        Random random = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            long swap = a[j];
            a[j] = a[i];
            a[i] = swap;
        }
    }

    void reverseInt(int[] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int swap = a[i];
            a[i] = a[j];
            a[j] = swap;
        }
    }

    void reverseLong(long[] a) {
        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            long swap = a[i];
            a[i] = a[j];
            a[j] = swap;
        }
    }

    int maxInt(int[] a) {
        int max = a[0];
        for (int i = 1; i < a.length; i++)
            if (max < a[i])
                max = a[i];
        return max;
    }

    long maxLong(long[] a) {
        long max = a[0];
        for (int i = 1; i < a.length; i++)
            if (max < a[i])
                max = a[i];
        return max;
    }

    int minInt(int[] a) {
        int min = a[0];
        for (int i = 1; i < a.length; i++)
            if (min > a[i])
                min = a[i];
        return min;
    }

    long minLong(long[] a) {
        long min = a[0];
        for (int i = 1; i < a.length; i++)
            if (min > a[i])
                min = a[i];
        return min;
    }

    long sumInt(int[] a) {
        long s = 0;
        for (int i = 0; i < a.length; i++)
            s += a[i];
        return s;
    }

    long sumLong(long[] a) {
        long s = 0;
        for (int i = 0; i < a.length; i++)
            s += a[i];
        return s;
    }

    long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    long binpow(long a, long n) {
        long res = 1;
        while (n > 0) {
            if (n % 2 == 1)
                res = res * a;
            a *= a;
            n /= 2;
        }
        return res;
    }

    long binpowmod(long a, long n) {
        long res = 1;
        a %= mod;
        n %= mod - 1;
        while (n > 0) {
            if (n % 2 == 1)
                res = (res * a) % mod;
            a = (a * a) % mod;
            n /= 2;
        }
        return res;
    }

    class GraphPair implements Comparable<GraphPair> {
        int v;
        long w;

        GraphPair(int v, long w) {
            this.v = v;
            this.w = w;
        }

        public int compareTo(GraphPair o) {
            return w != o.w ? Long.compare(w, o.w) : Integer.compare(v, o.v);
        }
    }

    ArrayList<Integer>[] createGraph(int n) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        return graph;
    }

    ArrayList<GraphPair>[] createWeightedGraph(int n) {
        ArrayList<GraphPair>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        return graph;
    }

    class FastScanner {
        StringTokenizer st;
        BufferedReader br;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s), 32768);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        boolean hasNext() throws IOException {
            return br.ready() || (st != null && st.hasMoreTokens());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = in.nextInt();
            return a;
        }

        int[][] nextIntTable(int n, int m) throws IOException {
            int[][] a = new int[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    a[i][j] = in.nextInt();
            return a;
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        long[] nextLongArray(int n) throws IOException {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = in.nextLong();
            return a;
        }

        long[][] nextLongTable(int n, int m) throws IOException {
            long[][] a = new long[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    a[i][j] = in.nextLong();
            return a;
        }

        ArrayList<Integer>[] nextGraph(int n, int m, boolean directed) throws IOException {
            ArrayList<Integer>[] graph = createGraph(n);
            for (int i = 0; i < m; i++) {
                int v = in.nextInt() - 1, u = in.nextInt() - 1;
                graph[v].add(u);
                if (!directed)
                    graph[u].add(v);
            }
            return graph;
        }

        ArrayList<GraphPair>[] nextWeightedGraph(int n, int m, boolean directed) throws IOException {
            ArrayList<GraphPair>[] graph = createWeightedGraph(n);
            for (int i = 0; i < m; i++) {
                int v = in.nextInt() - 1, u = in.nextInt() - 1;
                long w = in.nextLong();
                graph[v].add(new GraphPair(u, w));
                if (!directed)
                    graph[u].add(new GraphPair(v, w));
            }
            return graph;
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        boolean hasNextLine() throws IOException {
            return br.ready();
        }
    }

    private void run() throws IOException {
        in = new FastScanner(System.in); // new FastScanner(new FileInputStream(".in"));
        out = new PrintWriter(System.out); // new PrintWriter(new FileOutputStream(".out"));

        for (int t = multitests ? in.nextInt() : 1; t-- > 0; )
            solve();

        out.flush();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}