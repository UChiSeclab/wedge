import java.util.*;
import java.io.*;

public class Boxers {
    public static void main(String[] args) {
        FastScanner scanner = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        int n = scanner.nextInt();
        ArrayList<Integer> arr = new ArrayList<>();
        for(int i = 0; i < n; i++) arr.add(scanner.nextInt());
        Collections.sort(arr, Collections.reverseOrder());
        boolean[] used = new boolean[150002];
        int ans = 0;
        for(int i = 0; i < n; i++) {
            int cur = arr.get(i);
            if (!used[cur+1]) {
                used[cur+1] = true;
                ans++;
            }
            else if (!used[cur]) {
                used[cur] = true;
                ans++;
            }
            else if (cur > 1 && !used[cur-1]) {
                used[cur-1] = true;
                ans++;
            }
        }
        out.println(ans);
        out.flush();
    }
    
    public static class FastScanner {
        BufferedReader br;
        StringTokenizer st;
        
        public FastScanner(Reader in) {
            br = new BufferedReader(in);
        }
        
        public FastScanner() {
            this(new InputStreamReader(System.in));
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
        
        String readNextLine() {
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
