
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        long t = 0;
        int n = in.nextInt();
        int k = in.nextInt();
        int max = 10001;
        long[] m = new long[max];
        for (int i = 0; i < n; i++)
            m[in.nextInt()]++;

        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                if (m[i] > 0 && m[j] > 0 && Integer.bitCount(i ^ j) == k) {
                    if (k == 0) {
                        t += (m[i]) * (m[i] - 1);
                    } else {
                        t += m[i] * m[j];
                    }

                }


            }
        }
        System.out.println(t/2);
    }
}