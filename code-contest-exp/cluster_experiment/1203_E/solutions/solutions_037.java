import java.util.Arrays;
import java.util.Scanner;

public class E1203 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        Integer[] A = new Integer[N];
        for (int n=0; n<N; n++) {
            A[n] = in.nextInt();
        }
        Arrays.sort(A);
        int count = 0;
        int lastUsed = 0;
        for (int n=0; n<N; n++) {
            int a = A[n];
            if (a+1 > lastUsed) {
                count++;
                lastUsed = Math.max(lastUsed+1, a-1);
            }
        }
        System.out.println(count);
    }

}
