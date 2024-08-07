/***************************
 *  https://codeforces.com/contest/1138/problem/A
 *
 *  @author nucleusfox
 ***************************/
import java.util.Scanner;

public class Sushi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        String[] sushiRawLine = sc.nextLine().split("\\s");
        int maxMatch = 0;
        int current;
        int[] sushiLine = new int[sushiRawLine.length];
        int[] tuna_eel = new int[2];

        for (int i = 0; i < sushiRawLine.length; i++) {
            sushiLine[i] = Integer.parseInt(sushiRawLine[i]);
        }

        current = sushiLine[0];
        for (int sushi : sushiLine) {

            if (current == sushi) {
                tuna_eel[current-1]++;
            } else {
                current = sushi;
                tuna_eel[current-1] = 1;
            }

            if (maxMatch < Math.min(tuna_eel[0], tuna_eel[1]))
                maxMatch = Math.min(tuna_eel[0], tuna_eel[1]);
        }

        System.out.println(maxMatch*2);
    }
}
