import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.next());
        int[] a = new int[n];
        ArrayList<ArrayList<Integer>> edge = new ArrayList<ArrayList<Integer>>();
        for (int i=0;i<n;i++) {
            ArrayList<Integer> add = new ArrayList<Integer>();
            edge.add(add);
        }
        for (int i=0;i<n;i++) {
            a[i] = Integer.parseInt(sc.next());
            if (0<=i+a[i] && i+a[i]<=n-1) edge.get(i+a[i]).add(i);
            if (0<=i-a[i] && i-a[i]<=n-1) edge.get(i-a[i]).add(i);
        }
        // System.out.println(edge);

        ArrayDeque<int[]> q = new ArrayDeque<int[]>();
        for (int i=0;i<n;i++) {
            if (a[i]%2==0) {
                int[] add = {i, 0};
                q.add(add);
            }
        }
        int[] ans = new int[n];

        // bfs odd
        boolean[] visited_1 = new boolean[n];
        while (!q.isEmpty()) {
            int[] rem = q.poll();
            // flag
            if (visited_1[rem[0]]) continue;
            visited_1[rem[0]] = true;

            // ans
            if (a[rem[0]]%2==1) {
                ans[rem[0]] = rem[1];
            }
            for (Integer i : edge.get(rem[0])) {
                int[] add = {i, rem[1]+1};
                q.add(add);
            }
        }


        for (int i=0;i<n;i++) {
            if (a[i]%2==1) {
                int[] add = {i, 0};
                q.add(add);
            }
        }

        // bfs even
        boolean[] visited_2 = new boolean[n];
        while (!q.isEmpty()) {
            int[] rem = q.poll();
            // flag
            if (visited_2[rem[0]]) continue;
            visited_2[rem[0]] = true;

            // ans
            if (a[rem[0]]%2==0) {
                ans[rem[0]] = rem[1];
            }
            for (Integer i : edge.get(rem[0])) {
                int[] add = {i, rem[1]+1};
                q.add(add);
            }
        }
        for (int i=0;i<n;i++) {
            if (ans[i]==0) ans[i]=-1;
            if (i!=n-1) {
                System.out.print(ans[i]+" ");
            } else {
                System.out.println(ans[i]);
            }
        }
    }
}