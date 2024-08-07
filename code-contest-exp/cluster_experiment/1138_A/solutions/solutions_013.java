import java.io.*;
import java.util.*;

import java.lang.*;

public final class cf1138A2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int x[] = new int[(int) n];
        int a[][] = new int[3][(int) n];

        int re = 0;
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            if (x[i] == 2) x[i] = 0;

            if (i > 0 && x[i] == x[i - 1]) {
                a[x[i]][i] = a[x[i]][i - 1] + 1;
                a[1 - x[i]][i] = 0;
            } else {
                a[x[i]][i] = 1;
                a[1 - x[i]][i] = 0;
            }
            if (i - a[x[i]][i] >= 0) {
                int p = a[1 - x[i]][i - a[x[i]][i]];
                if (p >= a[x[i]][i]) {
                    re = Math.max(re, a[x[i]][i] * 2);
                }
            }
        }
        System.out.println(re);
    }

}