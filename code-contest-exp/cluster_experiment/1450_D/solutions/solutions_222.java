import java.util.*;
import java.io.*;

public class _1450D {
    static int[] MODS = {1000000007, 998244353, 1000000009};
    static int MOD = MODS[0];

    public static void main(String[] args) {
        sc = new MyScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            scan(arr);
            int[] ans = new int[n + 1];
            HashSet<Integer> set = new HashSet<>();
            for (int x : arr) {
                set.add(x);
            }
            if (set.size() == n) {
                ans[1] = 1;
            }
            if (set.contains(1)) {
                ans[n] = 1;
            }
            int left = 0;
            int right = n-1;
            int current = 1;
            SegmentTreeRMQ tree = new SegmentTreeRMQ(n, arr);
            while (left <= right) {
                if (arr[left] == current) {
                    current++;
                    left++;
                } else if (arr[right] == current) {
                    current++;
                    right--;
                } else {
                    break;
                }
                if (left <= right && tree.RMQ(left, right) != current) {
                    current--;
                    break;
                }
            }
            for (int i = n-current+1; i < n; i++) {
                ans[i] = 1;
            }
            for (int i = 1; i <= n; i++) {
                out.print(ans[i]);
            }
            out.println();
        }
        out.close();
    }

    static class SegmentTreeRMQ {
        int st[];
        int n;
        SegmentTreeRMQ(int n, int[] arr) {
            this.n = n;
            constructST(arr, n);
        }
        int minVal(int x, int y) {
            return Math.min(x, y);
        }
        int getMid(int s, int e) {
            return s + (e - s) / 2;
        }
        int RMQUtil(int ss, int se, int qs, int qe, int index) {
            if (qs <= ss && qe >= se)
                return st[index];
            if (se < qs || ss > qe)
                return Integer.MAX_VALUE;
            int mid = getMid(ss, se);
            return minVal(RMQUtil(ss, mid, qs, qe, 2 * index + 1),
                    RMQUtil(mid + 1, se, qs, qe, 2 * index + 2));
        }
        int RMQ(int qs, int qe) {
            if (qs < 0 || qe > n - 1 || qs > qe) {
                System.out.println("Invalid Input");
                return -1;
            }
            return RMQUtil(0, n - 1, qs, qe, 0);
        }
        int constructSTUtil(int arr[], int ss, int se, int si) {
            if (ss == se) {
                st[si] = arr[ss];
                return arr[ss];
            }
            int mid = getMid(ss, se);
            st[si] = minVal(constructSTUtil(arr, ss, mid, si * 2 + 1),
                    constructSTUtil(arr, mid + 1, se, si * 2 + 2));
            return st[si];
        }
        void constructST(int arr[], int n) {
            int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
            int max_size = 2 * (int) Math.pow(2, x) - 1;
            st = new int[max_size];
            constructSTUtil(arr, 0, n - 1, 0);
        }
    }

    public static int[] sort(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        Collections.sort(list);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static void scan(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
    }

    public static MyScanner sc;
    public static PrintWriter out;

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
