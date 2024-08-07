//package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.*;

public class CF_1203_E {
    public static void main(String args[]) throws Exception {
//        Scanner sc=new Scanner(System.in);
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        int a[]=new int[n];
        StringTokenizer st=new StringTokenizer(br.readLine());
//        int nums[]=new int[150001];
        TreeMap<Integer,Integer> hm=new TreeMap<>();
        for(int i=0;i<n;++i) {
            a[i] = Integer.parseInt(st.nextToken());
//            nums[a[i]]++;
            hm.put(a[i],hm.getOrDefault(a[i],0)+1);
        }

//        boolean nos[]=new boolean[150002];
        HashSet<Integer> hs=new HashSet<>();
        int count=0;
        for(Map.Entry<Integer,Integer> entry:hm.entrySet()) {
            int i=entry.getKey();
            int val=entry.getValue();
            if(i>1&&!hs.contains(i-1)) {
//                nos[a[i] - 1] = true;
                hs.add(i-1);
                count++;
                val--;
            }
            if(val>0&&!hs.contains(i)) {
//                nos[a[i]] = true;
                hs.add(i);
                count++;
                val--;
            }
            if(val>0&&!hs.contains(i+1)) {
//                nos[a[i] + 1 = true;
                hs.add(i+1);
                count++;
            }

        }
        System.out.println(count);
    }
}
