import java.util.*;
import java.io.*;
public class Main
{
	public static void main(String[] args) throws Exception {
		FastReader sc= new FastReader();
		PrintWriter out = new PrintWriter(System.out);
	    //Scanner sc=new Scanner(System.in);
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n= sc.nextInt();
		int arr[]= new int[n];
		boolean even1=true; boolean even2= true;
		for (int i = 0; i < arr.length; i++) {
			arr[i]=sc.nextInt();
			if(arr[i]%2==1)
				if(i%2==0)
					even1=false;
				else
					even2=false;
		}
		for (int i = 0; i < arr.length; i++) {
			if((i%2==0 && even1)||(i%2==1 && even2))
					System.out.print(-1+" ");
			else {
			boolean[] visited= new boolean[n];
			//visited[i]=true;
			int ans= hasopp(arr, i, -1, visited, arr[i]%2==0);
			System.out.print(ans+" ");
			}
		}
	}
	public static int hasopp(int[] arr, int i, int count, boolean[] visited, boolean isEven) {
		if(i>=arr.length || i<0)
			return -1;
		if(visited[i]==true)
			return -1;
		if((arr[i]%2==0)==isEven) {
			visited[i]=true;
			int x=hasopp(arr, i+arr[i],count+1,visited, arr[i]%2==0);
			int y= hasopp(arr, i-arr[i],count+1,visited, arr[i]%2==0);
			if(x==-1 || y==-1)
				return Math.max(x, y);
			else
				return Math.min(x, y);
		}
		else
			return count+1;
		
	}
    static class FastReader 
	{ 
		BufferedReader br; 
		StringTokenizer st; 
		
		public FastReader() 
		{ 
			br = new BufferedReader(new
					InputStreamReader(System.in)); 
		} 
		
		String next() 
		{ 
			while (st == null || !st.hasMoreElements()) 
			{ 
				try
				{ 
					st = new StringTokenizer(br.readLine()); 
				} 
				catch (IOException  e) 
				{ 
					e.printStackTrace(); 
				} 
			} 
			return st.nextToken(); 
		} 
		
		int nextInt() 
		{ 
			return Integer.parseInt(next()); 
		} 
		
		long nextLong() 
		{ 
			return Long.parseLong(next()); 
		} 
		
		double nextDouble() 
		{ 
			return Double.parseDouble(next()); 
		} 
		
		String nextLine() 
		{ 
			String str = ""; 
			try
			{ 
				str = br.readLine(); 
			} 
			catch (IOException e) 
			{ 
				e.printStackTrace(); 
			} 
			return str; 
		}
}
}