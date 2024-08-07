import java.io.*;
import java.util.*;
import java.lang.*;

public final class cf1138A {
    static final long lim_m = (long) (double) 1e17;
    static boolean check(int m, long x[], long a[], long b[], int n) {
        // 1 2 1 1 2 2 1 1 1 2 2  2
        // 0 1 2 3 4 5 6 7 8 9 10 11
        for (int i = 0; i < n; i ++) {
            if (!(i - m >= 0 && i + m < n)) continue;
            if (a[i] - ((i - m == -1)? 0 : a[i - m]) == m && b[i + m] - b[i] == m) {
                return true;
            }
            if (b[i] - ((i - m == -1)? 0 : b[i - m]) == m && a[i + m] - a[i] == m) {
                return true;
            }
        }
        return false;
    }
    static long run() {
        Scanner in = new Scanner(System.in);
        int n;
        n = in.nextInt();
        long x[] = new long[(int) n];
        long a[] = new long[(int) n];
        long b[] = new long[(int) n];

        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            if (i > 0) {
                a[i] = a[i - 1] + ((x[i] == 1)? 1 : 0);
                b[i] = b[i - 1] + ((x[i] == 2)? 1 : 0);
            } else if (i == 0) {
                a[0] = (x[0] == 1)? 1: 0;
                b[0] = (x[0] == 2)? 1: 0;
            }
            // System.out.println(i + ": " + a[i] + "_" + b[i]);
        }
        if (a[n - 1] == n || b[n - 1] == n) {
            // System.out.println(a[n - 1] + " " + b[n - 1]);
            return 0;
        }
        
        int l = 1, r = n + 1, mid;
        while (l != r - 1) {
            mid = (l + r) >> 1;
            if (check(mid, x, a, b, n) == true) l = mid;
            else r = mid;
            // System.out.println(l + " " + mid + " " + r);
        }
        return l * 2;
    }
    public static void main(String[] args) {
        System.out.println(run());
    }

}