// Working program with FastReader 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.Scanner; 
import java.util.StringTokenizer;

// import jdk.javadoc.internal.doclets.toolkit.util.links.LinkFactory;

import java.util.*;
import java.io.*;
public class D_Rating_Compression{
    public static void main(String[] args) {
       FastReader sc=new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        int test = sc.nextInt();
        for(int t=0;t<test;t++){
            int n = sc.nextInt();
            int[] array = new int[n];
            HashSet<Integer> store = new HashSet<>();
            for(int i =0;i<n;i++){
                array[i] = sc.nextInt();
                store.add(array[i]);
            }
            int left = 0;
            int right = n-1;
            int[] ans = new int[n];
            // if(array[left]==1 || array[right]==1)ans[n-1]=1;
            int find = 1;

            for(int i =n-1;i>=0;i--){
                if(store.contains(find)){
                    ans[i]=1;
                    if(array[left]==find && array[right]==find){
                        break;
                    }
                    if(array[left]==find){
                        left++;
                    }
                    else if(array[right]==find){
                        right--;
                    }
                    else{
                        break;
                    }
                    find++;
                }
                else{
                    break;
                }
            }
            // check for along
            Arrays.sort(array);
            boolean yes = true;
            for(int i =0;i<n;i++){
                if(array[i]!=i+1){
                    yes = false;
                    break;
                }
            }
            if(yes)ans[0] = 1;
            
            for(int i =0;i<n;i++){
                out.print(ans[i]);
            }
            out.println();
        }
        out.close();
    }

    
    public static class FastReader 
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
                catch (IOException e) 
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
