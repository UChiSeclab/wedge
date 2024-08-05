import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;

public class CodeForces {

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    // ===================================================================================================================================================
    public static int gcd(int a, int b) {
      if (b == 0)
        return a;
      return gcd(b, a % b);
    }
    
    
    public static void main(String args[]) {
        FastReader in = new FastReader();
        int t = in.nextInt();
        while (t-- > 0) {
            int n = in.nextInt();
            String s = in.nextLine();
            int dp[][] = new int[n][2];
            if(s.charAt(0) == 'D') {
                dp[0][0] = 1;
                dp[0][1] = 0;
            } else {
                dp[0][1] = 1;
                dp[0][0] = 0;
            }
            for(int i = 1;i<n;i++) {
                char ch = s.charAt(i);
                if(ch == 'D') {
                    dp[i][0] = dp[i-1][0]+1;
                    dp[i][1] = dp[i-1][1];
                } else {
                    dp[i][0] = dp[i-1][0];
                    dp[i][1] = dp[i-1][1]+1;
                }
            }
            int res[] = new int[n];
            boolean success = true;
            for(int i = 0;i<n;i++) {
                res[i]=1;
                success = true;
                if(dp[i][0] == 0 || dp[i][1] == 0) {
                    res[i] = Math.max(dp[i][1], dp[i][0]);
                } else {
                    int g = gcd(dp[i][0],dp[i][1]);
                    
                    if(g>1) {
                        
                        while(g>1) {
                            success = true;
                            int part = (dp[i][0] + dp[i][1])/g;
                            for(int j = 1;j<=g; j++) {
                                int min = Math.min(dp[j*part-1][0],dp[j*part-1][1]);
                                if(min != j) {
                                    success = false;
                                    break;
                                }
                            }
                            if(success) {
                                break;
                            }
                            if(g%2!=0) {
                                break;
                            }
                            g=g-2;
                        }
                        if(g==0) {
                            g++;
                        }
                        res[i] = g;
                    } else {
                        res[i] = 1;
                    }
                }
            }
            for(int i = 0;i<n;i++) {
                System.out.print(res[i] + " ");
            }
            System.out.println();
        }
    }
}