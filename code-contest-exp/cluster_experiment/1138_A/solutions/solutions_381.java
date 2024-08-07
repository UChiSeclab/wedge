import java.io.*;
import java.util.*;

public final class cf1138A3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int x[] = new int[(int) n];
        int f = 1, g = 0, re = 0;

        for (int i = 0; i < n + 1; i++) {
            if (i < n) x[i] = in.nextInt();
            if (i == 0) continue;
            if (i > 0 && i < n && x[i] == x[i - 1]) {
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
