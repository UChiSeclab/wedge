import java.util.Scanner;

public class C {
    static Scanner sc = new Scanner(System.in);
    public static void solve(){
        int n = sc.nextInt();
        char[]chars = sc.next().toCharArray();
        int[][]prev = new int[n + 1][2];
        for(int i = 0;i < n;i++){
            int idx = chars[i] == 'D' ? 0 : 1;
            prev[i + 1][idx] = prev[i][idx] + 1;
            prev[i + 1][1 - idx] = prev[i][1 -idx];
        }
        int[]ans = new int[n];
        for(int i = 1;i <= n;i++){
            int d = prev[i][0],p = prev[i][1];
            for(int j = i - 1;j < n;j += i){
                int dx = prev[j + 1][0] - prev[j + 1 - i][0],px = prev[j + 1][1] - prev[j + 1 - i][1];
                if(dx != d || px != p)
                    break;
                if(ans[j] == 0)
                    ans[j] = (j + 1) / i;
            }
        }
        for(int i : ans){
            if(i == 0)
                i = 1;
            System.out.print(i + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int n = sc.nextInt();
        for(int i = 0;i < n;i++)
            solve();
    }
}
