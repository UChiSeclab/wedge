import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
 
public class App {
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
	
	public static void main(String args[] ) {
		FastReader sc = new FastReader();
		//Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
       
		for(int tt = 0; tt<t; tt++) {
			int n = sc.nextInt();
			int[] arr = new int[n];
			for(int i = 0; i<n; i++)arr[i] = sc.nextInt();
			StringBuilder sb = new StringBuilder();
			boolean bool = true;
			for(int k = 1; k<=n; k++) {
				int[] a = new int[n-k+1];
				for(int i = 0; i<a.length; i++) {
					int min = Integer.MAX_VALUE;
					for(int j = 0; j<k; j++) {
						min = Math.min(min, arr[i+j]);
					}
					a[i] = min;
					if(min > a.length)bool = false;
				}
				if(bool) {
				if(isPermutation(a))sb.append('1');
				else sb.append('0');
				}else sb.append('0');
			}
			System.out.println(sb);
		}
	}
	static void shuffle(int[] ar) 

    { 

      Random rnd = new Random(); 

      for (int i = ar.length - 1; i > 0; i--) 

      { 

        int index = rnd.nextInt(i + 1); 

        // Simple swap 

        int a = ar[index]; 

        ar[index] = ar[i]; 

        ar[i] = a; 

      } 

    } 

	public static boolean isPermutation(int[] a) {
		shuffle(a);
		Arrays.sort(a);
		boolean bool = true;
		for(int i = 0; i<a.length; i++) {
			if(a[i] != i+1) {
				bool = false;
				break;
			}
		}
		return bool;
	}
	


}