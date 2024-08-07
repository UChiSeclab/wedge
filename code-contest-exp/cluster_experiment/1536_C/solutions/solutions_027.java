import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    static class FastReader {
            BufferedReader br;
            StringTokenizer st;

            public FastReader()
            {
                br = new BufferedReader(
                        new InputStreamReader(System.in));
            }

            String next()
            {
                while (st == null || !st.hasMoreElements()) {
                    try {
                        st = new StringTokenizer(br.readLine());
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return st.nextToken();
            }

            int nextInt() { return Integer.parseInt(next()); }

            long nextLong() { return Long.parseLong(next()); }

            double nextDouble()
            {
                return Double.parseDouble(next());
            }

            String nextLine()
            {
                String str = "";
                try {
                    str = br.readLine();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return str;
            }
        }

    public static void main(String[] args) {
        FastReader in = new FastReader();

        int test = in.nextInt();
        while (test-- > 0) solve(in);
    }

    private static void solve(FastReader in) {
        int n = in.nextInt();
        String s = in.next();

        Map<String, Integer> mp = new HashMap<>();
        StringBuilder ans = new StringBuilder();

        int d = 0, k = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'D') ++d;
            else ++k;
            int a = d, b = k;
            if (a == 0) b = 1;
            else if (b == 0) a = 1;
            else {
                int gcd = gcd(a, b);
                a /= gcd;
                b /= gcd;
            }
            String key = a + ":" + b;
            if (!mp.containsKey(key)) mp.put(key, 1);
            else mp.put(key, mp.get(key) + 1);
            ans.append(mp.get(key)).append(" ");
        }
        System.out.println(ans);

    }

    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a  % b);
    }

}