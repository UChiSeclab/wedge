import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        int a = arr[0];
        int buff1 = 0;
        int buff2 = 0;
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] != a) {
                buff2 = buff1;
                buff1 = 0;
                a = arr[i];
            }
            buff1++;
            result = Math.max(result, Math.min(buff1, buff2));

        }
        System.out.print(result * 2);


    }

}