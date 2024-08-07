import java.io.*;
import java.util.*;

public class q3 {
    public static int gcd(int a,int b){
        return b == 0 ? a : gcd(b,a % b);
    }
    public static void main(String[] args) throws Exception {
        // String[] parts=br.readLine().split(" ");
        // int n=Integer.parseInt(parts[0]);
        // int k=Integer.parseInt(parts[1]);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tests = Integer.parseInt(br.readLine());

        for (int test = 1;test <= tests;test++) {
            String[] parts = br.readLine().split(" ");
            int n = Integer.parseInt(parts[0]);

            String str = br.readLine();
            int[] ans = new int[str.length()];
            int[] countd = new int[str.length()];
            int[] countk = new int[str.length()];
            HashMap<String,Integer>[] dp = new HashMap[str.length()];

            int cd = 0,ck = 0;
            dp[0] = new HashMap<>();
            ans[0] = 1;
            if(str.charAt(0) == 'D') {
                countd[0] = 1;
                cd++;
                dp[0].put("1#0",1);
            }else {
                countk[0] = 1;
                ck++;
                dp[0].put("0#1",1);
            }

            for(int i = 1;i < str.length();i++){
                if(str.charAt(i) == 'D') {
                    cd++;
                }else {
                    ck++;
                }
                countd[i] = cd;
                countk[i] = ck;

                dp[i] = new HashMap<>();
                if(ck == 0) {
                    dp[i].put(cd + "#0",i + 1);
                    ans[i] = i + 1;
                }else if(cd == 0){
                    dp[i].put("0#" + ck,i + 1);
                    ans[i] = i + 1;
                }else if(cd % ck == 0 || ck % cd == 0){
                    int g = gcd(cd,ck);
                    int val = cd / g + ck / g;
                    for(int j = i - val;j >= 0;j -= val){
                        int d = countd[i] - countd[j];
                        int k = countk[i] - countk[j];
                        int gcd = gcd(d,k);
                        d /= gcd;
                        k /= gcd;
                        String temp = d + "#" + k;

                        if(dp[j].containsKey(temp)){
                            val = dp[j].get(temp) + 1;
                            if(val > ans[i]){
                                ans[i] = val;
                                dp[i].put(temp,val);
                            }
                            
                        }
                    }

                    if(ans[i] == 0){
                        dp[i].put(cd + "#" + ck,1);
                        ans[i] = 1;
                    }
                }else{
                    dp[i].put(cd + "#" + ck,1);
                    ans[i] = 1;
                }
            }

            for(int i = 0;i < ans.length;i++){
                System.out.print(ans[i] + " ");
            }
            System.out.println();
        }
    }
}
