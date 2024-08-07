import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MaxUniqueWeight {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int arr[] = new int[n];
        String input[] = br.readLine().split(" ");
        Set<Integer> ans = new HashSet<>();
        int l[]=new int[150001];
        for (int j = 0; j < n; j++) {
            arr[j] = Integer.parseInt(input[j]);
            l[arr[j]]++;
        }
        //Arrays.sort(arr);
        for (int i=150000; i >= 0; i--) {
            if(l[i]==1) {
                if (!ans.contains(i + 1)) {
                    ans.add(i + 1);
                } else if (!ans.contains(i)) {
                    ans.add(i);
                } else if (i - 1 > 0 && !ans.contains(i - 1)) {
                    ans.add(i - 1);
                }
            }else if (l[i]==2){
                if (!ans.contains(i + 1) && !ans.contains(i) ){
                    ans.add(i + 1);
                    ans.add(i);
                } else if (!ans.contains(i + 1) && i - 1 > 0 && !ans.contains(i - 1)) {
                    ans.add(i+1);
                    ans.add(i-1);
                } else  {
                    ans.contains(i + 1);
                    ans.add(i);
                    if(i - 1 > 0) {
                        ans.add(i - 1);
                    }
                }
            }else if(l[i]>=3){
                ans.add(i + 1);
                ans.add(i);
                if(i - 1 > 0) {
                    ans.add(i - 1);
                }
            }
        }
        System.out.println(ans.size());
    }

}
