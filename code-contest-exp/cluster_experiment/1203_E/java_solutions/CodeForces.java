import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class CodeForces {
	static char[] s, t;

	static long gcd(long a, long b) {
		if (a == 0)
			return b;
		return gcd(b % a, a);
	}

	static long no(int n) {
		long sum = 0;
		for (int i = 1; i * i <= n; i++) {
			if (n % i == 0)
				sum++;
		}
		return sum;
	}

	static long divCount(long n) {
		long sum = 0;
		for(int i = 1 ;i*i<=n ;i++){
			if(n%i==0){
				if(n/i==i){
					sum++;
				}else{
					sum+=2;
				}
			}
		}
		return sum;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int n = sc.nextInt();
		Integer[] arr = new Integer[n];
		for(int i = 0;i<n;i++){
			arr[i] = sc.nextInt();
		}
		Arrays.sort(arr);
		HashSet<Integer> set = new HashSet<Integer>();
		
		for(int i = 0;i<n;i++){
			if(arr[i]==1){
				if(set.contains(1)){
					set.add(2);
				}else{
					set.add(1);
				}
			}else{
				if(set.contains(arr[i]-1)){
					if(set.contains(arr[i])){
						set.add(arr[i]+1);
					}else{
						set.add(arr[i]);
					}
				}else{
					set.add(arr[i]-1);
				}
			}
		}
		System.out.println(set.size());
		
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public double nextDouble() throws IOException {
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

	}
}
