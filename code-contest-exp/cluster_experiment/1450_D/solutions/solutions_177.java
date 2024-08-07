import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner();
        PrintWriter out = new PrintWriter(System.out);
        int tc = sc.nextInt();
        main:
        while (tc-- > 0) {
            int n = sc.nextInt();
            int[] a = sc.nextIntArray(n);
            int[] left = new int[n];
            int[] right = new int[n];
            TreeMap<Integer, Integer> map = new TreeMap<>();
            int dup = n + 1;
            for (int i = n - 1; i >= 0; i--) {
                int cur = a[i];
                Integer g = map.lowerKey(cur);
                right[i] = g != null ? map.get(g) : n;
                if (map.containsKey(cur)) dup = Math.min(cur, dup);
                map.put(cur, i);
            }
            map.clear();
            HashSet<Integer> valid = new HashSet<>();
            for (int i = 0; i < n; i++) {
                int cur = a[i];
                Integer g = map.lowerKey(cur);
                left[i] = g != null ? map.get(g) : -1;
                map.put(cur, i);
                int dist = right[i] - left[i] - 1;
                if (dist >= n - cur + 1) valid.add(cur);
            }
            int[] ans = new int[n];
            for (int x = 1; x <= Math.min(dup, n); x++) {
                if (valid.contains(x)) ans[n - x] = 1;
                else break;
            }
            if (dup > n) ans[0] = 1;
            for (int x : ans)
                out.print(x);
            out.println();
        }
        out.flush();
        out.close();
    }


    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public Scanner(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(s));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public int[] nextIntArray(int n) throws IOException {
            int[] ans = new int[n];
            for (int i = 0; i < n; i++)
                ans[i] = nextInt();
            return ans;
        }

        public Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] ans = new Integer[n];
            for (int i = 0; i < n; i++)
                ans[i] = nextInt();
            return ans;
        }

        public char nextChar() throws IOException {
            return next().charAt(0);
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public boolean ready() throws IOException {
            return br.ready();
        }


    }
}