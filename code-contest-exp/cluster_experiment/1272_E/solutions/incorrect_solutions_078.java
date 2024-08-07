/* Rajkin Hossain */

import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class E {
   
    FastInput k = new FastInput(System.in);
    //FastInput k = new FastInput("/home/rajkin/Desktop/input.txt");
    FastOutput z = new FastOutput();
    
    int n;
    int [] y;
    
    ArrayList<Integer> [] oddGraph, evenGraph;
    ArrayDeque<Integer> oddQueue, evenQueue;
    
    int [] oddD, evenD;
    
    void processForOdd() {
    	boolean [] isVisited = new boolean[n+1];
    	
    	while(!oddQueue.isEmpty()) {
    		int u = oddQueue.poll();
    		if(isVisited[u]) continue;
    		
    		isVisited[u] = true;
    		
    		for(int v : oddGraph[u]) {
    			if(!isVisited[v]) {
    				isVisited[u] = true;
    				oddQueue.add(v);
    				oddD[v] = oddD[u] + 1;
    			}
    		}
    	}
    }
    
    void processForEven() {
    	boolean [] isVisited = new boolean[n+1];
    	
    	while(!evenQueue.isEmpty()) {
    		int u = evenQueue.poll();
    		
    		if(isVisited[u]) continue;
    		
    		isVisited[u] = true;
    		
    		for(int v : evenGraph[u]) {
    			if(!isVisited[v]) {
    				isVisited[u] = true;
    				evenQueue.add(v);
    				evenD[v] = evenD[u] + 1;
    			}
    		}
    	}
    }
    
    void startAlgo() {
		for(int i = 1; i<=n; i++) {
			if(y[i] % 2 == 0) {
				if(i-y[i] >= 1) {
					evenGraph[i-y[i]].add(i);
					
					if(y[i-y[i]] % 2 != 0) {
						evenQueue.add(i-y[i]);
					}
				}
				
				if(i+y[i] <= n) {
					evenGraph[i+y[i]].add(i);
					
					if(y[i+y[i]] % 2 != 0) {
						evenQueue.add(i+y[i]);
						//System.out.println(i+y[i]);
					}
				}
			}
			else {
				if(i-y[i] >= 1) {
					oddGraph[i-y[i]].add(i);
					
					if(y[i-y[i]] % 2 == 0) {
						oddQueue.add(i-y[i]);
					}
				}
				
				if(i+y[i] <= n) {
					oddGraph[i+y[i]].add(i);
					
					if(y[i+y[i]] % 2 == 0) {
						oddQueue.add(i+y[i]);
					}
				}
			}
		}
		
		processForOdd();
		processForEven();
		
		for(int i = 1; i<=n; i++) {
			if(y[i] % 2 == 0) {
				evenD[i] = evenD[i] == 0 ? -1 : evenD[i];
				z.print(evenD[i]+" ");
			}
			else {
				oddD[i] = oddD[i] == 0 ? -1 : oddD[i];
				z.print(oddD[i]+" ");
			}
		}
		
		z.println();
    }
    
    void startProgram() {
    	while(k.hasNext()) {
    		n = k.nextInt();
    		
    		y = k.getInputIntegerArrayOneBasedIndex(n);
    		
    		oddGraph = new ArrayList[n+1];
    		evenGraph = new ArrayList[n+1];
    		
    		oddQueue = new ArrayDeque<Integer>();
    		evenQueue = new ArrayDeque<Integer>();
    		
    		oddD = new int[n+1];
    		evenD = new int[n+1];
    		
    		for(int i = 1; i<=n; i++) {
    			oddGraph[i] = new ArrayList<Integer>();
    			evenGraph[i] = new ArrayList<Integer>();
    		}
    		
    		startAlgo();
    	}
    	
    	z.flush();
    	System.exit(0);
    }
    
    public static void main(String [] args) throws IOException {
        new Thread(null, new Runnable(){
            public void run(){
            	try{ 
            		new E().startProgram();
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            }
        },"Main",1<<28).start();
    }
    

    /* MARK: FastInput and FastOutput */
 
    class FastInput {
        BufferedReader reader;
        StringTokenizer tokenizer;
 
        FastInput(InputStream stream){
            reader = new BufferedReader(new InputStreamReader(stream));
        }
       
        FastInput(String path){
            try {
                reader = new BufferedReader(new FileReader(path));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            tokenizer = null;
        }
 
        String next() {
            return nextToken();
        }
       
        String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
            return null;
        }
 
        boolean hasNext(){
            try {
                return reader.ready();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }
 
        String nextToken() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (line == null) {
                    return null;
                }
                tokenizer = new StringTokenizer(line);
            }
            return tokenizer.nextToken();
        }
 
        int nextInt() {
            return Integer.parseInt(nextToken());
        }
       
        long nextLong() {
            return Long.valueOf(nextToken());
        }
        
        int [] getInputIntegerArray(int n) {
        	
        	int [] input = new int[n];
        	
        	for(int i = 0; i<n; i++) {
        		input[i] = nextInt();
        	}
        	
        	return input;
        }
        
        long [] getInputLongArray(int n) {
        	
        	long [] input = new long[n];
        	for(int i = 0; i<n; i++) {
        		input[i] = nextLong();
        	}
        	
        	return input;
        }
        
        int [] getInputIntegerArrayOneBasedIndex(int n) {
        	
        	int [] input = new int[n+1];
        	
        	for(int i = 1; i<=n; i++) {
        		input[i] = nextInt();
        	}
        	
        	return input;
        }
        
        long [] getInputLongArrayOneBasedIndex(int n) {
        	
        	long [] input = new long[n+1];
        	
        	for(int i = 1; i<=n; i++) {
        		input[i] = nextLong();
        	}
        	
        	return input;
        }
    }
 
    class FastOutput extends PrintWriter {
		FastOutput() {
			super(new BufferedOutputStream(System.out)); 
		}
		
		public void debug(Object...obj) {
		    System.err.println(Arrays.deepToString(obj));
		}
    }
}
