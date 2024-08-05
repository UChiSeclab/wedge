import java.util.Scanner;

public class Temp3{
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            boolean inc = true;
            boolean dec = true;
            boolean stay = true;
            for (int k = i + 1; k < n; k++) {
                if (arr[i] == arr[k]) {
                    stay = false;
                    break;
                }
            }
            for (int k = i - 1; k >= 0; k--) {
                if (arr[i] == arr[k]) {
                    stay = false;
                    break;
                }
            }
            if (stay) {
                count++;
            } else {
                for (int k = i + 1; k < n; k++) {
                    if (arr[i] == arr[k] - 1) {
                        inc = false;
                        break;
                    }
                }
                for (int k = i - 1; k >= 0; k--) {
                    if (arr[i] == arr[k] - 1) {
                        inc = false;
                        break;
                    }
                }
                if (inc) {
                    count++;
                    arr[i]++;
                } else {
                    if (arr[i] <= 1) {
                        arr[i] = -1;
                    } else {
                        for (int k = i - 1; k > 0; k--) {
                            if (arr[k] != -1)
                                if (arr[i] == arr[k] + 1) {
                                    dec = false;
                                    break;
                                }
                        }
                        for (int k = i + 1; k < n; k++) {
                            if (arr[k] != -1)
                                if (arr[i] == arr[k] + 1) {
                                    dec = false;
                                    break;
                                }
                        }
                        if (dec) {
                            count++;
                            arr[i]--;
                        } else
                            arr[i] = -1;
                    }
                }
            }
        }
        System.out.println(count);
    }
}
