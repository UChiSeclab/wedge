import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), k = sc.nextInt();
        byte[][] a = new byte[n][15];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            String s = Integer.toString(sc.nextInt(), 2);
            int b = s.length();
            for (int m = b - 1; m >= 0; m--) {
                if (s.charAt(m) == '1') a[i][15 + m - b] = 1;
            }
            for (int j = 0; j < i; j++) {
                int v=0;
                for (int h = 0; h < 14; h++) {
                    if (a[i][h] != a[j][h]) v++;
                }
                if (v == k) ans++;
            }
        }
        System.out.print(ans);
    }
}