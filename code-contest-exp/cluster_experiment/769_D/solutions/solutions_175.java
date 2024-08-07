import java.io.*;
import java.util.*;

public class D {

    MyScanner in;
    PrintWriter out;

    public static void main(String[] args) throws Exception {
        new D().run();
    }

    public void run() throws Exception {
        in = new MyScanner();
        out = new PrintWriter(System.out);

        solve();

        out.close();
    }

    ArrayList<Integer> maskList;

    void genMasksRec(int current, int k, int i, int maxDepth, int depth) {
        if (depth == maxDepth) {
            if (k == i) {
                maskList.add(current);
            }
        } else {
            genMasksRec(2 * current, k, i, maxDepth, depth + 1);
            genMasksRec(2 * current + 1, k, i + 1, maxDepth, depth + 1);
        }
    }

    void genMasks(int k, int m) {
        maskList = new ArrayList<Integer>();
        genMasksRec(0, k, 0, Integer.toBinaryString(m).length(), 0);
    }

    public void solve() throws Exception {
        int n = in.nextInt();
        int k = in.nextInt();

        int[] a = new int[n];
        int m = 0;
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            m = Math.max(m, a[i]);
        }

        genMasks(k, m);
         //for (int mask : maskList) { System.err.println(mask); } System.err.println(maskList.size());
        int[] b = new int[m + 1];
        for (int i = 0; i < n; i++) {
            b[a[i]]++;
        }

        long ans = 0;
        for (int i = 0; i <= m; i++) {
            for (int mask : maskList) {
                int i2 = i ^ mask;
                if (i2 <= m) {
                    ans += (i != i2) ? (1L * b[i] * b[i2]) : (1L * b[i] * (b[i] - 1));
                }
            }
        }

        out.println(ans / 2);
    }

    class MyScanner {

        BufferedReader br;
        StringTokenizer st;

        public MyScanner() throws Exception {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws Exception {
            while ((st == null) || (!st.hasMoreTokens())) {
                String t = br.readLine();
                if (t == null) {
                    return null;
                }
                st = new StringTokenizer(t);
            }
            return st.nextToken();
        }

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        double nextDouble() throws Exception {
            return Double.parseDouble(next());
        }

        boolean nextBoolean() throws Exception {
            return Boolean.parseBoolean(next());
        }

        long nextLong() throws Exception {
            return Long.parseLong(next());
        }
    }
}
