import java.util.*;
public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int m = input.nextInt();
        int max[] = new int[n];
        int hash[] = new int[10001];
        int res = 0;

        for (int i = 0; i < n; i++) {
            int kek = input.nextInt();
            hash[kek]++;
        }

        if (m == 0) {
            for (int i = 0; i < hash.length - 1; i++) {
                if (hash[i] != 0) {
                    res += hash[i];
                }
            }

            System.out.printf(Integer.toString(res));
            return;
        }

        for (int i = 0; i < hash.length - 1; i++) {
            if (hash[i] != 0) {
                for (int g = i + 1; g < hash.length; g++) {
                    if (hash[g] != 0) {
                        int xorInt = i ^ g;
                        int count = Integer.bitCount(xorInt);
                        if (count == m) res += (hash[i] + hash[g]) / 2;
                    }
                }
            }
        }

        System.out.printf(Integer.toString(res));
    }
}
