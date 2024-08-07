import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    private static FastReader fr = new FastReader();
    private static Helper hp = new Helper();
    private static StringBuilder result = new StringBuilder();

    public static void main(String[] args) {
        Task solver = new Task();
        solver.solve();
    }

    static class Task {

        public void solve() {
            int tc = fr.ni();
            while (tc-- > 0){
                int n = fr.ni();
                int[] arr = new int[n];
                int min = Integer.MAX_VALUE;
                int[] ans = new int[n+1];
                int[] counts = new int[n+1];
                for(int i=0; i<n; i++){
                    arr[i] = fr.ni();
                    min = Math.min(min, arr[i]);
                    counts[arr[i]]++;
                }
                ans[n] = 1;
                if(min != 1){
                    ans[1] = ans[n] = 0;
                    for(int i=1; i<=n; i++){
                        result.append(ans[i]);
                    }
                    result.append("\n");
                    continue;
                }
                else{
                    ans[1] = 1;
                    for(int i=1; i<n+1; i++){
                        if(counts[i] != 1){
                            ans[1] = 0;
                            break;
                        }
                    }
                }
                int left = 0, right = n-1;
                for(int i=1; i<=n; i++){
                    if(counts[i] == 0){
                        break;
                    }
                    ans[n-i+1] = 1;
                    if(arr[left] == i && counts[i] == 1){
                        left++;
                    }
                    else if(arr[right] == i && counts[i] == 1){
                        right--;
                    }
                    else{
                        break;
                    }
                }
                for(int i=1; i<=n; i++){
                    result.append(ans[i]);
                }
                result.append("\n");
            }
            System.out.println(result);
        }
    }

    static class Helper {
        public int[] ipArrInt(int n) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++)
                arr[i] = fr.ni();
            return arr;
        }

        public long[] ipArrLong(int n, int si) {
            long[] arr = new long[n];
            for (int i = si; i < n; i++)
                arr[i] = fr.nl();
            return arr;
        }
    }

    private static long computeNcR(int n, int r){
        long p = 1, k = 1;
        if (n - r < r) {
            r = n - r;
        }
        if (r != 0) {
            while (r > 0) {
                p *= n;
                k *= r;
                long m = gcd(p, k);
                p /= m;
                k /= m;
                n--;
                r--;
            }
        }
        return p;
    }

    private static long gcd(long n1, long n2) {
        long gcd = 1;
        for (int i = 1; i <= n1 && i <= n2; ++i) {
            if (n1 % i == 0 && n2 % i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }

    static class FastReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
        private static PrintWriter pw;

        public FastReader() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            pw = new PrintWriter(System.out);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int ni() {
            return Integer.parseInt(next());
        }

        public long nl() {
            return Long.parseLong(next());
        }

        public String rl() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public int[] nextIntArr(int n) throws IOException {
            int[] arr = new int[n];
            for (int i = 0; i < arr.length; i++)
                arr[i] = fr.ni();
            return arr;
        }

        public void print(String str) {
            pw.print(str);
            pw.flush();
        }
    }
}