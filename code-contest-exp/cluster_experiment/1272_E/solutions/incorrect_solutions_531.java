import java.util.Arrays;
import java.util.Scanner;

public class CR605E {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] a = new int[n];
        for(int i=0; i<n; i++) {
            a[i] = sc.nextInt();
        }

        int[] dp = new int[n];
        Arrays.fill(dp, 0);

        for(int i=0; i<n; i++) {
            dp[i] = calculate(dp, a, n, i, 0);
        }

//        for(int i=0; i<n; i++) {
//            int nextMinus = i - a[i];
//            int nextPlus = i + a[i];
//
//            if(nextMinus >= 0 && a[i] % 2 != a[nextMinus] % 2) {
//                dp[i] = 1;
//            }
//            if(nextPlus < n && a[i] % 2 != a[nextPlus] % 2) {
//                dp[i] = 1;
//            }
//        }
//
//        for(int i=0; i<n; i++) {
//
//        }

        for(int i=0; i<n; i++) {
            System.out.print(dp[i] + " ");
        }

    }

    public static int calculate(int[] dp, int[] a, int n, int i, int k) {
        if(k == n+1) {
            return - 1;
        }
        if(dp[i] != 0) {
            return dp[i];
        }
        int nextMinus = i - a[i];
        int nextPlus = i + a[i];

        if(nextMinus >= 0 && a[i] % 2 != a[nextMinus] % 2) {
            dp[i] = 1;
            return 1;
        }
        if(nextPlus < n && a[i] % 2 != a[nextPlus] % 2) {
            dp[i] = 1;
            return 1;
        }

        int minus = -1;
        int plus = -1;
        if(nextMinus >= 0) {
            minus = calculate(dp, a, n, nextMinus, k+1);
        }
        if(nextPlus < n) {
            plus = calculate(dp, a, n, nextPlus, k+1);
        }

        if(plus != -1 && minus != -1) {
            dp[i] = Math.min(minus, plus) + 1;
        } else if (plus != -1) {
            dp[i] = plus + 1;
        } else if (minus != -1) {
            dp[i] = minus + 1;
        } else {
            dp[i] = -1;
        }

        return dp[i];
    }
}
