import java.util.*;
import java.io.*;
public class E605
{
	public static void main(String [] args)   
	{
		MyScanner sc = new MyScanner();
	    PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
	    int n = sc.nextInt();
	    int [] arr = new int [n+1];
	    ArrayList <Integer> [] adj = new ArrayList [n+1];
	    ArrayList <Integer> odd = new ArrayList <Integer> ();
	    ArrayList <Integer> even = new ArrayList <Integer> ();
	    for (int i = 1; i <= n; i++)
	    {
	    	adj[i] = new ArrayList <Integer> ();
	    }
	    for (int i = 1; i <= n; i++)
	    {
	    	int num = sc.nextInt();
	    	arr[i] = num;
	    	if (num % 2 == 0) even.add(i); else odd.add(i);
	    	if (i - num >= 1)
	    	{
	    		adj[i-num].add(i);
	    	}
	    	
	    	if (i + num <= n)
	    	{
	    		adj[i+num].add(i);
	    	}
	    }
	    
    	boolean [] visited = new boolean [n+1];
    	int [] depth = new int [n+1];
    	Arrays.fill(depth, Integer.MAX_VALUE);
    	LinkedList <Integer> q = new LinkedList <Integer> ();
    	for (Integer i: even)
    	{
    		q.add(i);
    		depth[i] = 0;
    	}
    	while (q.size() > 0)
    	{
    		int s = q.remove();
    		visited[s] = true;
    		for (Integer a: adj[s])
    		{
    			if (!visited[a])
    			{
    				q.add(a);
    				depth[a] = Math.min(depth[a], depth[s] + 1);
    			}
    		}
    	}
    	
    	q.clear();
    	visited = new boolean [n+1];
    	int [] depth2 = new int [n+1];
    	Arrays.fill(depth2, Integer.MAX_VALUE);
    	for (Integer i: odd)
    	{
    		q.add(i);
    		depth2[i] = 0;
    	}
    	while (q.size() > 0)
    	{
    		int s = q.remove();
    		visited[s] = true;
    		for (Integer a: adj[s])
    		{
    			if (!visited[a])
    			{
    				q.add(a);
    				depth2[a] = Math.min(depth2[a], depth2[s] + 1);
    			}
    		}
    	}
    	
    	for (int i = 1; i <= n; i++)
    	{
    		if (depth2[i] == 0)
    		{
    			if (depth[i] == Integer.MAX_VALUE) depth[i] = -1;
    			depth2[i] = depth[i];
    		}
    	}
	    
	    StringBuilder s = new StringBuilder("");
	    for (int i = 1; i <= n; i++)
	    {
	    	s.append(depth2[i] + " ");
	    }
	    out.println(s.toString());
	    out.close();
	}
		      
	   //-----------MyScanner class for faster input----------
	   public static class MyScanner
	   {
	      BufferedReader br;
	      StringTokenizer st;
	 
	      public MyScanner() {
	         br = new BufferedReader(new InputStreamReader(System.in));
	      }
	 
	      String next() {
	          while (st == null || !st.hasMoreElements()) {
	              try {
	                  st = new StringTokenizer(br.readLine());
	              } catch (IOException e) {
	                  e.printStackTrace();
	              }
	          }
	          return st.nextToken();
	      }
	 
	      int nextInt() {
	          return Integer.parseInt(next());
	      }
	 
	      long nextLong() {
	          return Long.parseLong(next());
	      }
	 
	      double nextDouble() {
	          return Double.parseDouble(next());
	      }
	 
	      String nextLine(){
	          String str = "";
		  try {
		     str = br.readLine();
		  } catch (IOException e) {
		     e.printStackTrace();
		  }
		  return str;
	      }
	      
	      

	   }
	   

}
