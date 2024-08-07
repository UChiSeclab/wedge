import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.lang.StringBuffer;
public class ProbA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int arr[] = new int[n];
        ArrayList<Integer>[] adj = new ArrayList[n];
        boolean mark[] = new boolean[n];
        int[] dist = new int[n];

        for (int i = 0; i < n; i++){
            dist[i] = -1;
            arr[i] = sc.nextInt();

            int x = i + arr[i];
            int y = i - arr[i];

            arr[i] = (arr[i] % 2);
            adj[i] = new ArrayList<>();

            if (x < n){
                adj[i].add(x);
            }
            if (y >= 0){
                adj[i].add(y);
            }
        }
        solve(adj, dist, arr, mark);

        for (int i = 0; i < n; i++){
            System.out.print(dist[i] +" ");
        }
    }

    private static void solve(ArrayList<Integer>[] adj, int[] dist, int[] arr, boolean[] mark) {
        for (int src = 0; src < arr.length; src++){
            Util(adj, dist, arr, mark, src);
        }
    }

    private static void Util(ArrayList<Integer>[] adj, int[] dist, int[] arr, boolean[] mark, int src) {
        mark[src] = true;
        for (int dest : adj[src]){
            if (arr[dest] != arr[src]){
                dist[src] = 1;
            }
            else if (dist[dest] != -1){ // traverse before
                dist[src] = (dist[src] == -1) ? dist[dest] + 1 : Math.min(dist[src], dist[dest] + 1);
            }
            if (!mark[dest]){ // not mark
                Util(adj, dist ,arr, mark, dest);
                if (dist[dest] != -1){
                    dist[src] = (dist[src] == -1) ? dist[dest] + 1 : Math.min(dist[src], dist[dest] + 1);
                }
            }
        }
    }
}
