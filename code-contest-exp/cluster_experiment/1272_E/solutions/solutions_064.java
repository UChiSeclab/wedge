import java.util.*;
import java.io.*;

public class R605E {

	public static void main(String[] args) {
		JS scan = new JS();
		PrintWriter out = new PrintWriter(System.out);
		int n = scan.nextInt();
		ArrayList<Integer>[] graph = new ArrayList[n];
		ArrayList<Integer>[] rev = new ArrayList[n];
		for(int i = 0; i < n; i++) {
			graph[i] = new ArrayList<Integer>();
			rev[i] = new ArrayList<Integer>();
		}
		int[] arr = new int[n];
		for(int i = 0; i < n; i++) arr[i] = scan.nextInt();
		for(int i = 0; i < n; i++) {
			int right = i+arr[i];
			int left = i-arr[i];
			if(right < n) {
				graph[i].add(right);
				rev[right].add(i);
			}
			if(left >= 0) {
				graph[i].add(left);
				rev[left].add(i);
			}
		}
		int[] ans = new int[n];
		Arrays.fill(ans, -1);
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < graph[i].size(); j++) {
				int to = graph[i].get(j);
				if(arr[i] % 2 != arr[to] % 2) {
					ans[i] = 1;
					q.add(i);
				}
			}
		}
		while(!q.isEmpty()) {
			int p = q.poll();
			for(int i = 0; i < rev[p].size(); i++) {
				int to = rev[p].get(i);
				if(arr[p] % 2 == arr[to] % 2) {
					if(ans[to] == -1) {
						ans[to] = ans[p]+1;
						q.add(to);
					}
				}
			}
			
		}
		for(int i = 0; i < n; i++) out.print(ans[i]+" ");
		out.println();
		out.flush();
	}

	static class JS{
		public int BS = 1<<16;
		public char NC = (char)0;
		byte[] buf = new byte[BS];
		int bId = 0, size = 0;
		char c = NC;
		double num = 1;
		BufferedInputStream in;

		public JS() {
			in = new BufferedInputStream(System.in, BS);
		}

		public JS(String s) throws FileNotFoundException {
			in = new BufferedInputStream(new FileInputStream(new File(s)), BS);
		}

		public char nextChar(){
			while(bId==size) {
				try {
					size = in.read(buf);
				}catch(Exception e) {
					return NC;
				}				
				if(size==-1)return NC;
				bId=0;
			}
			return (char)buf[bId++];
		}

		public int nextInt() {
			return (int)nextLong();
		}

		public long nextLong() {
			num=1;
			boolean neg = false;
			if(c==NC)c=nextChar();
			for(;(c<'0' || c>'9'); c = nextChar()) {
				if(c=='-')neg=true;
			}
			long res = 0;
			for(; c>='0' && c <='9'; c=nextChar()) {
				res = (res<<3)+(res<<1)+c-'0';
				num*=10;
			}
			return neg?-res:res;
		}

		public double nextDouble() {
			double cur = nextLong();
			return c!='.' ? cur:cur+nextLong()/num;
		}

		public String next() {
			StringBuilder res = new StringBuilder();
			while(c<=32)c=nextChar();
			while(c>32) {
				res.append(c);
				c=nextChar();
			}
			return res.toString();
		}

		public String nextLine() {
			StringBuilder res = new StringBuilder();
			while(c<=32)c=nextChar();
			while(c!='\n') {
				res.append(c);
				c=nextChar();
			}
			return res.toString();
		}

		public boolean hasNext() {
			if(c>32)return true;
			while(true) {
				c=nextChar();
				if(c==NC)return false;
				else if(c>32)return true;
			}
		}
	}
}
