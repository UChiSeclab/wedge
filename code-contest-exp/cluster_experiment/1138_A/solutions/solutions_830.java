import java.util.*;

public class SushiForTwo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int s = 1;
        int e = 1;
        int r = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] == arr[i]) {
                s++;
            } else {
                e = s;
                s = 1;
            }
            r = (Math.max(r, Math.min(e, s)));
        }
        System.out.println(r * 2);
    }
}