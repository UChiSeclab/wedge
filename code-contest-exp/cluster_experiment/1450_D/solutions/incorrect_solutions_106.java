import java.util.*;

public class A {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int testCases = sc.nextInt();

        for (int t = 0; t < testCases; t++) {
            int n = sc.nextInt();
            int arr[] = new int[n];

            int count[] = new int[n + 1];

            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
                count[arr[i]]++;
            }

            int left = 0;
            int right = arr.length - 1;

            int needed = 1;

            int k = n;

            int ans[] = new int[n];

            while (k != 1) {
                k--;

                if (count[needed - 1] > 1) {
                    break;
                }

                if (arr[left] < needed || arr[right] < needed) {
                    ans[k] = 0;
                    break;
                }

                if (arr[left] == needed) {
                    left++;
                    needed++;
                    ans[k] = 1;
                    continue;
                }

                if (arr[right] == needed) {
                    right--;
                    needed++;
                    ans[k] = 1;
                    continue;
                }

                for (int i = left; i <= right; i++) {
                    if (arr[i] == needed) {
                        ans[k] = 1;
                        break;
                    }
                }
            }

            HashSet<Integer> set = new HashSet<>();

            boolean done = false;

            for (int i = 0; i < n; i++) {
                int cur = arr[i];
                if (set.contains(cur)) {
                    ans[0] = 0;
                    done = true;
                    break;
                }
                set.add(cur);
            }

            if (!done) {
                ans[0] = 1;
            }

            for (int i = 0; i < n; i++) {
                sb.append(ans[i]);
            }

            sb.append("\n");

        }

        System.out.print(sb);
    }
}