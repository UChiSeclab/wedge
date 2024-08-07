import java.util.Scanner;

public class Solution {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        //int t = 0;
        //if (sc.hasNext())
        //  t = sc.nextInt();
        //  while (t-- > 0) {
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = sc.nextInt();
        solve(n, a);
        //}
    }

    private static void solve(int n, int[] a) {
        long prev_tuna = 0, prev_eel = 0, tuna = 0, eel = 0, ans = 0;
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                if (a[i] != a[i - 1]) {
                    ans = Math.max(Math.min(eel, tuna), ans);
                    if (a[i] == 1)
                        tuna = 0;
                    else
                        eel = 0;
                }
            }
            if (a[i] == 1) {
                tuna++;
            } else {
                eel++;
            }
        }
        ans = Math.max(Math.min(eel, tuna), ans);
        System.out.println(ans * 2);
    }
}