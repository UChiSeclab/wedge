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
        while (cur < n) {
            while (cur == 0 || (cur < n && t[cur] == t[cur - 1])) {
                v[cnt]++;
                cur++;
            }
            cnt++;
            v[cnt]++;
            cur++;
            //System.out.println(cur);
        }
        int ans = 0;
        for (int i = 1; i < cnt; i++) {
            ans = Math.max(ans, Math.min(v[i], v[i - 1]));
        }
        System.out.println(ans * 2);
    }

}