import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;
 
 
public class hello
 {static class FastReader 
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
    
    static String sum (String s)
    {
        String s1 = ""; 
        
        if(s.contains("a"))
            s1+="a";
        if(s.contains("e"))
            s1+="e";
        if(s.contains("i"))
            s1+="i";
        if(s.contains("o"))
            s1+="o";
        if(s.contains("u"))
            s1+="u";
        
            return s1;
            
        
    }
    
 
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
    
    
public static void main(String args[])
    {
        FastReader input =new FastReader();
      
        int n = input.nextInt();
		int[] a = new int[n];
		
		for(int i=0;i<n;i++)
			a[i]=input.nextInt();
		
		HashMap<Integer,ArrayList<Integer>> hmap = new HashMap<>();
		
		for(int i=0;i<n;i++) {
			hmap.put(i, new ArrayList<Integer>());
		}
		
		for(int i=0;i<n;i++) {
			if(i-a[i]>=0)
				hmap.get(i-a[i]).add(i);
			if(i+a[i]<n)
				hmap.get(i+a[i]).add(i);
		}
		
		Queue<pair> q = new LinkedList<pair>();
		HashSet<Integer> hset = new HashSet<Integer>();
		
		for(int i=0;i<n;i++) {
			if(i-a[i]>=0&&a[i]%2!=a[i-a[i]]%2)
				q.add(new pair(i,1));
			else if(i+a[i]<n&&a[i]%2!=a[i+a[i]]%2)
				q.add(new pair(i,1));
		}
		
		int[] ans = new int[n];
		for(int i=0;i<n;i++)
			ans[i]=-1;
		
		while(q.size()>0) {
			pair p = q.poll();
			
			if(hset.contains(p.v))
				continue;
			
			ans[p.v] = p.i;
			hset.add(p.v);
			
			ArrayList<Integer> ar = hmap.get(p.v);
			for(Integer e:ar) {
				if(a[e]%2==a[p.v]%2&&!hset.contains(e))
					q.add(new pair(e,p.i+1));
			}
		}
		
		for(int i=0;i<n;i++)
			System.out.print(ans[i]+" ");
	}
        
        
        	
        	
    


    
        

        
   
        
        
        
        

	


static long phil(long x)
{
	long phi = x;
    for(long i=2 ; i*i<=x ; i++) {
        if(x%i == 0) {
            while(x%i == 0) {
                x /= i;
            }
            phi = phi/i*(i-1);
        }
    }
    if(x > 1) {
        phi = phi/x*(x-1);
    }
    return phi;
}


static long gcd(long a,long b)
{
	 if (a == 0)  
	        return b;  
	    return gcd(b % a, a);  
}
	
 
 
static boolean find(int a[],int A,int B)
	{
		if( root(a,A)==root(a,B) )       //if A and B have the same root, it means that they are connected.
			return true;
		else
			return false;
	}
 
 
 
 
 
	static void union(int Arr[],int size[],int A,int B)
	{
		int root_A = root(Arr,A);
		int root_B = root(Arr,B);
		
		
		if(root_A == root_B)
		{
			return;
		}
		if(size[root_A] < size[root_B] )
		{
			Arr[ root_A ] = Arr[root_B];
			size[root_B] += size[root_A];
			size[root_A] = 0;	
			
		}
		else
		{
			Arr[ root_B ] = Arr[root_A];
			size[root_A] += size[root_B];
			size[root_B] = 0;
			
 
			
			
			
		}
 
	}
	
	
	
	static int root (int Arr[ ] ,int i)
	{
		while(Arr[ i ] != i)          
        {
			i = Arr[ i ];
        }
        return i;
	}
	static long factorial(long n)
    {
        long fact = 1;
        for(int i=1;i<=n;i++)
        {
            fact*=i;
        }
        return fact;
    }
 }
 
 

	class pair{
		int v;
		int i;
		
		pair(int v,int i){
			this.v = v;
			this.i = i;
		}
	}
 
