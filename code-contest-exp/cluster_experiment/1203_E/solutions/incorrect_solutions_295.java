import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.HashSet;

public class Main
{
	public static void main(String[] args)
	{
		FastReader fr =new FastReader();	PrintWriter op =new PrintWriter(System.out);

		int n =fr.nextInt() ,i ,arr[] =new int[n] ,j ;	HashSet<Integer> hs =new HashSet<Integer>() ;

		for (i =0 ; i<n; ++i ) {
			arr[i] =fr.nextInt() ;
 		}
 		for (i =0 ; i<n ; ++i) {
 			if (hs.contains(arr[i])) {
	 			if (arr[i] == 1) {
	 				j =arr[i] + 1 ;

	 				hs.add(j) ;
	 			}
	 			else {
	 				j =arr[i] - 1 ;
	 				if (!hs.contains(j))
	 					hs.add(j) ;
	 				else {
	 					j =arr[i] + 1 ;	hs.add(j) ;
	 				}
	 			}
	 		}
	 		else 	hs.add(arr[i]) ;
 		}
 		op.println( hs.size() ) ;//System.out.println(hs) ;
		op.flush();	op.close();
	}
	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br =new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st==null || (!st.hasMoreElements())) 
			{
				try
				{
					st =new StringTokenizer(br.readLine());
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
			}
			return st.nextToken();
		}

		String nextLine() {
			String str ="";

			try
			{
				str =br.readLine();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}

			return str;
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next()) ;
		}
	}
}