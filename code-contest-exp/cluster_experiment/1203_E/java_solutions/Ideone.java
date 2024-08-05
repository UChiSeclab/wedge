import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.*; 
import java.util.StringTokenizer; 
import java.io.PrintWriter;
import java.io.*;
import java.lang.*;
public class Ideone
{ 
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
    static class aksh 
{
 
    int x, y;
    public aksh(int a, int b) 
    {
        x = a;
        y = b;
    }
}
static long power(long x,long y) 
{ 
    long res = 1;      // Initialize result 
  
      // Update x if it is more than or  
                // equal to p 
  
    while (y > 0) 
    { 
        // If y is odd, multiply x with result 
        if (y%2==1) 
            res = (res*x); 
  
        // y must be even now 
        y = y>>1; // y = y/2 
        x = (x*x);   
    } 
    return res; 
} 

    public static void main (String[] args) throws java.lang.Exception
    {
        // your code goes here
        FastReader sc=new FastReader();
        int n=sc.nextInt();
        Integer[] a=new Integer[n];

        for(int i=0;i<n;i++)
        {
            a[i]=sc.nextInt();
        }

        Arrays.sort(a);
        int x= 150010;
        int ans=0;
        boolean[] visited = new boolean[x];
        for(int i=0;i<n;i++)
        {
           if((a[i]-1)>0 && visited[a[i]-1]==false)
           {
            ans++;
            visited[a[i]-1]=true;
           }
           else if(visited[a[i]]==false)
           {
            ans++;
            visited[a[i]]=true;
           }
           else if(visited[a[i]+1]==false)
           {
            ans++;
            visited[a[i]+1]=true;
           }
        }
        
        System.out.println(ans);


    }
} 