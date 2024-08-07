import java.util.*;
import java.io.*;
 
public class Solution{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // int t=Integer.parseInt(br.readLine());
        // while(t!=0){
            int n=Integer.parseInt(br.readLine());
            int[] arr=new int[n];
            String line=br.readLine();
            // String s2=br.readLine();
            String[] strs=line.trim().split(" ");
            for(int i=0;i<arr.length;i++){
                arr[i]=Integer.parseInt(strs[i]);
            }
            // int c=find(arr,0,new HashMap<>());
            // System.out.println(c);
            System.out.println(find(arr));
        //     t--;
        // }
    }
    public static int find(int[] arr){
        HashMap<Integer,Integer> map=new HashMap<>();
        Arrays.sort(arr);
        for(int i=0;i<arr.length;i++){
            if(map.containsKey(arr[i])){
                int v=map.get(arr[i]);
                map.put(arr[i],v+1);
            }else{
                map.put(arr[i],1);
            }
        }
        int mi=arr[0];
        int ma=arr[arr.length-1];
        for(int i=mi-1;i<=ma+1;i++){
            if(i<=0){
                continue;
            }
            if(map.containsKey(i)){
                continue;
            }
            int v1=i-1;
            int v2=i+1;
            if(map.containsKey(v1)&&map.containsKey(v2)){
                int s1=map.get(v1);
                int s2=map.get(v2);
                if(s1<s2){
                    if(s2==1){
                        map.remove(v2);
                    }else{
                        map.put(v2,s2-1);
                    }
                    map.put(i,1);
                }else{
                    if(s1==1){
                        map.remove(v1);
                    }else{
                        map.put(v1,s1-1);
                    }
                    map.put(i,1);
                }
            }else if(map.containsKey(v1)){
                int s1=map.get(v1);
                if(s1==1){
                    map.remove(v1);
                }else{
                    map.put(v1,s1-1);
                }
                map.put(i,1);
            }else if(map.containsKey(v2)){
                int s2=map.get(v2);
                if(s2==1){
                    map.remove(v2);
                }else{
                    map.put(v2,s2-1);
                }
                map.put(i,1);
            }else{
                continue;
            }
        }
        return map.size();
    }
    public static int find(int[] arr,int index,HashMap<Integer,Integer> map){
        if(index>=arr.length){
            return map.size();
        }
        int c=Integer.MIN_VALUE;
        
        //-1
        if(arr[index]>1){
            if(map.containsKey(arr[index]-1)){
                int v=map.get(arr[index]-1);
                map.put(arr[index]-1,v+1);
            }else{
                map.put(arr[index]-1,1);
            }
            c=Math.max(c,find(arr,index+1,map));
            int v=map.get(arr[index]-1);
            if(v==1){
                map.remove(arr[index]-1);
            }else{
                map.put(arr[index]-1,v-1);
            }
        }
        
        //normal
        if(map.containsKey(arr[index])){
            int v=map.get(arr[index]);
            map.put(arr[index],v+1);
        }else{
            map.put(arr[index],1);
        }
        c=Math.max(c,find(arr,index+1,map));
        int v=map.get(arr[index]);
        if(v==1){
            map.remove(arr[index]);
        }else{
            map.put(arr[index],v-1);
        }
        
        
        //+1
        if(map.containsKey(arr[index]+1)){
            v=map.get(arr[index]+1);
            map.put(arr[index]+1,v+1);
        }else{
            map.put(arr[index]+1,1);
        }
        c=Math.max(c,find(arr,index+1,map));
        v=map.get(arr[index]+1);
        if(v==1){
            map.remove(arr[index]+1);
        }else{
            map.put(arr[index]+1,v-1);
        }
        return c;
    }
}