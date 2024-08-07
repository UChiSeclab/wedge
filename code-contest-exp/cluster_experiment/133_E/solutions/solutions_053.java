import java.util.Scanner;


public class CF133E {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String commands = sc.next();
        int n = sc.nextInt();
        
        State[][][][] dp = new State[commands.length()+1][n+1][201][2];
        State init = State.valueOf(0);
        dp[0][0][100][0] = init;
        for (int i = 1; i < dp.length; i++) {
            boolean commandIsT = commands.charAt(i-1) == 'T';
            for (int j = 0; j < n+1; j++) {
                for (int k = 0; k <= 200; k++) {
                    for (int l = 0; l < 2; l++) {
                        State temp;
                        int dx = l == 0 ? -1 : 1;
                        if (commandIsT) {
                            if(dp[i-1][j][k][l] != null) {
                                temp = State.getBigger(dp[i-1][j][k][l], dp[i][j][k][(l+1)%2]);
                                dp[i][j][k][(l+1)%2] = temp;
                            }
                            if(j > 0 && dp[i-1][j-1][k][l] != null) {
                                temp = State.getBigger(dp[i-1][j-1][k][l].forward(dx), dp[i][j][k+dx][l]);
                                dp[i][j][k+dx][l] = temp;
                            }
                        }
                        else {
                            if(dp[i-1][j][k][l] != null) {
                                temp = State.getBigger(dp[i-1][j][k][l].forward(dx), dp[i][j][k+dx][l]);
                                dp[i][j][k+dx][l] = temp;
                            }
                            if(j > 0 && dp[i-1][j-1][k][l] != null) {
                                temp = State.getBigger(dp[i-1][j-1][k][l], dp[i][j][k][(l+1)%2]);
                                dp[i][j][k][(l+1)%2] = temp;
                            }
                        }
                        if(j > 1) dp[i][j][k][l] = State.getBigger(dp[i][j-2][k][l], dp[i][j][k][l]);
                    }
                }
            }
        }
        int max = 0;
        for (int i = 0; i <= 200; i++) {
            for (int j = 0; j < 2; j++) {
                if(dp[commands.length()][n][i][j] != null)
                    max = Math.max(max, dp[commands.length()][n][i][j].dist());                
            }
        }
        System.out.println(max);
    }
    
    static class State {
        final int x;
        public State(int x) {
            this.x = x;
        }
        static State valueOf(int x) {
            return new State(x);
        }
        State forward(int dx) {
            return valueOf(x + dx);
        }
        int dist() {
            return Math.abs(x);
        }
        
        static State getBigger(State a, State b) {
            if (a == null) return b; 
            if (b == null) return a; 
            return a.dist() > b.dist() ? a : b;
        }
        
    }

}
