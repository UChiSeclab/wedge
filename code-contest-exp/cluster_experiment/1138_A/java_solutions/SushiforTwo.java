//package A;
// In the name of Allah
// Vahid_Ghafourian

import java.util.Scanner;

public class SushiforTwo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int[] t = new int[n];

        int max = 0;
        int sum = 0;
        for (int i = 0; i<n; i++){
            t[i] = scan.nextInt();
        }

        int m = 1;
        int a = 1;
        sum += a*2;
        if (max<sum)
            max = sum;
        sum = 0;
        for (int i = 1; i<n; i++){
            if (t[i] == t[i-1]){
                m++;
                if (a==m){
                    sum += a*2;
                    if (max<sum)
                        max = sum;
                    sum = 0;
                }
            }
            else {
                a = m;
                m = 1;
            }
        }

        System.out.println(max);
    }
}
