import java.io.*;
import java.util.StringTokenizer;

public class Solution {
    static final int INF = (int) 1e9;
    static final int mod = (int) (1e9 + 7);
    static final int UNCALC = -1;
    static final double EPS = 1e-9;
    static int[] onecnt;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int[] a = sc.nextIntArray(n);
        onecnt = new int[n];
        for (int i = 0; i < n; i++) {
            if (a[i] == 1) onecnt[i]++;
        }
        for (int i = 1; i < n; i++) {
            onecnt[i] += onecnt[i - 1];
        }
        int lo = 0;
        int hi = n;
        int ans = 0;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;
            if (check(a, mid)) {
                ans = mid;
                lo = mid + 1;
            } else
                hi = mid - 1;
        }
        out.println(ans % 2 == 0 ? ans : ans - 1);
        out.flush();
        out.close();
    }

    static boolean check(int[] a, int len) {
        if (len <= 1) return true;
        if (len % 2 != 0) len--;
        int n = a.length;
        boolean f = false;
        int mid = len / 2;
        for (int i = 0; i < n && !f; i++) {
            int j = i + len - 1;
            if (j >= n) break;
            if (getOne(i, i + mid - 1) == mid && getOne(i + mid, j) == 0) f = true;
        }
        return f;
    }

    static int getOne(int i, int j) {
        return onecnt[j] - (i > 0 ? onecnt[i - 1] : 0);
    }


    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream system) {
            br = new BufferedReader(new InputStreamReader(system));
        }

        public Scanner(String file) throws Exception {
            br = new BufferedReader(new FileReader(file));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public char nextChar() throws IOException {
            return next().charAt(0);
        }

        public Long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public boolean ready() throws IOException {
            return br.ready();
        }

        public void waitForInput() throws InterruptedException {
            Thread.sleep(3000);
        }

        public int[] nextIntArray(int n) throws IOException {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public double[] nextDoubleArray(int n) throws IOException {
            double[] ans = new double[n];
            for (int i = 0; i < n; i++)
                ans[i] = nextDouble();
            return ans;
        }

    }
}