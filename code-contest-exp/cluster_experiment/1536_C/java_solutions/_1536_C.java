import java.util.*;
import java.io.*;

public class _1536_C {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int t = Integer.parseInt(in.readLine());
        while(t-- > 0) {
            int n = Integer.parseInt(in.readLine());
            String s = in.readLine();
            int[] a = new int[n];
            int[] pre = new int[n];
            for(int i = 0; i < n; i++) {
                if(s.charAt(i) == 'K') a[i] = 1;
                if(i == 0) pre[i] = a[i];
                else pre[i] = a[i] + pre[i - 1];
            }
            int[] res = new int[n];
            for(int i = 0; i < n; i++) {
                int sum = sum(0, i, pre);
                if(sum == 0 || sum == i + 1) {
                    res[i] = Math.max(res[i], i + 1);
                }
            }
            for(int i = 1; i <= n; i++) {
                int sum = sum(0, i - 1, pre);
                if(sum == 0 || sum == i) continue;
                int gcd = gcd(sum, i);
                sum /= gcd;
                int sum2 = sum(0, i / gcd - 1, pre);
                if(gcd > 1 && sum2 / gcd(sum2, sum) == sum) {
                    continue;
                }
                res[i - 1] = Math.max(res[i - 1], 1);
                int prev = i;
                int count = 1;
                for(int j = 2 * i; j <= n; j += i) {
                    int sum3 = sum(prev, j - 1, pre);
                    if(sum3 / gcd(sum3, j - prev) == sum) {
                        count++;
                        prev = j;
                        res[j - 1] = Math.max(res[j - 1], count);
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < n; i++) {
                sb.append(res[i]);
                sb.append(' ');
            }
            out.println(sb.toString());
        }
        in.close();
        out.close();
    }
    static int gcd(int a, int b) {
        if(b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
    static int sum(int l, int r, int[] pre) {
        return pre[r] - (l > 0 ? pre[l - 1] : 0);
    }
}
