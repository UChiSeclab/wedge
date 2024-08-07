import java.util.Scanner;


public class CF133E {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String commands = sc.next();
        int n = sc.nextInt();
        
        State[][][][] dp = new State[commands.length()+1][n+1][201][4];
        State init = State.valueOf(0, 0, Direction.LEFT);
        dp[0][0][100][0] = init;
        for (int i = 1; i < dp.length; i++) {
            boolean commandIsT = commands.charAt(i-1) == 'T';
            for (int j = 0; j < n+1; j++) {
                for (int k = 0; k <= 200; k++) {
                    for (int l = 0; l < 4; l++) {
                        State temp;
                        if (commandIsT) {
                            if(dp[i-1][j][k][l] != null) {
                                temp = State.getBigger(dp[i-1][j][k][l].turnAround(), dp[i][j][k][(l+1)%4]);
                                dp[i][j][k][(l+1)%4] = temp;
                            }
                            if(j > 0 && dp[i-1][j-1][k][l] != null) {
                                int dx = dp[i-1][j-1][k][l].dir.dx;
                                temp = State.getBigger(dp[i-1][j-1][k][l].forward(), dp[i][j][k+dx][l]);
                                dp[i][j][k+dx][l] = temp;
                            }
                        }
                        else {
                            if(dp[i-1][j][k][l] != null) {
                                int dx = dp[i-1][j][k][l].dir.dx;
                                temp = State.getBigger(dp[i-1][j][k][l].forward(), dp[i][j][k+dx][l]);
                                dp[i][j][k+dx][l] = temp;
                            }
                            if(j > 0 && dp[i-1][j-1][k][l] != null) {
                                temp = State.getBigger(dp[i-1][j-1][k][l].turnAround(), dp[i][j][k][(l+1)%4]);
                                dp[i][j][k][(l+1)%4] = temp;
                            }
                        }
                        if(j > 1) dp[i][j][k][l] = State.getBigger(dp[i][j-2][k][l], dp[i][j][k][l]);
                    }
                }
            }
        }
        int max = 0;
        for (int i = 0; i <= 200; i++) {
            for (int j = 0; j < 4; j++) {
                if(dp[commands.length()][n][i][j] != null)
                    max = Math.max(max, dp[commands.length()][n][i][j].dist);                
            }
        }
        System.out.println(max);
    }
    
    enum Direction {
        LEFT(-1,0), UP(0,-1), RIGHT(1,0), DOWN(0,1);
        
        private Direction(int x, int y) {
            dx = x;
            dy = y;
        }
        int dx,dy;
    }
    
    
    static class State {
        final int x;
        final int y;
        final Direction dir;
        final int dist;
        public State(int x, int y, Direction dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            dist = Math.abs(x) + Math.abs(y);
        }
        static State valueOf(int x, int y, Direction dir) {
            return new State(x, y, dir);
        }
        State forward() {
            return valueOf(x + dir.dx, y + dir.dy, dir);
        }
        State turnAround() {
            if (valuesTemp == null) {
                valuesTemp = Direction.values();
            }
            return valueOf(x, y, valuesTemp[(dir.ordinal() + 1) % valuesTemp.length]);
        }
        static Direction[] valuesTemp;
        State command(boolean c) {
            return c ? turnAround() : forward();
        }
        
        static State getBigger(State a, State b) {
            if (a == null) return b; 
            if (b == null) return a; 
            return a.dist > b.dist ? a : b;
        }
        
        
    }

}
