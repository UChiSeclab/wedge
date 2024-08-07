import java.util.Arrays;
import java.util.Scanner;


public class CF133E {

    final static int UNDEF = 1000000;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String commands = sc.next();
        int n = sc.nextInt();
        
        int[][][][] dp = new int[commands.length()+1][n+1][201][2];
        for (int[][][] is : dp) {
            for (int[][] is2 : is) {
                for (int[] is3 : is2) {
                    Arrays.fill(is3, UNDEF);
                }
            }
        }
        dp[0][0][100][0] = 0;
        for (int i = 1; i < dp.length; i++) {
            boolean commandIsT = commands.charAt(i-1) == 'T';
            for (int j = 0; j < n+1; j++) {
                for (int k = 0; k <= 200; k++) {
                    for (int l = 0; l < 2; l++) {
                        int dx = l == 0 ? -1 : 1;
                        if (commandIsT) {
                            if(dp[i-1][j][k][l] != UNDEF) {
                                dp[i][j][k][(l+1)%2] = getBigger(dp[i-1][j][k][l], dp[i][j][k][(l+1)%2]);
                            }
                            if(j > 0 && dp[i-1][j-1][k][l] != UNDEF) {
                                dp[i][j][k+dx][l] = getBigger(dp[i-1][j-1][k][l] + dx, dp[i][j][k+dx][l]);
                            }
                        }
                        else {
                            if(dp[i-1][j][k][l] != UNDEF) {
                                dp[i][j][k+dx][l] = getBigger(dp[i-1][j][k][l] + dx, dp[i][j][k+dx][l]);
                            }
                            if(j > 0 && dp[i-1][j-1][k][l] != UNDEF) {
                                dp[i][j][k][(l+1)%2] = getBigger(dp[i-1][j-1][k][l], dp[i][j][k][(l+1)%2]);
                            }
                        }
                        if(j > 1) dp[i][j][k][l] = getBigger(dp[i][j-2][k][l], dp[i][j][k][l]);
                    }
                }
            }
        }
        int max = 0;
        for (int i = 0; i <= 200; i++) {
            for (int j = 0; j < 2; j++) {
                if(dp[commands.length()][n][i][j] != UNDEF)
                    max = Math.max(max, Math.abs(dp[commands.length()][n][i][j]));                
            }
        }
        System.out.println(max);
    }
    
    static int getBigger(int a, int b) {
        if (a == UNDEF) return b;
        if (b == UNDEF) return a;
        return Math.abs(a) > Math.abs(b) ? a : b;
    }

}
