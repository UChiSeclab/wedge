//package VKCup;

import java.util.Scanner;

public class D {
    public static int count(int i) {
        int ans = 0;
        for (i = i - 1; i > 0; i--) {
            ans += i;
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int k = input.nextInt();
        int[] array = new int[10001];
        int t;
        int[] binOnesCount = new int[10001];
        for (int i = 0; i < n; i++) {
            t = input.nextInt();
            array[t]++;
        }
        int[] involution = {1, 2, 4, 8, 16, 31, 64, 128, 256, 512, 1024, 2048, 4096, 8192};
        int temp;
        for (int i = 0; i < 10001; i++) {
            temp = i;
            for (int j = 13; j >= 0; j--) {
                if (temp > involution[j]) {
                    binOnesCount[i]++;
                    temp -= involution[j];
                }
            }
        }
        int count = 0;
        for (int i = 0; i < 10001; i++) {
            if (array[i] > 0) {
                for (int j = i; j < 10001; j++) {
                    if (array[j] > 0) {
                        if (k > 0) {
                            if (binOnesCount[i ^ j] == k) {
                                count += array[i] * array[j];
                            }
                        } else {
                            if (i == j) {
                                count += count(array[i]);
                            }
                        }
                    }
                }
            }
        }
        System.out.print(count);
    }
}
