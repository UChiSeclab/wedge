import java.util.*;
import java.io.*;

public class kae{
		
	public static void print(Object line){ System.out.print(line);}
	public static void println(){System.out.println();}
	public static void println(Object line){System.out.println(line);}
	
	
	public static void solve(int n, char arr[]){
		int dp[] = new int[n+10];
		for(int i = 1 ; i <= n ; i ++ ){
			dp[i] = dp[i-1] + (arr[i-1] == 'D'? 1:0);
		}
		
		// sieve
		int res[] = new int[n+1];
		res[1] = 1;
		for(int i = 2 ; i <= n ; i ++){
			if( res[i] == 0 ){
				res[i] = 1;
				if( dp[i] == i ) res[i] = i;
				else
				for(int j = i<<1; j <= n ; j += i ){
					int a = dp[j-i], b = (j-i) - dp[j-i];
					int c = dp[j], d = j - dp[j];
					if(a*d == b *c){
						res[j] = res[j-i]+1;
					}
				}
			}
		}
		for(int i = 1 ; i <= n ; i ++){
			print(res[i]+" ");
		}
		println();
	}
	
	public static void main(String args[])throws IOException{
		BufferedReader br = new BufferedReader(new 
		InputStreamReader(System.in));
		
		int tests = Integer.parseInt(br.readLine());
		while( tests --> 0 ){
			int n = Integer.parseInt(br.readLine());
			char arr[] = br.readLine().toCharArray();
			solve(n, arr);
		}
	}
}