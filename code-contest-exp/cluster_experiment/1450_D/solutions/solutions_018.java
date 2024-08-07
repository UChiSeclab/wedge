import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MOD = 1000000007;

    // After writing solution, quick scan for:
    //   array out of bounds
    //   special cases e.g. n=1?
    //
    // Big numbers arithmetic bugs:
    //   int overflow
    //   sorting, or taking max, after MOD
    void solve() throws IOException {
        int T = ri();
        for (int Ti = 0; Ti < T; Ti++) {
            int n = ri();
            int[] a = ril(n);

            Map<Integer, List<Integer>> numToIdx = new HashMap<>();
            for (int i = 0; i < a.length; i++) {
                if (!numToIdx.containsKey(a[i])) numToIdx.put(a[i], new ArrayList<>());
                numToIdx.get(a[i]).add(i);
            }

            int[] prev = new int[n];
            prev[0] = -1;
            for (int i = 1; i < n; i++) {
                int j = i-1;
                while (j >= 0 && a[j] >= a[i]) j = prev[j];
                prev[i] = j;
            }
            int[] next = new int[n];
            next[n-1] = n;
            for (int i = n-2; i >= 0; i--) {
                int j = i+1;
                while (j < n && a[j] >= a[i]) j = next[j];
                next[i] = j;
            }
            int[] len = new int[n];
            for (int i = 0; i < n; i++) {
                len[i] = (i - prev[i]) + (next[i] - i) - 1;
            }

            TreeMap<Integer, Integer> lens = new TreeMap<>();
            for (int i = 1; i <= n; i++) {
                if (!numToIdx.containsKey(i)) {
                    numToIdx.put(i, null);
                    lens.put(0, lens.getOrDefault(0, 0) + 1);
                }
            }
            for (int l : len) lens.put(l, lens.getOrDefault(l, 0) + 1);

            if (lens.firstKey() >= 1) pw.print("1");
            else pw.print("0");

            int toKill = n;
            for (int k = 2; k <= n; k++) {
                List<Integer> idx = numToIdx.get(toKill);
                if (idx == null) {
                    lens.put(0, lens.get(0) - 1);
                    if (lens.get(0) == 0) lens.remove(0);
                } else {
                    for (int i : idx) {
                        int l = len[i];
                        lens.put(l, lens.get(l) - 1);
                        if (lens.get(l) == 0) lens.remove(l);
                    }
                }
                toKill--;
                if (lens.firstKey() >= k) pw.print("1");
                else pw.print("0");
            }
            pw.println();
        }
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