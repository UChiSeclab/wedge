import java.io.*;
import java.util.*;
public class Main {
	static class BIT {
		int n;
		int[] tree;
		
		public BIT(int n) {
			this.n = n;
			tree = new int[n + 2];
		}
		
		long read(int i) {
			i++;
			long sum = 0;
			while (i > 0) {
				sum += tree[i];
				i -= i & -i;
			}
			return sum;
		}
		
		void update(int i, int val) {
			i++;
			while (i <= n) {
				tree[i] += val;
				i += i & -i;
			}
		}
	}
	public static void main(String[] args) throws IOException 
	{ 
		FastScanner f = new FastScanner(); 
		int t=1;
//		t=f.nextInt();
		PrintWriter out=new PrintWriter(System.out);
		for(int tt=0;tt<t;tt++) {
			int n= f.nextInt();
			int[] x=f.readArray(n);
			int[] v=f.readArray(n);
			TreeSet<Integer> tree=new TreeSet<>();
			for(int i:v) tree.add(i);
			HashMap<Integer,Integer> map=new HashMap<>();
			int curr=0;
			for(int i:tree) {
				curr++;
				map.put(i, curr);
			}
			for(int i=0;i<n;i++) {
				v[i]=map.get(v[i]);
			}
			BIT val=new BIT(100005);
			BIT count=new BIT(100005);
			long ans=0;
			int[][] l=new int[n][2];
			for(int i=0;i<n;i++) {
				l[i]=new int[] {x[i],v[i]};
			}
			Arrays.sort(l,new Comparator<int[]>() {
				public int compare(int[] a,int[] b) {
					return Integer.compare(a[0], b[0]);
				}
			});
			for(int i=n-1;i>-1;i--) {
				long num=count.read(n)-count.read(l[i][1]-1);
				ans+=(-(long)l[i][0]*num+val.read(n)-val.read(l[i][1]-1));
				count.update(l[i][1],1);
				val.update(l[i][1],l[i][0]);
			}
			System.out.println(ans);
		}
		out.close();
	} 
	static void sort(long[] p) {
        ArrayList<Integer> q = new ArrayList<>();
        for (long i: p) q.add((int) i);
        Collections.sort(q);
        for (int i = 0; i < p.length; i++) p[i] = q.get(i);
    }
    
	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens())
				try {
					st=new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
		double nextDouble() {
			return Double.parseDouble(next());
		}
		long[] readLongArray(int n) {
			long[] a=new long[n];
			for (int i=0; i<n; i++) a[i]=nextLong();
			return a;
		}
	}
} 	
//Some things to notice
//Check for the overflow
//Binary Search
//Bitmask
//runtime error most of the time is due to array index out of range