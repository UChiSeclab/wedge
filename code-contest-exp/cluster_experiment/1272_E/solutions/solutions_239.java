import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.AbstractCollection;
import java.util.PriorityQueue;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastReader in = new FastReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        ENearestOppositeParity solver = new ENearestOppositeParity();
        solver.solve(1, in, out);
        out.close();
    }

    static class ENearestOppositeParity {
        public void solve(int testNumber, FastReader s, PrintWriter out) {
            int n = s.nextInt();
            int[] arr = s.nextIntArray(n);

            int[][] ans = new int[2][n];
            Arrays.fill(ans[0], -1);
            Arrays.fill(ans[1], -1);

            int[] from = new int[2 * n], to = new int[2 * n];
            int pos = 0;

            int[] lol = new int[n];
            Arrays.fill(lol, 100000000);
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((o1, o2) -> o1[1] - o2[1]);
            for (int i = 0; i < n; i++) {
                if (i + arr[i] < n) {
                    to[pos] = i;
                    from[pos] = i + arr[i];
                    pos++;

                    if (arr[i + arr[i]] % 2 != arr[i] % 2) {
                        lol[i] = 1;
                        pq.add(new int[]{i, 1});
                    }
                }
                if (i - arr[i] >= 0) {
                    to[pos] = i;
                    from[pos] = i - arr[i];
                    pos++;

                    if (arr[i - arr[i]] % 2 != arr[i] % 2) {
                        lol[i] = 1;
                        pq.add(new int[]{i, 1});
                    }
                }
            }

            from = Arrays.copyOf(from, pos);
            to = Arrays.copyOf(to, pos);

            int[][] g = packD(n, from, to);
            boolean[] done = new boolean[n];

            while (!pq.isEmpty()) {
                int[] rv = pq.poll();
                int u = rv[0], w = rv[1];
                if (done[u]) {
                    continue;
                }
                done[u] = true;
                for (int v : g[u]) {
                    if (1 + w < lol[v]) {
                        lol[v] = 1 + w;
                        pq.add(new int[]{v, lol[v]});
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                if (done[i]) {
                    out.print(lol[i] + " ");
                } else {
                    out.print(-1 + " ");
                }
            }
            out.println();
        }

        int[][] packD(int n, int[] from, int[] to) {
            int[][] g = new int[n][];
            int[] p = new int[n];
            for (int f : from)
                p[f]++;
            for (int i = 0; i < n; i++)
                g[i] = new int[p[i]];
            for (int i = 0; i < from.length; i++) {
                g[from[i]][--p[from[i]]] = to[i];
            }
            return g;
        }

    }

    static class FastReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private FastReader.SpaceCharFilter filter;

        public FastReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public int[] nextIntArray(int n) {
            int[] array = new int[n];
            for (int i = 0; i < n; ++i) array[i] = nextInt();
            return array;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

