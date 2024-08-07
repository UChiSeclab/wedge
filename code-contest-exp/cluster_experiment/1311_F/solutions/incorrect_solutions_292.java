import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        //br = new BufferedReader(new FileReader("monument.in"));
        pw = new PrintWriter(System.out);
        //pw = new PrintWriter("monument.out");
        new Main().run();
    }

    long[] put;
    int[] not_zero;

    void add(long x, int pos) {
        put[pos] = x;
        not_zero[pos] = 1;
        pos /= 2;
        while (pos > 0) {
            not_zero[pos] = not_zero[pos * 2] + not_zero[pos * 2 + 1];
            put[pos] = put[pos * 2] + put[pos * 2 + 1];
            pos /= 2;
        }
      }

    pair find_sum(int l, int r, int v, int vl, int vr) {
        if (vr < l || r < vl) return new pair(0, 0);
        if (l <= vl && vr <= r) return new pair(put[v], not_zero[v]);
        int m = (vl + vr) / 2;
        pair a = find_sum(l, r, v * 2, vl, m);
        pair b = find_sum(l, r, v * 2 + 1, m + 1, vr);
        return new pair(a.x + b.x, a.num + b.num);
    }

    void run() throws IOException {
        int n = nextInt();
        pair[] a = new pair[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = new pair(nextInt(), i);
        }
        Arrays.sort(a);
        long[] v = new long[n];
        for (int i = 0; i < v.length; i++) {
            v[i] = nextInt();
        }
        Arrays.sort(v);
        HashMap<Long, Stack<Integer>> tm = new HashMap<>();
        for (int i = 0; i < v.length; i++) {
            if (!tm.containsKey(v[i])) {
                tm.put(v[i], new Stack<>());
            }
            tm.get(v[i]).add(i);
        }
        int x = 1;
        while (x < n) x *= 2;
        put = new long[x * 2];
        not_zero = new int[x * 2];
        long ans = 0;
        for (int i = 0; i < a.length; i++) {
            long sp = v[a[i].num];
            int pos_in_put = tm.get(sp).pop();
            add(a[i].x, pos_in_put + x);
            int l = -1;
            int r = n;
            while (l + 1 < r) {
                int m = (l + r) / 2;
                if (v[m] > sp) r = m;
                else l = m;
            }
            pair k = find_sum(0, l, 1, 0, x - 1);
            ans += a[i].x * k.num - k.x;
        }
        pw.print(ans);
        pw.close();
    }

    class pair implements Comparable<pair> {
        long x;
        int num;

        public pair(long x, int num) {
            this.x = x;
            this.num = num;
        }

        @Override
        public int compareTo(pair o) {
            return -Long.compare(o.x, this.x);
        }
    }

    static BufferedReader br;
    static StringTokenizer st = new StringTokenizer("");
    static PrintWriter pw = new PrintWriter(System.out);

    public static int nextInt() throws IOException {
        if (!st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return Integer.parseInt(st.nextToken());
    }

    public static String next() throws IOException {
        if (!st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    public static long nextLong() throws IOException {
        if (!st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return Long.parseLong(st.nextToken());
    }

    public static double nextDouble() throws IOException {
        if (!st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return Double.parseDouble(st.nextToken());
    }

}