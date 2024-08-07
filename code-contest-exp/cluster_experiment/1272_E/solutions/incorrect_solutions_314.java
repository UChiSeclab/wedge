
import java.io.PrintWriter;
import java.util.*;

public class NearestOppositeParity {

    static int n, arr[], dp[][][], visited[][][];

    public static int solve(int pos, int even, int count, int dir) {
        if (pos < 1 || pos > n) {
            return Integer.MAX_VALUE;
        }
        if (dp[pos][even][dir] != -1) {
            return count + dp[pos][even][dir];
        }
        if (visited[pos][even][dir] == 1) {
            return Integer.MAX_VALUE;
        }
        if ((even == 1 && arr[pos - 1] % 2 == 1) || (even == 0 && arr[pos - 1] % 2 == 0)) {
            return count;
        }
        int res = 0;
        visited[pos][even][dir] = 1;
        res = Math.min(solve(pos - arr[pos - 1], even, count + 1, 0), solve(pos + arr[pos - 1], even, count + 1, 1));
        dp[pos][even][dir] = res - count;
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        n = in.nextInt();
        arr = new int[n];
        dp = new int[n + 2][2][2];
        visited = new int[n + 2][2][2];
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < 2; j++) {
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
            }
        }
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        long ans;
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 0) {
                ans = Math.min(solve(i + 1, 1, 0, 0), solve(i + 1, 1, 0, 1));
            } else {
                ans = Math.min(solve(i + 1, 0, 0, 0), solve(i + 1, 0, 0, 1));
            }
            /*if(i < n - 1){
             if(ans < 1000000){
             out.print(ans + " ");
             }else{
             out.print("-1 ");
             }
             }else{
             if(ans < 1000000){
             out.println(ans);
             }else{
             out.println("-1");
             }
             }*/
        }
        for (int i = 1; i < n + 1; i++) {
            if (arr[i - 1] % 2 == 0) {
                if (i < n) {
                    int a = dp[i][1][0];
                    int b = dp[i][1][1];
                    if (a != -1 && b != -1) {
                        out.print(Math.min(a, b) + " ");
                    } else if (a == -1 || a > 1000000) {
                        if (b < 1000000) {
                            out.print(b + " ");
                        } else {
                            out.print("-1 ");
                        }
                    } else if (b == -1 || b > 1000000) {
                        if (a < 1000000) {
                            out.print(a + " ");
                        } else {
                            out.print("-1 ");
                        }
                    }else if(a > 1000000 && b > 1000000){
                        out.print("-1 ");
                    }
                } else {
                    int a = dp[i][1][0];
                    int b = dp[i][1][1];
                    if (a != -1 && b != -1) {
                        out.print(Math.min(a, b) + "\n");
                    } else if (a == -1) {
                        if (b < 1000000 || a > 1000000) {
                            out.print(b + "\n");
                        } else {
                            out.print("-1\n");
                        }
                    } else if (b == -1 || b > 1000000) {
                        if (a < 1000000 ) {
                            out.print(a + "\n");
                        } else {
                            out.print("-1\n");
                        }
                    }else if(a > 1000000 && b > 1000000){
                        out.print("-1\n");
                    }
                }
            }else{
                if (i < n) {
                    int a = dp[i][0][0];
                    int b = dp[i][0][1];
                    if (a != -1 && b != -1 && a < 1000000 && b < 1000000) {
                        out.print(Math.min(a, b) + " ");
                    } else if (a == -1 || a > 1000000) {
                        if (b < 1000000) {
                            out.print(b + " ");
                        } else {
                            out.print("-1 ");
                        }
                    } else if (b == -1 || b > 1000000) {
                        if (a < 1000000) {
                            out.print(a + " ");
                        } else {
                            out.print("-1 ");
                        }
                    }else if(a > 1000000 && b > 1000000){
                        out.print("-1 ");
                    }
                } else {
                    int a = dp[i][0][0];
                    int b = dp[i][0][1];
                    if (a != -1 && b != -1) {
                        out.print(Math.min(a, b) + "\n");
                    } else if (a == -1) {
                        if (b < 1000000) {
                            out.print(b + "\n");
                        } else {
                            out.print("-1\n");
                        }
                    } else if (b == -1) {
                        if (a < 1000000) {
                            out.print(a + "\n");
                        } else {
                            out.print("-1\n");
                        }
                    }else if(a > 1000000 && b > 1000000){
                        out.print("-1\n");
                    }
                }
            }
        }
        out.close();
    }
}
