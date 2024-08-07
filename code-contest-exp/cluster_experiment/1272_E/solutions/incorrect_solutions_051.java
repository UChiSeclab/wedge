import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class E_Nearest_Opposite_Parity {
	
	static int[] values;
	static int[] distEven;
	static int[] distOdd;
	
	public static void main(String[] args) {
		MyScanner sc = new MyScanner();
	    PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
	    int N = sc.nextInt();
	    values = new int[N + 1];
	    
	    for (int i = 1; i < values.length; i++) {
			values[i] = sc.nextInt();
		}
	    
	    distEven = new int[N + 1];
	    Arrays.fill(distEven, -2);	
	    distOdd = new int[N + 1];	
	    Arrays.fill(distOdd, -2);
	    
	    for(int i = 1; i <= N; i++) {
	    	if(values[i]%2 == 0) {
	    		out.print(calcDistOdd(i) + " ");
	    	}else {
	    		out.print(calcDistEven(i) + " ");
	    	}
	    }
	    out.println();
	    
	    out.close();
	}
	
	public static int calcDistOdd(int index) {
		return calcDistOdd(index, new HashSet<Integer>());
	}
	
	public static int calcDistOdd(int index, HashSet<Integer> visited) {
		int currVal = values[index];	
		if(visited.contains(index)) {
			distOdd[index] = -1;
			return -1;	
		}
		
		visited.add(index);
		if(isOdd(currVal)) {
			distOdd[index] = 0;
		}else if(distOdd[index] == -2) {
			int l = -1;
			if(index - currVal >= 1) {
				l = calcDistOdd(index - currVal, visited);
			}
			int r = -1;
			if(index + currVal < values.length) {
				r = calcDistOdd(index + currVal, visited);
			}
			
			if(l == -1 && r == -1) {
				distOdd[index] = -1;
			}else if(l == -1) {
				distOdd[index] = r + 1;
			}else if(r == -1) {
				distOdd[index] = l + 1;
			}else {
				distOdd[index] = Math.min(l, r) + 1;
			}
		}
		
		visited.remove(index);
		return distOdd[index];
	}
	
	public static boolean isOdd(int value) {
		return value%2 == 1;
	}
	
	public static int calcDistEven(int index) {
		return calcDistEven(index, new HashSet<Integer>());
	}
	
	public static int calcDistEven(int index, HashSet<Integer> visited) {
		int currVal = values[index];	
		if(visited.contains(index)) {
			distEven[index] = -1;
			return -1;	
		}
		
		visited.add(index);
		if(isEven(currVal)) {
			distEven[index] = 0;
		}else if(distEven[index] == -2) {
			int l = -1;
			if(index - currVal >= 1) {
				l = calcDistEven(index - currVal, visited);
			}
			int r = -1;
			if(index + currVal < values.length) {
				r = calcDistEven(index + currVal, visited);
			}
			
			if(l == -1 && r == -1) {
				distEven[index] = -1;
			}else if(l == -1) {
				distEven[index] = r + 1;
			}else if(r == -1) {
				distEven[index] = l + 1;
			}else {
				distEven[index] = Math.min(l, r) + 1;
			}
		}
		
		visited.remove(index);
		return distEven[index];
	}
	
	public static boolean isEven(int value) {
		return value%2 == 0;
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