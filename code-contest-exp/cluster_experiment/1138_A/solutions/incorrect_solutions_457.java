import java.io.*;
import java.util.*;

public final class cf1138A3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int x[] = new int[(int) n + 1];
        int f = 1, g = 0, re = 0;
        x[n] = 3;

        for (int i = 1; i < n + 1; i++) {
            if (i < n) x[i] = in.nextInt();
            if (i > 0 && x[i] == x[i - 1]) {
                f++;
            } else {
                re = Math.max(re, Math.min(g, f) * 2);
                g = f;
                f = 1;
            }
        }
        System.out.println(re);
    }
}
