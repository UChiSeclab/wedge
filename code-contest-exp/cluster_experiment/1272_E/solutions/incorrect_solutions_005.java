import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.*;

public class Main {
    int[] a;
    long[] ans;

    long rec(int pos, int chet, boolean[] used) {
        if (pos >= a.length || pos < 0 || used[pos]) return Integer.MAX_VALUE;
        used[pos] = true;
        if (a[pos] % 2 == chet) return 1;
        ans[pos] = min(ans[pos], min(rec(pos + a[pos], chet, used), rec(pos - a[pos], chet, used)));
        return ans[pos] + 1;
    }

    void run() throws IOException {
        int n = nextInt();
        a = new int[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = nextInt();
        }
        ans = new long[n];
        Arrays.fill(ans, Integer.MAX_VALUE);
        for (int i = 0; i < a.length; i++) {
            if (ans[i] == Integer.MAX_VALUE) {
                boolean[] used = new boolean[n];
                rec(i, (a[i] % 2) ^ 1, used);
            }
        }
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] > (int) 1e8) pw.print(-1 + " ");
            else pw.print(ans[i] + " ");
        }
        pw.close();
    }

    class point implements Comparable<point> {
        int x, y;

        public point(int a, int b) {
            x = a;
            y = b;
        }

        @Override
        public int compareTo(point o) {
            if (o.y == this.y) return Integer.compare(o.x, this.x);
            return -Integer.compare(o.y, this.y);
        }

    }

    long mod = (long) 1e9 + 7;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //BufferedReader br = new BufferedReader(new FileReader("qual.in"));

    StringTokenizer st = new StringTokenizer("");
    PrintWriter pw = new PrintWriter(System.out);
    //PrintWriter pw = new PrintWriter("qual.out");

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    String next() throws IOException {
        if (!st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    public Main() throws FileNotFoundException {
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}