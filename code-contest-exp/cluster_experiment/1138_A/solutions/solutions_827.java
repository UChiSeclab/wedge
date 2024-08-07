import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.Scanner; 
import java.util.StringTokenizer; 
import java.util.*;
import java.io.*;
public class codeforces 
{ 
    static int max=Integer.MAX_VALUE;
    static class FastReader 
    { 
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
    public static void main(String[] args){
        //long sum=0;
        int n,m,i,ans=0,j,c_1,c_2,ch_1,ch_2;
        long sum=0;
        FastReader sc=new FastReader();
        n=sc.nextInt();
        int a[]=new int[n];
        for(i=0;i<n;i++)
            a[i]=sc.nextInt();
        c_1=0;
        c_2=0;
        ch_1=0;
        ch_2=0;
        for(i=0;i<n-1;i++){
            if(a[i]==1){
                //else{
                ++c_1;
                //}
            }
                 else if(a[i]==2){
                //else{
                     ++c_2;
            //    }
            }
            ans=Math.max(ans,Math.min(c_1,c_2));
            if(a[i]==1&&a[i+1]!=a[i]){
                c_2=0;
                ch_1=1;
                ch_2=0;
            }
            else if(a[i]==2&&a[i]!=a[i+1]){
                c_1=0;
                ch_2=1;
                ch_1=0;
            }
        }
        if(a[n-1]==1){
            if(ch_2!=0){
                ++c_1;
                ans=Math.max(ans,Math.min(c_1,c_2));
            }
        }
        else{
            if(ch_1!=0){
                ++c_2;
                ans=Math.max(ans,Math.min(c_1,c_2));
            }
        }
        ans=Math.max(ans,Math.min(c_1,c_2));
        System.out.println(ans*2);
    }
}