import java.util.Scanner;

public class codeForce {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = sc.nextInt();
        }
        int min = Integer.MAX_VALUE;
        int count = 1;
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == 1) {
                if (a[i] == a[i + 1]) {
                    count++;
                } else {
                    if (count < min) {
                        min = count;
                    }
                    count = 1;
                }
            }
        }
        System.out.println(min * 2);
    }
}
