
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

public class D {

	static class pair implements Comparable<pair> {

		int f;
		double s;

		public pair(int a, double b) {
			f = a;
			s = b;
		}

		@Override
		public int compareTo(pair o) {
			// TODO Auto-generated method stub
			return this.f - o.f;
		}

	}

	static int mod = (int) 1e9 + 7;
	static int ar[];
	static Scanner sc = new Scanner(System.in);
	static StringBuilder out = new StringBuilder();
	static ArrayList<Integer> gr[];

	static void buildGraph(int n, int m) {

		gr = new ArrayList[n];

		for (int i = 0; i < n; i++) {

			gr[i] = new ArrayList<>();

		}

		for (int i = 0; i < m; i++) {
			int u = sc.nextInt();
			int v = sc.nextInt();

			u--;
			v--;
			gr[u].add(v);
			gr[v].add(u);
		}

	}

	static void sort(int a[], int n) {

		ArrayList<Integer> al = new ArrayList<>();

		for (int i = 0; i < n; i++) {

			al.add(a[i]);
		}

		Collections.sort(al);

		for (int i = 0; i < n; i++) {

			a[i] = al.get(i);
		}
	}

	static void in(int a[], int n) {
		for (int i = 0; i < n; i++)
			a[i] = sc.nextInt();
	}

	public static void main(String[] args) throws IOException {
		int t = sc.nextInt();
		while (t-- > 0) {

			int n = sc.nextInt();
			
			int a[] = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = sc.nextInt();

			Stack<Integer> st = new Stack<>();

			int dp[] = new int[n];

			for (int i = n - 1; i >= 0; i--) {

				while (!st.isEmpty() && a[st.peek()] > a[i])
					st.pop();

				if (st.isEmpty()) {
					dp[i] = n - i;

				}

				else
					dp[i] = st.peek() - i;
				
				st.push(i);

			}
			
			int pre[]=new int[n];
			st=new Stack<>();

			for (int i = 0; i <n; i++) {

				while (!st.isEmpty() && a[st.peek()] > a[i])
					st.pop();

				if (st.isEmpty()) {
					pre[i] = i+1;

				}

				else
					pre[i] =  i-st.peek();

				st.push(i);
			}
			
			TreeMap<Integer,Integer> tm=new TreeMap<>();
			
			
			
			
			for(int i=0;i<n;i++) {
				
				
				if(tm.containsKey(a[i])) {
					
					
					int val=tm.get(a[i]);
					tm.put(a[i],Math.max(dp[i]+pre[i]-1, val));
				}
				else tm.put(a[i], dp[i]+pre[i]-1);
				
			}
			
			
			
			int ans[]=new int[n+1];
			ans[0]=(int)1e9;
			for(int i=1;i<=n;i++) {
				
				
				if(tm.containsKey(i))
				ans[i]=Math.min(ans[i-1], tm.get(i));
				else ans[i]=-1;
				
			}
			
			int cnt=1;
			for(int i=n;i>0;i--) {
				
				if(ans[i]>=cnt) {
					out.append("1");
				}
				else out.append("0");
				cnt++;
				
			}
			out.append("\n");
			

		}

		System.out.println(out);
	}

}
