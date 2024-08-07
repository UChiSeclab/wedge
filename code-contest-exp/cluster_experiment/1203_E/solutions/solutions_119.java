import java.io.*;
import java.util.*;

public class Main {
    static final int MAX = 150001;
    static boolean[] exist = new boolean[MAX + 2];
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        
        int n = sc.nextInt();
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; ++i)
            arr[i] = sc.nextInt();
        Arrays.sort(arr);
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            if (arr[i] - 1 > 0)
                if (!exist[arr[i] - 1]) {
                    exist[arr[i] - 1] = true;
                    ans++;
                    continue;
                }
            if (!exist[arr[i]]) {
                exist[arr[i]] = true;
                ans++;
                continue;
            }
            if (arr[i] + 1 <= MAX)
                if (!exist[arr[i] + 1]) {
                    exist[arr[i] + 1] = true;
                    ans++;
                }
        }
        out.println(ans);

        out.close();
        out.flush();
    }
    
    static class Scanner {
        StringTokenizer st;
        BufferedReader br;
        
        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }
        
        public Scanner(FileReader f) {
            br = new BufferedReader(f);
        }
        
        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
        
        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        
        public String nextLine() throws IOException {
            return br.readLine();
        }
        
        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }
        
        public boolean ready() throws IOException {
            return br.ready();
        }
        
    }
}