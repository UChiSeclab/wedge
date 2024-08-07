import java.io.PrintStream;
import java.util.Scanner;


public class Solution {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final PrintStream out = System.out;

        final int tests = in.nextInt();

        for (int test = 1; test <= tests; test++) {
            final int n = in.nextInt();
            final int[] a = new int[n];
            final char[] b = new char[n];
            final int[] c = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt() - 1;
                b[i] = '0';
                c[a[i]]++;
            }

            int l = 0;
            int r = n - 1;

            b[l] = '1';
            b[r] = c[0] > 0 ? '1' : '0';

            for (int i = 0; i < n; i++) {
                if (c[i] != 1) {
                    b[l] = '0';
                    break;
                }
            }

            if (b[r] == '1') {
                for (int i = 0; i < n - 2; i++) {
                    if ((a[l] == i || a[r] == i) && c[i] == 1 && c[i + 1] > 0) {
                        if (a[l] == i) l++;
                        else r--;
                        b[n - 2 - i] = '1';
                        continue;
                    }

                    break;
                }
            }


            out.println(b);
        }

    }

}
