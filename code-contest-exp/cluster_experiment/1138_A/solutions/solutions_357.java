import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;

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
            int[] ar = new int[n];
            for (int i = 0; i < n; i++)
                ar[i] = in.nextInt();
            int ans = f(ar, n);
            out.println(ans);
        }

        private int f(int[] ar, int n) {
            ArrayList<Integer> l = new ArrayList<>();
            int l1 = 0, l2 = 0;
            for (int i = 0; i < n; i++) {
                if (ar[i] == 1) {
                    l1++;
                    if (l2 > 0) {
                        l.add(l2);
                        l2 = 0;
                    }
                } else {
                    l2++;
                    if (l1 > 0) {
                        l.add(l1);
                        l1 = 0;
                    }
                }
            }
            if (l1 > 0)
                l.add(l1);
            if (l2 > 0)
                l.add(l2);
            int maxLen = 0;
            for (int i = 0, len = l.size(); i < len - 1; i++)
                maxLen = Math.max(maxLen, 2 * Math.min(l.get(i), l.get(i + 1)));
            return maxLen;
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

