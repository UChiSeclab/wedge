
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Round545A {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		out=new PrintWriter (new BufferedOutputStream(System.out));
		FastReader s=new FastReader();
		int n=s.nextInt();
		int[] arr=new int[n];
		for(int i=0;i<n;i++) {
			arr[i]=s.nextInt();
		}
		ArrayList<Integer> change=new ArrayList<Integer>();
		for(int i=1;i<n;i++) {
			if(arr[i]!=arr[i-1]) {
				change.add(i);
			}
		}
		int maxcount=0;
		for(Integer x:change) {
			int second=x;
			int first=x-1;
			int firsttime=arr[x-1];
			int secondtime=arr[x];
			int count=0;
			while(first>=0 && second<n && arr[first]==firsttime && arr[second]==secondtime) {
				second++;
				first--;
				count+=2;
			}
			maxcount=Integer.max(count, maxcount);
		}
		out.println(maxcount);
		out.close();
	}
	
	public static PrintWriter out;

	public static class FastReader {
		BufferedReader br;
		StringTokenizer st;
		//it reads the data about the specified point and divide the data about it ,it is quite fast
		//than using direct 

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());//converts string to integer
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}
	}
	
}
