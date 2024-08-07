import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int check[] = new int[150002];

        int n = sc.nextInt();
        Arrays.fill(check, 0);

        int cnt = 0;

        for (int i = 0; i < n; i++) {
            int num = sc.nextInt();
            check[num]++;
        }

        for (int i = 1; i < 150001 ; i++) {
            if(check[i-1] > 0) {
                cnt++;
                check[i-1]--;
            } else if(check[i] > 0) {
                cnt++;
                check[i]--;
            } else if(check[i+1] > 0) {
                cnt++;
                check[i+1]--;
            }
        }

        System.out.println(cnt);
    }
}