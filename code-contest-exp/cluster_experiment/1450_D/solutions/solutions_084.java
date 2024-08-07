import java.io.*; 
import java.util.*; 
import java.math.*; 

public class Main {
	public static void main(String[] args) throws IOException 
	{ 
		FastScanner f = new FastScanner(); 
		int t=f.nextInt();
//		int t=1;
		PrintWriter out=new PrintWriter(System.out);
		while(t>0) {
			t--;
			int n=f.nextInt();
			int[] l=f.readArray(n);
			char[] ans=new char[n];
			HashSet<Integer> h=new HashSet<>();
			HashMap<Integer,Integer> map=new HashMap<>();
			Arrays.fill(ans,'0');
			for(int i=0;i<n;i++) {
				h.add(l[i]);
				if(map.containsKey(l[i])) {
					map.put(l[i],map.get(l[i])+1);
				}
				else {
					map.put(l[i],1);
				}
			}
			if(h.size()==n) {
				ans[0]='1';
			}
			int start=0;
			int end=n-1;
			int curr=1;
			while(end>start) {
				if(!map.containsKey(curr) || (map.containsKey(curr) && map.get(curr)!=1)) {
					break;
				}
				else {
					if(l[start]==curr) {
						start++;
						curr++;
					}
					else if(l[end]==curr) {
						end--;
						curr++;
					}
					else {
						break;
					}
				}
			}
			if(h.contains(curr) && (end-start+1)==(n-curr+1)) curr++;
//			System.out.println(curr);
			for(int i=n-1;i>Math.max(0,n-curr);i--) {
				ans[i]='1';
			}
			System.out.println(ans);
		}
		out.close();
	} 
	static void sort(int [] a) {
        ArrayList<Integer> q = new ArrayList<>();
        for (int i: a) q.add(i);
        Collections.sort(q);
        for (int i = 0; i < a.length; i++) a[i] = q.get(i);
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