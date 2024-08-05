import java.util.*;
import java.io.*;

public class cp{


	public static void main(String[] args) {
		Scanner sc = new Scanner();


		int n = sc.nextInt();

		int ar[] = new int[n];

		for(int i=0;i<n;i++)
		{
			ar[i] = sc.nextInt();
		}

		int ans1 = 0;
		int count = 0;

		for(int i=1;i<n;i++)
		{
			if(ar[i]==ar[i-1] && ar[i]==1)
			{
				count++;
			}else{
				ans1 = Math.max(ans1,count);
				count = 1;
			}
		}		ans1 = Math.max(ans1,count);

		int ans2 = 0;
		int count2 = 0;

		for(int i=1;i<n;i++)
		{
			if(ar[i]==ar[i-1] && ar[i]==2)
			{
				count2++;
			}else{
				ans2 = Math.max(ans2,count2);
				count2 = 1;
			}
		}ans2 = Math.max(ans2,count2);



		out.println(Math.min(ans2,ans1)*2);
		
		out.close();
 
	}


	static	PrintWriter out=new PrintWriter(System.out);
	static class Scanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
		String nextLine() {
			while (!st.hasMoreTokens())
				try { 
                                        st=new StringTokenizer(br.readLine());				               
                                } catch (IOException e) {}
			return st.nextToken();
		}
		char nextChar() {
			char c = '$';
				try { 
                                        c = (char)br.read();			               
                                } catch (IOException e) {}
			return c;
		}		
		int nextInt() {
			return Integer.parseInt(nextLine());
		}
		Double nextDouble() {
			return Double.parseDouble(nextLine());
		}
		long nextLong() {
			return Long.parseLong(nextLine());
		}
	}
}