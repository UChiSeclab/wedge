import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Temp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr, Collections.reverseOrder());
        int count = 1;
        int last = arr[0]+1;
        for (int i = 1; i < n; i++) {
            if (arr[i]+1 < last) {last = arr[i]+1; count++;} else {
                if (arr[i]  < last) {last = arr[i]; count++;} else {
                    if (arr[i] == last && arr[i] != 1) {last = arr[i]-1; count++;}
                }
            }
        }
        System.out.println(count);

    }
}