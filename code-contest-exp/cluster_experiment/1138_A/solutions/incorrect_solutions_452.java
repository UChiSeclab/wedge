import java.util.*;

public class Solution {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int max = 2, prev = sc.nextInt();
        int[] arr = new int[3];
        arr[prev]++;
        for (int i = 1; i < N; i++) {
            int x = sc.nextInt();
            arr[x]++;
            if (x != prev) {
                if (arr[prev] > 0) {
                    arr[prev] = 1;
                }
            }
            max = Math.max(max, Math.min(arr[1], arr[2]));
            prev = x;
        }
        max = Math.max(max, Math.min(arr[1], arr[2]));
        System.out.println(max);
    }
    
}