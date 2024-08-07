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
 		sort(arr , 0 , n-1) ;

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
 		op.println(hs.size()) ;//System.out.println(hs) ;
		op.flush();	op.close();
	}
	public static void sort(int[] arr , int l , int u) {
		int m ;
 
		if(l < u){
			m =(l + u)/2 ;
 
			sort(arr , l , m);	sort(arr , m + 1 , u);
 
			merge(arr , l , m , u);
		}
	}
 
	public static void merge(int[] arr , int l , int m , int u) {
		int[] low = new int[m - l + 1] ;
 
		int[] upr = new int[u - m] ;
 
		int i ,j =0 ,k =0 ;
 
		for(i =l;i<=m;i++)
			low[i - l] =arr[i] ;
 
		for(i =m + 1;i<=u;i++)
			upr[i - m - 1] =arr[i] ;

		i =l;
 
		while((j < low.length) && (k < upr.length))
		{
			if(low[j] < upr[k])
				arr[i++] =low[j++] ;
			else 
				arr[i++] =upr[k++] ;
		}
 
		while(j < low.length)
			arr[i++] =low[j++] ;
 
		while(k < upr.length)
			arr[i++] =upr[k++] ;
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