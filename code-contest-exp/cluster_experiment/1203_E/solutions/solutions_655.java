  import java.util.*;
import java.lang.*;
import java.io.*;

public class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		FastReader sc=new FastReader();
		int n=sc.nextInt();
		int a[]=new int[n];
		int b[]=new int[150002];
		int c[]=new int[150002];
		for(int i=0;i<n;i++)
		{a[i]=sc.nextInt();
		b[a[i]]++;}
		int t=0;
		for(int i=1;i<150002;i++)
		{
		    if(i==1 && b[i]>=1)
		    {
		       c[1]++;
		       t++;
		       b[i]--;
		       if(b[i]>=1)
		       {
		       c[2]++;
		       t++;}
		    }
		    else if(b[i]>=1)
		    {
		        if(c[i-1]==0)
		        {c[i-1]++;
		        t++;
		            b[i]--;
		        }
		        if(b[i]>=1 && c[i]==0)
		        {c[i]++;
		        t++;
		            b[i]--;
		        }
		        if(b[i]>=1)
		        {
		            c[i+1]++;
		            t++;
		        }
		    }
		}
		System.out.println(t);
	 }
	 
	 
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
    static long gcd(long a,long b)
		{
		    if(b==0)
		    return a;
		    return gcd(b,a%b);
		}
		
	static ArrayList<Integer> sieve(int n) 
    { 
        ArrayList<Integer> arr=new ArrayList<Integer>();
        boolean prime[] = new boolean[n+1]; 
        for(int i=0;i<n;i++) 
            prime[i] = true; 
          
        for(int p = 2; p*p <=n; p++) 
        { 
            if(prime[p] == true) 
            { 
                for(int i = p*p; i <= n; i += p) 
                    prime[i] = false; 
            } 
        } 
          
        for(int i = 2; i <= n; i++) 
        { 
            if(prime[i] == true) 
                arr.add(i); 
        } 
        return arr;
    } 
      
	static float power(float x, int y) 
    { 
        float temp; 
        if( y == 0) 
            return 1; 
        temp = power(x, y/2);  
          
        if (y%2 == 0) 
            return temp*temp; 
        else
        { 
            if(y > 0) 
                return x * temp * temp; 
            else
                return (temp * temp) / x; 
        } 
    } 
    static long pow(int a,int b)
    {
        long result=1;
        if(b==0)
        return 1;
        long x=a;
        while(b>0)
        {
            if(b%2!=0)
            result*=x;
            
            x=x*x;
            b=b/2;
        }
        return result;
    }
}