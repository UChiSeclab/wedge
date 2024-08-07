import java.io.*;
import java.util.*;

public class problemA {
    //    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int t = 1;
//        t = Integer.parseInt(in.readLine());
//        t = in.nextInt();
        while (t != 0) {
            play();
            t--;
        }
    }

    public static void play() throws IOException {
        int n = in.nextInt();
        int[] a = new int[n], b = new int[n];
        int c = 0;
        for (int i = 0; i < n; ++i) {
            a[i] = in.nextInt();
        }
        int count = 1;
        for (int i = 1; i < n; ++i) {
            if (a[i] != a[i - 1]) {
                b[c++] = count;
                count = 1;
            } else {
                count++;
            }
        }
        b[c++] = count;
        int ans = 0;
        for (int i = 0; i < c - 1; ++i) {
            ans = Math.max(ans, Math.min(b[i], b[i + 1]) * 2);
        }
        System.out.println(ans);
    }
}

