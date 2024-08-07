import java.io.PrintWriter;
import java.util.*;
import java.math.*;
import java.lang.ArrayIndexOutOfBoundsException;

public class Main {
    public static void main(String[] args) {
        //write ur code
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        long[] a = new long[N + 1];

        long count = 0;
        for(int i = 1; i <= N; i++) {
            a[i] = in.nextInt();
            count += a[i];
        }
    System.out.print((count % N) + 2);
    }
}