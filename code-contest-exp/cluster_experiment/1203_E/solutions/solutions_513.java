import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


public class E{
	
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
	
	public static void main(String[] args) 
	{
		OutputStream outputStream = System.out;
        FastReader sc = new FastReader();
        PrintWriter out = new PrintWriter(outputStream);
        
        int n = sc.nextInt();
        int a[] = new int[n];
        
        
        boolean vis[] = new boolean[150002];
        int map[] = new int[150002];
        
        Arrays.fill(vis, false);
        for(int i = 0; i < n; i++)
        {
        	int x = sc.nextInt();
        	map[x]++;
        }
        
        for(int i = 1; i < map.length-1; i++)
        {
        	if(map[i] >= 3)
        	{
        		if(i-1 > 0)
        		{
        			vis[i-1] = true;
        		}
        		vis[i] = true;
        		vis[i+1] = true;
        	}
        	
        	else if(map[i] == 2)
        	{	
        		int count = 2;
        		if(i-1 > 0 && !vis[i-1])
        		{
        			vis[i-1] = true;
        			count--;
        		}
        		if(!vis[i])
        		{
        			vis[i] = true;
        			count--;
        		}
        		if(!vis[i+1] && count > 0)
        		{
        			vis[i+1] = true;
        		}
        	}
        	else if(map[i] == 1)
        	{
        		int count = 1;
        		if(i-1 > 0 && !vis[i-1])
        		{
        			vis[i-1] = true;
        			count--;
        		}
        		if(!vis[i] && count > 0)
        		{
        			vis[i] = true;
        			count--;
        		}
        		if(!vis[i+1] && count > 0)
        		{
        			vis[i+1] = true;
        		}
        	}
        }
        
        int count = 0;
        for(int i = 0; i < vis.length; i++)
        {
        	if(vis[i]) count++;
        }
        /*Arrays.sort(a);
        
        if(a[0] > 1)
        {
        	a[0]--;
        }
        for(int i = 1; i < n; i++)
        {
        	if(a[i]-1 > a[i-1])
        	{
        		a[i]--;
        	}
        	else if(a[i] > a[i-1])
        	{
        		a[i] = a[i];
        	}
        	else
        	{
        		a[i]++;
        	}
        }
        
        int count = 1;
        //System.out.println(Arrays.toString(a));
        for(int i = 1; i < n; i++)
        {	
        	if(a[i] != a[i-1])
        		count++;
        }
        
        out.println(count);
        */
        
        out.println(count);
        out.close();
	}

}
