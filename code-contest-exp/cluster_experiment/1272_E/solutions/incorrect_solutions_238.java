import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	
	static int INF = (int)1e9;
	
	public static void main(String[] argv)throws IOException {
		
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        
        int n = in.nextInt();
        int[] arr = new int[n];
        
        for (int i=0; i<n; i++)
        	arr[i] = in.nextInt();
        
        int[] even = new int[n];
        int[] odd = new int[n];
        boolean[] visited = new boolean[n];
        
        for (int i=0; i<n; i++) {
        	if (arr[i] % 2 == 0) {
        		even[i] = 0;
        		odd[i] = -2;
        	} else {
        		even[i] = -2;
        		odd[i] = 0;
        	}
        }
        
        
        for (int i=0; i<n; i++) {
        	findEven(i, arr, even, visited);
        	findEven(i, arr, odd, visited);
        	int res = arr[i] % 2 == 0 ? odd[i] : even[i];
        	res = res >= INF ? -1 : res;
        	sb.append(res);
        	sb.append(" ");
        }
        System.out.println(sb.toString());
        
	}
	
	static public int findEven(int i, int[] arr, int[] even, boolean[] visited) {
		if (even[i] != -2) return even[i];
		even[i] = INF;
		if (visited[i]) { // loop
			visited[i] = false;
			
			return even[i];
		}
		
		visited[i] = true;
		if (i - arr[i] >= 0) {
			even[i] = Math.min(even[i], findEven(i-arr[i], arr, even, visited)+1);
		}
		if (i + arr[i] < arr.length) {
			even[i] = Math.min(even[i], findEven(i+arr[i], arr, even, visited)+1);
		}
		
		visited[i] = false;
		return even[i];
	}
	
	
	static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
        
        public long nextLong() {
        	return Long.parseLong(next());
        }

	}
}


