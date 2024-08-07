import java.util.Scanner;

/**
 * Created by USER on 3/4/2017.
 */
public class Prob4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n=scanner.nextInt();
        int k=scanner.nextInt();
        int a=0;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i]=scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if((arr[i]^arr[j])==k){
                    a++;
                }
            }
        }
        System.out.println(a);
    }
}
