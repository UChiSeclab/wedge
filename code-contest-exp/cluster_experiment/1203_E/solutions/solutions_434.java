import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boxers {
    public static void main(String[] args) throws IOException {
        BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bi.readLine());
        int n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bi.readLine());
        long time = System.currentTimeMillis();

        int[] dp = new int[150001];

        for (int i = 0; i < n; i++) {
//            dp[i] = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            dp[a]++;
//            int[] count = new int[1500002];
        }
        boolean[] seen = new boolean[150002];
        seen[0] = true;
        int cnt = 0;
        for (int i = 1; i <= 150000; i++) {
            while (dp[i] > 0) {
                if (!seen[i-1]) {
                    seen[i - 1] = true;
                    cnt++;
                } else if (!seen[i]) {
                    seen[i] = true;
                    cnt++;
                } else if (!seen[i+1]) {
                    seen[i+1] = true;
                    cnt++;
                }
                else {
                    break;
                }
                dp[i]--;
            }
        }
        System.out.println(cnt);
    }
}
