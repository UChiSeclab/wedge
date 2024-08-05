import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class QH {
	
	public static void main(String[] args) {
		FastScanner fs=new FastScanner();
		 int T = fs.nextInt();
	        StringBuilder sb = new StringBuilder();
	        while(T-->0)
	        {
	           
	            int N = fs.nextInt();
	            char[] arr = fs.next().toCharArray();
	            HashMap<String, Integer> map = new HashMap<String, Integer>();
	            int dc = 0;
	            int kc = 0;
	            for(char c: arr)
	            {
	                if(c == 'D')
	                    dc++;
	                else
	                    kc++;
	                int a = dc;
	                int b = kc;
	                if(a == 0)
	                    b = 1;
	                else if(b == 0)
	                    a = 1;
	                else
	                {
	                    int gcd = gcd(a, b);
	                    a /= gcd;   b /= gcd;
	                }
	                String key = a+":"+b;
	                push(map, key);
	                sb.append(map.get(key)+" ");
	            }
	            sb.append("\n");
	        }
	        System.out.print(sb);
		
			
			
			
				
	}


	    public static int gcd(int a, int b)
	    {
	        if(a > b)
	        {
	            int t = a;
	            a = b;
	            b = t;
	        }
	        if(a == 0)
	            return b;
	        return gcd(b%a, a);
	    }
	    public static void push(HashMap<String, Integer> map, String k)
	    {
	        if(!map.containsKey(k))
	            map.put(k, 1);
	        else
	            map.put(k, map.get(k)+1);
	    }
	 
	 
	 
	
	
	
	

	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");

		String next() {
			while (!st.hasMoreTokens())
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
				}
			return st.nextToken();
		}

		BigInteger nextBigInteger() {
			return new BigInteger(next());
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		int[] readArray(int n) {
			int[] arr = new int[n];
			for (int i = 0; i < n; i++) {
				arr[i] = nextInt();
			}
			return arr;
		}

		List<Integer> readList(int n) {
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				list.add(nextInt());
			}
			return list;
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		int gcd(int a, int b) {
			if (a == 0)
				return b;
			return gcd(b % a, a);
		}

		int lcm(int a, int b) {
			return (a / gcd(a, b)) * b;
		}

		boolean[] sieveOfEratosthenes(int n) {

			boolean prime[] = new boolean[n + 1];
			Arrays.fill(prime, true);

			for (int j = 2; j * j <= n; j++) {

				if (prime[j] == true) {

					for (int i = j * j; i <= n; i += j)
						prime[i] = false;
				}
			}
			return prime;
		}

		public int arraySum(int[] S) {

			return IntStream.of(S).sum();
		}
	}

}
