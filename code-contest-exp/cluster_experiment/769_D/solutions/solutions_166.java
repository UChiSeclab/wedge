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
        d769 solver = new d769();
        solver.solve(1, in, out);
        out.close();
    }

    static class d769 {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int k = in.nextInt();
            int[] aCnt = new int[10_001];
            for (int i = 0; i < n; i++) {
                int a = in.nextInt();
                aCnt[a]++;
            }
            long ans = 0;
            if (k == 0) {
                for (int v = 0; v <= 10_000; v++) {
                    ans += (long) aCnt[v] * (aCnt[v] - 1);
                }
                ans /= 2;
            } else {
                for (int v1 = 0; v1 <= 10_000; v1++) {
                    for (int v2 = v1 + 1; v2 <= 10_000; v2++) {
                        if (Integer.bitCount(v1 ^ v2) == k) {
                            ans += (long) aCnt[v1] * aCnt[v2];
                        }
                    }
                }
            }
            out.println(ans);
        }

    }

    static class InputReader {
        private BufferedReader reader;
        private StringTokenizer stt;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream));
        }

        public String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public String next() {
            while (stt == null || !stt.hasMoreTokens()) {
                stt = new StringTokenizer(nextLine());
            }
            return stt.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

