import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int t[] = new int[n];
        for (int i = 0; i < n; i++) {
            t[i] = in.nextInt();
        }
        int cnt = 0, cur = 0, sz = 0;
        int v[] = new int[n];
        for (int i = 0, j; i < n; i = j) {
            j = i;
            while (j < n && t[i] == t[j]) {
                j++;
            }
            v[cur++] = j - i;
        }
        int ans = 0;
        for (int i = 1; i < cur; i++) {
            ans = Math.max(ans, Math.min(v[i], v[i - 1]));
        }
        System.out.println(ans * 2);
    }

}