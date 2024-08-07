
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class NearestOppositeParity {

    static int n, arr[], dp[][], visited[][], max = Integer.MAX_VALUE - 1, cnt;
    static boolean v;

    public static int solve(int pos, int even, int count) {
        if (pos < 1 || pos > n) {
            return Integer.MAX_VALUE;
        }
        if (dp[pos][even] != -1 && dp[pos][even] != Integer.MAX_VALUE) {
            return count + dp[pos][even];
        }
        if (dp[pos][even] == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (visited[pos][even] == 1) {
            v = true;
            cnt = count - 1;
            return Integer.MAX_VALUE;
        }
        if ((even == 1 && arr[pos - 1] % 2 == 1) || (even == 0 && arr[pos - 1] % 2 == 0)) {
            return count;
        }
        int res, r, l;
        visited[pos][even] = 1;
        l = solve(pos - arr[pos - 1], even, count + 1);
        r = solve(pos + arr[pos - 1], even, count + 1);
        res = Math.min(l, r);
        visited[pos][even] = 0;
        if (!v || count == 0 || count > cnt) {
            dp[pos][even] = res - count;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        Scan in = new Scan();
        PrintWriter out = new PrintWriter(System.out);
        n = in.nextInt();
        arr = new int[n];
        dp = new int[n + 2][2];
        visited = new int[n + 2][2];
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < 2; j++) {
                dp[i][j] = -1;
            }
        }
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        long ans;
        for (int i = 0; i < n; i++) {
            v = false;
            cnt = -1;
            if (arr[i] % 2 == 0) {
                ans = solve(i + 1, 1, 0);
            } else {
                ans = solve(i + 1, 0, 0);
            }
            if (i < n - 1) {
                if (ans < 1000000 && ans > 0) {
                    out.print(ans + " ");
                } else {
                    out.print("-1 ");
                }
            } else {
                if (ans < 1000000 && ans > 0) {
                    out.println(ans);
                } else {
                    out.println("-1");
                }
            }
        }
        out.close();
    }

    static class Scan {

        public java.io.InputStream stream = System.in;
        private final static byte EOF = -1, NL = '\n', D = '-', SPC = ' ', buffer[] = new byte[0xFFFF];
        private char cBuff[] = new char[0xFF];
        public int $index, $readCount, itr;

        private void inc() {
            cBuff = java.util.Arrays.copyOf(cBuff, cBuff.length << 1);
        }

        private boolean readLINE() throws IOException {
            if ($readCount == EOF) {
                return false;
            }
            for (itr = 0;;) {
                while ($index < $readCount) {
                    if (buffer[$index] != NL) {
                        if (itr == cBuff.length) {
                            inc();
                        }
                        cBuff[itr++] = (char) buffer[$index++];
                    } else {
                        $index++;
                        return true;
                    }
                }
                $index = 0;
                $readCount = stream.read(buffer);
                if ($readCount == EOF) {
                    return true;
                }
            }
        }

        private boolean readPRT() throws IOException {
            if ($readCount == EOF) {
                return false;
            }
            T:
            for (;;) {
                while ($index < $readCount) {
                    if (buffer[$index] > SPC) {
                        break T;
                    } else {
                        $index++;
                    }
                }
                $index = 0;
                $readCount = stream.read(buffer);
                if ($readCount == EOF) {
                    return false;
                }
            }
            for (itr = 0;;) {
                while ($index < $readCount) {
                    if (buffer[$index] > SPC) {
                        if (itr == cBuff.length) {
                            inc();
                        }
                        cBuff[itr++] = (char) buffer[$index++];
                    } else {
                        return true;
                    }
                }
                $index = 0;
                $readCount = stream.read(buffer);
                if ($readCount == EOF) {
                    return true;
                }
            }
        }

        public int nextInt() throws IOException {
            if (!readPRT()) {
                throw new IOException();
            } else {
                int v = 0, i = 0;
                boolean neg;
                if (cBuff[i] == D) {
                    neg = true;
                    i++;
                } else {
                    neg = false;
                }
                while (i < itr) {
                    v = (v << 3) + (v << 1) + cBuff[i++] - '0';
                }
                return neg ? -v : v;
            }
        }

        public long nextLong() throws IOException {
            if (!readPRT()) {
                throw new IOException();
            } else {
                long v = 0;
                int i = 0;
                boolean neg;
                if (cBuff[i] == D) {
                    neg = true;
                    i++;
                } else {
                    neg = false;
                }
                while (i < itr) {
                    v = (v << 3L) + (v << 1L) + cBuff[i++] - '0';
                }
                return neg ? -v : v;
            }
        }

        public char[] buffer() throws IOException {
            return readPRT() ? cBuff : null;
        }

        public String next() throws IOException {
            return readPRT() ? new String(cBuff, 0, itr) : null;
        }

        public char[] nextArr() throws IOException {
            return readPRT() ? java.util.Arrays.copyOf(cBuff, itr) : null;
        }

        public String nextLine() throws IOException {
            return readLINE() ? new String(cBuff, 0, itr) : null;
        }

        public char[] nextLineArr() throws IOException {
            return readLINE() ? java.util.Arrays.copyOf(cBuff, itr) : null;
        }

        public float nextFloat() throws IOException {
            return Float.parseFloat(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }
}
