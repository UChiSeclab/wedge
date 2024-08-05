import java.io.*;
import java.util.*;

public class codeforces724_C {
    private static void solve(FastIOAdapter ioAdapter) {
        int n = ioAdapter.nextInt();
        char[] c = ioAdapter.next().toCharArray();

        HashMap<String, Integer> map = new HashMap<>();
        int dc = 0;
        int kc = 0;
        for (char cur : c) {
            if (cur == 'D') {
                dc++;
            } else {
                kc++;
            }
            int a = dc;
            int b = kc;
            if (a == 0) {
                b = 1;
            } else if (b == 0) {
                a = 1;
            } else {
                int gcd = gcd(a, b);
                a /= gcd;
                b /= gcd;
            }
            String key = a + ":" + b;
            map.merge(key, 1, Integer::sum);
            ioAdapter.out.print(map.get(key) + " ");
        }
        ioAdapter.out.println();
    }

    static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static void main(String[] args) throws Exception {
        try (FastIOAdapter ioAdapter = new FastIOAdapter()) {
            int count = 1;
            count = ioAdapter.nextInt();
            while (count-- > 0) {
                solve(ioAdapter);
            }
        }
    }

    static void shuffleSort(int[] arr) {
        int n = arr.length;
        Random rnd = new Random();
        for (int i = 0; i < n; ++i) {
            int tmp = arr[i];
            int randomPos = i + rnd.nextInt(n - i);
            arr[i] = arr[randomPos];
            arr[randomPos] = tmp;
        }
        Arrays.sort(arr);
    }

    static class FastIOAdapter implements AutoCloseable {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        public PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter((System.out))));
        StringTokenizer st = new StringTokenizer("");

        String next() {
            while (!st.hasMoreTokens())
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] readArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        @Override
        public void close() throws Exception {
            out.flush();
            out.close();
            br.close();
        }
    }
}