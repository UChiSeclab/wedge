import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class SushiForTwo {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int one = 0;
        int two = 0;
        int res = 0;
        int previous = 0;
        for (int i = 0; i < n; i++) {
            int c = Integer.parseInt(st.nextToken());
            if (previous != c) {
                res = Math.max(res, Math.min(one, two) * 2);
                if (c == 1)
                    one = 0;
                else
                    two = 0;
            }
            if (c == 1) {
                one ++;
            } else {
                two++;
            }
            previous = c;
        }
        res = Math.max(res, Math.min(one, two) * 2);
        bw.write(String.valueOf(res));
        bw.newLine();
        bw.flush();
    }
}
