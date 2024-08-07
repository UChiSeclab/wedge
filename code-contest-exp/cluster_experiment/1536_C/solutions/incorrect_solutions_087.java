import java.io.*;
import java.util.*;

public class Solution {

	//test cases
	/*
1
3 3
010
2 1 3

	 */
	static int gcd(int x, int y) {

		if (y == 0) return x;
		return gcd(y, x % y);
	}

	public static void main(String[] args) throws IOException {

		Reader sc=new Reader();
		sc.init(System.in);

		int t=Integer.parseInt(sc.nextLine());

		for (int i = 0; i < t; i++) {

			int n=Integer.parseInt(sc.nextLine());

			String s=sc.nextLine();

			int dk[][]=new int[s.length()+1][2];

			for (int j = 0; j <= s.length()-1; j++) {

				int len=j+1;

				if(s.charAt(j)=='D') {
					dk[j][0]=j>0? dk[j-1][0]+1:1;
					dk[j][1]=j>0? dk[j-1][1]:0;

//					System.out.println(dk[j][0]+" "+dk[j][1]+" adf");
				}
				else{
					dk[j][1]=j>0? dk[j-1][1]+1:1;
					dk[j][0]=j>0? dk[j-1][0]:0;
				}


				if(dk[j][0]==0 || dk[j][1]==0) {
					System.out.print(Math.max(dk[j][0], dk[j][1])+" ");
					continue;
				}

				boolean d=false;
				double ratio=0;

				if(dk[j][0]>dk[j][1]) {
					d=true;
					ratio=(double)dk[j][0]/(double)dk[j][1];
					
					if(dk[j][0]/dk[j][1]!=ratio) {
						System.out.print(1+" ");
						continue;
					}
					
//					System.out.println(ratio);

					boolean pos=true;

					int temp=j;

					int count=0;

					while(temp>0) {
						if(dk[temp][0]!=0 && dk[temp][1]!=0 && ratio==((double)dk[temp][0]/(double)dk[temp][1])) count++;
//						System.out.println(temp);
						temp-=(int)(ratio+1);
//						System.out.println(temp);
					}

					System.out.print(count+" ");
				}
				else {
					d=true;
					ratio=dk[j][1]/dk[j][0];

					if(dk[j][1]/dk[j][0]!=ratio) {
						System.out.print(1+" ");
						continue;
					}
//					System.out.println(ratio);
					//					boolean pos=true;

					int temp=j;

					int count=0;

					while(temp>0) {
						if(dk[temp][0]!=0 && dk[temp][1]!=0 && ratio==((double)dk[temp][1]/(double)dk[temp][0])) count++;
						
						temp-=(int)(ratio+1);
					}

					System.out.print(count+" ");
				}

			}
			System.out.println();
		}

	}

	static class Reader {
		static BufferedReader reader;
		static StringTokenizer tokenizer;

		/** call this method to initialize reader for InputStream */
		static void init(InputStream input) {
			reader = new BufferedReader(
					new InputStreamReader(input) );
			tokenizer = new StringTokenizer("");
		}

		/** get next word */
		static String next() throws IOException {
			while ( ! tokenizer.hasMoreTokens() ) {		
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return tokenizer.nextToken();
		}

		String nextLine() {
			String str = "";
			try {
				str = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

		static int nextInt() throws IOException {
			return Integer.parseInt( next() );
		}

		static double nextDouble() throws IOException {
			return Double.parseDouble( next() );
		}
	}
}
