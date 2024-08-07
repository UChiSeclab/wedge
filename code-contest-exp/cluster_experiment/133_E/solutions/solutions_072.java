import java.util.Arrays;
import java.util.Scanner;


public class Main {
    static char[] a;
    public static void main(String[] args) {
        Scanner r = new Scanner(System.in);
        
        a = r.next().toCharArray();
        int n = r.nextInt();
        
        for(int[][][][] l: dp)
        for(int[][][] i : l)
        for(int[][] j : i)
        for(int[] k : j)
            Arrays.fill(k, -1);
        
        int max = go(0, n, 0, 1, 0);
        
        System.out.println(max);
    }

    static int off = 102;
    static int[][][][][] dp = new int[101][51][210][3][2];
    private static int go(int i, int rem, int pos, int dir, int c) {
        if(i == a.length){
            if(rem == 0)return Math.abs(pos);
            else return 0; 
        }
        if(dp[i][rem][pos + off][dir + 1][c] != -1)return dp[i][rem][pos + off][dir + 1][c];
        
        int w1 = 0, w2 = 0;
        if(!(a[i] == 'F' ^ c == 0)){
            w1 = go(i+1, rem, pos + dir, dir, 0);
            if(rem-1 >= 0)w2 = go(i, rem-1, pos, dir, 1-c);
            
            return dp[i][rem][pos + off][dir + 1][c] = Math.max(w1, w2);
        }else{
            w1 = go(i+1, rem, pos, -dir, 0);
            if(rem-1 >= 0)w2 = go(i, rem-1, pos, dir, 1-c);
            
            return dp[i][rem][pos + off][dir + 1][c] = Math.max(w1, w2);
        }
    }
}
