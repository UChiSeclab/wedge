import java.util.*;
import java.io.*;
 
public class Solution{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        String line=br.readLine();
        String[] str=line.trim().split(" ");
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=Integer.parseInt(str[i]);
        }
        // Arrays.sort(arr);
        TreeMap<Integer,Integer> map=new TreeMap<>();
        for(int i=0;i<arr.length;i++){
            if(map.containsKey(arr[i])){
                int v=map.get(arr[i]);
                map.put(arr[i],v+1);
            }else{
                map.put(arr[i],1);
            }
        }
        int c=0;
        HashSet<Integer> set=new HashSet<>();
        for(int k:map.keySet()){
            int v=map.get(k);
            if(v==1){
                int v1=k-1;
                int v2=k+1;
                boolean x1=set.contains(v1);
                boolean x2=set.contains(v2);
                boolean x3=set.contains(k);
                int z=0;
                if(v1>0&&!x1){
                    z++;
                    set.add(v1);
                }if(z<1&&!x3){
                    z++;
                    set.add(k);
                }
                if(z<1&&!x2){
                    set.add(v2);
                }
            }else if(v==2){
                int v1=k-1;
                int v2=k+1;
                boolean x1=set.contains(v1);
                boolean x2=set.contains(v2);
                boolean x3=set.contains(k);
                int z=0;
                if(v1>0&&!x1){
                    z++;
                    set.add(v1);
                }if(!x3){
                    z++;
                    set.add(k);
                }
                if(z<2&&!x2){
                    set.add(v2);
                }
            }else{
                int v1=k-1;
                int v2=k+1;
                boolean x1=set.contains(v1);
                boolean x2=set.contains(v2);
                boolean x3=set.contains(k);
                // int z=0;
                if(v1>0&&!x1){
                    set.add(v1);
                }if(!x3){
                    set.add(k);
                }
                if(!x2){
                    set.add(v2);
                }
            }
        }
        System.out.println(set.size());
    }
}