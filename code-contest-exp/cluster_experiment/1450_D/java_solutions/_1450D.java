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
            while (left <= right) {
                if (arr[left] == current && arr[right] == current) {
                    break;
                } else if (arr[left] == current) {
                    current++;
                    left++;
                } else if (arr[right] == current) {
                    current++;
                    right--;
                } else {
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
