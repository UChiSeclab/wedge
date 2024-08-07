import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Boxers {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MyScanner input =new MyScanner();
		
		int n = input.nextInt();
		int[] arr =new int[n];
		for(int i=0; i<n;i++)arr[i] = input.nextInt();
		//int ans=0;
		//boolean[] used = new boolean[15002];
		Arrays.sort(arr);
		int last = 0;
		int count=0;
		//ArrayList<Integer> list;
		for (int i = 0; i < n; i++) {
			if(last>=arr[i]+1)continue;
			else if(arr[i]==last) {
				last = arr[i]+1;
				count++;
			}
			else if(last==arr[i]-1){
				last =arr[i];
				count++;
			}
			else {
			  last = arr[i]	;
			  count++;
			}
			
		}
		System.out.println(count);
		
	//	System.out.println(count
	}

	//static ArrayList<Long> list;
		static class MyScanner {
			BufferedReader br;
			StringTokenizer st;
			public MyScanner() {br = new BufferedReader(new InputStreamReader(System.in));}
			String next() {while (st == null || !st.hasMoreElements()) {
				try {st = new StringTokenizer(br.readLine());}
				catch (IOException e) {e.printStackTrace();}}
			return st.nextToken();}
			int nextInt() {return Integer.parseInt(next());}
			long nextLong() {return Long.parseLong(next());}
			double nextDouble() {return Double.parseDouble(next());}
			String nextLine(){String str = "";
			try {str = br.readLine();}
			catch (IOException e) {e.printStackTrace();}
			return str;}
		}

}
