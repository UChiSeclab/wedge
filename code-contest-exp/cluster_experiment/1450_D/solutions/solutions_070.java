import java.util.*;
import java.io.*;

public class D_Rating_Compression {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();
        while(testCases-- > 0) {
            int n = scanner.nextInt();
            int[] a = new int[n + 1];
            int[] count = new int[n + 1];
            int[] ans = new int[n + 1];
            for(int i = 1; i < n + 1; i++) {
                int val = scanner.nextInt();
                a[i] = val;
                if(val < count.length) {
                    count[val]++;
                }
            }
            // SPECIAL CASE: K=1
            ans[1] = 1;
            for(int i = 1; i <= n; i++) {
                if(count[i] != 1) {
                    ans[1] = 0;
                    break;
                }
            }
            int l = 1;
            int r = a.length - 1;
            for(int i = 1; i <= n; i++) {
                if(count[i] > 0) {
                    ans[n - i + 1] = 1;
                    if(a[l] == i && count[i] == 1) {
                        l++;
                    } else if(a[r] == i && count[i] == 1) {
                        r--;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            for(int i = 1; i <= n; i++) {
                System.out.print(ans[i]);
            }
            System.out.println();
        }
    }
}