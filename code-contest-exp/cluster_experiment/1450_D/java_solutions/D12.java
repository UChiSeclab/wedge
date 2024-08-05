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
            Set<Integer> set = new HashSet<>();
            for (int i = 1; i <= n; i++) {
                a[i] = sc.nextInt();
                set.add(a[i]);
            }

            int left = 1; int right = n;
            int [] res = new int[n + 1];
            int cur = n;
            while (left < right) {
                if (a[left] == n + 1 - cur && a[right] != a[left]) {
                    res[cur] = 1;
                    cur--;
                    left++;
                    continue;
                }
                if (a[right] == n + 1 - cur && a[right] != a[left]) {
                    res[cur] = 1;
                    cur--;
                    right--;
                    continue;
                }
                boolean ok = false;
                for (int i = left; i <= right; i++) {
                    if (a[i] == n + 1 - cur) ok = true;
                }
                if (ok) res[cur] = 1;
                break;
            }
            if (set.size() == n) res[1] = 1;
            for (int i = 1; i <= n; i++) out.print(res[i]);
            out.println();
        }
        out.close();
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