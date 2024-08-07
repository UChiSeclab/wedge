import java.util.*;
import java.io.*;

public class D12 {
    public static void main(String[] args) {
        MyScanner sc = new MyScanner();
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int [] a = new int[n + 1];
            Set<Integer> set = new HashSet<>(); RangeTree seg = new RangeTree(n + 1);
            for (int i = 1; i <= n; i++) {
                a[i] = sc.nextInt();
                set.add(a[i]);
                seg.update(i, i, a[i]);
            }

            int left = 1; int right = n;
            int [] res = new int[n + 1];
            int cur = n;
            while (left < right) {
                int qry = (int) seg.minQuery(left, right);
                if (a[left] == n + 1 - cur && a[right] != a[left] && qry == n + 1 - cur) {
                    res[cur] = 1;
                    cur--;
                    left++;
                    continue;
                }
                if (a[right] == n + 1 - cur && a[right] != a[left] && qry == n + 1 - cur) {
                    res[cur] = 1;
                    cur--;
                    right--;
                    continue;
                }
                if (qry == n + 1 - cur) res[cur] = 1;
                break;
            }
            if (set.size() == n) res[1] = 1;
            if (set.contains(1)) res[n] = 1;
            for (int i = 1; i <= n; i++) out.print(res[i]);
            out.println();
        }
        out.close();
    }

    static class RangeTree {
        private long[] max;
        private long[] min;
        private long[] lazy;
        private long[] sum;
        private int size;
        public RangeTree(int size) {
            this.size = size;
            max = new long[4*size];
            min = new long[4*size];
            sum = new long[4*size];
            lazy = new long[4*size];
        }
        public void update(int l, int r, int inc) {
            update(1, 0, size-1, l, r, inc);
        }
        private void pushDown(int index, int l, int r) {
            min[index] += lazy[index];
            max[index] += lazy[index];
            sum[index] += lazy[index] * (r-l+1);
            if(l != r) {
                lazy[2*index] += lazy[index];
                lazy[2*index+1] += lazy[index];
            }
            lazy[index] = 0;
        }
        private void pullUp(int index, int l, int r) {
            int m = (l+r)/2;
            min[index] = Math.min(evaluateMin(2*index, l, m), evaluateMin(2*index+1, m+1, r));
            max[index] = Math.max(evaluateMax(2*index, l, m), evaluateMax(2*index+1, m+1, r));
            sum[index] = evaluateSum(2*index, l, m) + evaluateSum(2*index+1, m+1, r);
        }
        private long evaluateSum(int index, int l, int r) {
            return sum[index] + (r-l+1)*lazy[index];
        }
        private long evaluateMin(int index, int l, int r) {
            return min[index] + lazy[index];
        }
        private long evaluateMax(int index, int l, int r) {
            return max[index] + lazy[index];
        }
        private void update(int index, int l, int r, int left, int right, int inc) {
            if(r < left || l > right) return;
            if(l >= left && r <= right) {
                lazy[index] += inc;
                return;
            }
            pushDown(index, l, r);
            int m = (l+r)/2;
            update(2*index, l, m, left, right, inc);
            update(2*index+1, m+1, r, left, right, inc);
            pullUp(index, l, r);
        }
        public long minQuery(int l, int r) {
            return minQuery(1, 0, size-1, l, r);
        }
        private long minQuery(int index, int l, int r, int left, int right) {
            if(r < left || l > right) return Long.MAX_VALUE;
            if(l >= left && r <= right) {
                return evaluateMin(index, l, r);
            }
            pushDown(index, l, r);
            int m = (l+r)/2;
            long ret = Long.MAX_VALUE;
            ret = Math.min(ret, minQuery(2*index, l, m, left, right));
            ret = Math.min(ret, minQuery(2*index+1, m+1, r, left, right));
            pullUp(index, l, r);
            return ret;
        }
        public long maxQuery(int l, int r) {
            return maxQuery(1, 0, size-1, l, r);
        }
        private long maxQuery(int index, int l, int r, int left, int right) {
            if(r < left || l > right) return Long.MIN_VALUE;
            if(l >= left && r <= right) {
                return evaluateMax(index, l, r);
            }
            pushDown(index, l, r);
            int m = (l+r)/2;
            long ret = Long.MIN_VALUE;
            ret = Math.max(ret, maxQuery(2*index, l, m, left, right));
            ret = Math.max(ret, maxQuery(2*index+1, m+1, r, left, right));
            pullUp(index, l, r);
            return ret;
        }
        public long sumQuery(int l, int r) {
            return sumQuery(1, 0, size-1, l, r);
        }
        private long sumQuery(int index, int l, int r, int left, int right) {
            if(r < left || l > right) return 0;
            if(l >= left && r <= right) {
                return evaluateSum(index, l, r);
            }
            pushDown(index, l, r);
            int m = (l+r)/2;
            long ret = 0;
            ret += sumQuery(2*index, l, m, left, right);
            ret += sumQuery(2*index+1, m+1, r, left, right);
            pullUp(index, l, r);
            return ret;
        }
    }





    //-----------MyScanner class for faster input----------
    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }


    }

}