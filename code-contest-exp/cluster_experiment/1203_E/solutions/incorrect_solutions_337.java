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
        int count = 0;
        for (int i = 0; i < n; i++) {
            boolean inc = true;
            boolean dec = true;
            boolean ec = true;
            for (int k = i-1; k >= 0; k--) {
                if (arr[i]+1 == arr[k]) {inc = false; break;}
            }
            if (inc) { count++; arr[i]++; }
            else {
                if (arr[i] != 1) {
                    for (int k = i - 1; k >= 0; k--) {
                        if (arr[i] - 1 == arr[k]) {
                            dec = false;
                            break;
                        }
                    }
                    for (int k = i + 1; k < n; k++) {
                        if (arr[i] - 1 == arr[k]) {
                            dec = false;
                            break;
                        }
                    }
                } else dec = false;
                if (dec) { count++; arr[i]--; }
                else {
                    for (int k = i-1; k >= 0; k--) {
                        if (arr[i].equals(arr[k])) {ec = false; break;}
                    }
                    for (int k = i+1; k < n; k++) {
                        if (arr[i].equals(arr[k])) {ec = false; break;}
                    }
                    if (ec) { count++; } else arr[i] = -1;
                }
            }
        }
        System.out.println(count);

    }
}