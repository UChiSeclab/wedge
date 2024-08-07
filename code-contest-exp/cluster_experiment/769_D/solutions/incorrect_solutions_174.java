import java.util.Scanner;

public class VK_CUP_2017_D {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int k = sc.nextInt();

        String arr[] = new String[n];
        for (int i = 0; i < n; i++) {
            arr[i] = String.format("%16s", Integer.toBinaryString(sc.nextInt())).replace(' ', '0');
        }

        int res = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (check(arr[i], arr[j], k)) {
                    res++;
                }
            }
        }

        System.out.println(res);

        sc.close();

//        System.out.println(String.format("%16s", Integer.toBinaryString(10000)).replace(' ', '0'));
    }

    private static boolean check(String s1, String s2, int k) {
        int kol = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                kol++;
                if (kol > k) return false;
            }
        }
        return true;
    }
}
