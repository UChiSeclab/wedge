import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
public class Acm {
public static void main (String args[]){
Scanner sc = new Scanner ();
int n = sc.nextInt();
int m = sc.nextInt();
int p = sc.nextInt();
if (m>=n && p >=n)
	System.out.println("Yes");
else 
	System.out.println("No");

	
		
}

  
}



/*
for (int h = 0 ;h<n ;h++){
	
	
	
}
*/

 class Scanner {
		BufferedReader br;
		StringTokenizer st;

		public Scanner(String s) {
			try {
				br = new BufferedReader(new FileReader(s));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public Scanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(nextToken());
		}

		long nextLong() {
			return Long.parseLong(nextToken());
		}

		double nextDouble() {
			return Double.parseDouble(nextToken());
		}
	}