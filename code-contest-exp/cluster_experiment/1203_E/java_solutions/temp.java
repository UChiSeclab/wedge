import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.StringTokenizer;
 
public class temp {
	
	void solve() throws IOException {
		FastReader sc = new FastReader();
		int n = sc.nextInt();
		int a[] = new int[n];
		
		for(int i=0;i<n;i++)
			a[i] = sc.nextInt();
		
		HashMap<Integer,Integer> hm = new HashMap<>();
		
		for(int i=0;i<n;i++)
		{
			if(!hm.containsKey(a[i]))
				hm.put(a[i],1);
			else
			{
				if(hm.containsKey(a[i]+1) && !hm.containsKey(a[i]-1) && a[i]!=1)
					hm.put(a[i]-1,1);
				else if(!hm.containsKey(a[i]+1) && hm.containsKey(a[i]-1))
					hm.put(a[i]+1,1);
				else if(!hm.containsKey(a[i]+1) && !hm.containsKey(a[i]-1))
					hm.put(a[i]+1,1);
			}
		}
		
		System.out.println(hm.size());
	}
 
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new temp().solve();
	}
	
	class FastReader {
		BufferedReader br;
		StringTokenizer st;
 
		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}
 
		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}
 
		int nextInt() {
			return Integer.parseInt(next());
		}
 
		long nextLong() {
			return Long.parseLong(next());
		}
 
		double nextDouble() {
			return Double.parseDouble(next());
		}
 
		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}