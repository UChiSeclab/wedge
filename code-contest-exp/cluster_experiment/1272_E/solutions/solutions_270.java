import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Sparsh Sanchorawala
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        ENearestOppositeParity solver = new ENearestOppositeParity();
        solver.solve(1, in, out);
        out.close();
    }

    static class ENearestOppositeParity {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            ArrayList<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            Queue<Integer> q = new LinkedList<>();
            int[] level = new int[n];
            Arrays.fill(level, -1);
            for (int i = 0; i < n; i++) {
                if (i - a[i] >= 0) {
                    if (a[i - a[i]] % 2 != a[i] % 2) {
                        if (level[i] == -1) {
                            q.add(i);
                            level[i] = 1;
                        }
                    } else
                        adj[i - a[i]].add(i);
                }
                if (i + a[i] < n) {
                    if (a[i + a[i]] % 2 != a[i] % 2) {
                        if (level[i] == -1) {
                            q.add(i);
                            level[i] = 1;
                        }
                    } else
                        adj[i + a[i]].add(i);
                }
            }
            while (!q.isEmpty()) {
                int x = q.poll();
                for (int y : adj[x]) {
                    if (level[y] == -1) {
                        q.add(y);
                        level[y] = level[x] + 1;
                    }
                }
            }
            for (int i = 0; i < n; i++)
                w.print(level[i] + " ");
            w.println();
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;

        public InputReader(InputStream stream) {
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

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

