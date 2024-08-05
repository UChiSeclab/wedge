import java.util.Scanner;
import java.util.TreeMap;

@SuppressWarnings("Duplicates")

public class hjh {
    private static long GCD(long a, long b) {
        return b != 0 ? GCD(b, a % b) : a;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int c = 160000;
        long arr[] = new long[c];
        for (int i = 0; i < n; i++) {
            arr[in.nextInt() - 1]++;
        }
        for (int i = 0; i < c; i++) {
            if (arr[i] == 1||arr[i]==0) {
                continue;
            } else if (arr[i] == 2) {
                if (i > 0 && arr[i - 1] == 0) {
                    arr[i - 1]++;
                } else if (i + 1 < c && arr[i + 1] == 0) {
                    arr[i + 1]++;
                }
            } else if (arr[i] > 2) {
                arr[i] = 1;
                if (i > 0 && arr[i - 1] == 0) {
                    arr[i - 1]++;
                }
                if (i + 1 < c && arr[i + 1] == 0) {
                    arr[i + 1]++;
                }
            }
        }
        long ans = 0;
        for (int i = 0; i < c; i++) {
            if (arr[i] != 0) {
                ans++;
            }
        }
        System.out.println(ans);
        /*int n = in.nextInt();
        long gcd = in.nextLong();
        for (int i = 1; i < n && gcd != 1; i++) {
            gcd = GCD(gcd, in.nextLong());
        }
        long ans = 0, o = 0;
        if (gcd % Math.sqrt(gcd) == 0) {
            o++;
        }
        for (long i = 1; i < Math.sqrt(gcd); i++) {
            if (gcd % i == 0) {
                ans++;
            }
        }
        System.out.println(ans * 2 + o);*/


    }
}