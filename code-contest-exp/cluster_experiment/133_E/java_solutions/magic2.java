import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.StringTokenizer;

public class magic2 {
	static char[] input;
	static int[][][] memo;
	public static void main(String[] args) throws Exception {
		FasterScanner sc = new FasterScanner();
		PrintWriter out = new PrintWriter(System.out);
		input = sc.next().toCharArray();
		int N = sc.nextInt();
		memo = new int[input.length][2][N+1];
		for(int[][] x : memo)
			for(int[] y : x)
				Arrays.fill(y,987654321);
		int ans = 0;
		for(int a=N;a>=0;a-=2){
			ans = Math.max(ans,DP(0,0,a));
			ans = Math.max(ans,DP(0,1,a));
		}
		out.println(ans);
		out.close();
	}

	private static int DP(int pos, int dir, int changes) {
		if(changes<0)return -987654;
		if(pos==input.length){
			if(changes==0)return 0;
			return -987654;
		}
		if(memo[pos][dir][changes]!=987654321)return memo[pos][dir][changes];
		int change = 1;
		if(dir==0)change = -1;
		int ans = -987654321;
		if(input[pos]=='F'){
			//forward
			ans=Math.max(ans,change+DP(pos+1,dir,changes));
			ans=Math.max(ans,DP(pos+1,1-dir,changes-1));
		}
		else{
			//turn
			ans=Math.max(ans,DP(pos+1,1-dir,changes));
			ans=Math.max(ans,change+DP(pos+1,dir,changes-1));
		}
		return memo[pos][dir][changes]=ans;
	}

	private static int fix(char c) {
		if(c=='S')return 0;
		return c-'a'+1;
	}

	static class FasterScanner{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		
		public FasterScanner(){
			stream = System.in;
		}
		int read(){
			if(numChars==-1)
				throw new InputMismatchException();
			if(curChar>=numChars){
				curChar = 0;
				try{
					numChars = stream.read(buf);
				} catch (IOException e){
					throw new InputMismatchException();
				}
				if(numChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}
		
		boolean isSpaceChar(int c){
			return c==' '||c=='\n'||c=='\r'||c=='\t'||c==-1;
		}
		boolean isEndline(int c){
			return c=='\n'||c=='\r'||c==-1;
		}
		int nextInt(){
			return Integer.parseInt(next());
		}
		long nextLong(){
			return Long.parseLong(next());
		}
		double nextDouble(){
			return Double.parseDouble(next());
		}
		String next(){
			int c = read();
			while(isSpaceChar(c))
				c=read();
			StringBuilder res = new StringBuilder();
			do{
				res.appendCodePoint(c);
				c=read();
			} while(!isSpaceChar(c));
			return res.toString();
		}
		String nextLine(){
			int c = read();
			while(isEndline(c))
				c=read();
			StringBuilder res = new StringBuilder();
			do{
				res.appendCodePoint(c);
				c = read();
			}while(!isEndline(c));
			return res.toString();
		}
		
	}
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(InputStream in) throws Exception {
			br = new BufferedReader(new InputStreamReader(in));
			st = new StringTokenizer(br.readLine().trim());
		}

		public int numTokens() throws Exception {
			if (!st.hasMoreTokens()) {
				st = new StringTokenizer(br.readLine().trim());
				return numTokens();
			}
			return st.countTokens();
		}

		public boolean hasNext() throws Exception {
			if (!st.hasMoreTokens()) {
				st = new StringTokenizer(br.readLine().trim());
				return hasNext();
			}
			return true;
		}

		public String next() throws Exception {
			if (!st.hasMoreTokens()) {
				st = new StringTokenizer(br.readLine().trim());
				return next();
			}
			return st.nextToken();
		}

		public double nextDouble() throws Exception {
			return Double.parseDouble(next());
		}

		public float nextFloat() throws Exception {
			return Float.parseFloat(next());
		}

		public long nextLong() throws Exception {
			return Long.parseLong(next());
		}

		public int nextInt() throws Exception {
			return Integer.parseInt(next());
		}

		public String nextLine() throws Exception {
			return br.readLine();
		}
	}

}
