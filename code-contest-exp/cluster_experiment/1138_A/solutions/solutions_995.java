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

		ArrayList<Integer> al = new ArrayList<>();

		int count = 1;

		for (int i=1;i<n ;i++) {

			if(ar[i]==ar[i-1])
			{
				count++;
			}else{
				al.add(count);
				count =1;
			}
		}
		al.add(count);

		int min = Integer.MIN_VALUE;

		for(int i=1;i<al.size();i++)
		{
			min = Math.max(min,Math.min(al.get(i),al.get(i-1)));
		}

		//out.println(al);
		out.println(2*min);

	
		
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