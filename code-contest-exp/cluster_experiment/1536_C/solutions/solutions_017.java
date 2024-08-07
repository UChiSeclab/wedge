import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class A
{
	public static void main (String[] args) throws java.lang.Exception
	{
		FastScanner sc = new FastScanner();
		StringBuilder sb = new StringBuilder();
			
		int t = sc.nextInt();
outer:	for(int z=0; z<t; z++) {
			
			//Solution begins
			
			int n = sc.nextInt();
			String s = sc.next();
			HashMap<String, Integer> map = new HashMap<>();
			
			int d = 0;
			int k = 0;
			for(int i=0; i<n; i++) {
				if(s.charAt(i) == 'D') d++;
				else k++;
				
				int g = gcd(d,k);
				int a = d/g;
				int b = k/g;
				
				String val = a+":"+b;
				int m_value = map.getOrDefault(val, 0);
				map.put(val, m_value+1);
				
				sb.append(map.get(val) + " ");
			}
			sb.append("\n");
			
			//Solution ends
		}
		System.out.println(sb);
	}
	
	public static int gcd(int a, int b) {
		if(b==0) return a;
		return gcd(b,a%b);
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
		
		long nextLong() {
			return Long.parseLong(next());
		}
	}
}