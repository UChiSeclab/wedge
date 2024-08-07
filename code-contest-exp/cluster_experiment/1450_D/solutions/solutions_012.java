import java.util.*;
import java.io.*;
public class A {

















    static boolean tests = true;
    static class SegmentTree{
        private Pair[] tree;
        private int n, input[];
        public SegmentTree(int[] input){
            this.n = input.length;
            int[] temp = new int[n+1];
            for (int i = 1; i <= n; ++i){
                temp[i] = input[i-1];
            }
            this.input = temp;
            tree = new Pair[4*n+5];
            build(1, 1, n);
        }
        int l(int u){
            return 2*u;
        }
        int r(int u){
            return l(u)+1;
        }
        void build(int u, int left, int right){
            tree[u] = new Pair(left, right);
            if (left == right){
                tree[u].min = input[left];
                tree[u].max = input[left];
                tree[u].sum = input[left];
                return;
            }
            int mid = (left+right)/2;
            build(l(u), left, mid);
            build(r(u), mid+1, right);
            tree[u].min = Math.min(tree[l(u)].min, tree[r(u)].min);
            tree[u].max = Math.max(tree[l(u)].max, tree[r(u)].max);
            tree[u].sum = tree[l(u)].sum+tree[r(u)].sum;
        }
        void prop(int u){
            if (tree[u].left != tree[u].right){
                tree[l(u)].delta += tree[u].delta;
                tree[r(u)].delta += tree[u].delta;
                tree[u].min += tree[u].delta;
                tree[u].max += tree[u].delta;
                tree[u].delta = 0;
            }
        }
        void update(int u){
            if (tree[u].left != tree[u].right){
                tree[u].min = tree[l(u)].min+tree[l(u)].delta;
                tree[u].min = Math.min(tree[u].min,tree[r(u)].min+tree[r(u)].delta);
                tree[u].max = tree[l(u)].max+tree[l(u)].delta;
                tree[u].max = Math.max(tree[u].max,tree[r(u)].max+tree[r(u)].delta);
                tree[u].sum = tree[l(u)].sum+(tree[l(u)].right-tree[l(u)].left+1)*tree[l(u)].delta;
                tree[u].sum += tree[r(u)].sum+(tree[r(u)].right-tree[r(u)].left+1)*tree[r(u)].delta;
            }
        }
        int rangeMin(int u, int left, int right){
            if (tree[u].right < left || tree[u].left > right){
                return (int)1e9;
            }
            if (tree[u].left >= left && tree[u].right <= right){
                return tree[u].min+tree[u].delta;
            }
            prop(u);
            int min = rangeMin(l(u), left, right);
            min = Math.min(min, rangeMin(r(u), left, right));
            update(u);
            return min;
        }
        int rangeMax(int u, int left, int right){
            if (tree[u].right < left || tree[u].left > right){
                return (int)-1e9;
            }
            if (tree[u].left >= left && tree[u].right <= right){
                return tree[u].max+tree[u].delta;
            }
            prop(u);
            int max = rangeMax(l(u), left, right);
            max = Math.max(max, rangeMax(r(u), left, right));
            update(u);
            return max;
        }
        long rangeSum(int u, int left, int right){
            if (tree[u].right < left || tree[u].left > right){
                return 0L;
            }
            if (tree[u].left >= left && tree[u].right <= right){
                return tree[u].sum+(tree[u].right-tree[u].left+1)*tree[u].delta;
            }
            prop(u);
            long sum = rangeSum(l(u), left, right);
            sum += rangeSum(r(u), left, right);
            update(u);
            return sum;
        }
        void rangeAdd(int u, int left, int right, int add){
            if (tree[u].right < left || tree[u].left > right){
                return;
            }
            if (tree[u].left >= left && tree[u].right <= right){
                tree[u].delta += add;
                return;
            }
            prop(u);
            rangeAdd(l(u), left, right, add);
            rangeAdd(r(u), left, right, add);
            update(u);
        }
        private class Pair{
            int min, max, left, right;
            long sum;
            int delta = 0;
            public Pair(int l, int r){
                this.left = l;
                this.right = r;
                sum = 0L;
                min = (int)1e9;
                max = -min;
            }
        }
    }
    static PrintWriter out = new PrintWriter(System.out);
    static void solve(){
       int n = fs.nextInt();
       int[] a = fs.readIntArray(n);
       int left = 0, right = n-1;
       int[] b = new int[n];
       for (int i = 0; i < n; ++i){
           b[i] = a[i];
       }
       Arrays.sort(b);
       String ans = "1";
       for (int i = 0; i < n-1; ++i){
           if (b[i+1]-b[i] != 1){
               ans = "0";
               break;
           }
       }
       SegmentTree st = new SegmentTree(a);
       int ind = int_max;
       if (st.rangeMin(1, 1, n) == 1){
           for (int x = 1; x < n; ++x){
               int l = left, r = right;
               if (a[left] == x) ++left;
               else if (a[right] == x) --right;
               if (l == left && r == right){
                   ind = x;
                   break;
               }
               if (st.rangeMin(1, left+1, right+1) != x+1){
                   ind = x;
                   break;
               }
           }
       }else ind = 0;
       out.print(ans);
       int cnt = 1;
       for (int i = 2; i <= n-ind; ++i){
           ++cnt;
           out.print("0");
       }
      while (cnt < n){
          out.print("1");
          ++cnt;
      }
      out.println();
    }
























    static FastScanner fs = new FastScanner();
    static int int_max = (int)1e9+5;
    public static void main(String[] args) {
        int t = 1;
        if (tests) t = fs.nextInt();
        while (t-- > 0) solve();
        out.close();
    }
    static class FastScanner {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer("");
        String next() {
            while (!st.hasMoreTokens())
                try {
                    st=new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
        int[] readIntArray(int n) {
            int[] a=new int[n];
            for (int i=0; i<n; i++) a[i]=nextInt();
            return a;
        }
        long[] readLongArray(int n){
            long a[] = new long[n];
            for (int i = 0; i < n; ++i){
                a[i] = nextLong();
            }
            return a;
        }
        long nextLong() {
            return Long.parseLong(next());
        }
        double nextDouble(){
            return Double.parseDouble(next());
        }
    }
}