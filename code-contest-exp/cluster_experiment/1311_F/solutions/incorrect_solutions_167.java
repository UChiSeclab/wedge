//package Round624;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author sguar <shugangcao@gmail.com>
 * strive for greatness
 * Created on 2020-01-10
 */
public class E {
    InputStream is;
    PrintWriter out;
    private static final String INPUT_FILE_NAME = "/Users/sguar/IdeaProjects/kotlinLearn/src/input.txt";

    void solve() {
        int n = ni();
        int[] x = na(n);
        int[] v = na(n);

        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(i, x[i]);
        }
        Arrays.sort(pairs, Comparator.comparingInt(o -> o.y));
        Map<Integer, Integer> xMap = new HashMap<>();

        Pair[] points = new Pair[n];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            Pair p = pairs[i];
            if (!xMap.containsKey(p.y)) {
                xMap.put(++cnt, p.y);
            }
            points[i] = new Pair(cnt, v[pairs[i].x]);
        }
        Arrays.sort(points, (o1, o2) -> {
            if (o1.y == o2.y) {
                return o1.x - o2.x;
            }
            return o1.y - o2.y;
        });

        debug(xMap);
        debug(points);
        BIT bit = new BIT(cnt);
        long ans = 0;
        for (int i = 0; i < n; i++) {
            Pair p = points[i];
            int realX = xMap.get(p.x);
            ans += bit.query(p.x, realX);
            bit.add(p.x, realX);
        }
        out.println(ans);
    }

    public class BIT {
        int n;
        long[] a;
        int[] cnt;

        BIT(int n) {
            this.n = n;
            this.a = new long[n << 1];
            this.cnt = new int[n << 1];
        }

        int lowBit(int x) {
            return x & (-x);
        }

        void add(int index, int cnt) {
            while (index <= this.n) {
                this.a[index] += cnt;
                this.cnt[index] += 1;
                index += lowBit(index);
            }
        }

        long query(int index, int now) {
            long sum = 0;
            while (index > 0) {
                sum += now * this.cnt[index] - this.a[index];
                index = index - lowBit(index);
            }
            return sum;
        }
    }

    class Pair {
        int x;
        int y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "x: " + x + ", y: " + y;
        }
    }

    public static long invl(long a, long mod) {
        long b = mod;
        long p = 1, q = 0;
        while (b > 0) {
            long c = a / b;
            long d;
            d = a;
            a = b;
            b = d % b;
            d = p;
            p = q;
            q = d - c * q;
        }
        return p < 0 ? p + mod : p;
    }


    void run() throws Exception {

        is = oj ? System.in : new FileInputStream(INPUT_FILE_NAME);
        out = new PrintWriter(System.out);

        long s = System.currentTimeMillis();
        solve();
        out.flush();
        debug(System.currentTimeMillis() - s + "ms");
    }

    public static void main(String[] args) throws Exception {
        new E().run();
    }

    private byte[] inbuf = new byte[1024];
    int lenbuf = 0;
    int ptrbuf = 0;

    private int readByte() {
        if (lenbuf == -1) throw new InputMismatchException();
        if (ptrbuf >= lenbuf) {
            ptrbuf = 0;
            try {
                lenbuf = is.read(inbuf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (lenbuf <= 0) return -1;
        }
        return inbuf[ptrbuf++];
    }

    private boolean isSpaceChar(int c) {
        return !(c >= 33 && c <= 126);
    }

    private int skip() {
        int b;
        while ((b = readByte()) != -1 && isSpaceChar(b)) ;
        return b;
    }

    private double nd() {
        return Double.parseDouble(ns());
    }

    private char nc() {
        return (char) skip();
    }

    private String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != ' ')
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    private char[] ns(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while (p < n && !(isSpaceChar(b))) {
            buf[p++] = (char) b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }

    private char[][] nm(int n, int m) {
        char[][] map = new char[n][];
        for (int i = 0; i < n; i++) map[i] = ns(m);
        return map;
    }

    private int[] na(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = ni();
        return a;
    }

    private int ni() {
        int num = 0, b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
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

    private long nl() {
        long num = 0;
        int b;
        boolean minus = false;
        while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-')) ;
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

    private boolean oj = System.getProperty("ONLINE_JUDGE") != null;

    private void debug(Object... o) {
        if (!oj) System.out.println(Arrays.deepToString(o));
    }

    private void debug(Map map) {
        if (!oj) {
            if (map.isEmpty()) {
                System.out.println("map is empty");
            } else {
                map.forEach(this::debug);
            }
        }
    }

    private void debug(Object k, Object v) {
        if (!oj) {
            System.out.println(k.toString() + ": " + v.toString());
        }
    }
}