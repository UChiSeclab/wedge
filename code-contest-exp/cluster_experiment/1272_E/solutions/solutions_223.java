// package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;
//****Use Integer Wrapper Class for Arrays.sort()****
public class BB5 {
    public static void main(String[] Args){
        FastReader scan=new FastReader();
        int n=scan.nextInt();
        int[] arr=new int[n];
        for (int i = 0; i <n ; i++) {
            arr[i]=scan.nextInt();
        }
        int[] ans=new int[n];
        for(int i=0;i<n;i++){
            ans[i]=Integer.MAX_VALUE;
        }
        Node[] a=new Node[n];
        for(int i=0;i<n;i++){
            a[i]=new Node();
        }
        for(int i=0;i<n;i++){
            int l=i-arr[i];
            int r=i+arr[i];
            if(l>=0){
                a[l].ns.add(i);
            }
            if(r<n){
                a[r].ns.add(i);
            }
        }
        Queue<Integer> q=new LinkedList<>();
        Set<Integer> s=new HashSet<>();
        for(int i=0;i<n;i++){
            int m=arr[i]%2;
            int l=i-arr[i];
            int r=i+arr[i];
            boolean add=false;
            if(l>=0){
                int lm=arr[l]%2;
                if(lm!=m){
                    add=true;
                }
            }if(r<n){
                int rm=arr[r]%2;
                if(rm!=m){
                    add=true;
                }
            }
            if(add){
                ((LinkedList<Integer>) q).add(i);
                s.add(i);
            }
        }
        int d=1;
        ((LinkedList<Integer>) q).add(null);
        while(q.size()>1){
            Integer cur=q.remove();
            if(cur==null){
                d++;
                ((LinkedList<Integer>) q).add(null);
                continue;
            }
            ans[cur]=d;
            for(Integer i:a[cur].ns){
                if(!s.contains(i)){
                    s.add(i);
                    ((LinkedList<Integer>) q).add(i);
                }
            }
        }
        StringBuilder print=new StringBuilder();
        for(int i=0;i<n;i++){
            if(ans[i]==Integer.MAX_VALUE){
                print.append("-1 ");
            }
            else{
                print.append(ans[i]+" ");
            }
        }
        System.out.println(print);
    }
    static class Node{
        ArrayList<Integer> ns;
        Node(){
            ns=new ArrayList<>();
        }
    }
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt()
        {
            return Integer.parseInt(next());
        }

        long nextLong()
        {
            return Long.parseLong(next());
        }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }

}
