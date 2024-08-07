

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
    	void dfs(int even,int odd,int step,int u,int[] a,int fa,ArrayList<Integer>[] G,int[] dis) {
    		if(a[u]%2==1) {
    			if(even!=Integer.MAX_VALUE)
    				dis[u]=Math.min(dis[u], step-even);
    		}
    		else {
    			if(odd!=Integer.MAX_VALUE)
    				dis[u]=Math.min(dis[u], step-odd);
    		}
    		for(int v:G[u]) {
    			if(v==fa)
    				continue;
    			if(a[u]%2==1)
    				dfs(even,step,step+1,v,a,u,G,dis);
    			else
    				dfs(step,odd,step+1,v,a,u,G,dis);
    		}
    	}
    	
        public void solve(int testNumber, InputReader sc, PrintWriter out) throws IOException {
        	int n=sc.nextInt();
        	int[] a=new int[n+1];
        	
        	for(int i=1;i<=n;i++)
        		a[i]=sc.nextInt();
        	ArrayList<Integer>[] G=new ArrayList[n+1];
        	for(int i=0;i<=n;i++)
        		G[i]=new ArrayList<Integer>();
        	for(int i=1;i<=n;i++) {
        		if(i-a[i]>=1) {
        			G[i-a[i]].add(i);
        		}
        		if(i+a[i]<=n) {
        			G[i+a[i]].add(i);
        		}
        	}
        	int[] d=new int[n+1];
        	for(int i=1;i<=n;i++) {
        		for(int v:G[i]) {
        		//	out.println(i+" "+v);
        			d[v]++;
        		}
        	}
        	int[] dis=new int[n+1];
        	Arrays.fill(dis, Integer.MAX_VALUE);
        	for(int i=1;i<=n;i++) {
        		if(d[i]==0) {
        			if(a[i]%2==1) {
        				dfs(Integer.MAX_VALUE,Integer.MAX_VALUE,0,i,a,-1,G,dis);
        			}
        			else {
        				dfs(Integer.MAX_VALUE,Integer.MAX_VALUE,0,i,a,-1,G,dis);
        			}
        		}
        	}
        	for(int i=1;i<=n;i++) {
        		if(dis[i]==Integer.MAX_VALUE)
        			out.print(-1+" ");
        		else
        			out.print(dis[i]+" ");
        	}
        }

    }
    
    static class Node{
    	int index;
    	public int[] f;
    	public int step;
    	
    	public Node(int index,int even,int odd,int step) {
    		f=new int[2];
    		f[0]=even;
    		f[1]=odd;
    		this.step=step;
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
