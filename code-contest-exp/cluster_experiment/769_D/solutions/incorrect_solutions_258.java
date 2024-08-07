import java.util.Scanner;

public class _769D {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        int size = in.nextInt();
        int bitDiffer = in.nextInt();

        int count2 = 0;

        int[] count = new int[10001];
        int[] numbers;
        for (int i = 0; i < size; i++) {
            int tmp = in.nextInt();
            count[tmp]++;
            if (count[tmp] == 1) count2++;
        }
        numbers = new int[count2];
        for (int i = 0, k = 0; i < count.length; i++) {
            if (count[i] != 0) numbers[k++] = i;
        }
        long ans = 0l;
        for (int i = 0; i < count2; i++) {
            for (int j = i; j < count2; j++) {
                if (Integer.bitCount(numbers[i] ^ numbers[j]) == bitDiffer)
                    if (numbers[i] == numbers[j])
                        ans += (count[numbers[i]] * count[numbers[j]] - count[numbers[i]]) >> 1;
                    else
                        ans += (count[numbers[i]] * count[numbers[j]]);
            }
        }
        System.out.println(ans);
    }
}
