import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author revanth
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        DRatingCompression solver = new DRatingCompression();
        solver.solve(1, in, out);
        out.close();
    }

    static class DRatingCompression {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int t = in.nextInt();
            while (t-- > 0) {
                int n = in.nextInt();
                int[] a = in.nextIntArray(n);
                Stack<Integer> st = new Stack<>();
                int[] left = new int[n];
                for (int i = 0; i < n; i++) {
                    while (!st.empty() && a[st.peek()] >= a[i])
                        st.pop();
                    left[i] = st.isEmpty() ? -1 : st.peek();
                    st.push(i);
                }
                st.clear();
                int[] right = new int[n];
                for (int i = n - 1; i >= 0; i--) {
                    while (!st.empty() && a[st.peek()] >= a[i])
                        st.pop();
                    right[i] = st.isEmpty() ? n : st.peek();
                    st.push(i);
                }
                int[] nums = new int[n + 1];
                for (int i = 0; i < n; i++)
                    nums[a[i]] = Math.max(nums[a[i]], right[i] - left[i] - 1);
                for (int i = 2; i <= n; i++)
                    nums[i] = Math.min(nums[i], nums[i - 1]);
                int[] ans = new int[n + 1];
                for (int i = 1; i <= n; i++) {
                    int len = n + 1 - i;
                    out.print(nums[len] >= i ? 1 : 0);
                }
                out.println();
            }
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

        public void println() {
            writer.println();
        }

        public void close() {
            writer.close();
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1 << 16];
        private int curChar;
        private int snumChars;
        private InputReader.SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                try {
                    snumChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public int[] nextIntArray(int n) {
            int a[] = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            boolean isSpaceChar(int ch);

        }

    }
}

