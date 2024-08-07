import java.io.*;
import java.util.*;

public class Main {
    static FastScanner in = new FastScanner();

    public static void main(String[] args) throws IOException {
        int n = in.nextInt();
        point[] a = new point[n];
        for (int i = 0; i < n; i++) {
            a[i] = new point(in.nextInt(), 0);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            a[i].v = in.nextInt();
        }
        Arrays.sort(a, new point());
        point[] b = new point[n];
        for (int i = 0; i < n; ) {
            int j = i;
            while (i < n && a[i].v == a[j].v) {
                b[i] = new point(a[i].x, j);
                i++;
            }
        }
        Arrays.sort(b, new point1());
        segment_tree st = new segment_tree(n, new int[n]);
        for (int i = 0; i < n; i++) {
            ans += st.get(1, 0, n - 1, 0, b[i].v) - st.get_cnt(1, 0, n - 1, 0, b[i].v) *  b[i].x;
            st.set(1, 0, n - 1, b[i].v, b[i].x);
        }
        in.out.println(-ans);
        in.out.close();
    }
}

class point implements Comparator<point> {
    int x, v;

    public point(int x, int v) {
        this.x = x;
        this.v = v;
    }

    public point() {
    }

    @Override
    public int compare(point o1, point o2) {
        return Integer.compare(o1.v, o2.v);
    }
}

class point1 implements Comparator<point> {

    @Override
    public int compare(point o1, point o2) {
        return Integer.compare(o1.x, o2.x);
    }
}


class segment_tree {
    int n;
    int[] a;
    long[] sum;
    int[] cnt;

    public segment_tree(int n, int[] a) {
        this.n = n;
        this.a = a;
        sum = new long[n * 4];
        cnt = new int[n * 4];
    }


    void set(int v, int tl, int tr, int ind, int val) {
        if (tl > ind || tr < ind) return;
        if (tl == tr) {
            sum[v] += val;
            cnt[v]++;
            return;
        }
        int tm = (tl + tr) >> 1;
        set((v << 1), tl, tm, ind, val);
        set((v << 1) | 1, tm + 1, tr, ind, val);
        sum[v] = sum[v << 1] + sum[(v << 1) | 1];
        cnt[v] = cnt[v << 1] + cnt[(v << 1) | 1];
    }

    long get(int v, int tl, int tr, int l, int r) {
        if (r < tl || l > tr) return 0;
        if (l <= tl && tr <= r) {
            return sum[v];
        }
        int tm = (tl + tr) >> 1;
        return get(v << 1, tl, tm, l, r) + get((v << 1) | 1, tm + 1, tr, l, r);
    }

    long get_cnt(int v, int tl, int tr, int l, int r) {
        if (r < tl || l > tr) return 0;
        if (l <= tl && tr <= r) {
            return cnt[v];
        }
        int tm = (tl + tr) >> 1;
        return get_cnt(v << 1, tl, tm, l, r) + get_cnt((v << 1) | 1, tm + 1, tr, l, r);
    }
}


class FastScanner {
    BufferedReader br;
    StringTokenizer st = new StringTokenizer("");
    PrintWriter out;

    FastScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
    }

    FastScanner(String in, String out_) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(in));
        out = new PrintWriter(out_);
    }

    String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}