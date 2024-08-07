import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Collections;
import java.util.Set;
import java.util.Map.Entry;
public class Codeforces
{
    public static void main(String[] args) throws java.lang.Exception
    {
        FastReader ob=new FastReader();
            int t=ob.nextInt();
            while(t-->0)
            {
                int n=ob.nextInt();
                String str=ob.next();
        ArrayList<Integer> ans=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        Map<Double,Integer> map=new HashMap<>();
        double r=0;double d=0;double k=0;
        for(int i=0;i<n;i++)
        {
            if(str.charAt(i)=='D') {
                d++;
            }
            else {
                k++;
            }
 
            if(k==0)
                r=0;
            else
                r=d/k;
 
            if(!map.containsKey(r)) {
                sb.append(1+" ");
                map.put(r,1);
            }
            else {
                int val = map.get(r);
                sb.append((val+1)+" ");
                map.put(r,val+1);
            }
        }
        System.out.println(sb);
            
            
            }
    }
}
class FastReader
	{
	    BufferedReader br;
	    StringTokenizer st;
	    public FastReader()
	    {
	        br=new BufferedReader(new InputStreamReader(System.in));
	    }
	    String next()
	    {
	        while(st==null || !st.hasMoreElements())
	        {
	            try {
	                st=new StringTokenizer(br.readLine());
	                
	            } catch(Exception e) {
	             e.printStackTrace();
	            }
	        }
	        return st.nextToken();
	    }
	    int nextInt()
	    {
	        return Integer.parseInt(next());
	    }
	    double nextDouble()
	    {
	        return Double.parseDouble(next());
	    }
	    long nextLong()
	    {
	        return Long.parseLong(next());
	    }
	    String nextLine()
	    {
	        String str="";
	        try
	        {
	            str=br.readLine();
	        }
	        catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	        return str;
	    }
	}