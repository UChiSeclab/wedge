import java.util.Scanner;


public class CF133E {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String commands = sc.next();
        int n = sc.nextInt();
        
        State[][][] dp = new State[commands.length()+1][n+1][4];
        State init = State.valueOf(0, 0, Direction.LEFT);
        dp[0][0][0] = init;
        for (int i = 1; i < dp.length; i++) {
            boolean commandIsT = commands.charAt(i-1) == 'T';
            for (int j = 0; j < 4; j++) {
                if(dp[i-1][0][(commandIsT ? j+1 : j) % 4] != null) {
                    dp[i][0][j] = dp[i-1][0][(commandIsT ? j+1 : j) % 4].command(commandIsT);
                }
            }
            for (int j = 1; j < n+1; j++) {
                for (int k = 0; k < 4; k++) {
                    State fromLeft = null;
                    State fromTop = null;
                    if (commandIsT) {
                        if(dp[i-1][j][(k-1+4)%4] != null) fromLeft = dp[i-1][j][(k-1+4)%4].turnAround();
                        if(dp[i-1][j-1][k] != null) fromTop = dp[i-1][j-1][k].forward();
                    }
                    else {
                        if(dp[i-1][j][k] != null) fromLeft = dp[i-1][j][k].forward();
                        if(dp[i-1][j-1][(k-1+4)%4] != null) fromTop = dp[i-1][j-1][(k-1+4)%4].turnAround();
                    }
                    dp[i][j][k] = fromLeft == null ? fromTop
                            :  (fromTop != null && fromLeft.dist > fromTop.dist) ? fromLeft : fromTop;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if(dp[commands.length()][n][i] != null)
                max = Math.max(max, dp[commands.length()][n][i].dist);
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
    }

}
