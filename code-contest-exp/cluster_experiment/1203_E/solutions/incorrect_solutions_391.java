import java.util.Scanner;

/**
 * Created by AMK on 8/13/2019.
 * Life is nice :)
 * Enjoy coding :D
 */
public class Main5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int arr[] = new int[150002];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            arr[x]++;
            if (arr[x] == 1){
                cnt++;
            }
        }
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] > 1 && arr[i + 1] == 0){
                cnt++;
                arr[i + 1] =1;
            }
            if (i != 1 && arr[i] > 1 && arr[i - 1] == 0){
                cnt++;
                arr[i - 1] = 1;
            }
        }
        System.out.println(cnt);
    }
}
