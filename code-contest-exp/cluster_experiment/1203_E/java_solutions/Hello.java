import java.io.BufferedReader;
 
import java.io.IOException; 
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner; 
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Collections;
  
public class Hello
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
  
    public static void main(String[] args) 
    { 
        FastReader in=new FastReader(); 
        
        
        //start code here
        int n= in.nextInt();
        ArrayList<Integer> a = new ArrayList<>();
        for(int i=0;i<n;++i)
        	a.add(in.nextInt());
        TreeSet<Integer> ts = new TreeSet<>();
        Collections.sort(a);
        for(int i=0;i<n;++i)
        {
        	int x=a.get(i);
        	if(!ts.contains(x-1)&&(x!=1))
        		ts.add(x-1);
        	else if(!ts.contains(x))
        		ts.add(x);
        	else if(!ts.contains(x+1))
        		ts.add(x+1);
        }
        System.out.print(ts.size());
    }
} 