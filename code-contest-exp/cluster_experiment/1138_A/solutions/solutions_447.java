import java.io.BufferedReader;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.fill;
import static java.util.Arrays.sort;
import static java.util.Collections.reverse;
import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;

public class Main {
    FastScanner in;
    PrintWriter out;

    private void solve() throws IOException {
        solveA();
    }

    private void solveA() throws IOException {
        int n = in.nextInt();
        int[] t = new int[n];
        for (int i = 0; i < n; i++)
            t[i] = in.nextInt();
        int ans = 0;
        for (int l = 0, m = 0, r; l < n; l = m) {
            while (m < n && t[l] == t[m])
                m++;
            r = m;
            while (r < n && t[m] == t[r])
                r++;
            ans = max(ans, min(m - l, r - m));
        }
        out.println(ans * 2);
    }

    private void solveB() throws IOException {
        int n = in.nextInt();
        char[] c = in.next().toCharArray();
        for (int i = 0; i < n; i++)
            c[i] -= '0';
        char[] a = in.next().toCharArray();
        for (int i = 0; i < n; i++)
            a[i] -= '0';

        int[][] q = new int[4][n];
        int[] ql = new int[4], qr = new int[4];
        for (int i = 0; i < n; i++)
            q[c[i] * 2 + a[i]][qr[c[i] * 2 + a[i]]++] = i;

        int[] ans = new int[n / 2];
        int s = 0;
        while (qr[3] - ql[3] > 1) {
            ans[s++] = q[3][ql[3]++];
            ql[3]++;
        }

        if (qr[3] - ql[3] > 0) {
            if (qr[2] - ql[2] > qr[1] - ql[1]) {
                ans[s++] = q[2][ql[2]++];
                ql[3]++;
            } else if (qr[1] - ql[1] > 0) {
                ans[s++] = q[3][ql[3]++];
                ql[1]++;
            }
        }

        while (qr[2] - ql[2] > 0 && qr[1] - ql[1] > 0) {
            ans[s++] = q[2][ql[2]++];
            ql[1]++;
        }

        while (qr[1] - ql[1] > 0 && qr[0] - ql[0] > 0) {
            ans[s++] = q[1][ql[1]++];
            ql[0]++;
        }

        while (qr[0] - ql[0] > 0 && qr[2] - ql[2] > 0) {
            ans[s++] = q[0][ql[0]++];
            ql[2]++;
        }

        if (s < n / 2)
            out.print(-1);
        else
            for (int i = 0; i < s; i++)
                out.print(ans[i] + 1 + " ");
    }

    private void solveC() throws IOException {
        int n = in.nextInt(), m = in.nextInt();
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                a[i][j] = in.nextInt();

        int[][] as = new int[n][m];
        for (int i = 0; i < n; i++) {
            System.arraycopy(a[i], 0, as[i], 0, m);
            shuffle(as[i]);
            sort(as[i]);
        }

        int[][] ars = new int[m][n];
        int[][] mrs = new int[m][n];
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++)
                ars[j][i] = a[i][j];
            shuffle(ars[j]);
            sort(ars[j]);
            for (int i = 1; i < n; i++)
                mrs[j][i] = ars[j][i - 1] == ars[j][i] ? mrs[j][i - 1] : mrs[j][i - 1] + 1;
        }

        int[] ms = new int[m];
        for (int i = 0, pi, pj, mx; i < n; i++) {
            for (int j = 1; j < m; j++)
                ms[j] = as[i][j - 1] == as[i][j] ? ms[j - 1] : ms[j - 1] + 1;
            for (int j = 0; j < m; j++) {
                pj = Arrays.binarySearch(as[i], a[i][j]);
                pi = Arrays.binarySearch(ars[j], a[i][j]);
                mx = max(ms[pj], mrs[j][pi]);
                out.print(max(ms[m - 1] + 1 + mx - ms[pj], mrs[j][n - 1] + 1 + mx - mrs[j][pi]) + " ");
            }
            out.println();
        }
    }

    private void solveD() throws IOException {
        String s = in.next();
        int[] sCnt = new int[2];
        for (int i = 0; i < s.length(); i++)
            sCnt[s.charAt(i) - '0']++;

        String t = in.next();
        int m = t.length();
        int[] tCnt = new int[2];
        for (int i = 0; i < m; i++)
            tCnt[t.charAt(i) - '0']++;

        int pi[] = new int[m];
        for (int i = 1, j = 0; i < m; i++) {
            while (j > 0 && t.charAt(i) != t.charAt(j))
                j = pi[j - 1];
            if (t.charAt(i) == t.charAt(j))
                j++;
            pi[i] = j;
        }

        if (sCnt[0] >= tCnt[0] && sCnt[1] >= tCnt[1]) {
            out.print(t);
            sCnt[0] -= tCnt[0];
            sCnt[1] -= tCnt[1];
            fill(tCnt, 0);
            for (int i = pi[m - 1]; i < m; i++)
                tCnt[t.charAt(i) - '0']++;
            t = t.substring(pi[m - 1]);
            int min = min(tCnt[0] == 0 ? 1000000 : sCnt[0] / tCnt[0], tCnt[1] == 0 ? 1000000 : sCnt[1] / tCnt[1]);
            for (int i = min; i > 0; i--)
                out.print(t);
            for (int i = sCnt[0] - min * tCnt[0]; i > 0; i--)
                out.print('0');
            for (int i = sCnt[1] - min * tCnt[1]; i > 0; i--)
                out.print('1');
            out.println();
        } else {
            out.println(s);
        }
    }

    private void solveF() throws IOException{

    }

    private void shuffle(int[] a) {
        int b;
        Random r = new Random();
        for (int i = a.length - 1, j; i > 0; i--) {
            j = r.nextInt(i + 1);
            b = a[j];
            a[j] = a[i];
            a[i] = b;
        }
    }

    private class FastScanner {
        StringTokenizer st;
        BufferedReader br;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        boolean hasNext() throws IOException {
            return br.ready() || (st != null && st.hasMoreTokens());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        String nextLine() throws IOException {
            return br.readLine();
        }

        boolean hasNextLine() throws IOException {
            return br.ready();
        }

    }

    private void run() throws IOException {
        in = new FastScanner(System.in); // new FastScanner(new FileInputStream(".in"));
        out = new PrintWriter(System.out); // new PrintWriter(new FileOutputStream(".out"));

        solve();

        out.flush();
        out.close();
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}