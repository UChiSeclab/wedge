import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class R96D2_E {
    static String inp;
    static int n;
    static int st;
    static int DP[][][][];
    
    public static int call(int i, int rem, int pos, int add) {
        if (i == n && (rem%2==0))
            return Math.abs(pos);
        if (i == n)
            return -1000000;
        if (pos == 0 && add == -1)
            add = 1;
        int d = add==-1?1:0;
        if(DP[i][rem][pos][d]!=-1)
            return DP[i][rem][pos][d];
        if (rem == 0) {
            if (inp.charAt(i) == 'F')
                return DP[i][rem][pos][d] = call(i + 1, rem, pos + add, add);
            else
                return DP[i][rem][pos][d] = call(i + 1, rem, pos, add * -1);
        } else {
            int res = 0;
            for (int j = 0; j <= rem; j++) {
                if ((inp.charAt(i) == 'F' && j % 2 == 0)
                        || (inp.charAt(i) == 'T' && j % 2 == 1))
                    res = Math.max(res, call(i + 1, rem - j, pos + add, add));
                else
                    res = Math.max(res, call(i + 1, rem - j, pos, -1 * add));
            }
            return DP[i][rem][pos][d] = res;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        // int n = Integer.parseInt(in.readLine());
        // String []s = in.readLine().split(" ");
        inp = in.readLine();
        n = inp.length();
        st = Integer.parseInt(in.readLine());
        DP = new int [n][st+1][n+2][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < st+1; j++) {
                for (int j2 = 0; j2 < n+2; j2++) {
                    for (int k = 0; k < 2; k++) {
                        DP[i][j][j2][k] = -1;
                    }
                }
            }
        }
        System.out.println(call(0, st, 0, 1));
    }
}
