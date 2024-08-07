import java.util.Scanner;

public class sample {
    static char[] s = new char[110];
    static int l;
    static int n;
    static int dp[][][][][] = new int[110][55][2][2][220];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char[] t = in.next().toCharArray();
        for (int i = 0; i < t.length; i++)
            s[i] = t[i];
        l = t.length;
        n = in.nextInt();
        for (int i = 0; i < dp.length; i++)
            for (int j = 0; j < dp[0].length; j++)
                for (int j2 = 0; j2 < dp[0][0].length; j2++)
                    for (int k = 0; k < dp[0][0][0].length; k++)
                        for (int k2 = 0; k2 < dp[0][0][0][0].length; k2++)
                            dp[i][j][j2][k][k2] = -1;
        System.out.println(solve(0, 0, 0, s[0] == 'T' ? 0 : 1, 110));
    }

    private static int solve(int at, int d, int dir, int com, int pos) {
        if (d == n && at == l)
            return Math.abs(pos - 110);
        else if (at == l && d < n)
            return -1000;
        else if (d > n)
            return -1000;
        if (dp[at][d][dir][com][pos] != -1)
            return dp[at][d][dir][com][pos];
        int max = -10000;
        int nex = s[at + 1] == 'T' ? 0 : 1;
        if (dir == 0) {
            if (com == 0) {
                max = Math.max(max, solve(at, d + 1, dir, 1, pos));
                max = Math.max(max, solve(at + 1, d, 1 - dir, nex, pos));
            } else {
                max = Math.max(max, solve(at, d + 1, dir, 0, pos));
                max = Math.max(max, solve(at + 1, d, dir, nex, pos + 1));
            }
        } else {
            if (com == 0) {
                max = Math.max(max, solve(at, d + 1, dir, 1, pos));
                max = Math.max(max, solve(at + 1, d, 1 - dir, nex, pos));
            } else {
                max = Math.max(max, solve(at, d + 1, dir, 0, pos));
                max = Math.max(max, solve(at + 1, d, dir, nex, pos - 1));
            }
        }
        return dp[at][d][dir][com][pos] = max;
    }
}
// TFTTFFFTFFTTFFTTFTTFTFTFFFTTFTTTF
//
// 4