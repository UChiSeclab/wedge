import java.util.*;
import java.io.*;
import static java.lang.Integer.*;
public class main{
    static int dist[];
    static boolean vis[];
    static Map<Integer,ArrayList<Integer>> hmap;
    static void bfs(int src,int n){
        Queue<Integer> q=new LinkedList<>();
        q.add(src);
        while(!q.isEmpty()){
            int curr=q.poll();
            vis[curr]=true;
            if(hmap.containsKey(curr)){
                for(int x:hmap.get(curr)){
                    if(!vis[x]){
                        dist[x]=min(dist[x],dist[curr]+1);
                        q.add(x);
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        int n=parseInt(in.readLine());
        String ss[]=in.readLine().split(" ");
        int arr[]=new int[n];
        for(int i=0;i<n;i++)
            arr[i]=parseInt(ss[i]);
        vis=new boolean[n];
        dist=new int[n];
        Arrays.fill(dist,MAX_VALUE);
        hmap = new HashMap<>();
        for(int i=0;i<n;i++){
            int ai=i-arr[i];
            int bi=i+arr[i];
            if(ai>=0){
                if(!hmap.containsKey(ai))
                    hmap.put(ai,new ArrayList<>());
                hmap.get(ai).add(i);
            }
            
            if(bi<n){
                if(!hmap.containsKey(bi))
                    hmap.put(bi,new ArrayList<>());
                hmap.get(bi).add(i);
            }
            if(ai>=0 && arr[i]%2!=arr[ai]%2){
                dist[i]=1;
            }
            if(bi<n && arr[i]%2!=arr[bi]%2){
                dist[i]=1;
            }
        }
        
        for(int i=0;i<n;i++){
            if(!vis[i]){
                bfs(i,n);
            }
        }
        StringBuilder sb=new StringBuilder("");
        for(int x:dist){
            if(x==MAX_VALUE){
                sb.append("-1 ");
            }
            else
                sb.append(x+" ");
        }
        System.out.println(sb);
    }
}
