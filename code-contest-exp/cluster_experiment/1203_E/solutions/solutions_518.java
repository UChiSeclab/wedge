import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskE solver = new TaskE();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskE {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int MAX = 150001;
            int n = in.nextInt();
            int[] ar = new int[n];
            int[] cnt = new int[MAX + 1];
            for (int i = 0; i < n; i++) {
                ar[i] = in.nextInt();
                cnt[ar[i]]++;
            }
            for (int w = 1; w < MAX; w++) {
                if (cnt[w] == 0)
                    continue;
                if (w == 1) {
                    if (cnt[w] > 1) {
                        cnt[w]--;
                        cnt[w + 1]++;
                    }
                    continue;
                }
                if (cnt[w - 1] == 0) {
                    cnt[w - 1] = 1;
                    cnt[w]--;
                }
                if (cnt[w] > 1) {
                    cnt[w + 1]++;
                    cnt[w]--;
                }
            }
            int ans = 0;
            for (int w = 1; w <= MAX; w++)
                if (cnt[w] >= 1)
                    ans++;
            out.println(ans);
        }

    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
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

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

