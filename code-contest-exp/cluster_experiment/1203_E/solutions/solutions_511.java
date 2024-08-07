/*
 * @author derrick20
 */
import java.io.*;
import java.util.*;

public class Boxers {
    public static void main(String args[]) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int N = sc.nextInt();
        Integer[] arr = new Integer[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);
        boolean[] visited = new boolean[150001 + 1]; // max
        for (int val : arr) {
            if (!visited[val - 1] && val > 1) {
                visited[val - 1] = true;
            }
            else if (!visited[val]) {
                visited[val] = true;
            }
            else if (!visited[val + 1]) {
                visited[val + 1] = true;
            }
        }
        int ct = 0;
        for (int i = 1; i < visited.length; i++) {
            if (visited[i]) {
                ct++;
            }
        }
        out.println(ct);
        out.close();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        FastScanner(FileReader s) {
            br = new BufferedReader(s);
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        String nextLine() throws IOException { return br.readLine(); }

        double nextDouble() throws IOException { return Double.parseDouble(next()); }

        int nextInt() throws IOException { return Integer.parseInt(next()); }

        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}