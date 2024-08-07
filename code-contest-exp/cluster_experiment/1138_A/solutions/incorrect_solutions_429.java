//https://codeforces.com/problemset/problem/1138/A
import java.util.Scanner;

public class Problem_1138_A {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int n = scanner.nextInt();
        int [] array = new int[n];
        int maxOne = Integer.MIN_VALUE;
        int maxTwo = Integer.MIN_VALUE;
        int countOne = 0;
        int countTwo = 0;

        for (int i=0; i<n; i++){
            array[i] = scanner.nextInt();
            if (array[i] == 2){
                countTwo++;
                maxTwo = Math.max(maxTwo, countTwo);
                countOne = 0;
            }else {
                countOne++;
                maxOne = Math.max(maxOne, countOne);
                countTwo = 0;
            }
        }

        System.out.println(Math.min(maxOne, maxTwo) * 2);




        scanner.close();
    }
}
