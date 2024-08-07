import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
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
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskE solver = new TaskE();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskE {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] a = IOUtils.readIntArray(in, n);
            ArrayUtils.shuffle(a);
            Arrays.sort(a);
            int last = Math.max(1, a[0] - 1);
            int ans = 1;
            for (int i = 1; i < a.length; i++) {
                if (last < a[i] - 1) {
                    ans++;
                    last = a[i] - 1;
                } else if (last < a[i]) {
                    ans++;
                    last = a[i];
                } else if (last < a[i] + 1) {
                    ans++;
                    last = a[i] + 1;
                }
            }
            out.println(ans);
        }

    }

    static final class ArrayUtils {
        static Random rnd = new Random();

        public static void shuffle(int[] ar) {
            for (int i = ar.length - 1; i > 0; i--) {
                int index = rnd.nextInt(i + 1);
                int a = ar[index];
                ar[index] = ar[i];
                ar[i] = a;
            }
        }

    }

    static class IOUtils {
        public static int[] readIntArray(InputReader ir, int n) {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++) {
                ar[i] = ir.nextInt();
            }
            return ar;
        }

    }

    static class InputReader {
        final InputStream is;
        final byte[] buffer = new byte[1024];
        int curCharIdx;
        int nChars;

        public InputReader(InputStream is) {
            this.is = is;
        }

        public int read() {
            if (curCharIdx >= nChars) {
                try {
                    curCharIdx = 0;
                    nChars = is.read(buffer);
                    if (nChars == -1)
                        return -1;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return buffer[curCharIdx++];
        }

        public int nextInt() {
            int sign = 1;
            int c = skipDelims();
            if (c == '-') {
                sign = -1;
                c = read();
                if (isDelim(c))
                    throw new RuntimeException("Incorrect format");
            }
            int val = 0;
            while (c != -1 && !isDelim(c)) {
                if (!isDigit(c))
                    throw new RuntimeException("Incorrect format");
                val = 10 * val + (c - '0');
                c = read();
            }
            return val * sign;
        }

        private final int skipDelims() {
            int c = read();
            while (isDelim(c)) {
                c = read();
            }
            return c;
        }

        private static boolean isDelim(final int c) {
            return c == ' ' ||
                    c == '\n' ||
                    c == '\t' ||
                    c == '\r' ||
                    c == '\f';
        }

        private static boolean isDigit(final int c) {
            return '0' <= c && c <= '9';
        }

    }
}

