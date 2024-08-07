/**
 * author: derrick20
 * created: 11/26/20 10:37 PM
 */
import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class TrucksAndCitiesFast {
    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        
        int N = sc.nextInt();
        int M = sc.nextInt();
        long[] a = sc.nextLongs(N);
        long limitingCapacity = 0;
        ArrayList<int[]>[] queries = new ArrayList[N + 1];
        Arrays.setAll(queries, i -> new ArrayList<>());
        for (int i = 0; i < M; i++) {
            int start = sc.nextInt() - 1;
            int finish = sc.nextInt() - 1;
            int useRate = sc.nextInt();
            int refuels = sc.nextInt();
            queries[refuels].add(new int[]{start, finish, useRate});
        }
        long[][][] dp = new long[2][N][N];
        int curr = 0;
        for (int k = 0; k <= N; k++) {
//            System.out.println("For k = " + k);
            int next = 1 ^ curr;
            for (int l = 0; l <= N - 1; l++) {
                /*
                Key idea:
                Now, because the optimal structure of the partition is such
                that the last rest stop is non-decreasing, we can solve n values
                of r in O(n) time total, rather than O(n) for each.

                Our easy transition is:
                min over all last vals of {max(dp[l][last][k - 1], a[r] - a[last]}
                Now, we just need to keep moving forward while that's non-increasing

                Need to go in order of increasing k
                 */
                dp[next][l][l] = 0;
                int last = l;
                for (int r = l + 1; r <= N - 1; r++) {
                    if (k == 0) {
                        dp[next][l][r] = a[r] - a[l];
                    } else {
                        while (last + 1 <= r && max(dp[curr][l][last], a[r] - a[last]) >= max(dp[curr][l][last + 1], a[r] - a[last + 1])) {
                            last++;
                        }
                        dp[next][l][r] = max(dp[curr][l][last], a[r] - a[last]);
                    }
//                    System.out.println(l + " " + r + " => " + dp[l][r][k]);
                }
            }
            curr = next;
            for (int[] query : queries[k]) {
                // Very nice problem
                int start = query[0];
                int finish = query[1];
                long useRate = query[2];
                long requiredCapacity = useRate * dp[curr][start][finish];
                limitingCapacity = max(limitingCapacity, requiredCapacity);
            }
        }
        out.println(limitingCapacity);
        out.close();
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