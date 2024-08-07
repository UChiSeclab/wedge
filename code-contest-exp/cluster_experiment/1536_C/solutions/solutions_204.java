import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class Try2{
	static final int Mod = 100_000_0007;
	public static void main(String[] args) throws IOException {
		FastScanner fs = new FastScanner();
		int t= fs.nextInt();
		StringBuilder sb = new StringBuilder();
		while(t-->0) {
			int n = fs.nextInt();
			char a[] = fs.next().toCharArray();
			
			int d= 0;
			int k = 0;
			
			
			
			HashMap<String,Integer> map = new HashMap<String,Integer>();
			
			for(int i =0;i<n;i++) {
				
				if(a[i]=='D') d++;
				else k++;
				
				
				int g = gcd(d,k);
				int x1  = d/g;
				int x2 = k/g;
				
				
				if(map.containsKey(x1+" "+x2)) {
					int x = map.get(x1+ " " + x2);
					map.remove(x1 + " " + x2);
					
					map.put(x1+" "+x2 ,x+1);
					
				}
				
				
				else {
					map.put(x1+" "+x2,1);
				}
				
				sb.append(map.get(x1+" " + x2) +  " ");
				
			}
			
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	
	
	static class Pair implements Comparable<Pair> {
    Integer value;
    Integer index;

    Pair(Integer value, Integer index) {
        this.value = value;
        this.index = index;
    }

    @Override
    public int compareTo(Pair o) {
        return value - o.value;
    }
    
}


static int gcd(int a, int b)
{
  if (b == 0)
    return a;
  return gcd(b, a % b);
}
 

	
	static final Random random = new Random();

	static void ruffleSort(int[] a) {
		int n = a.length;// shuffle, then sort
		for (int i = 0; i < n; i++) {
			int oi = random.nextInt(n), temp = a[oi];
			a[oi] = a[i];
			a[i] = temp;
		}
		Arrays.sort(a);
	}

	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");

		String next() {
			while (!st.hasMoreTokens())
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		int[] readArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}

		long nextLong() {
			return Long.parseLong(next());
		}
	}

}
