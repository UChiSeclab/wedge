import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author KharYusuf
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
        public void solve(int testNumber, FastReader s, PrintWriter w) {
            int n = s.nextInt(), MX = (int) 1e8;
            int[][] x = new int[n][2];
            for (int i = 0; i < n; i++) x[i][0] = s.nextInt();
            TreeSet<Integer> p = new TreeSet<>(), ne = new TreeSet<>();
            TreeMap<Integer, Integer> po = new TreeMap<>(), nee = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                x[i][1] = s.nextInt();
                if (x[i][1] > 0) p.add(x[i][1]);
                else if (x[i][1] < 0) ne.add(x[i][1] + MX);
            }
            int cnt = 0;
            for (int i : p) po.put(i, cnt++);
            cnt = 0;
            for (int i : ne) nee.put(i, cnt++);
            func.sortbyColumn(x, 0);
            long ans = 0, cntneg = 0, cntz = 0, cneg = 0, cz = 0;
            bit pos = new bit(n), neg = new bit(n);
            bit poscnt = new bit(p.size()), negcnt = new bit(ne.size());
            for (int i = 0; i < n; i++) {
                if (x[i][1] > 0) {
                    ans += x[i][0] * cntneg - cneg;
                    ans += x[i][0] * cntz - cz;
                    int cur = po.get(x[i][1]);
                    ans += poscnt.query(cur) * (long) x[i][0] - pos.query(cur);
                    poscnt.modify(cur, 1);
                    pos.modify(cur, x[i][0]);
                } else if (x[i][1] < 0) {
                    cntneg++;
                    int v = nee.get(MX + x[i][1]);
                    ans += negcnt.query(v) * (long) x[i][0] - neg.query(v);
                    negcnt.modify(v, 1);
                    neg.modify(v, x[i][0]);
                    cneg += x[i][0];
                } else {
                    cntz++;
                    cz += x[i][0];
                    ans += cneg;
                }
            }
            w.println(ans);
        }

        public class bit {
            public int n;
            public long[] t;

            public bit(int n) {
                t = new long[n];
                this.n = n;
            }

            public void modify(int ind, long val) {
                for (; ind < n; ind = ind | (++ind)) t[ind] += val;
            }

            public long query(int ind) {
                long sum = 0;
                for (; ind >= 0; ind = (ind & (++ind)) - 1) sum += t[ind];
                return sum;
            }

        }

    }

    static class bit {
        public int n;
        public int[] t;

        public bit(int n) {
            t = new int[n];
            this.n = n;
        }

        public bit(int[] a, int n) {
            t = new int[n];
            this.n = n;
            for (int i = 0; i < n; i++) {
                modify(i, a[i]);
            }
        }

        public void modify(int ind, int val) {
            for (; ind < n; ind = ind | (++ind)) t[ind] += val;
        }

        public int query(int ind) {
            int sum = 0;
            for (; ind >= 0; ind = (ind & (++ind)) - 1) sum += t[ind];
            return sum;
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

            if (numChars == -1)
                throw new InputMismatchException();

            if (curChar >= numChars) {

                curChar = 0;

                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }

                if (numChars <= 0)
                    return -1;
            }

            return buf[curChar++];
        }

        public int nextInt() {

            int c = read();

            while (isSpaceChar(c))
                c = read();

            int sgn = 1;

            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;

            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();

                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));

            return res * sgn;
        }

        public boolean isSpaceChar(int c) {

            if (filter != null)
                return filter.isSpaceChar(c);

            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }

    static class func {
        public static void sortbyColumn(int[][] arr, final int col) {
            Arrays.sort(arr, new Comparator<int[]>() {

                public int compare(final int[] entry1, final int[] entry2) {
                    if (entry1[col] > entry2[col]) return 1;
                    if (entry1[col] < entry2[col]) return -1;
                    return 0;
                }
            });
        }

    }
}

