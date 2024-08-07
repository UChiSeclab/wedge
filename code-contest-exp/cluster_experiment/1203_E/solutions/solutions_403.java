import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Map.Entry;
 
public class Main {
	static long gcd(long a, long b) {

		if (b == 0)
			return a;
		return gcd(b, a % b);
	}
	public static void main(String[] args) throws IOException {
		MScanner sc = new MScanner(System.in);
		PrintWriter pw=new PrintWriter(System.out);
		int n=sc.nextInt();
		HashSet<Integer>tak=new HashSet<Integer>();
		Integer[]in=new Integer[n];
		for(int i=0;i<n;i++)
			in[i]=sc.nextInt();
		Arrays.sort(in);
		for(int i=0;i<n;i++) {
			if(in[i].intValue()==1) {
				if(!tak.contains(1)) {
					tak.add(1);
				}
				else {
					if(!tak.contains(2)) {
						tak.add(2);
					}
				}
			}
			else {
				if(!tak.contains(in[i]-1)) {
					tak.add(in[i]-1);
				}
				else {
					if(!tak.contains(in[i])) {
						tak.add(in[i]);
					}
					else {
						if(!tak.contains(in[i]+1)) {
							tak.add(in[i]+1);
						}
					}
				}
			}
		}
		pw.println(tak.size());
		pw.flush();
 
	}
    static class MScanner {
		StringTokenizer st;
		BufferedReader br;
 
		public MScanner(InputStream system) {
			br = new BufferedReader(new InputStreamReader(system));
		}
 
		public MScanner(String file) throws Exception {
			br = new BufferedReader(new FileReader(file));
		}
 
		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
 
		public String nextLine() throws IOException {
			return br.readLine();
		}
 
		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
 
		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}
 
		public char nextChar() throws IOException {
			return next().charAt(0);
		}
 
		public Long nextLong() throws IOException {
			return Long.parseLong(next());
		}
 
		public boolean ready() throws IOException {
			return br.ready();
		}
 
		public void waitForInput() throws InterruptedException {
			Thread.sleep(3000);
		}
	}
}