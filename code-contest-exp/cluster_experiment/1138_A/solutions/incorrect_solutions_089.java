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
        int prev_tuna = 0, prev_eel = 0, tuna = 0, eel = 0, ans = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] == 1) {
                tuna++;
                if (eel != 0)
                    prev_eel = eel;
                eel = 0;
            } else if (a[i] == 2) {
                eel++;
                if (tuna != 0)
                    prev_tuna = tuna;
                tuna = 0;
            }
        }
        if (eel > prev_eel)
            prev_eel = eel;
        if (tuna > prev_tuna)
            prev_tuna = tuna;
        ans = Math.min(prev_tuna, prev_eel);
        System.out.println(ans * 2);
    }
}