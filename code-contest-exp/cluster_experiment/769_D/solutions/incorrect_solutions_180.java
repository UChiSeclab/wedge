import java.io.*;
import java.util.*;

public class D {

    public static void main(String[] args) throws IOException {
        new D().solve();
    }

    StringTokenizer tokenizer = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    String nextToken() throws IOException {
        if (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(reader.readLine());
        }
        return tokenizer.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    void solve() throws IOException {
        int n = nextInt();
        int k = nextInt();
        long[] quantities = new long[10001];
        for (int i = 0; i < n; i++) {
            int a = nextInt();
            quantities[a]++;
        }
        int edge = (1 << 14);
        List<Integer> masks = new ArrayList<>(edge);
        for (int i = 0; i < edge; i++) {
            if (isContainsOnes(i, k)) {
                masks.add(i);
            }
        }
        long result = 0;
        for (int i = 0; i < 10001; i++) {
            for (int mask: masks) {
                int inverted = i | mask;
                if (inverted <= 10000 && inverted > i) {
                    result += quantities[i] * quantities[inverted];
                } else if (inverted <= 10000 && inverted == i) {
                    result += (quantities[i] * (quantities[i] - 1)) / 2;
                }
            }
        }
        System.out.println(result);
    }

    boolean isContainsOnes(int number, int k) {
        int ones = 0;
        while (number > 0) {
            if (number % 2 == 1) {
                ones++;
            }
            number /= 2;
        }
        return (ones == k);
    }

}