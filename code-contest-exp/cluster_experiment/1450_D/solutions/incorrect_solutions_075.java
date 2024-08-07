import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Pranay2516
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastReader in = new FastReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        DRatingCompression solver = new DRatingCompression();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class DRatingCompression {
        int[] a;
        int[] segTree;

        public void solve(int testNumber, FastReader in, PrintWriter out) {
            int n = in.nextInt();
            a = in.readArray(n);
            segTree = new int[4 * n + 1];
            buildTree(0, n - 1, 0);
            HashSet<Integer> h = new HashSet<>();
            boolean not = false;
            for (int i = 0; i < n; ++i) {
                if (h.contains(a[i])) {
                    not = true;
                    break;
                }
                h.add(a[i]);
            }
            for (int i = 1; i <= h.size(); ++i) {
                if (!h.contains(i)) {
                    not = true;
                    break;
                }
            }
            out.print(not ? 0 : 1);
            for (int k = 2; k < n; ++k) {
                not = false;
                h.clear();
                for (int i = 0; i <= n - k; ++i) {
                    int min = minQuery(0, n - 1, i, i + k - 1, 0);
                    if (h.contains(min)) {
                        not = true;
                        break;
                    }
                    h.add(min);
                }
                for (int i = 1; i <= h.size(); ++i) {
                    if (!h.contains(i)) {
                        not = true;
                        break;
                    }
                }
                out.print(not ? 0 : 1);
            }
            if (n > 1) out.println(minQuery(0, n - 1, 0, n - 1, 0) == 1 ? 1 : 0);
        }

        void buildTree(int l, int r, int index) {
            if (l > r) return;
            if (l == r) {
                segTree[index] = a[l];
                return;
            }
            int mid = (l + r) >> 1;
            buildTree(l, mid, 2 * index + 1);
            buildTree(mid + 1, r, 2 * index + 2);
            segTree[index] = Math.min(segTree[2 * index + 1], segTree[2 * index + 2]);
        }

        int minQuery(int l, int r, int ql, int qr, int index) {
            if (ql > r || qr < l) return (int) 1e9 + 1;
            if (ql <= l && qr >= r) return segTree[index];
            int mid = (l + r) >> 1;
            return Math.min(minQuery(l, mid, ql, qr, 2 * index + 1), minQuery(mid + 1, r, ql, qr, 2 * index + 2));
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
            if (numChars == -1) throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));
            return res * sgn;
        }

        public String next() {
            int c = read();
            while (isSpaceChar(c)) c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public int[] readArray(int size) {
            int[] array = new int[size];
            for (int i = 0; i < size; i++) array[i] = nextInt();
            return array;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

