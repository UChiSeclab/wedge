import java.io.*;
import java.util.*;

public class E_Boxers {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader inp = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Solver solver = new Solver();
        solver.solve(inp, out);
        out.close();
    }

    private static class Solver {
        private void solve(InputReader inp, PrintWriter out) {
            int n = inp.nextInt();
            int[] cnt = new int[150001];
            for (int i = 0; i < n; i++) cnt[inp.nextInt()]++;
            boolean[] vis = new boolean[150002];
            for (int i = 1; i <= 150000; i++) {
                if (cnt[i] > 0 && !vis[i-1] && i - 1 > 0) {
                    vis[i-1] = true;
                    cnt[i]--;
                }
                if (cnt[i] > 0 && !vis[i]) {
                    vis[i] = true;
                    cnt[i]--;
                }
                if (cnt[i] > 0 && !vis[i+1]) {
                    vis[i+1] = true;
                    cnt[i]--;
                }
            }
            int res = 0;
            for (boolean b: vis) if (b) res++;
            out.print(res);
        }
    }

    static class InputReader {
        BufferedReader reader;
        StringTokenizer tokenizer;

        InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}