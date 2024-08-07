import java.util.*;

public class SushiForTwo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] t = new int[n];
        for (int i = 0; i < n; i++) {
            t[i] = in.nextInt();
        }

        int numTuna = 0;
        int numEel = 0;
        int ans = 0;
        if (t[0] == 1) {
            numTuna++;
        }
        else {
            numEel++;
        }


        for (int i = 1; i < n; i++) {
            if (t[i] == t[i - 1]) {
                if (t[i] == 1) {
                    numTuna++;
                }
                else {
                    numEel++;
                }
                ans = Math.max(ans, Math.min(numTuna, numEel) * 2);
            }
            else {
                if (t[i] == 1) {
                    numTuna = 1;
                }
                else {
                    numEel = 1;
                }
            }
        }

        System.out.println(ans);
    }
}
