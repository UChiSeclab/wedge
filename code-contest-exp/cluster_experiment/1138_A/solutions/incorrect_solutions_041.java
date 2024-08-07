import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SushiForTwo {
    static ArrayList<Integer> interval = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(f.readLine());
        int N = Integer.parseInt(st.nextToken());
        int curType = 0;
        int count = 0;
        st = new StringTokenizer(f.readLine());
        for (int i = 0; i < N; i++) {
            int next = Integer.parseInt(st.nextToken());
            if (curType == 0) {
                curType = next;
                count = 1;
            }
            else {
                if (curType == next) {
                    count++;
                }
                else {
                    curType = next;
                    interval.add(count);
                    count = 1;
                }
            }
        }
        int maxLength = 0;
        for (int i = 0; i < interval.size() - 1; i++) {
            maxLength = Math.max(maxLength, Math.min(interval.get(i), interval.get(i+1)*2));
        }
        System.out.println(maxLength);

    }
}
