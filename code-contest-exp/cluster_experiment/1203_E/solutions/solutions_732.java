import java.io.*;
import java.util.*;
import java.lang.*;
 
public class TestClass {
	private static Reader s;
	private static PrintWriter out;
	private final int delta = (int) 1e9 + 7;
 
	private static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;
 
		public Reader() {
			din = new DataInputStream(System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}
 
		public String readLine() throws IOException {
			byte[] buf = new byte[100000];
			int cnt = 0, c;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, cnt);
		}
 
		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
 
			if (neg)
				return -ret;
			return ret;
		}
 
		public long nextLong() throws IOException {
			long ret = 0;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}
 
		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			byte c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
 
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
 
			if (c == '.') {
				while ((c = read()) >= '0' && c <= '9') {
					ret += (c - '0') / (div *= 10);
				}
			}
 
			if (neg)
				return -ret;
			return ret;
		}
 
		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}
 
		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}
	}
 
	public static void main(String args[]) throws IOException {
		run();
	}
 
	private static void run() throws IOException {
		s = new Reader();
		out = new PrintWriter(System.out);
		solve();
		out.flush();
		out.close();
	}
 
	public static long gcd(long a, long b) {
		while (b > 0) {
			long c = a;
			a = b;
			b = c % b;
		}
		return a;
	}
 
 
	private static void solve() throws IOException {
		int n=s.nextInt(),t;
		int arr[]= new int[150002];
		int visited[]= new int[150002];
		visited[0]=1;
		for (int i=0;i<n;i++)
			arr[s.nextInt()]++;
 	
 		int count=0;
		
		for(int i=1;i<150002;i++)
		{
			if(arr[i]>0)
			{	
				if(visited[i-1]!=1)
				{	
					visited[i-1]++;
					arr[i]--;
					count++;
				}
				
				if(visited[i]!=1&&arr[i]>0)
				{
					visited[i]++;
					arr[i]--;
					count++;
				}
				
				if(visited[i+1]!=1&&arr[i]>0)
				{
					visited[i+1]++;
					count++;
				}

			}
		}
 		
		out.println(count);
 
	}
		
 
}