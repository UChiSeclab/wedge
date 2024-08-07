// package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;
//****Use Integer Wrapper Class for Arrays.sort()****
public class GI4 {
    static PrintWriter out=new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] Args)throws Exception{
        FastReader scan=new FastReader(System.in);
        int t=1;
        t=scan.nextInt();
        while(t-->0){
            int n=scan.nextInt();
            int[] arr=new int[n];
            int[] f=new int[n+1];
            int c=0;
            for(int i=0;i<n;i++){
                arr[i]=scan.nextInt();
                f[arr[i]]++;
                if(f[arr[i]]==1){
                    c++;
                }
            }
            StringBuilder ans=new StringBuilder();
            for(int i=0;i<n;i++){
                ans.append('0');
            }
            if(c==n){
                ans.setCharAt(0,'1');
            }
            int l=0;
            int r=n-1;
            for(int i=1;i<=n;i++){
                if(f[i]>0){
                    ans.setCharAt(n-i,'1');
                    if(f[i]>1){
                        break;
                    }
                    if(arr[l]==i){
                        l++;
                        continue;
                    }
                    if(arr[r]==i){
                        r--;
                        continue;
                    }
                    break;
                }else{
                    break;
                }
            }
            out.println(ans);
        }
        out.flush();
        out.close();
    }
    static class FastReader {

        byte[] buf = new byte[2048];
        int index, total;
        InputStream in;

        FastReader(InputStream is) {
            in = is;
        }

        int scan() throws IOException {
            if (index >= total) {
                index = 0;
                total = in.read(buf);
                if (total <= 0) {
                    return -1;
                }
            }
            return buf[index++];
        }

        String next() throws IOException {
            int c;
            for (c = scan(); c <= 32; c = scan()) ;
            StringBuilder sb = new StringBuilder();
            for (; c > 32; c = scan()) {
                sb.append((char) c);
            }
            return sb.toString();
        }

        int nextInt() throws IOException {
            int c, val = 0;
            for (c = scan(); c <= 32; c = scan()) ;
            boolean neg = c == '-';
            if (c == '-' || c == '+') {
                c = scan();
            }
            for (; c >= '0' && c <= '9'; c = scan()) {
                val = (val << 3) + (val << 1) + (c & 15);
            }
            return neg ? -val : val;
        }
        long nextLong() throws IOException {
            int c;
            long val = 0;
            for (c = scan(); c <= 32; c = scan()) ;
            boolean neg = c == '-';
            if (c == '-' || c == '+') {
                c = scan();
            }
            for (; c >= '0' && c <= '9'; c = scan()) {
                val = (val << 3) + (val << 1) + (c & 15);
            }
            return neg ? -val : val;
        }
    }
}
