


import java.util.Scanner;

public class ya {


    public static int count(int a, int b) {
        a = a ^ b;
        return Integer.toBinaryString(a).length() - Integer.toBinaryString(a).replace("1", "").length();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] ar = new int[n];
        if (k == 0) {
            System.out.println(n);
        } else {
            for (int i = 0; i < n; i++) {
                ar[i] = sc.nextInt();
            }
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (count(ar[i], ar[j]) == k) {
                        count++;
                    }
                }
            }
            System.out.println(count);
        }
    }
}