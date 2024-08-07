import java.io.*;
import java.util.*;

public class C {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter write = new PrintWriter(System.out);
        int t = Integer.parseInt(br.readLine());
        for (int z = 0; z < t; z++) {

            int n = Integer.parseInt(br.readLine());
            String line = br.readLine();
            HashMap<Long, Integer> lastAns = new HashMap<>();
            StringBuilder ans = new StringBuilder();
            long d = 0;
            long k = 0;
            for (int i = 0; i < n; i++) {
                if (line.charAt(i) == 'D') {
                    d++;
                } else {
                    k++;
                }
                long key = getKey(d, k);
                ans.append(lastAns.getOrDefault(key, 0) + 1);
                lastAns.put(key, lastAns.getOrDefault(key, 0) + 1);
                if (i != n - 1 ) {
                    ans.append(' ');
                }
            }
            write.println(ans);

        }
        br.close();
        write.close();

    }

    public static long getKey(long d, long k) {

        long g = gcd(d, k);
        d /= g;
        k /= g;
        return d * 10000000 + k;

    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
