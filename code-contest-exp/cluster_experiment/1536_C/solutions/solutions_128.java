import java.io.*;
import java.util.*;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.in;
import static java.lang.System.out;
import static java.util.Arrays.asList;
import static java.util.Collections.max;
import static java.util.Collections.min;

public class Main {
    private static final int MOD = (int) (1E9 + 7);
    private static final int INF = (int) (1E9);
    static FastScanner scanner = new FastScanner(in);

    public static void main(String[] args) throws IOException {
        // Write your solution here
        StringBuilder answer = new StringBuilder();
        int t = 1;
        t = parseInt(scanner.nextLine());
        while (t-- > 0) {
            answer.append(solve()).append("\n");
        }
        out.println(answer);
    }

    private static Object solve() throws IOException {
       int n = scanner.nextInt();
       char[] s = scanner.nextLine().toCharArray();
       int K = 0, D = 0;
       StringBuilder ans = new StringBuilder();
       HashMap<String,Integer> map = new HashMap<>();
       for (int i = 0; i < n; i++) {
          if(s[i] == 'K') K++;
          else D++;
          String key = key(K,D);
          map.put(key,map.getOrDefault(key,0) + 1);
          if(K == 0)ans.append(D).append(" ");
          else if(D == 0) ans.append(K).append(" ");
          else ans.append(map.get(key)).append(" ");
       }
        return ans;
    }
    private static String key(int k,int d){
        if(k != 0 && d != 0){
            int g = gcd(k,d);
            k /= g;
            d /= g;
        }
        return k +" - "+ d;
    }

    private static class Pair implements Comparable<Pair> {
        int index, value;

        public Pair(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public int compareTo(Pair o) {
            if (value != o.value) return compare(value, o.value);
            return compare(index, o.index);
        }
    }

    private static int maxx(Integer... a) {
        return max(asList(a));
    }

    private static int minn(Integer... a) {
        return min(asList(a));
    }

    private static long maxx(Long... a) {
        return max(asList(a));
    }

    private static long minn(Long... a) {
        return min(asList(a));
    }

    private static int add(int a, int b) {
        long res = ((long) (a + MOD) % MOD + (b + MOD) % MOD) % MOD;
        return (int) res;
    }

    private static int mul(int a, int b) {
        long res = ((long) a % MOD * b % MOD) % MOD;
        return (int) res;
    }

    private static int pow(int a, int b) {
        a %= MOD;
        int res = 1;
        while (b > 0) {
            if ((b & 1) != 0)
                res = mul(res, a);
            a = mul(a, a);
            b >>= 1;
        }
        return res;
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    private static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String nextLine() {
            try {
                return br.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return parseInt(next());
        }

        long nextLong() {
            return parseLong(next());
        }

        double nextDouble() {
            return parseDouble(next());
        }

    }
}