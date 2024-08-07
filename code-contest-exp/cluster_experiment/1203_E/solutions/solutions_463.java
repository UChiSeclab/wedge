import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner();
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        int max = 150001;
        int[] cnt = new int[max + 1];
        while (n-- > 0)
            cnt[sc.nextInt()]++;
        int ans = 0;
        boolean[] taken = new boolean[max + 1];
        for (int i = max - 1; i > 0; i--) {
            if (cnt[i] != 0 && !taken[i + 1]) {
                taken[i + 1] = true;
                cnt[i]--;
                ans++;
            }
            if (!taken[i] && cnt[i] != 0) {
                taken[i] = true;
                cnt[i]--;
                ans++;
            }
            if (i != 1 && cnt[i] != 0 && !taken[i - 1]) {
                taken[i - 1] = true;
                cnt[i]--;
                ans++;
            }

        }
        out.println(ans);
        out.flush();
        out.close();
    }

    static class Scanner {
        BufferedReader br;
        StringTokenizer st;

        Scanner() throws FileNotFoundException {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        Integer[] nextIntegerArray(int n) throws IOException {
            Integer[] ans = new Integer[n];
            for (int i = 0; i < n; i++)
                ans[i] = nextInt();
            return ans;
        }

        int[] nextIntArray(int n) throws IOException {
            int[] ans = new int[n];
            for (int i = 0; i < n; i++)
                ans[i] = nextInt();
            return ans;
        }
    }
}