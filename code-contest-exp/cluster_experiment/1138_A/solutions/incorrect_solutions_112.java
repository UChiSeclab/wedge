

import java.util.Scanner;

public class Solution {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        solve(a);

    }

    private static void solve(int[] a) {


        int lastTwoCount = -1;
        int lastOneCount = -1;

        int i = 0;
        int max = 0;

        while (i < a.length) {
            if (a[i] == 1) {
                int onesCount = 0;
                while (i < a.length && a[i] == 1) {
                    onesCount++;
                    i++;
                }
                lastOneCount = onesCount;
                if (lastTwoCount != -1) {
                    max = Math.min(lastOneCount, lastTwoCount);
                    lastTwoCount = -1;
                }
            } else {
                int twosCount = 0;
                while (i < a.length && a[i] == 2) {
                    twosCount++;
                    i++;
                }
                lastTwoCount = twosCount;
                if (lastOneCount != -1) {
                    max = Math.min(lastOneCount, lastTwoCount);
                    lastOneCount = -1;
                }
            }
        }
        System.out.println(max * 2);
    }


}
