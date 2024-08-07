import java.util.*;

public class A {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int testCases = sc.nextInt();

        first: for (int t = 0; t < testCases; t++) {
            int n = sc.nextInt();
            int arr[] = new int[n];

            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            int left = 0;
            int right = arr.length - 1;

            int needed = 1;

            boolean done = false;
            int k = n;

            int ans[] = new int[n];

            while (k != 1) {

                k--;

                if (done) {
                    ans[k] = 0;
                    continue;
                }

                if (arr[left] < needed || arr[right] < needed) {
                    ans[k] = 0;
                    done = true;
                    continue;
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

                done = true;

                boolean find = false;

                for (int i = left; i <= right; i++) {
                    if (arr[i] == needed) {
                        ans[k] = 1;
                        find = true;
                        break;
                    }
                }

                if (!find) {
                    ans[k] = 0;
                }

            }

            HashSet<Integer> set = new HashSet<>();

            done = false;

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