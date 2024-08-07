import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class E_Nearest_Opposite_Parity {
	
	static int[] values;
	
	public static void main(String[] args) {
		MyScanner sc = new MyScanner();
	    PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
	    int N = sc.nextInt();
	    values = new int[N + 1];
	    
	    for(int i = 1; i < values.length; i++) {
			values[i] = sc.nextInt();
		}
	    
	    ArrayList<ArrayList<Integer>> oddGraph = new ArrayList<>();
	    ArrayList<ArrayList<Integer>> evenGraph = new ArrayList<>();

	    for(int i = 0; i <= N; i++) {
	    	oddGraph.add(new ArrayList<Integer>());
	    	evenGraph.add(new ArrayList<Integer>());
	    }
	    for(int i = 1; i <= N; i++) {
	    	if(values[i]%2 == 1) {
		    	if(i - values[i] >= 1) {
		    		//insert directed edge in reverse
		    		oddGraph.get(i - values[i]).add(i);
		    	}
		    	if(i + values[i] <= N) {
		    		//insert directed edge in reverse
		    		oddGraph.get(i + values[i]).add(i);
		    	}
	    	}else {
		    	if(i - values[i] >= 1) {
		    		//insert directed edge in reverse
		    		evenGraph.get(i - values[i]).add(i);
		    	}
		    	if(i + values[i] <= N) {
		    		//insert directed edge in reverse
		    		evenGraph.get(i + values[i]).add(i);
		    	}
	    	}
	    }
	    
	    int[] ans = new int[N + 1];
	    Queue<Location> toVisit;
	    boolean[] visited;
	    
	    //Solve Odd
	    toVisit = new LinkedList<Location>();
	    visited = new boolean[N + 1];
	    //Add even as the bfs sources
	    for(int i = 1; i <= N; i++) {
	    	if(values[i]%2 == 0) {
	    		toVisit.add(new Location(i, 0));
	    		visited[i] = true;
	    	}
	    }
	    
	    while(!toVisit.isEmpty()) {
	    	Location curr = toVisit.poll();
	    	ans[curr.loc] = curr.dist;
	    	
	    	for(int neighbor : oddGraph.get(curr.loc)) {
	    		if(visited[neighbor] == false) {
	    			visited[neighbor] = true;
	    			toVisit.add(new Location(neighbor, curr.dist + 1));
	    		}
	    	}
	    }
	    //Any unvisited odd number should be marked -1
	    for(int i = 1; i <= N; i++) {
	    	if(values[i]%2 == 1 && !visited[i])
	    		ans[i] = -1;
	    }
	    
	    //Solve even
	    toVisit = new LinkedList<Location>();
	    visited = new boolean[N + 1];
	    //Add even as the bfs sources
	    for(int i = 1; i <= N; i++) {
	    	if(values[i]%2 == 1) {
	    		toVisit.add(new Location(i, 0));
	    		visited[i] = true;
	    	}
	    }
	    
	    while(!toVisit.isEmpty()) {
	    	Location curr = toVisit.poll();
	    	if(values[curr.loc]%2 != 1)
	    		ans[curr.loc] = curr.dist;
	    	
	    	for(int neighbor : evenGraph.get(curr.loc)) {
	    		if(visited[neighbor] == false) {
	    			visited[neighbor] = true;
	    			toVisit.add(new Location(neighbor, curr.dist + 1));
	    		}
	    	}
	    }
	    //Any unvisited odd number should be marked -1
	    for(int i = 1; i <= N; i++) {
	    	if(values[i]%2 == 0 && !visited[i])
	    		ans[i] = -1;
	    }
	    
	    for(int i = 1; i <= N; i++) {
	    	out.print(ans[i] + " ");
	    }
	    out.println();
	    
	    out.close();
	}
	
	static class Location {
		int loc = 0;
		int dist = 0;
		
		Location(int loc, int dist) {
			this.loc = loc;
			this.dist = dist;
		}
	}
	
	public static class MyScanner {
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