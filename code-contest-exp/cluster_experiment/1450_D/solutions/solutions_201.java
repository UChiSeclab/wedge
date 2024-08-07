import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(null, new TaskAdapter(), "", 1 << 27);
        thread.start();
        thread.join();
    }

    static class TaskAdapter implements Runnable {
        @Override
        public void run() {
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
    }

    static class DRatingCompression {
        public void solve(int testNumber, FastReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n + 1];
            for (int i = 1; i <= n; i++) a[i] = s.nextInt();
            int[] left = new int[n + 1], right = new int[n + 1];
            Arrays.fill(right, n + 1);
            Stack<pair<Integer, Integer>> st = new Stack<>();
            for (int i = 1; i <= n; i++) {
                while (!st.isEmpty() && st.peek().x >= a[i]) st.pop();
                if (!st.isEmpty()) {
                    left[i] = st.peek().y;
                }
                st.add(new pair<>(a[i], i));
            }
            st = new Stack<>();
            for (int i = n; i >= 0; i--) {
                while (!st.isEmpty() && st.peek().x > a[i]) st.pop();
                if (!st.isEmpty()) {
                    right[i] = st.peek().y;
                }
                st.add(new pair<>(a[i], i));
            }
            int[] ok = new int[n + 10], range = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                range[a[i]] = Math.max(range[a[i]], right[i] - left[i] - 1);
            }
            for (int i = 1; i <= n; i++) {
                int mx = n - i + 1;
                ok[1]++;
                ok[Math.min(range[i], mx) + 1]--;
            }
            for (int i = 1; i <= n; i++) ok[i] += ok[i - 1];
            for (int i = 1; i <= n; i++) w.print(ok[i] == n - i + 1 ? 1 : 0);
            w.println();
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

        public String next() {

            int c = read();

            while (isSpaceChar(c))
                c = read();

            StringBuilder res = new StringBuilder();

            do {
                res.appendCodePoint(c);
                c = read();
            }
            while (!isSpaceChar(c));

            return res.toString();
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

    static class pair<U extends Comparable<U>, V extends Comparable<V>> implements Comparable<pair<U, V>> {
        public U x;
        public V y;

        public pair(U x, V y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(pair<U, V> other) {
            int cmp = x.compareTo(other.x);
            return cmp == 0 ? y.compareTo(other.y) : cmp;
        }

        public String toString() {
            return x.toString() + " " + y.toString();
        }

        public boolean equals(Object obj) {
            if (this.getClass() != obj.getClass()) return false;
            pair<U, V> other = (pair<U, V>) obj;
            return x.equals(other.x) && y.equals(other.y);
        }

        public int hashCode() {
            return 31 * x.hashCode() + y.hashCode();
        }

    }
}

