import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
//        InputStream inputStream = new FileInputStream("sum.in");
        OutputStream outputStream = System.out;
//        OutputStream outputStream = new FileOutputStream("sum.out");

        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Answer solver = new Answer();
        solver.solve(in, out);
        out.close();
    }
}

class Answer {
    private final int INF = (int) (1e9 + 7);
    private final int MOD = (int) (1e9 + 7);
    private final int MOD1 = (int) (1e6 + 3);
    private final long INF_LONG = (long) (1e18 + 1);
    private final double EPS = 1e-9;

    private long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    private int sumBits(int n) {
        int k = 0;
        while (n != 0) {
            k += n % 2;
            n >>= 1;
        }
        return k;
    }

    public void solve(InputReader in, PrintWriter out) throws IOException {
        int n = in.nextInt();
        int k = in.nextInt();

        long[] counter = new long[11111];
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            counter[x]++;
        }

        long ans = 0;

        if (k == 0) {
            for (int i = 0; i < 10001; i++) {
                if (counter[i] != 0) {
                    ans += (counter[i] - 1) * counter[i] / 2;
                }
            }
            out.println(ans);
            return;
        }

        int sum[] = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            sum[i] = sumBits(i);
        }

        for (int i = 0; i < 10001; i++) {
            if (counter[i] == 0) {
                continue;
            }

            for (int j = i + 1; j < 10001; j++) {
                if (counter[j] == 0) {
                    continue;
                }
                int xx = i ^ j;
                if (0 <= xx && xx <= 1000000 && sum[xx] == k) {
                    ans += counter[i] * counter[j];
                }
            }
        }

        out.println(ans);
    }
}

class InputReader {
    private BufferedReader reader;
    private StringTokenizer tokenizer;

    InputReader(InputStream stream) {
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

    public long nextLong() {
        return Long.parseLong(next());
    }

    public int[] nextArrayInt(int count) {
        int[] a = new int[count];
        for (int i = 0; i < count; i++) {
            a[i] = nextInt();
        }
        return a;
    }

    public long[] nextArrayLong(int count) {
        long[] a = new long[count];
        for (int i = 0; i < count; i++) {
            a[i] = nextLong();
        }
        return a;
    }
}