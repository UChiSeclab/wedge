import java.util.*;
import java.io.*;
public class Main {
      static class pair implements Comparable<pair>{
        int x;
        int y;
        public pair(int x, int y){
            this.x=x;
            this.y=y;
        }
        public int compareTo(pair p){
            return (x-p.x==0)?y-p.y:x-p.x;
        }}
    
  public static void main(String[] args) throws IOException,InterruptedException{
	BufferedReader	 br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter out = new PrintWriter(System.out);
     	// String s = br.readLine();
        // char[] arr=s.toCharArray();
        // ArrayList<Integer> arrl = new ArrayList<Integer>(); 
        // TreeSet<Integer> ts1 = new TreeSet<Integer>(); 
        // HashSet<Integer> h = new HashSet<Integer>(); 
        // HashMap<Integer, Integer> map= new HashMap<>(); 
        // PriorityQueue<String> pQueue = new PriorityQueue<String>(); 
        // LinkedList<String> object = new LinkedList<String>(); 
        // StringBuilder str = new StringBuilder(); 
 	    StringTokenizer   st = new StringTokenizer(br.readLine());
 	   	 int n = Integer.parseInt(st.nextToken());
 	   	 int [] arr = new int [n];
 	   	  st = new StringTokenizer(br.readLine());
 	   	 for(int i=0; i<n; i++){
 	   	     arr[i] =  Integer.parseInt(st.nextToken());
 	   	 }
 	   	 ArrayList<Integer>[] adj = new ArrayList[n];
 	   	 for(int i=0; i<n; i++) adj[i] = new ArrayList<Integer>(); 
 	   	 for(int i=0; i<n; i++){
 	   	     if(i-arr[i]>=0) adj[i-arr[i]].add(i);
 	   	     if(i+arr[i]<n) adj[i+arr[i]].add(i);
 	   	 }
 	   	 int [] ans = new int [n];
 	   	 boolean visited[] = new boolean[n]; 
 	   	 LinkedList<Integer> queue = new LinkedList<Integer>(); 
 	   	 Arrays.fill(ans,-1);
 	  for(int i=0; i<n; i++){
 	       if(arr[i]%2==0){
           visited[i]=true; 
           ans[i]=0;
           queue.add(i);}}
            while (queue.size() != 0) 
           {   
            int tmp = queue.poll();
            Iterator<Integer> itr = adj[tmp].listIterator(); 
            while (itr.hasNext()) 
            { 
                int k = itr.next(); 
                if (!visited[k]) 
                { 
                    visited[k] = true;
                    ans[k] = ans[tmp]+1;
                    queue.add(k); 
                } 
            } 
          } 
 	   	 
 	   	 int [] ans1 = new int [n];
 	   	  Arrays.fill(ans1,-1);
 	   	 queue = new LinkedList<Integer>(); 
 	   	 visited = new boolean[n]; 
 	   	 for(int i=0; i<n; i++){
 	       if(arr[i]%2!=0){
           visited[i]=true;
           ans1[i]=0;
           queue.add(i);}}
            while (queue.size() != 0) 
           {   
            int tmp = queue.poll(); 
            Iterator<Integer> itr = adj[tmp].listIterator(); 
            while (itr.hasNext()) 
            { 
                int k = itr.next(); 
                if (!visited[k]) 
                { 
                    visited[k] = true;
                    ans1[k] = ans1[tmp]+1;
                    queue.add(k); 
                } 
            } 
          } 
 	   	 
 	  // 	 out.println(Arrays.toString(ans));
 	  // 	 out.println(Arrays.toString(ans1));
 	      for(int i=0; i<n; i++){
 	           out.print(((arr[i]%2==0)?ans1[i]:ans[i])+" ");
 	      }
  	   	  out.flush();
	}
    
}