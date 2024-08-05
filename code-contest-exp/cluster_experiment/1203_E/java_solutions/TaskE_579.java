import java.util.Arrays;
import java.util.Scanner;

public class TaskE_579 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; ++i)
            a[i] = sc.nextInt();
        Arrays.sort(a);
        int last = 0;
        int count = 0;
        for (int i = 0; i < n; ++i) {
            if (a[i] > last + 1) {
                last  = a[i] - 1;
                count++;
            } else if (a[i] == last || a[i] == last + 1) {
                last++;
                count++;
            }
        }
        System.out.println(count);
    }
}
