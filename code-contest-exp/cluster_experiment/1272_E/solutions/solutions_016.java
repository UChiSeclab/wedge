// package Quarantine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class NearestOppositeParity {
    public static void main(String[] args)throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        int a[]=new int[n+1];
        StringBuilder print=new StringBuilder();
        StringTokenizer st=new StringTokenizer(br.readLine());
        ArrayList<Integer> graph[]=new ArrayList[n+1];
        for(int i=1;i<=n;i++){
            graph[i]=new ArrayList<>();
        }
        for(int i=1;i<=n;i++){
            a[i]=Integer.parseInt(st.nextToken());
            int r=i+a[i];
            int l=i-a[i];
            if(l>=1){
                graph[l].add(i);
            }
            if(r<=n){
                graph[r].add(i);
            }
        }
        int ans[]=new int[n+1];
        Arrays.fill(ans,-1);
        Queue<Integer> queue=new LinkedList<>();
        int d[]=new int[n+1];
        boolean visited[]=new boolean[n+1];
        for(int i=1;i<=n;i++){
            if(a[i]%2==0){
                queue.add(i);
                visited[i]=true;
            }
        }
        while(!queue.isEmpty()){
            int curr=queue.remove();
            for(int j:graph[curr]){
                if(!visited[j]){
                    ans[j]=d[curr]+1;
                    queue.add(j);
                    visited[j]=true;
                    d[j]=d[curr]+1;
                }
            }
        }
        queue=new LinkedList<>();
        d=new int[n+1];
        visited=new boolean[n+1];
        for(int i=1;i<=n;i++){
            if(a[i]%2==1){
                queue.add(i);
                visited[i]=true;
            }
        }
        while(!queue.isEmpty()){
            int curr=queue.remove();
            for(int j:graph[curr]){
                if(!visited[j]){
                    ans[j]=d[curr]+1;
                    queue.add(j);
                    visited[j]=true;
                    d[j]=d[curr]+1;
                }
            }
        }
        for(int i=1;i<=n;i++){
            print.append(ans[i]+" ");
        }
        System.out.println(print.toString());
    }
}
