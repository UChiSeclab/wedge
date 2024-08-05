import java.util.Scanner;

public class _769D {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int bitDif = in.nextInt();
        int[] count = new int[10001];

        for (int i = 0; i < size; i++) {
            count[in.nextInt()]++;
        }

        long ans = 0l;
        for (int i = 0; i < 10001; i++) {
            if (count[i] == 0) continue;
            for (int j = i; j < 10001; j++) {
                if (count[j] == 0) continue;
                if (Integer.bitCount(i ^ j) == bitDif) {
                    ans += i == j ?
                           count[i] * (count[i] - 1) >> 1 :
                           count[i] * count[j];
                }
            }
        }
        System.out.println(ans);
    }
}