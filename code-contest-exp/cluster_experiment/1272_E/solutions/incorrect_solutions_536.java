import java.io.PrintWriter;
import java.util.*;
 
public class NearestOppositeParity {
 
    static int n, arr[], dp[][], visited[];
 
    public static int solve(int pos, int even, int count) {
        if (pos < 1 || pos > n) {
            return 10000000;
        }
        if (dp[pos][even] != -1) {
            return count + dp[pos][even];
        }
        if (visited[pos] == 1) {
            return 10000000;
        }
        if ((even == 1 && arr[pos - 1] % 2 == 1) || (even == 0 && arr[pos - 1] % 2 == 0)) {
            return count;
        }
        int res = 0;
        visited[pos] = 1;
        res = Math.min(solve(pos - arr[pos - 1], even, count + 1), solve(pos + arr[pos - 1], even, count + 1));
        dp[pos][even] = (int) (res - count);
        visited[pos] = 0;
        return res;
    }
 
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        n = in.nextInt();
        arr = new int[n];
        dp = new int[n + 2][2];
        visited = new int[n + 2];
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < 2; j++) {
                dp[i][j] = -1;
                dp[i][j] = -1;
            }
        }
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        long ans;
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 0) {
                ans = solve(i + 1, 1, 0);
            } else {
                ans = solve(i + 1, 0, 0);
            }
            if (i < n - 1) {
                if (ans < 1000000) {
                    out.print(ans + " ");
                } else {
                    out.print("-1 ");
                }
            } else {
                if (ans < 1000000) {
                    out.println(ans);
                } else {
                    out.println("-1");
                }
            }
        }
        out.close();
    }
}