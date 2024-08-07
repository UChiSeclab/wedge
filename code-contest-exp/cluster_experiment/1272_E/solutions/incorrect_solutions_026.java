// package Quarantine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class NearestOppositeParity {
    public static void main(String[] args)throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        int a[]=new int[n+1];
        StringTokenizer st=new StringTokenizer(br.readLine());
        for(int i=1;i<=n;i++){
            a[i]=Integer.parseInt(st.nextToken());
        }
        int prev=0;
        int numset=0;
        int ans[]=new int[n+1];
        HashSet<Integer> unvisited=new HashSet<>();
        for(int i=1;i<=n;i++){
            int v=a[i];
            int p=a[i]%2;
            if(i-v>=1){
                int q=a[i-v]%2;
                if(p+q==1){
                    ans[i]=1;
                }
            }
            if(i+v<=n){
                int q=a[i+v]%2;
                if(p+q==1){
                    ans[i]=1;
                }
            }
            if(ans[i]==0){
                unvisited.add(i);
            }
            else{
                numset++;
            }
        }
        while(unvisited.size()!=0&&prev!=numset){
            if(n==1000)
            System.out.println(unvisited.toString());
            prev=numset;
            ArrayList<Integer> visited=new ArrayList<>();
            for(int i:unvisited){
                int v=a[i];
                int p=a[i]%2;
                int temp1=Integer.MAX_VALUE,temp2=Integer.MAX_VALUE;
                if(i-v>=1){
                    int q=a[i-v]%2;
                    if(p==q&&ans[i-v]!=0){
                        temp1=ans[i-v]+1;
                    }
                }
                if(i+v<=n){
                    int q=a[i+v]%2;
                    if(p==q&&ans[i+v]!=0){
                        temp2=ans[i+v]+1;
                    }
                }
                int min=Math.min(temp1,temp2);
                if(min!=Integer.MAX_VALUE){
                    ans[i]=min;
                    numset++;
                    visited.add(i);
                }
            }
            for(int i:visited){
                unvisited.remove(i);
            }
        }
        StringBuilder print=new StringBuilder();
        for(int i=1;i<=n;i++){
            if(ans[i]==0){
                print.append("-1 ");
            }
            else{
                print.append(ans[i]+" ");
            }
        }
        System.out.println(print.toString());
    }
}
