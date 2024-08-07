import java.util.*;

public class TaskD {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();

        int[] a = new int[n];

        for (int i = 0; i < n; ++i) {
            a[i] = in.nextInt();
        }

        if (k == 0) {
            System.out.println(n);
            return;
        }

        int pairCount = 0;
        for (int i = 1; i < n; ++i) {
            int ai = a[i];

            for (int j = 0; j < i; j++) {
                int aj = a[j];

                if (bitSumQuick(ai ^ aj) == k) {
                    ++pairCount;
                }
            }
        }

        System.out.println(pairCount);
    }

    private static int bitSumQuick(int num) {
        num = num - ((num >> 1) & 0x55555555);
        num = (num & 0x33333333) + ((num >> 2) & 0x33333333);
        return (((num + (num >> 4)) & 0x0F0F0F0F) * 0x01010101) >> 24;
    }

}
