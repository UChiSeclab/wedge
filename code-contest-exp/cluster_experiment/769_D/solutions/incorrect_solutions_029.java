import java.util.Scanner;

public class _769D {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        int size = in.nextInt();
        int bitDiffer = in.nextInt();

        int size2 = 0;

        int[] count = new int[100001];
        int[] number;
        for (int i = 0; i < size; i++) {
            int tmp = in.nextInt();
            count[tmp]++;
            if (count[tmp] == 1) size2++;
        }
        number = new int[size2];
        for (int i = 0, k = 0; i < count.length; i++) {
            if (count[i] != 0) number[k++] = i;
        }
        long ans = 0l;
        for (int i = 0; i < size2; i++) {
            for (int j = i; j < size2; j++) {
                if (Integer.bitCount(number[i] ^ number[j]) == bitDiffer)
                    if (number[i] == number[j])
                        ans += (count[number[i]] * (count[number[j]]-1)) >> 1;
                    else
                        ans += (count[number[i]] * count[number[j]]);
            }
        }
        System.out.println(ans);
    }
}