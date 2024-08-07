import java.io.*;
import java.util.*;

public class SushiForTwo {
	static BufferedReader br ;
	static StringTokenizer st;
	static PrintWriter out;
	
	static String next() throws Exception{
		while(st == null || !st.hasMoreElements() ) {
			st = new StringTokenizer(br.readLine());
		}
		return st.nextToken();
	}
	
	static long nextLong() throws Exception{
		return Long.parseLong(next());
	}
	
	static int nextInt() throws Exception{
		return Integer.parseInt(next());
	}
	
	static String nextLine() throws Exception{
			return br.readLine();
	}
	
	public static void main(String[] args) throws Exception{
		br = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
    int n = nextInt();
    int min = 1, val =1, x=nextInt(), prev=0;
    for(int i=1; i<n; i++){
      int type = nextInt();
      if(type==x)val++;
      else{
        int res = Math.min(prev,val);
        if(min<res)min=res;
        x=type;
        prev=val;
        val =1;
      }
    }
    int res = Math.min(prev,val);
    if(min<res)min=res;
    out.println(min*2);
    out.close();
		br.close();
	}
}
