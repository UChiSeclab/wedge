// package Div3605;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class ProblemE {
    public static void print(int a[],int n){
        for(int i=1;i<=n;i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
    public static void solve(int a[],HashMap<Integer,Integer> map,int n,int b[]){
        HashMap<Integer,Integer> naksha=(HashMap<Integer, Integer>)map.clone();
        for(int i:naksha.keySet()){
            int left=i-a[i];
            int right=i+a[i];
            if(left>=0){
                if(a[i]%2==0&&a[left]%2==1){
                    b[i]=1;
                    map.remove(i);
                    continue;
                }
                else if(a[i]%2==1&&a[left]%2==0){
                    b[i]=1;
                    map.remove(i);
                    continue;
                }
            }
            if(right<=n){
                if(a[i]%2==0&&a[right]%2==1){
                    b[i]=1;
                    map.remove(i);
                    continue;
                }
                else if(a[i]%2==1&&a[right]%2==0){
                    b[i]=1;
                    map.remove(i);
                    continue;
                }
            }
            if(left<0&&right>n){
                b[i]=-1;
                map.remove(i);
                continue;
            }
            int op1=Integer.MAX_VALUE,op2=Integer.MAX_VALUE;
            if(left>=0&&b[left]!=0) {
                op1=b[left];
            }
            if(right<=n&&b[right]!=0) {
                op2=b[right];
            }
            if(op1==Integer.MAX_VALUE&&op2==Integer.MAX_VALUE){
                b[i]=0;
            }
            else if(op1==-1&&op2==-1){
                b[i]=-1;
                map.remove(i);
            }
            else {
                b[i] = Math.min(op1, op2)+1;
                map.remove(i);
            }
        }
    }
    public static void main(String[] args)throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        String line[]=br.readLine().split(" ");
        int a[]=new int[n+1];
        for(int i=1;i<=n;i++){
            a[i]=Integer.parseInt(line[i-1]);
        }
        int b[]=new int[n+1];
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=1;i<=n;i++){
            map.put(i,1);
        }
        while(map.size()!=0){
            solve(a,map,n,b);
        }
        StringBuilder ans=new StringBuilder();
        for(int i=1;i<=n;i++){
            ans.append(b[i]+" ");
        }
        System.out.println(ans.toString());
    }
}
