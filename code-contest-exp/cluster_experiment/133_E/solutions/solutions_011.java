import java.util.Scanner;

public class E {

    static int L;
    static String s;
    static boolean[][][][]calculated;
    static int ans = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        s = sc.next();
        L = s.length();
        int n = sc.nextInt();
        calculated = new boolean[205][L+1][n+1][2];
        calc(100, 0, n, 1);
        System.out.println(ans);
    }

    private static void calc(int pos, int ind, int change, int orient) {
        if (L==ind) {
            if (change%2==0)
                ans = Math.max(ans, Math.abs(pos-100));
            return;
        }
        if (change < 0)
            return;
        if (calculated[pos][ind][change][orient])
            return;
        calculated[pos][ind][change][orient] = true;
        int dx = -1;
        if (orient==1)
            dx = 1;
        if (s.charAt(ind)=='F') {
            calc(pos+dx, ind+1, change, orient);
            calc(pos, ind+1, change-1, 1-orient);
        }
        else {
            calc(pos, ind+1, change, 1-orient);
            calc(pos+dx, ind+1, change-1, orient);
        }
    }
}
