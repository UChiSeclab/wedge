import java.io.*;
import java.util.*;

public class Solution3 {

    static Scanner io = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int t = io.nextInt();
        for (int i = 0; i < t; i++) {
            solve();
        }
        io.close();
    }

    private static void solve() throws Exception {
        int n = io.nextInt();
        String s = io.next();
        long[][] prefix = new long[s.length()][2];
        for (int i = 0; i < s.length(); i++) {
            if (i > 0) {
                prefix[i][0] += prefix[i - 1][0];
                prefix[i][1] += prefix[i - 1][1];
            }
            if (s.charAt(i) == 'D') {
                prefix[i][0]++;
            } else {
                prefix[i][1]++;
            }
        }
        int[] output = new int[s.length()];
        Arrays.fill(output, 1);
        for (int i = 1; i < s.length(); i++) {
            int len = i + 1;
            for (int j = 1; j * j <= len; j++) {
                if (len % j == 0) {
                    {
                        int div0 = j;
                        int prevIndex = i - div0;
                        long[] prevPrefix = prefix[prevIndex];
                        long[] currPrefix = new long[]{
                                prefix[i][0] - prevPrefix[0],
                                prefix[i][1] - prevPrefix[1]
                        };
                        if (currPrefix[1] * prevPrefix[0] == currPrefix[0] * prevPrefix[1]) {
                            output[i] = Math.max(output[i], output[prevIndex] + 1);
                        }
                    }
                    {
                        int div1 = len / j;
                        int prevIndex = i - div1;
                        long[] prevPrefix = prevIndex >= 0 ? prefix[prevIndex] : new long[]{0, 0};
                        long[] currPrefix = new long[]{
                                prefix[i][0] - prevPrefix[0],
                                prefix[i][1] - prevPrefix[1]
                        };
                        if (currPrefix[1] * prevPrefix[0] == currPrefix[0] * prevPrefix[1]) {
                            output[i] = Math.max(output[i], (prevIndex >= 0 ? output[prevIndex] : 0) + 1);
                        }
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < output.length; i++) {
            sb.append(output[i]);
            if (i < output.length - 1) {
                sb.append(" ");
            }
        }
        System.out.println(sb);
    }
}