import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Comparator;
import java.io.InputStreamReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author xwchen
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskF solver = new TaskF();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskF {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] x = in.nextIntArray(n);
            int[] v = in.nextIntArray(n);
            v = ArrayUtils.shrink(v);
            Point[] points = new Point[n];
            for (int i = 0; i < n; ++i) {
                points[i] = new Point(x[i], v[i]);
            }
            Arrays.sort(points, Comparator.comparingInt(p -> p.x));
            BinaryIndexedTree Vcnt = new BinaryIndexedTree(n);
            BinaryIndexedTree VXsum = new BinaryIndexedTree(n);
            long res = 0;
            for (int i = n - 1; i >= 0; --i) {
                int X = points[i].x;
                int V = points[i].v;
                long cnt = Vcnt.queryRange(V, n);
                long sum = VXsum.queryRange(V, n);
                res += sum - X * cnt;
                Vcnt.update(V, 1);
                VXsum.update(V, X);
            }
            out.println(res);
        }

        class Point {
            int x;
            int v;

            public Point(int x, int v) {
                this.x = x;
                this.v = v;
            }

        }

    }

    static class BinaryIndexedTree {
        int n;
        long[] bit;

        public BinaryIndexedTree(int n) {
            this.n = n;
            bit = new long[n + 1];
        }

        public void update(int i, int add) {
            while (i > 0 && i <= n) {
                bit[i] += add;
                i = i + (i & (-i));
            }
        }

        public long sum(int i) {
            long ans = 0;
            while (i > 0) {
                ans += bit[i];
                i = i - (i & (-i));
            }
            return ans;
        }

        public long queryRange(int i, int j) {
            return sum(j) - sum(i - 1);
        }

    }

    static class ArrayUtils {
        public static int[] shrink(int[] a) {
            int n = a.length;
            int[] ret = new int[n];
            Integer[] b = new Integer[n];
            for (int i = 0; i < n; ++i) {
                b[i] = i;
            }
            Arrays.sort(b, Comparator.comparingInt(x -> a[x]));
            int p = 0;
            for (int i = 0; i < n; ++i) {
                if (i - 1 >= 0 && a[b[i]] == a[b[i - 1]]) {
                    ret[b[i]] = p;
                } else {
                    ret[b[i]] = ++p;
                }
            }
            return ret;
        }

    }

    static class InputReader {
        private BufferedReader reader;
        private StringTokenizer tokenizer = new StringTokenizer("");

        public InputReader(InputStream inputStream) {
            this.reader = new BufferedReader(
                    new InputStreamReader(inputStream));
        }

        public String next() {
            while (!tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

    }
}

