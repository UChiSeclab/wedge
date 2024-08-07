import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
 
public class C {
 
    static PrintWriter out = new PrintWriter(System.out);
 
    static class FastScanner {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer("");
        String next() {
            while (!st.hasMoreTokens())
                try {
                    st=new StringTokenizer(br.readLine());
                } catch (IOException e) {}
            return st.nextToken();
        }
 
        int nextInt() {
            return Integer.parseInt(next());
        }
        long nextLong() {
            return Long.parseLong(next());
        }
    }
 
    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            String s = sc.next();
            int countD = 0, countK = 0;
            //D-K // D==0 or K==0
            Map<String, Integer> map = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == 'D') {
                    countD++;
                } else {
                    countK++;
                }
                if (countD == 0) {
                    map.put("0D", map.getOrDefault("0D", 0) + 1);
                    sb.append(map.get("0D"));
                    sb.append(" ");
                } else if (countK == 0) {
                    map.put("0K", map.getOrDefault("0K", 0) + 1);
                    sb.append(map.get("0K"));
                    sb.append(" ");
                } else {
                    int gcd = gcdThing(countD, countK);
                    String key = countD / gcd + "-" + countK / gcd;
                    map.put(key, map.getOrDefault(key, 0) + 1);
                    sb.append(map.get(key));
                    sb.append(" ");
                }
            }
            out.println(sb);
        }
        out.close();
    }
 
    private static int gcdThing(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
}