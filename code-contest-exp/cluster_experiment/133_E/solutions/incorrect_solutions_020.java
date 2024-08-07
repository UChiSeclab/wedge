import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class R96D2_E {
    static String inp;
    static int n;
    static int st;
    static int DP[][][];

    public static int call(int i, int rem, int add) {
        if (i == n && rem == 0)
            return 0;
        if (i == n || rem<0)
            return -1000000;
        int d = add == -1 ? 1 : 0;
        
        if (DP[i][rem][d] != -1)
            return DP[i][rem][d];

        if (rem == 0) {
            if (inp.charAt(i) == 'F')
                return DP[i][rem][d] = add + call(i + 1, rem, add);
            else
                return DP[i][rem][d] = call(i + 1, rem, add * -1);
        } else {
            int res = 0;
            int res2 = 1;
            for (int j = 0; j <= rem; j++) {
                if ((inp.charAt(i) == 'F' && j % 2 == 0)
                        || (inp.charAt(i) == 'T' && j % 2 == 1)){
                    int y = add + call(i + 1, rem - j,  add);
                    if(y<10000 && y>-10000){
                    res = Math.max(res, Math.abs(y));
                    if(y<0)
                    res2 = -1;
                    else
                        res2 = 1;
                    }
                }else{
                    int y = call(i + 1, rem - j, -1 * add);
                    if(y<10000 && y>-10000){
                    res = Math.max(res, Math.abs(y));
                    if(y<0)
                        res2 = -1;
                    else
                        res2 = 1;
                    }
                }
            }
            return DP[i][rem][d] = res*res2;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        // int n = Integer.parseInt(in.readLine());
        // String []s = in.readLine().split(" ");
        inp = in.readLine();
        n = inp.length();
        st = Integer.parseInt(in.readLine());
        DP = new int[n][st + 1][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < st + 1; j++) {
                for (int k = 0; k < 2; k++) {
                    DP[i][j][k] = -1;
                }
            }

        }
        System.out.println(Math.abs(call(0,st,1)));
    }
}
