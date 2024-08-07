import java.util.Arrays;
import java.util.Scanner;

public class F {
    static int[][][][] dp = new int[110][230][110][3];
    static String inp;

    public static int min(int index, int pos, int n, int direction) {
        if (index == inp.length() && n == 0)
            return Math.abs(pos);
        else if (index == inp.length())
            return Integer.MIN_VALUE;
        else if (dp[index][pos + 100][n][direction + 1] != -1)
            return dp[index][pos + 100][n][direction + 1];
        else {
            if (n == 0) {
                if (inp.charAt(index) == 'T') {
                    return dp[index][pos + 100][n][direction + 1] = min(
                            index + 1, pos, n, -direction);
                } else
                    return dp[index][pos + 100][n][direction + 1] = min(
                            index + 1, pos + direction, n, direction);
            }

            else {
                if (inp.charAt(index) == 'T')
                    return dp[index][pos + 100][n][direction + 1] = Math.max(
                            min(index + 1, pos + direction, n - 1, direction),
                            min(index + 1, pos, n, -direction));
                else {
                    return dp[index][pos + 100][n][direction + 1] = Math.max(
                            min(index + 1, pos + direction, n, direction),
                            (min(index + 1, pos, n - 1, -direction)));
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        inp = in.nextLine();

        int n = in.nextInt();
        for (int i = 0; i < dp.length; i++)
            for (int j = 0; j < dp[i].length; j++)
                for (int k = 0; k < dp[i][j].length; k++)
                    Arrays.fill(dp[i][j][k], -1);
        System.out.println(min(0, 0, n, 1));
    }
}
