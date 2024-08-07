import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final StringBuilder out = new StringBuilder();

    public static void main(String[] args) {
        int t = sc.nextInt();

        while(t-- > 0){
            compute(sc.nextInt());
        }

        System.out.print(out.toString());
    }

    private static void compute(int n){
        int[] nums = new int[n];
        int[] count = new int[n];
        int[] ans = new int[n];
        int l = 0, r = n - 1;

        for(int i = 0; i < n; i++){
            nums[i] = sc.nextInt() - 1;
            ++count[nums[i]];
        }

        ans[n - 1] = (count[0] > 0)? 1 : 0;
        ans[0] = 1;

        for(int e: count) {
            if(e != 1) {
                ans[0] = 0;
                break;
            }
        }

        for(int i = 0; i < n - 2; i++){
            if((nums[l] == i || nums[r] == i) && count[i] == 1 && count[i + 1] > 0){
                if(nums[l] == i)
                    ++l;
                else
                    --r;

                ans[n - i - 2] = 1;
            } else
                break;
        }

        for(int e: ans)
            out.append(e);

        out.append("\n");
    }
}