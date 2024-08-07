import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;
public class ProbC {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {
            HashMap<Double, Integer> hm = new HashMap<>();
            int n = Integer.parseInt(br.readLine());
            char ch[] = br.readLine().toCharArray();
            StringBuilder sb = new StringBuilder();
            sb.append("1");
            int d = 0;
            int k = 0;
            if (ch[0] == 'D') {
                d++;
                hm.put(-1.0, 1);
            } else {
                k++;
                hm.put(0.0, 1);
            }
            for (int j = 1; j < ch.length; j++) {
                if (ch[j] == 'K') {
                    k++;
                } else {
                    d++;
                }
                double val = -2;
                if (k == 0) {
                    val = -1;
                } else {
                    val = (1.0 * d) /  (1.0 * k);
                }
                if (hm.containsKey(val)) {
                    hm.put(val, hm.get(val) + 1);
                    sb.append(" " + hm.get(val));
                } else {
                    hm.put(val, 1);
                    sb.append(" 1");
                }
            }
            System.out.println(sb.toString());
        }
    }
}
