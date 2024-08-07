import java.util.Scanner;


public class E {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int L = s.length();
        char[]a = new char[L+1];
        for (int i = 1; i <= L; i++) {
            a[i] = s.charAt(i-1);
        }
        int INF = (int) 1e9;
        int n = sc.nextInt();
        int[][][]dp_l = new int[L+1][n+1][2], dp_r = new int[L+1][n+1][2];
        for (int i = 0; i <= L; i++) {
            for (int j = 0; j <= n; j++) {
                dp_l[i][j][0] = dp_l[i][j][1] = -INF;
                dp_r[i][j][0] = dp_r[i][j][1] = -INF;
            }
        }
        dp_l[0][0][0] = dp_r[0][0][1] = 0;
        dp_l[0][0][1] = dp_r[0][0][0] = 0;
        for (int i = 1; i <= L; i++) {
            for (int j = 0; j <= n; j++) {
                if (a[i]=='F') {
                    dp_l[i][j][0] = dp_l[i-1][j][0]+1;
                    dp_l[i][j][1] = dp_l[i-1][j][1]-1;
                    dp_r[i][j][1] = dp_r[i-1][j][1]+1;
                    dp_r[i][j][0] = dp_r[i-1][j][0]-1;
                    if (j > 0) {
                        dp_l[i][j][0] = Math.max(dp_l[i][j][0], dp_l[i-1][j-1][1]);
                        dp_l[i][j][1] = Math.max(dp_l[i][j][1], dp_l[i-1][j-1][0]);
                        dp_r[i][j][0] = Math.max(dp_r[i][j][0], dp_r[i-1][j-1][1]);
                        dp_r[i][j][1] = Math.max(dp_r[i][j][1], dp_r[i-1][j-1][0]);
                    }
                }
                else {
                    dp_l[i][j][0] = dp_l[i-1][j][1];
                    dp_l[i][j][1] = dp_l[i-1][j][0];
                    dp_r[i][j][1] = dp_r[i-1][j][0];
                    dp_r[i][j][0] = dp_r[i-1][j][1];
                    if (j > 0) {
                        dp_l[i][j][0] = Math.max(dp_l[i][j][0], dp_l[i-1][j-1][0]+1);
                        dp_l[i][j][1] = Math.max(dp_l[i][j][1], dp_l[i-1][j-1][1]-1);
                        dp_r[i][j][0] = Math.max(dp_r[i][j][0], dp_r[i-1][j-1][0]-1);
                        dp_r[i][j][1] = Math.max(dp_r[i][j][1], dp_r[i-1][j-1][1]+1);
                    }
                }
            }
        }
        int ans = -INF;
        for (int i = 1; i <= L; i++) {
            ans = Math.max(ans, Math.max(dp_l[i][n][0], dp_l[i][n][1]));
            ans = Math.max(ans, Math.max(dp_r[i][n][0], dp_r[i][n][1]));
        }
        System.out.println(ans);
    }

}
