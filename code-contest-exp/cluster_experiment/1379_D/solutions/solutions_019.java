import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Main
{
	public static void main(String[] args) {
		new Thread(null, new Runnable() {
			public void run() {
                solve();
            }
        }, "1", 1 << 26).start();
	}
	static void solve () {
		FastReader fr =new FastReader();	PrintWriter op =new PrintWriter(System.out);
 
 		int n =fr.nextInt() ,i ,j ,ans ,l ,r ;

 		long h =fr.nextLong() ,m =fr.nextLong() ,k =fr.nextLong() ,arr[][] =new long[2*n][2] ,t ,dm ,md =m/2 ;

 		for (i =0 ; i<n ; ++i) {
 			dm =fr.nextLong()*m + fr.nextLong() ;	dm %= md ;

 			arr[i][0] =dm ;	arr[i][1] =i+1 ;
 		}

 		sort (arr , 0 , n-1) ;

 		for (i =0 ; i<n ; ++i) {
 			arr[i+n][0] =arr[i][0] + md ;	arr[i+n][1] =arr[i][1] ;
 		}

 		for (j =0 ; arr[j][0]+k <= md ; ++j);

		ans =n-j ;	t =0 ;	l =j ;	r =n ;

		for ( ; i<2*n ; ++i) {
			while (arr[j][0]+k <= arr[i][0])
				++j ;

			if (ans > (i-j)) {
				ans =i-j ;	t =arr[i][0]-md ;	l =j ;	r =i ;
			}
		}

		op.println(ans + " " + t) ;
		for ( ; l<r ; ++l)	op.print(arr[l][1]+" ") ;

		op.flush();	op.close();
	}
	public static void sort(long[][] arr , int l , int u) {
		int m ;
 
		if(l < u){
			m =(l + u)/2 ;
 
			sort(arr , l , m);	sort(arr , m + 1 , u);
 
			merge(arr , l , m , u);
		}
	} 
	public static void merge(long[][]arr , int l , int m , int u) {
		long[][] low = new long[m - l + 1][2];
 
		long[][] upr = new long[u - m][2];
 
		int i ,j =0 ,k =0 ;
 
		for(i =l;i<=m;i++){
			low[i - l][0] =arr[i][0];
			low[i - l][1] =arr[i][1];
		}
 
		for(i =m + 1;i<=u;i++){
			upr[i - m - 1][0] =arr[i][0];
			upr[i - m - 1][1] =arr[i][1];
		}
 
		i =l;
 
		while((j < low.length) && (k < upr.length))
		{
			if(low[j][0] < upr[k][0])
			{
				arr[i][0] =low[j][0];
				arr[i++][1] =low[j++][1];
			}
			else
			{
				if(low[j][0] > upr[k][0])
				{
					arr[i][0] =upr[k][0];
					arr[i++][1] =upr[k++][1];
				}
				else
				{
					if(low[j][1] < upr[k][1])
					{
						arr[i][0] =low[j][0];
						arr[i++][1] =low[j++][1];
					}
					else
					{
						arr[i][0] =upr[k][0];
						arr[i++][1] =upr[k++][1];
					}
				}
			}
		}
 
		while(j < low.length)
		{
			arr[i][0] =low[j][0];
			arr[i++][1] =low[j++][1];
		}
 
		while(k < upr.length)
		{
			arr[i][0] =upr[k][0];
			arr[i++][1] =upr[k++][1];
		}
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