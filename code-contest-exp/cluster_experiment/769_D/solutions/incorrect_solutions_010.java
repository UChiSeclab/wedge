import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Solution {

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return str;
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}
	}

	public static void main(String[] args) {
		FastReader sc = new FastReader();
		int n = sc.nextInt();
		int k = sc.nextInt();
		int max = 10001;
		long[] arr = new long[max];
		for (int i = 0; i < n; i++)
			arr[sc.nextInt()]++;
		int count=0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(arr[i]>0 && arr[j]>0 && Integer.bitCount(i^j)==k){
					if(k==0){
						count+=arr[i]*(arr[i]-1);
					} else
						count+=arr[i]*arr[j];
				}
			}
		}
		System.out.println(count/2);
	}

}