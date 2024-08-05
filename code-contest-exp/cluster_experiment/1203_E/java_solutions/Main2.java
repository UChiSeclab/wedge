import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main2 {

    private FastScanner scanner = new FastScanner();

    public static void main(String[] args) {
        new Main2().solve();
    }

    private void solve() {

        int n = scanner.nextInt();

        Integer a[] = new Integer[n];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        Arrays.sort(a, (o1, o2) -> o2 - o1);

        int cnt = 1;
        int max = a[0] + 1;

        for (int i = 1; i < n; i++) {
            if (a[i] + 1 < max) {
                cnt ++;
                max = a[i] + 1;
            } else if (a[i] < max) {
                cnt ++;
                max = a[i];
            } else if (a[i] - 1 != 0 && a[i] - 1 < max) {
                cnt ++;
                max = a[i] - 1;
            }
        }

        System.out.println(cnt);

    }

    long gcd(long a, long b) {
        if (b != 0) {
            return gcd(b, a % b);
        }
        return a;
    }

    class FastScanner {
        BufferedReader reader;
        StringTokenizer tokenizer;

        FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in), 32768);
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

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] nextA(int n) {
            int a[] = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = scanner.nextInt();
            }
            return a;
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}