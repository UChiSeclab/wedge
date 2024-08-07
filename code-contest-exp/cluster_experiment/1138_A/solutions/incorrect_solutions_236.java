import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        int[] input_array = new int[n + 1];
        int[] input_array_cpy = new int[n + 1];
        input_array[n] = 3;
        int i, j, res1 = 0, res2 = 0, cnt = 0;
        for (i = 0; i < n; i++) {
            input_array[i] = reader.nextInt();
        }
        for (i = 0; i < n + 1; i++){
            input_array_cpy[i] = input_array[i];
        }
        for (i = 0; i < n + 1; i++) {
            if (input_array[i] == 2) {
                for (j = i - 1; j >= 0; j--) {
                    if (input_array[j] == 1) {
                        input_array[i] = input_array[j] = 0;
                        cnt++;
                        break;
                    }
                }
            } else {
                if (cnt > res1) {
                    res1 = cnt;
                }
                cnt = 0;
            }
        }
        /*for (i = 0; i < n + 1; i++) {
            if (input_array_cpy[i] == 1) {
                for (j = i - 1; j >= 0; j--) {
                    if (input_array_cpy[j] == 2) {
                        input_array_cpy[i] = input_array_cpy[j] = 0;
                        cnt++;
                        break;
                    }
                }
            } else {
                if (cnt > res2) {
                    res2 = cnt;
                }
                cnt = 0;
            }
        }*/
        System.out.println(res1);
        System.out.println(res2);
        if (res1 > res2) {
            System.out.println(2 * res1);
        } else {
            System.out.println(2 * res2);
        }
    }
}
