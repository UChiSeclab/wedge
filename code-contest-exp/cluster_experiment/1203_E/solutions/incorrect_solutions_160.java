// package Maths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            map.put(a[i],1);
        }
        for(int i=0;i<n;i++){
            map.put(a[i]-1,1);
            map.put(a[i]+1,1);
        }
        if(map.containsKey(0)){
            System.out.println(Math.min(map.size()-1,n));
        }
        else{
            System.out.println(Math.min(map.size(),n));
        }
    }
}
