import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //   npe, particularly in maps
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            char[] s = rs();

            // D : K
            int[] dprefix = new int[n];
            int[] kprefix = new int[n];
            dprefix[0] = s[0] == 'D' ? 1 : 0;
            kprefix[0] = s[0] == 'K' ? 1 : 0;
            for (int i = 1; i < n; i++) {
                dprefix[i] = dprefix[i-1] + (s[i] == 'D' ? 1 : 0);
                kprefix[i] = kprefix[i-1] + (s[i] == 'K' ? 1 : 0);
            }

            Map<Pair, Integer> ratioToIdx = new HashMap<>();
            Pair init = new Pair(dprefix[0], kprefix[0]);
            ratioToIdx.put(init, 0);

            // dp[i] is the best answer for the prefix dp[0..i]
            int[] dp = new int[n];
            dp[0] = 1;
            pw.print(1 + " ");

            // maybe remember the ratio for each s[0..i].
            // when we encounter some ratio, we look up a map for the last time that ratio occurred.

            for (int i = 1; i < n; i++) {
                // Overall ratio is dprefix[i] : kprefix[i].
                // As long as the first/last subsegment has the same ratio, we're guaranteed the rest does as well
                // Thus, the O(n^2) solution is clear.
                //   - compute the ratio D : K for the string
                //   - greedily take characters while that ratio is maintained.
                //
                // We need to make it faster...
                //
                // If we take the first/last segment, dp could be used to remember the answer to the rest.
                // So now the matter is: how do we find the last segment that works?
                int D = dprefix[i];
                int K = kprefix[i];
                if (D == 0 || K == 0) {
                    dp[i] = i+1;
                    pw.print(i+1 + " ");
                    continue;
                }
                int g = gcd(D, K);
                Pair curr = new Pair(D / g, K / g);
                if (g == 1) {
                    dp[i] = 1;
                    pw.print("1 ");
                    ratioToIdx.put(curr, i);
                    continue;
                }
                
                if (!ratioToIdx.containsKey(curr)) {
                    dp[i] = 1;
                    pw.print("1 ");
                    ratioToIdx.put(curr, i);
                    continue;
                } else {
                    int lastSeen = ratioToIdx.get(curr);
                    dp[i] = dp[lastSeen] + 1;
                    pw.print(dp[i] + " ");
                    ratioToIdx.put(curr, i);
                    continue;
                }
            }
            pw.println();
        }
    }
    // IMPORTANT
    // DID YOU CHECK THE COMMON MISTAKES ABOVE?

    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    // Template code below

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pw = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException {
        Main m = new Main();
        m.solve();
        m.close();
    }

    void close() throws IOException {
        pw.flush();
        pw.close();
        br.close();
    }

    int ri() throws IOException {
        return Integer.parseInt(br.readLine().trim());
    }

    long rl() throws IOException {
        return Long.parseLong(br.readLine().trim());
    }

    int[] ril(int n) throws IOException {
        int[] nums = new int[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            int x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    long[] rll(int n) throws IOException {
        long[] nums = new long[n];
        int c = 0;
        for (int i = 0; i < n; i++) {
            int sign = 1;
            c = br.read();
            long x = 0;
            if (c == '-') {
                sign = -1;
                c = br.read();
            }
            while (c >= '0' && c <= '9') {
                x = x * 10 + c - '0';
                c = br.read();
            }
            nums[i] = x * sign;
        }
        while (c != '\n' && c != -1) c = br.read();
        return nums;
    }

    int[] rkil() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return ril(x);
    }

    long[] rkll() throws IOException {
        int sign = 1;
        int c = br.read();
        int x = 0;
        if (c == '-') {
            sign = -1;
            c = br.read();
        }
        while (c >= '0' && c <= '9') {
            x = x * 10 + c - '0';
            c = br.read();
        }
        return rll(x);
    }

    char[] rs() throws IOException {
        return br.readLine().toCharArray();
    }

    void sort(int[] A) {
        Random r = new Random();
        for (int i = A.length-1; i > 0; i--) {
            int j = r.nextInt(i+1);
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
        Arrays.sort(A);
    }

    void printDouble(double d) {
        pw.printf("%.16f", d);
    }
}

class Pair {
    int fst;
    int snd;
    Pair(int f, int s) {
        fst = f;
        snd = s;
    }
    public int hashCode() {
        return fst * 9991 + snd;
    }
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return fst == p.fst && snd == p.snd;
    }
}