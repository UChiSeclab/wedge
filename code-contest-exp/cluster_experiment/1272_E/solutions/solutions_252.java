import java.io.*;
import java.util.*;

public class Main {
    static PrintWriter out = new PrintWriter(System.out);
    static Scanner sc = new Scanner(System.in);
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    long mod = 998244353;
    long[] debt;
    int[] a;
    int n;
    long[][] dp;
    boolean[][] vis;
    List<Integer>[] graph;
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.solve();
//        out.println(main.solve());
        out.flush();
    }
    void solve() throws IOException {
        n = sc.nextInt();
        a = new int[n];
        graph = new List[n];
        for(int i=0; i<n; i++) graph[i] = new LinkedList<>();
        for(int i=0; i<n; i++) {
            a[i] = sc.nextInt();
            if(i-a[i]>=0) graph[i-a[i]].add(i);
            if(i+a[i]<n) graph[i+a[i]].add(i);
        }
        int[] odd = new int[n], even = new int[n];
        helper(odd,1); helper(even, 0);
        for(int i=0; i<n; i++){
            int ans;
            if(a[i]%2==1){
                ans = even[i];
            } else ans = odd[i];
            out.print(ans); out.print(' ');
        }
    }
    void helper(int[] dist, int pari){
        Arrays.fill(dist, -1);
        boolean[] vis = new boolean[n];
        LinkedList<Integer> cur = new LinkedList<>(), next = new LinkedList<>();
        for(int i=0; i<n; i++){
            if(a[i]%2==pari){
                dist[i] = 0;
                vis[i] = true;
                cur.add(i);
            }
        }
        int step = 1;
        while(cur.size()>0){
            for(int now:cur){
                for(int to:graph[now]){
                    if(!vis[to]){
                        vis[to] = true;
                        dist[to] = step;
                        next.add(to);
                    }
                }
            }
            cur = next; next= new LinkedList<>(); step++;
        }
    }
}