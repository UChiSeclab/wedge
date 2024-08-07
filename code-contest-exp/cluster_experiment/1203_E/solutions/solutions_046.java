//Created by Aminul on 8/13/2019.

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class E_2 {
    public static void main(String[] args) throws Exception {
        //Scanner in = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int maxn = 150000 + 5;
        int a[] = new int[maxn];
        for (int i = 0; i < n; i++) {
            int x = Integer.parseInt(st.nextToken());
            a[x]++;
        }
        int ans = 0;
        for (int i = 1; i < maxn - 2; i++) {
            if (a[i - 1] > 0) {
                a[i - 1]--;
                ans++;
            } else if (a[i] > 0) {
                a[i]--;
                ans++;
            } else if (a[i + 1] > 0) {
                a[i + 1]--;
                ans++;
            }
        }

        pw.println(ans);

        pw.close();
    }

    static void debug(Object... obj) {
        System.err.println(Arrays.deepToString(obj));
    }
}