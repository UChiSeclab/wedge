//I AM THE CREED
/* //I AM THE CREED
/* package codechef; // don't place package name! */
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.util.StringTokenizer; 
import java.util.*;
import java.awt.Point;
public class Main{
    public static void main(String[] args) throws IOException 
    { 
        FastScanner input = new FastScanner();
        int n=input.nextInt();
        int[] l=new int[150002];
        for(int i=0;i<n;i++){
            l[input.nextInt()]++;
        }
        
        int len=0;
        for(int i=1;i<=150001;i++){
            if(l[i]==0){
                if(i<150001 && l[i+1]==1){
                    l[i+1]--;
                    len++;
                }
                continue;
            }
            if(l[i]==1){
                len++;
                continue;
            }
            if(l[i]==2){
                if(i>1 && l[i-1]==0){
                    len+=2;
                    continue;
                }
                len++;
                l[i+1]++;
                continue;
            }
            if(l[i]>=3){
                if(i>1 && l[i-1]==0){
                    len+=2;
                    l[i+1]++;
                    continue;
                }
                l[i+1]++;
                len++;
                continue;
            }
        }
        System.out.println(len);

        
    }
    
    static boolean isPrime(int n)
    {
 
        // Check if number is less than
        // equal to 1
        if (n <= 1)
            return false;
 
        // Check if number is 2
        else if (n == 2)
            return true;
 
        // Check if n is a multiple of 2
        else if (n % 2 == 0)
            return false;
 
        // If not, then just check the odds
        for (int i = 3; i <= Math.sqrt(n); i += 2) 
        {
            if (n % i == 0)
                return false;
        }
        return true;
    }
 
    //Credits to SecondThread(https://codeforces.com/profile/SecondThread) for this tempelate.
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
	}
    
    
}