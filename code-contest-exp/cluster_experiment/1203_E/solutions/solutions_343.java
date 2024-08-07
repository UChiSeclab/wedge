import java.util.*;
import java.io.*;

public class Main {
	public static void main(String args[]) {new Main().run();}

	FastReader in = new FastReader();
	PrintWriter out = new PrintWriter(System.out);
	void run(){
		out.println(work());
		out.flush();
	}
	long mod=998244353L;
	int[] count=new int[150005];
	int work() {
		int n=in.nextInt();
		int ret=0;
		for(int i=0;i<n;i++) {
			count[in.nextInt()]++;
		}
		for(int i=0,cur=0;i<150005;i++) {
			while(count[i]>0) {
				if(i-1>cur) {
					cur=i-1;
					ret++;
				}else if(i>cur) {
					cur=i;
					ret++;
				}else if(i+1>cur) {
					cur=i+1;
					ret++;
				}
				count[i]--;
			}
		}
		return ret;
	}
}



class FastReader
{
	BufferedReader br;
	StringTokenizer st;

	public FastReader()
	{
		br=new BufferedReader(new InputStreamReader(System.in));
	}

	public String next() 
	{
		if(st==null || !st.hasMoreElements())
		{
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	public int nextInt() 
	{
		return Integer.parseInt(next());
	}

	public long nextLong()
	{
		return Long.parseLong(next());
	}
}