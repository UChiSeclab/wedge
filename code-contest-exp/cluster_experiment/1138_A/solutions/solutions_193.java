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
        TaskA solver = new TaskA();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskA {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt() % 2;
            }
            int res = calc(a);
            for (int i = 0; i < n; i++) {
                a[i] = (a[i] + 1) % 2;
            }
            res = Math.max(res, calc(a));
            out.println(res);
        }

        private int calc(int[] a) {
            int res = 0;
            int[] longestEel = new int[a.length + 1];
            for (int i = a.length - 1; i >= 0; i--) {
                if (a[i] == 1) {
                    longestEel[i] = longestEel[i + 1] + 1;
                } else {
                    longestEel[i] = 0;
                }
            }
            int count = 0;
            for (int i = 0; i < a.length; i++) {
                if (a[i] == 0) {
                    count++;
                    int temp = Math.min(count, longestEel[i + 1]);
                    res = Math.max(res, temp + temp);
                } else {
                    count = 0;
                }
            }

            return res;
        }

    }

    static class InputReader {
        private StringTokenizer tokenizer;
        private BufferedReader reader;

        public InputReader(InputStream inputStream) {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

