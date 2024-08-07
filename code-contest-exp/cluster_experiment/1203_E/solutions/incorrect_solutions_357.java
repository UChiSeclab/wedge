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
        for (int i = 0; i < n; i++) {
            arr[scanner.nextInt()]++;
        }
        int cnt = 0;
        int index = 0;
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] > 1 && arr[i + 1] == 0){
                cnt++;
                index = i + 1;
            }
            if (i != 1 && arr[i] > 1 && arr[i - 1] == 0 && i - 1 != index){
                cnt++;
            }
            if (arr[i] != 0){
                cnt++;
            }
        }
        System.out.println(cnt);
    }
}
