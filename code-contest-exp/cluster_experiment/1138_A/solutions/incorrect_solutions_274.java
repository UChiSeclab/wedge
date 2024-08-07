import java.util.Scanner;
public class Main { 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int quantity = sc.nextInt();
        int oneCount = 0;
        int twoCount = 0;
        int length = 0;
        int[] a = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            a[i] = sc.nextInt();
            if (a[i] == 1) {
                oneCount++;
            } else if (a[i] == 2) {
                twoCount++;
            }
        }
        if (oneCount < twoCount) {
            length = oneCount * 2;
        } else if (twoCount < oneCount) { 
            length = twoCount * 2;
        } else if (oneCount == twoCount) { 
            int maxStreak = 0, streakCount = 1;
            for (int i = 1; i < a.length; i++) {
                if (a[i] == a[i-1]) {
                    streakCount++;
                } else { 
                    streakCount = 1;
                }
                if (streakCount >= maxStreak) {
                    maxStreak = streakCount;
                }
            }
            length = maxStreak * 2;
        }
        System.out.println(length);
    }
}
 