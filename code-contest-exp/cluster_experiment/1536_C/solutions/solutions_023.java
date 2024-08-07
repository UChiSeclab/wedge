import java.io.*;
import java.util.*;

public class Main {

    static Scanner sc;
    static PrintWriter out;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        out = new PrintWriter(System.out);
        int t = 1;
        if (true) {
            t = sc.nextInt();
        }
        for(int i=0; i<t; i++) {
            new Main().solve();
        }
        out.flush();
    }

    public void solve() {
        int n = sc.nextInt();
        char[] c = sc.next().toCharArray();
        int[] a = new int[n];
        int d = 0;
        int k = 0;
        Map<String ,Integer> map = new HashMap<>();
        for(int i=0; i<n; i++) {
            if(c[i]=='D') {
                d++;
            } else {
                k++;
            }
            String key = ratio(d, k);
            a[i] = map.getOrDefault(key, 0) + 1;
            map.put(key, a[i]);
        }

        for(int i=0; i<n; i++) {
            if(i>0) out.print(" ");
            out.print(a[i]);
        }
        out.println();
    }

    String ratio (int d, int k) {
        if(d == 0) {
            return "1:0";
        } else if (k == 0) {
            return "0:1";
        } else {
            int gcd = gcd(d , k);
            int dd = d/gcd;
            int kk = k/gcd;
            return dd + ":" + kk;
        }
    }

    static int gcd(int m, int n) {
        if(m < n) return gcd(n, m);
        if(n == 0) return m;
        return gcd(n, m % n);
    }


}
