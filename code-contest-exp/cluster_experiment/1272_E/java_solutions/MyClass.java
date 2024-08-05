import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;
public class MyClass {
    static void bfs(Queue<Integer> q, ArrayList<Integer>[] g, int[] dis) {
        while(!q.isEmpty()) {
            int x=q.poll();
            for(int i:g[x]) {
                if(dis[i]==Integer.MAX_VALUE) {
                    dis[i]=dis[x]+1;
                    q.add(i);
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        PrintWriter pw=new PrintWriter(System.out);
        int n=sc.nextInt();
        int[] a=new int[n];
        for(int i=0;i<n;i++)
            a[i]=sc.nextInt();
        ArrayList<Integer>[] g=new ArrayList[n];
        for(int i=0;i<n;i++)
            g[i]=new ArrayList<>();
        for(int i=0;i<n;i++) {
            if((i+a[i])<n)
                g[i+a[i]].add(i);
            if((i-a[i])>=0)
                g[i-a[i]].add(i);
        }
        HashSet<Integer> odd=new HashSet<>();
        HashSet<Integer> even=new HashSet<>();
        for(int i=0;i<n;i++) {
            if((a[i]%2)==0)
                even.add(i);
            else
                odd.add(i);
        }
        int[] dis=new int[n];
        int[] ans=new int[n];
        Arrays.fill(dis,Integer.MAX_VALUE);
        Queue<Integer> q=new LinkedList<>();
        for(int i:odd) {
            dis[i]=0;
            q.add(i);
        }
        bfs(q, g, dis);
        for(int i=0;i<n;i++) {
            if(dis[i]==Integer.MAX_VALUE)
                ans[i]=-1;
            else
                ans[i]=dis[i];
        }
        Arrays.fill(dis,Integer.MAX_VALUE);
        q=new LinkedList<>();
        for(int i:even) {
            q.add(i);
            dis[i]=0;
        }
        bfs(q, g, dis);
        for(int i=0;i<n;i++) {
            if(ans[i]==0) {
                if(dis[i]==Integer.MAX_VALUE)
                    ans[i]=-1;
                else
                    ans[i]=dis[i];
            }
        }
        for(int i=0;i<n;i++)
            pw.print(ans[i]+" ");
        pw.close();
    }
}