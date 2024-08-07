// package codeforce.Training1900;

import java.io.PrintWriter;
import java.util.*;

public class MovingPoints {

    // well this segment tree only can do range query and point update, so if you want range update, please go to lazy segment tree
    static class SegTree {
        private int N;

        // Let UNIQUE be a value which does NOT
        // and will not appear in the segment tree
        private long UNIQUE = 0;

        // Segment tree values
        private long[] tree;

        public SegTree(int size) {
            tree = new long[2 * (N = size)];
            java.util.Arrays.fill(tree, UNIQUE);
        }

        public SegTree(long[] values) {
            this(values.length);
            for (int i = 0; i < N; i++) modify(i, values[i]);
        }

        // This is the segment tree function we are using for queries.
        // The function must be an associative function, meaning
        // the following property must hold: f(f(a,b),c) = f(a,f(b,c)).
        // Common associative functions used with segment trees
        // include: min, max, sum, product, GCD, and etc...
        private long function(long a, long b) {
            if (a == UNIQUE) return b;
            else if (b == UNIQUE) return a;

            return a + b; // sum over a range
            //return (a > b) ? a : b; // maximum value over a range
            //return (a < b) ? a : b; // minimum value over a range
            // return a * b; // product over a range (watch out for overflow!)
        }

        // Adjust point i by a value, O(log(n))
        public void modify(int i, long value) {
            //tree[i + N] = function(tree[i + N], value);
            tree[i + N] = value;

            for (i += N; i > 1; i >>= 1) {
                tree[i >> 1] = function(tree[i], tree[i ^ 1]);
            }

        }

        // Query interval [l, r), O(log(n)) ----> notice the exclusion of r
        public long query(int l, int r) {
            long res = UNIQUE;
            for (l += N, r += N; l < r; l >>= 1, r >>= 1) {
                if ((l & 1) != 0) res = function(res, tree[l++]);
                if ((r & 1) != 0) res = function(res, tree[--r]);
            }
            if (res == UNIQUE) {
                //throw new IllegalStateException("UNIQUE should not be the return value.");
                return 0;
            }
            return res;
        }


    }

    //    MUST SEE BEFORE SUBMISSION
//    check whether int part would overflow or not, especially when it is a * b!!!!

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
//        int t = sc.nextInt();
        int t = 1;
        for (int i = 0; i < t; i++) {
            solve(sc, pw);
        }
        pw.close();
    }
    static long maxRight = Integer.MAX_VALUE;
    static void solve(Scanner in, PrintWriter out){
        int n = in.nextInt();
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            arr[i][1] = in.nextInt();
        }
        LinkedList<int[]> rev = new LinkedList<>();
        LinkedList<int[]> ok = new LinkedList<>();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i][1] < 0){
                rev.add(new int[]{-arr[i][0], -arr[i][1]});
            }else{
                ok.add(arr[i]);
            }
        }
        ans = sameDirection(rev) + sameDirection(ok);
        LinkedList<int[]> qs = new LinkedList<>();
        LinkedList<int[]> ql = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (arr[i][1] < 0){
                qs.add(arr[i]);
            }else{
                ql.add(arr[i]);
            }
        }
        ans += differenDirection(qs, ql);
        out.println(ans);
    }
    static long sameDirection(LinkedList<int[]> q){
        long ans = 0;
        int n = q.size();
        SegTree sg = new SegTree(n + 5);
        SegTree ct = new SegTree(n + 5);
        List<int[]> qq = new ArrayList<>();
        Collections.sort(q, (a, b) -> (a[0] - b[0]));
        for (int i = 0; i < n; i++) {
            int[] fk = q.poll();
            qq.add(new int[]{fk[0], fk[1], i});
        }
        Collections.sort(qq, (a, b) -> (a[1] - b[1]));
        for(int[] arr : qq){
            int pos = arr[0], idx = arr[2];
            long get = sg.query(0, idx);
            long tot = ct.query(0, idx);
            ans += (get - (maxRight - pos) * tot);
            sg.modify(idx, maxRight - pos);
            ct.modify(idx, 1);
        }
        return ans;
    }


    static long differenDirection(LinkedList<int[]> qs, LinkedList<int[]> ql){
        Collections.sort(qs, (a, b) -> (a[0] - b[0]));
        Collections.sort(ql, (a, b) -> (a[0] - b[0]));
        long ans = 0;
        int cnt = 0;
        long pre = 0;
        while (ql.size() > 0){
            int[] shit = ql.poll();
            int pos = shit[0];
            while (qs.size() > 0 && qs.peek()[0] < pos){
                int[] get = qs.poll();
                cnt++;
                pre += (maxRight - get[0]);
            }
            ans += (pre - cnt * (maxRight - pos));
        }
        return ans;
    }
}
