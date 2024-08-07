import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeSet;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author prem_cse
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastReader in = new FastReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        FMovingPoints solver = new FMovingPoints();
        solver.solve(1, in, out);
        out.close();
    }

    static class FMovingPoints {
        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            FMovingPoints.Pair[] p = new FMovingPoints.Pair[n];
            TreeSet<Integer> set = new TreeSet<>();
            for (int i = 0; i < n; i++) {
                p[i] = new FMovingPoints.Pair(a[i], sc.nextInt());
                set.add(p[i].s);
            }
            Arrays.sort(p, (x, y) -> x.v - y.v);
            set.add((int) -1e9);
            set.add((int) 1e9);
            TreeMap<Integer, Integer> map = new TreeMap<>();

            while (!set.isEmpty()) map.put(set.pollFirst(), map.size());
            FMovingPoints.FenwickTree count = new FMovingPoints.FenwickTree(map.size());
            FMovingPoints.FenwickTree sum = new FMovingPoints.FenwickTree(map.size());
            long ans = 0;
            for (int i = 0; i < n; i++) {
                int val = map.get(p[i].s);
                long c = count.query(val);
                ans += c * p[i].v - sum.query(val);
                count.update(val, 1);
                sum.update(val, p[i].v);
            }
            out.println(ans);
        }

        static class Pair {
            int v;
            int s;

            public Pair(int v, int s) {
                this.v = v;
                this.s = s;
            }

        }

        static class FenwickTree {
            int n;
            long[] ft;

            FenwickTree(int size) {
                n = size;
                ft = new long[n + 1];
            }

            long query(int b) {
                long sum = 0;
                while (b > 0) {
                    sum += ft[b];
                    b -= b & -b;
                }
                return sum;
            }

            void update(int k, int val) {
                while (k <= n) {
                    ft[k] += val;
                    k += k & -k;
                }
            }

        }

    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader(InputStream stream) {
            br = new BufferedReader(new InputStreamReader(stream), 32768);
            st = null;
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

