import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.Map;
import java.io.IOException;
import java.util.TreeSet;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Aman Kumar Singh
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        EBoxers solver = new EBoxers();
        solver.solve(1, in, out);
        out.close();
    }

    static class EBoxers {
        PrintWriter out;
        InputReader in;

        public void solve(int testNumber, InputReader in, PrintWriter out) {
            this.out = out;
            this.in = in;
            int n = ni();
            int[] arr = new int[n];
            int i = 0;
            TreeMap<Integer, Integer> tmap = new TreeMap<>();
            TreeSet<Integer> tset = new TreeSet<>();
            for (i = 0; i < n; i++) {
                int x = ni();
                tmap.put(x, tmap.getOrDefault(x, 0) + 1);
            }
            for (Integer x : tmap.keySet()) {
                int cnt = tmap.get(x);
                if (cnt == 1)
                    tset.add(x);
                else if (cnt == 3) {
                    if (x == 1) {
                        tset.add(1);
                        tset.add(2);
                    } else {
                        tset.add(x);
                        tset.add(x + 1);
                        tset.add(x - 1);
                    }
                } else if (cnt == 2) {
                    if (tset.contains(x - 1)) {
                        tset.add(x);
                        tset.add(x + 1);
                    } else {
                        tset.add(x);
                        tset.add(x + 1);
                    }
                }
            }
            pn(tset.size());

        }

        int ni() {
            return in.nextInt();
        }

        void pn(long zx) {
            out.println(zx);
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
            if (numChars == -1) {
                throw new UnknownError();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new UnknownError();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public String next() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
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

