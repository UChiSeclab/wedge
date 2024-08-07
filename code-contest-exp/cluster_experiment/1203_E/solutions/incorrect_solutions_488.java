/* Rajkin Hossain */

import java.io.*;
import java.util.*;

public class E{
   
    FastInput k = new FastInput(System.in);
    //FastInput k = new FastInput("/home/rajkin/Desktop/input.txt");
    
    
    FastOutput z = new FastOutput();
    
    int n;
    int [] y;
    
    int startAlgorithm() {
    	boolean [] save = new boolean[150003];
    	int ans = 0;
    	
    	for(int i = 0; i<n; i++) {
    		if(!save[y[i]]) {
    			save[y[i]] = true;
    			ans++;
    		}
    		else{
    			if(y[i] == 1) {
    				if(!save[y[i]+1]) {
    					save[y[i]+1] = true;
    					ans++;
    				}
    			}
    			else {
    				if(!save[y[i]-1]) {
    					save[y[i]-1] = true;
    					ans++;
    				}
    				else if(!save[y[i]+1]) {
    					save[y[i]+1] = true;
    					ans++;
    				}
    			}
    		}
    	}
    	
    	return ans;
    }
    
    void startProgram() {
    	while(k.hasNext()) {
    		n = k.nextInt();
    		y = new int[n];
    		
    		for(int i = 0; i<n; i++) {
    			y[i] = k.nextInt();
    		}
    		
    		Arrays.sort(y);
    		
    		z.println(startAlgorithm());
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
