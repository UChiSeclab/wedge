import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main{
    static int ans[];
    public static void main(String agr[]) throws Exception {
        FastReader sc = new FastReader();
        int n=sc.nextInt();
        ans=new int[n];
        Arrays.fill(ans,-1);
        ArrayList<Integer> odd=new ArrayList<>(),even=new ArrayList<>();
        int arr[]=new int[n];
        for (int i=0;i<n;i++){
            arr[i]=sc.nextInt();
            if ((arr[i]&1)!=0)odd.add(i);
            else even.add(i);
        }
        ArrayList<Integer> adj[]=new ArrayList[n];
        for (int i=0;i<n;i++)adj[i]=new ArrayList<>();
        for (int i=0;i<n;i++){
            int l=i-arr[i],r=i+arr[i];
            if (l>=0)adj[l].add(i);
            if (r<n)adj[r].add(i);
        }
        bfs(odd,even,adj,n);
        bfs(even,odd,adj,n);
        for (int i=0;i<n;i++){
            System.out.print(ans[i]+" ");
        }
        System.out.println();

    }
    static void bfs(ArrayList<Integer> src,ArrayList<Integer> end,ArrayList<Integer> adj[],int n){
        int res[]=new int[n];
        Arrays.fill(res,Integer.MAX_VALUE);
        LinkedList<Integer> q=new LinkedList<>();
        for (int x:src){
            res[x]=0;
            q.add(x);
        }
        while (!q.isEmpty()){
            int v=q.pop();
            for (int u:adj[v]){
                if (res[u]==Integer.MAX_VALUE){
                    res[u]=res[v]+1;
                    q.add(u);
                }
            }
        }
        for (int x:end){
            if (res[x]!=Integer.MAX_VALUE)ans[x]=res[x];
        }

    }
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}