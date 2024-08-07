import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        for (int i=0; i<n; i++) {
        	arr[i] = in.nextInt();
        	graph.add(new ArrayList<Integer>());
        }
        
        int[] even_dist = new int[n];
        int[] odd_dist = new int[n];
        Queue<Integer> even_q = new LinkedList<Integer>();
        Queue<Integer> odd_q = new LinkedList<Integer>();
        for (int i=0; i<n; i++) {
        	if (i + arr[i] < n)
        		graph.get(i+arr[i]).add(i);
        	if (i - arr[i] >= 0)
        		graph.get(i-arr[i]).add(i);
        	if (arr[i] % 2 == 0) {
        		even_dist[i] = 0;
        		even_q.add(i);
        		odd_dist[i] = INF;
        	} else {
        		odd_dist[i] = 0;
        		odd_q.add(i);
        		even_dist[i] = INF;
        	}
        }
        
        bfs(graph, even_q, even_dist);
        bfs(graph, odd_q, odd_dist);
       
        for (int i=0; i<n; i++) {
        	int res = arr[i] % 2 == 0 ? odd_dist[i] : even_dist[i];
        	res = res >= INF ? -1 : res;
        	sb.append(String.valueOf(res));
        	sb.append(" ");
        }
        
        System.out.println(sb.toString());
        
	}
	
	public static void bfs(List<List<Integer>> graph, Queue<Integer> q, int[] dist) {
		while (!q.isEmpty()) {
			int n = q.poll();
			
			for (Integer i: graph.get(n)) {
				if (dist[i] < INF)
					continue;
				dist[i] = dist[n] + 1;
				q.add(i);
			}
		}
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


