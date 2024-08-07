import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.io.Writer;
import java.io.OutputStreamWriter;
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
        OutputWriter out = new OutputWriter(outputStream);
        TaskD solver = new TaskD();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskD {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.nextInt(), h = in.nextInt(), m = in.nextInt(), k = in.nextInt();
            TreeMap<Integer, ArrayList<Integer>> changes = new TreeMap<>();
            HashSet<Integer> solution = new HashSet<>();
            long optimalTime = 0;
            for (int i = 0; i < n; i++) {
                int a = in.nextInt(), b = in.nextInt();
                int md = b % (m / 2);
                int L = (md + 1) % (m / 2);
                int R = (md + k - 1 + m / 2) % (m / 2);
                if ((L == 0 && R >= 0) || L > R) {
                    solution.add(i);
                }
                int H = ((R + 1) % (m / 2));
                if (L != 0 && !changes.containsKey(L)) {
                    changes.put(L, new ArrayList<>());
                }
                if (H != 0 && !changes.containsKey(H)) {
                    changes.put(H, new ArrayList<>());
                }
                if (L != 0) changes.get(L).add(2 * i);
                if (H != 0) changes.get(H).add(2 * i + 1);
            }
            if (k == 1) {
                out.println(0 + " " + 0);
                return;
            }
            HashSet<Integer> optimalSolution = (HashSet<Integer>) solution.clone();
            int minCancel = solution.size();
            for (int l : changes.keySet()) {
                for (int t : changes.get(l)) {
                    if (t % 2 == 1) solution.remove(t / 2);
                    else solution.add(t / 2);
                }

                if (solution.size() < minCancel) {
//                optimalSolution = (HashSet<Integer>) solution.clone();
                    minCancel = solution.size();
                    optimalTime = l;
                }
            }

            solution = (HashSet<Integer>) optimalSolution.clone();

            for (int l : changes.keySet()) {
                for (int t : changes.get(l)) {
                    if (t % 2 == 1) solution.remove(t / 2);
                    else solution.add(t / 2);
                }

                if (optimalTime == l) {
                    optimalSolution = (HashSet<Integer>) solution.clone();
                }
            }

            out.println(optimalSolution.size() + " " + optimalTime);
            for (int v : optimalSolution) out.print((v + 1) + " ");
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
            while (isSpaceChar(c)) c = read();
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

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void println(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
            writer.print('\n');
        }

        public void close() {
            writer.close();
        }

    }
}

