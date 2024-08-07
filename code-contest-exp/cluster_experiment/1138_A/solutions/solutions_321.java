import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int n = in.nextInt();
        int[] a = new int[n + 1];

        for (int t = 0; t < n; t++) {
            a[t] = in.nextInt();
        }

        int[] cnt = new int[n+1];

        int i = 0, k = 0;

        while (i < n) {

            if (a[i] == 1) {
                int c = 0;
                while (a[i] == 1 && i < n) {
                    c++;
                    i++;
                }
                cnt[k++] = c;

            } else if (a[i] == 2) {
                int c = 0;
                while (a[i] == 2 && i < n) {
                    c++;
                    i++;
                }
                cnt[k++] = c;
            }
        }
        int r = 0;
        for(i=0;i<k-1;i++){

            r = Math.max(r, 2*Math.min(cnt[i], cnt[i+1]));
        }
        System.out.println(r);

    }
}
