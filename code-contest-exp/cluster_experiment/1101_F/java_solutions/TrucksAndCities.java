/**
 * author: derrick20
 * created: 11/26/20 5:44 PM
 */
import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class TrucksAndCities {
    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        
        N = sc.nextInt();
        M = sc.nextInt();
        a = sc.nextLongs(N);
        long limitingCapacity = 0;
        int[][] queries = new int[M][4];
        for (int i = 0; i < M; i++) {
            int start = sc.nextInt() - 1;
            int finish = sc.nextInt() - 1;
            int useRate = sc.nextInt();
            int refuels = sc.nextInt();
            queries[i] = new int[]{start, finish, useRate, refuels};
        }
        shuffle(queries);
        for (int i = 0; i < M; i++) {
            if (!doable(limitingCapacity, queries[i][0], queries[i][1], queries[i][2], queries[i][3])) {
                long lo = limitingCapacity + 1;
                long hi = (long) (1e18 - 1e9);
                while (lo < hi) {
                    long mid = (lo + hi) / 2;
                    if (doable(mid, queries[i][0], queries[i][1], queries[i][2], queries[i][3])) {
                        hi = mid;
                    } else {
                        lo = mid + 1;
                    }
                }
//            System.out.println(lo);
                limitingCapacity = max(limitingCapacity, lo);
            }
        }
        out.println(limitingCapacity);
        out.close();
    }

    static int N, M;
    static long[] a;

    static void shuffle(int[][] a) {
        Random rand = new Random();
        for (int i = a.length - 1; i > 1; i--) {
            int j = rand.nextInt(i);
            int[] temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    static boolean doable(long capacity, int start, int finish, long useRate, int refuels) {
        long consumed = 0;
        long refueled = 0;
        for (int curr = start + 1; curr <= finish; curr++) {
            long dist = a[curr] - a[curr - 1];
            if (consumed + useRate * dist > capacity) {
                consumed = useRate * dist; // cleanse, and just add this distance
                if (consumed > capacity) {
                    return false;
                }
                refueled++;
                if (refueled > refuels) {
                    return false;
                }
             } else {
                consumed += useRate * dist;
            }
        }
        return true;
    }

    static class FastScanner {
        private int BS = 1 << 16;
        private char NC = (char) 0;
        private byte[] buf = new byte[BS];
        private int bId = 0, size = 0;
        private char c = NC;
        private double cnt = 1;
        private BufferedInputStream in;

        public FastScanner() {
            in = new BufferedInputStream(System.in, BS);
        }

        public FastScanner(String s) {
            try {
                in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
            } catch (Exception e) {
                in = new BufferedInputStream(System.in, BS);
            }
        }

        private char getChar() {
            while (bId == size) {
                try {
                    size = in.read(buf);
                } catch (Exception e) {
                    return NC;
                }
                if (size == -1) return NC;
                bId = 0;
            }
            return (char) buf[bId++];
        }

        public int nextInt() {
            return (int) nextLong();
        }

        public int[] nextInts(int N) {
            int[] res = new int[N];
            for (int i = 0; i < N; i++) {
                res[i] = (int) nextLong();
            }
            return res;
        }

        public long[] nextLongs(int N) {
            long[] res = new long[N];
            for (int i = 0; i < N; i++) {
                res[i] = nextLong();
            }
            return res;
        }

        public long nextLong() {
            cnt = 1;
            boolean neg = false;
            if (c == NC) c = getChar();
            for (; (c < '0' || c > '9'); c = getChar()) {
                if (c == '-') neg = true;
            }
            long res = 0;
            for (; c >= '0' && c <= '9'; c = getChar()) {
                res = (res << 3) + (res << 1) + c - '0';
                cnt *= 10;
            }
            return neg ? -res : res;
        }

        public double nextDouble() {
            double cur = nextLong();
            return c != '.' ? cur : cur + nextLong() / cnt;
        }

        public double[] nextDoubles(int N) {
            double[] res = new double[N];
            for (int i = 0; i < N; i++) {
                res[i] = nextDouble();
            }
            return res;
        }

        public String next() {
            StringBuilder res = new StringBuilder();
            while (c <= 32) c = getChar();
            while (c > 32) {
                res.append(c);
                c = getChar();
            }
            return res.toString();
        }

        public String nextLine() {
            StringBuilder res = new StringBuilder();
            while (c <= 32) c = getChar();
            while (c != '\n') {
                res.append(c);
                c = getChar();
            }
            return res.toString();
        }

        public boolean hasNext() {
            if (c > 32) return true;
            while (true) {
                c = getChar();
                if (c == NC) return false;
                else if (c > 32) return true;
            }
        }
    }
    static void ASSERT(boolean assertion, String message) {
        if (!assertion) throw new AssertionError(message);
    }
    static void ASSERT(boolean assertion) {
        if (!assertion) throw new AssertionError();
    }
}