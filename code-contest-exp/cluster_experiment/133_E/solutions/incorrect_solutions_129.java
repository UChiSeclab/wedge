import java.io.*;
import java.util.Arrays;
import static java.lang.Integer.*;
public class E {
    static int [][][][] dp;
    static String info;
    final static int OFFSET = 200; 
    //0 for forward, 1 for backward
    public static int runDP(int idx, int rem, int score, int dir){
        if(idx >= info.length() && rem == 0)
            return score;
        if(rem < 0)
            return -(int)1e9;
        if(idx >= info.length())
            return -(int)1e9;
        int accessScore = score < 0 ? score + OFFSET : score;
        if(dp[idx][rem][accessScore][dir] != -1)
            return dp[idx][rem][accessScore][dir];
        int ans = 0;
        //try executing current instruction i times from 0 to rem
        for(int i = 0 ; i <= rem ; ++i){
            if(info.charAt(idx) == 'T'){
                if(i % 2 == 0)
                    ans = Math.max(ans, runDP(idx + 1, rem - i, score, 1 - dir));
                else{
                    int nscore = dir == 0 ? score + 1 : score - 1;
                    ans = Math.max(ans, runDP(idx + 1, rem - i, nscore, dir));
                }
            }else{
                if(i % 2 == 0){
                    int nscore = dir == 0 ? score + 1 : score - 1;
                    ans = Math.max(ans, runDP(idx + 1, rem - i, nscore, dir));
                }else{
                    ans = Math.max(ans, runDP(idx + 1, rem - i, score, 1 - dir));
                }
            }
        }
        return dp[idx][rem][accessScore][dir] = ans;
    }
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in = br.readLine();
        info = in;
        int K =parseInt(br.readLine());
        dp = new int [in.length() + 1][K + 1][205][2];
        for(int i = 0 ; i < dp.length ; ++i)
            for(int j = 0 ; j < dp[i].length ; ++j)
                for(int k = 0 ; k < dp[i][j].length ; ++k)
                    Arrays.fill(dp[i][j][k], -1);
        int ans = runDP(0, K, 0, 0);
        System.out.println(ans);
    }
}
