import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        FMovingPoints solver = new FMovingPoints();
        solver.solve(1, in, out);
        out.close();
    }

    static class FMovingPoints {
        PrintWriter out;
        InputReader in;
        long[][] segtree;
        final Comparator<Tuple> com = new Comparator<Tuple>() {
            public int compare(Tuple t1, Tuple t2) {
                if (t1.x != t2.x)
                    return Integer.compare(t1.x, t2.x);
                else
                    return Integer.compare(t1.y, t2.y);
            }
        };

        long merge(long x, long y) {
            return x + y;
        }

        void segtree_update(int ind, long val, int n) {
            ind += n;
            for (segtree[0][ind] += val, segtree[1][ind]++; ind > 1; ind >>= 1) {
                segtree[0][ind >> 1] = merge(segtree[0][ind], segtree[0][ind ^ 1]);
                segtree[1][ind >> 1] = merge(segtree[1][ind], segtree[1][ind ^ 1]);
            }
        }

        long[] segtree_query(int l, int r, int n) {
            long res = 0;
            long cnt = 0;
            for (l += n, r += n; l < r; l >>= 1, r >>= 1) {
                if ((l & 1) == 1) {
                    res = merge(res, segtree[0][l]);
                    cnt = merge(cnt, segtree[1][l]);
                    l++;
                }
                if ((r & 1) == 1) {
                    --r;
                    res = merge(res, segtree[0][r]);
                    cnt = merge(cnt, segtree[1][r]);
                }
            }
            return new long[]{res, cnt};
        }

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            this.out = out;
            this.in = in;
            int n = ni();
            Tuple[] arr = new Tuple[n];
            int i = 0;
            segtree = new long[2][2 * n];
            HashMap<Integer, Integer> hmap = new HashMap<>();
            for (i = 0; i < n; i++)
                arr[i] = new Tuple(ni(), -1);
            TreeSet<Integer> tset = new TreeSet<>();
            for (i = 0; i < n; i++) {
                arr[i] = new Tuple(arr[i].x, ni());
                tset.add(arr[i].y);
            }
            Arrays.sort(arr, com);
            int c = 0;
            for (int x : tset)
                hmap.put(x, c++);
            long ans = 0;
            for (i = n - 1; i >= 0; i--) {
                long[] curr_query = segtree_query(hmap.get(arr[i].y), c, c);
                ans += curr_query[0] - arr[i].x * curr_query[1];
                segtree_update(hmap.get(arr[i].y), arr[i].x, c);
            }
            pn(ans);
        }

        int ni() {
            return in.nextInt();
        }

        void pn(long zx) {
            out.println(zx);
        }

        class Tuple {
            int x;
            int y;

            Tuple(int a, int b) {
                x = a;
                y = b;
            }

        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1)
                throw new UnknownError();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new UnknownError();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public String next() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuffer res = new StringBuffer();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));

            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

    }
}

