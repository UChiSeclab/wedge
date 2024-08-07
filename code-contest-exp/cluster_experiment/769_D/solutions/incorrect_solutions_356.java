import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt(), k = scanner.nextInt(), a[] = new int[count], temp, result = 0, mc;
        for (int i = 0; i < count; i++)
            a[i] = scanner.nextInt();
        if (k != 0) {
            for (int i = 0; i < count; i++)
                for (int j = 0; j < count; j++) {
                    mc = 0;
                    temp = a[i] & a[j];
                    while (temp > 0) {
                        if (temp % 2 == 1)
                            mc++;
                        temp /= 2;
                    }
                    if (mc == k & (i < j || j < i))
                        result++;
                }

            System.out.println(result);
        }
        else {
            for (int i = 0; i < count; i++)
                for (int j = 0; j < count; j++) {
                    if (a[i] == a[j] & (i < j))
                        result++;
                }

            System.out.println(result);
        }

    }
}
