import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Main {
    static PrintWriter out;
    static InputReader ir;

    static void solve() {
        int t = ir.nextInt();
        while (t-- > 0) {
            int n = ir.nextInt();
            int[] a = ir.nextIntArray(n);
            for (int i = 0; i < n; i++)
                a[i]--;
            int[] ct = new int[n];
            for (int i = 0; i < n; i++)
                ct[a[i]]++;
            char[] res = new char[n];
            Arrays.fill(res, '0');
            res[0] = '1';
            for (int i = 0; i < n; i++)
                if (ct[i] != 1)
                    res[0] = '0';
            int left = 0, right = n - 1;
            int ma = 0;
            while (ma < n && ct[ma] == 1)
                ma++;
            int idx = -1;
            for (int i = 0; i < ma; i++) {
                if (a[left] == i) {
                    left++;
                    res[n - 1 - i] = '1';
                } else if (a[right] == i) {
                    right--;
                    res[n - 1 - i] = '1';
                } else {
                    idx = i;
                    break;
                }
                if (i == ma - 1) {
                    idx = ma;
                    break;
                }
            }
            if (idx >= 0 && idx < n && ct[idx] >= 1) {
                res[n - 1 - idx] = '1';
            }
            out.println(String.valueOf(res));
        }
    }

    public static void main(String[] args) {
        ir = new InputReader(System.in);
        out = new PrintWriter(System.out);
        solve();
        out.flush();
    }

    static class InputReader {

        private InputStream in;
        private byte[] buffer = new byte[1024];
        private int curbuf;
        private int lenbuf;

        public InputReader(InputStream in) {
            this.in = in;
            this.curbuf = this.lenbuf = 0;
        }

        public boolean hasNextByte() {
            if (curbuf >= lenbuf) {
                curbuf = 0;
                try {
                    lenbuf = in.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (lenbuf <= 0)
                    return false;
            }
            return true;
        }

        private int readByte() {
            if (hasNextByte())
                return buffer[curbuf++];
            else
                return -1;
        }

        private boolean isSpaceChar(int c) {
            return !(c >= 33 && c <= 126);
        }

        private void skip() {
            while (hasNextByte() && isSpaceChar(buffer[curbuf]))
                curbuf++;
        }

        public boolean hasNext() {
            skip();
            return hasNextByte();
        }

        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            StringBuilder sb = new StringBuilder();
            int b = readByte();
            while (!isSpaceChar(b)) {
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        public int nextInt() {
            if (!hasNext())
                throw new NoSuchElementException();
            int c = readByte();
            while (isSpaceChar(c))
                c = readByte();
            boolean minus = false;
            if (c == '-') {
                minus = true;
                c = readByte();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res = res * 10 + c - '0';
                c = readByte();
            } while (!isSpaceChar(c));
            return (minus) ? -res : res;
        }

        public long nextLong() {
            if (!hasNext())
                throw new NoSuchElementException();
            int c = readByte();
            while (isSpaceChar(c))
                c = readByte();
            boolean minus = false;
            if (c == '-') {
                minus = true;
                c = readByte();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res = res * 10 + c - '0';
                c = readByte();
            } while (!isSpaceChar(c));
            return (minus) ? -res : res;
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }

        public char[][] nextCharMap(int n, int m) {
            char[][] map = new char[n][m];
            for (int i = 0; i < n; i++)
                map[i] = next().toCharArray();
            return map;
        }
    }

    static void tr(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}