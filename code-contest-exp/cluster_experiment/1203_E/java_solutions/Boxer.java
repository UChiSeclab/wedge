import java.util.Arrays;
import java.util.Scanner;

public class Boxer {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int[] boxer = new int[160000];
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = s.nextInt();
        }
        Arrays.sort(w);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (boxer[w[i]] == 0) {
                boxer[w[i]] ++;
                ans ++;
            }
            else {
                if (w[i] > 1){
                    if (boxer[w[i] - 1] == 0) {
                        boxer[w[i] - 1] ++;
                        ans ++;
                        continue;
                    }
                }
                if (boxer[w[i] + 1] == 0) {
                    boxer[w[i] + 1] ++;
                    ans ++;
                }
            }
        }
        System.out.println(ans);
    }
}
