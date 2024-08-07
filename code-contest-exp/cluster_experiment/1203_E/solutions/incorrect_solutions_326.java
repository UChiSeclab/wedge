// package Maths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Boxers {
    public static void main(String[] args)throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        int a[]=new int[n];
        HashMap<Integer,Integer> map=new HashMap<>();
        String line[]=br.readLine().split(" ");
        for(int i=0;i<n;i++){
            a[i]=Integer.parseInt(line[i]);
//            if(!map.containsKey(a[i])){
//                map.put(a[i],1);
//            }
//            else{
//                map.put(a[i],map.get(a[i])+1);
//            }
        }
        HashMap<Integer,Integer> temp=new HashMap<>();
        // Arrays.sort(a);
        for(int i=0;i<n;i++){
//            System.out.println(temp.size());
            if(!temp.containsKey(a[i]-1)&&a[i]!=1){
                temp.put(a[i]-1,0);
                continue;
            }
            if(!temp.containsKey(a[i])){
                temp.put(a[i],1);
                continue;
            }
            if(!temp.containsKey(a[i]+1)){
                temp.put(a[i]+1,1);
                continue;
            }
//            System.out.println(temp.size());
        }
        if(temp.containsKey(0)){
            System.out.println(Math.min(temp.size()-1,n));
        }
        else{
            System.out.println(Math.min(temp.size(),n));
        }
    }
}
