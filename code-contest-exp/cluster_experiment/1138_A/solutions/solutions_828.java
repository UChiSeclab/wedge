import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            int n = Integer.parseInt(br.readLine());
            String []line = br.readLine().split("\\s+");
            int pre = Integer.parseInt(line[0]);
            int max = 0;
            int count1 = 0, count2 = 0;

            for (int i = 0; i < n ; i++) {
                int temp = Integer.parseInt(line[i]);
                if (temp == pre) {
                    count2++;
                }
                else {
                    int x = Math.min(count1, count2);
                    if (x > max) {
                        max = x;
                    }
                    count1 = count2;
                    count2 = 0;
                    pre = temp;
                    i--;
                }
            }

            int x = Math.min(count1, count2);
            if (x > max) {
                max = x;
            }

            System.out.println(max*2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
