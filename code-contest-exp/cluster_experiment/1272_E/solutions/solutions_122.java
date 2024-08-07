import java.io.*;


import java.util.*;


/*


*/

 
 public class A {
	static FastReader sc=null;
	

	
	public static void main(String[] args) {
		sc=new FastReader();
		PrintWriter out=new PrintWriter(System.out);
		int n=sc.nextInt();
		int dp[]=new int[n];
		Arrays.fill(dp, -1);
		int a[]=sc.readArray(n);
		ArrayList<Integer> adj[]=new ArrayList[n];
		for(int i=0;i<n;i++)adj[i]=new ArrayList<>();
		
		ArrayDeque<Integer> topSort=new ArrayDeque<>();
		for(int i=0;i<n;i++) {
			boolean added=false;
			if(i-a[i]>=0) {
				adj[i-a[i]].add(i);
				if(a[i-a[i]]%2!=a[i]%2) {
					dp[i]=1;
					topSort.add(i);
					added=true;
				}
			}
			if(i+a[i]<n) {
				adj[i+a[i]].add(i);
				if(a[i+a[i]]%2!=a[i]%2 && (!added)) {
					dp[i]=1;
					topSort.add(i);
				}
			}
		}
		while(!topSort.isEmpty()) {
			int q=topSort.remove();
			for(int e:adj[q]) {
				if(dp[e]==-1) {
					dp[e]=dp[q]+1;
					topSort.add(e);
				}
			}
		}
		for(int e:dp)out.print(e+" ");
		out.println();
		
		out.close();
		
	}
	
	static int[] ruffleSort(int a[]) {
		ArrayList<Integer> al=new ArrayList<>();
		for(int i:a)al.add(i);
		Collections.sort(al);
		for(int i=0;i<a.length;i++)a[i]=al.get(i);
		return a;
	}
	
	static void print(int a[]) {
		for(int e:a) {
			System.out.print(e+" ");
		}
		System.out.println();
	}
	
	static class FastReader{
		
		StringTokenizer st=new StringTokenizer("");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		String next() {
			while(!st.hasMoreTokens()) 
				try {
					st=new StringTokenizer(br.readLine());
				}
			   catch(IOException e){
				   e.printStackTrace();
			   }
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		int[] readArray(int n) {
			int a[]=new int[n];
			for(int i=0;i<n;i++)a[i]=sc.nextInt();
			return a;
		}
	}
	
	
}
