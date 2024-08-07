import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            int n = in.nextInt();
            int result = -1;
            int t = 0;
            int e = 0;
            boolean prevT = false;
            for (int i = 0; i < n; i++) {
                if (in.nextInt() == 1) {
                    if (!prevT) {
                        t = 0;
                    }
                    t++;
                    prevT = true;
                } else {
                    if (prevT) {
                        e = 0;
                    }
                    e++;
                    prevT = false;
                }
                result = Math.max(result, Math.min(t, e) * 2);
            }
            System.out.println(result);
        }
    }
}