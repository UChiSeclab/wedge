import java.util.*;
import java.io.*;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.abs;

public class NearestOppositeParity {
    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        new NearestOppositeParity().run();
    }

    private static class InputReader {
        private final InputStream is;
        private final byte[] inbuf = new byte[1024];
        private int lenbuf = 0;
        private int ptrbuf = 0;

        public InputReader(InputStream stream) {
            is = stream;
        }

        private int readByte() {
            if (lenbuf == -1)
                throw new InputMismatchException();
            if (ptrbuf >= lenbuf) {
                ptrbuf = 0;
                try {
                    lenbuf = is.read(inbuf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (lenbuf <= 0)
                    return -1;
            }
            return inbuf[ptrbuf++];
        }

        private boolean isSpaceChar(int c) {
            return !(c >= 33 && c <= 126);
        }

        private int skip() {
            int b;
            while ((b = readByte()) != -1 && isSpaceChar(b))
                ;
            return b;
        }

        public double nextDouble() {
            return Double.parseDouble(nextString());
        }

        public char nextChar() {
            return (char) skip();
        }

        public String nextString() {
            int b = skip();
            StringBuilder sb = new StringBuilder();
            while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != ' ')
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        public char[] nextString(int n) {
            char[] buf = new char[n];
            int b = skip(), p = 0;
            while (p < n && !(isSpaceChar(b))) {
                buf[p++] = (char) b;
                b = readByte();
            }
            return n == p ? buf : Arrays.copyOf(buf, p);
        }

        public int nextInt() {
            int num = 0, b;
            boolean minus = false;
            while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
                ;
            if (b == '-') {
                minus = true;
                b = readByte();
            }

            while (true) {
                if (b >= '0' && b <= '9') {
                    num = num * 10 + (b - '0');
                } else {
                    return minus ? -num : num;
                }
                b = readByte();
            }
        }

        public long nextLong() {
            long num = 0;
            int b;
            boolean minus = false;
            while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
                ;
            if (b == '-') {
                minus = true;
                b = readByte();
            }

            while (true) {
                if (b >= '0' && b <= '9') {
                    num = num * 10 + (b - '0');
                } else {
                    return minus ? -num : num;
                }
                b = readByte();
            }
        }
    }

    private static final boolean oj = System.getProperty("ONLINE_JUDGE") != null;

    private static void debug(Object... o) {
        if (!oj) {
            System.out.println(Arrays.deepToString(o));
        }
    }

    // =================================================================================================================

    final int INF = (int) 1e9;
    int n;
    int[] a;
    int[][] d;
    ArrayList<Integer>[] adj;

    void bfs(int rem) {
        int[] q = new int[n];
        int qh, qt;
        qh = qt = 0;
        boolean[] vis = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            if (a[i] % 2 == rem) {
                d[rem][i] = 0;
                q[qt++] = i;
                vis[i] = true;
            } else {
                d[rem][i] = INF;
            }
        }
        while (qh < qt) {
            int u = q[qh++];
            for (int v : adj[u]) {
                if (!vis[v]) {
                    d[rem][v] = d[rem][u] + 1;
                    q[qt++] = v;
                    vis[v] = true;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    void solve() {
        n = in.nextInt();
        a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }

        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            int li = i - a[i], ri = i + a[i];
            if (li >= 1) {
                adj[li].add(i);
            }
            if (ri <= n) {
                adj[ri].add(i);
            }
        }
        
        d = new int[2][n + 1];
        bfs(0);
        bfs(1);

        for (int i = 1; i <= n; i++) {
            int rem = a[i] % 2;
            out.print(((d[1 - rem][i] == INF) ? -1 : d[1 - rem][i]) + " ");
        }
        out.println();
    }

    public void run() {
        in = new InputReader(System.in);
        out = new PrintWriter(System.out);
        int tt = 1;
        // int tt = in.nextInt();
        for (int i = 1; i <= tt; i++) {
            solve();
        }
        out.flush();
    }
}