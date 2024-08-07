

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 */
public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader sc = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(1, sc, out);
        out.close();
    }

    static class Task {
        public void solve(int testNumber, InputReader sc, PrintWriter out) throws IOException {
        	int n=sc.nextInt();
        	int[] a=new int[n+1];
        	
        	for(int i=1;i<=n;i++)
        		a[i]=sc.nextInt();
        	ArrayList<Integer>[] G=new ArrayList[n+1];
        	int[] ans=new int[n+1];
        	Arrays.fill(ans, -1);
        	for(int i=0;i<=n;i++)
        		G[i]=new ArrayList<Integer>();
        	for(int i=1;i<=n;i++) {
        		if(i-a[i]>=1) {
        			G[i-a[i]].add(i);
        			if((a[i]&1)!=(a[i-a[i]]&1))
        				ans[i]=1;
        		}
        		if(i+a[i]<=n) {
        			G[i+a[i]].add(i);
        			if((a[i]&1)!=(a[i+a[i]]&1))
        				ans[i]=1;
        		}
        	}
        	Queue<Integer> que=new ArrayDeque<Integer>();
        	for(int i=1;i<=n;i++)
        		if(ans[i]==1)
        			que.offer(i);
        	while(!que.isEmpty()) {
        		int u=que.poll();
        		for(int v:G[u]) {
        			if(ans[v]==-1&&(a[v]&1)==(a[u]&1)) {
        				ans[v]=ans[u]+1;
        				que.offer(v);
        			}
        		}
        	}
        	for(int i=1;i<=n;i++)
        		out.print(ans[i]+" ");
        }

    }

    static class InputReader{
        StreamTokenizer tokenizer;
        public InputReader(InputStream stream){
            tokenizer=new StreamTokenizer(new BufferedReader(new InputStreamReader(stream)));
            tokenizer.ordinaryChars(33,126);
            tokenizer.wordChars(33,126);
        }
        public String next() throws IOException {
            tokenizer.nextToken();
            return tokenizer.sval;
        }
        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        public boolean hasNext() throws IOException {
            int res=tokenizer.nextToken();
            tokenizer.pushBack();
            return res!=tokenizer.TT_EOF;
        }
        
        public double nextDouble() throws NumberFormatException, IOException {
        	return Double.parseDouble(next());
        }
        
        public BigInteger nextBigInteger() throws IOException {
        	return new BigInteger(next());
        }
    }
}
