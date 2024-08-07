import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Main {


    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int[] sushi = new int[n];
        for (int i = 0; i < n; i++) {
            sushi[i] = s.nextInt();
        }

        int prev = 0, current = 1, maxPrev = 0, maxCurrent = 0;

        for (int i = 1; i < n ; i++) {
            if(sushi[i] == sushi[i - 1]){
                current++;
            }
            if (sushi[i] != sushi[i - 1] || i == n - 1) {
                if (maxPrev < prev && maxCurrent < current){
                    maxPrev = prev;
                    maxCurrent = current;
                }
                prev = current;
                current = 1;
            }
        }


        System.out.println(Math.min(maxPrev, maxCurrent) * 2);
    }
}
