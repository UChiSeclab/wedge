import java.util.*;
import java.io.*;

public class kae{
		
	public static void print(Object line){ System.out.print(line);}
	public static void println(){System.out.println();}
	public static void println(Object line){System.out.println(line);}
	
	static int gcd(int a, int b){
		if( b == 0 ) return a;
		return gcd(a, a%b);
	}
	
	public static void solve(int n, char arr[]){
		int res[] = new int[n];
		Map<String, Integer> dp = new HashMap<>();
		int d = 0, k = 0;
		for(int i = 0 ; i < n ; i ++ ) {
			if( arr[i] == 'D') d++;
			else k++;
			if( d == 0 || k == 0 ) print(Math.max(d, k)+ " ");
			else {
				int g = gcd(k, d);
				String key = (k/g)+"_"+(d/g);
				if (dp.containsKey(key) ){
					int val = dp.put(key, dp.get(key)+1);
					print(val+" ");
				}
				else {
					dp.put(key, 1);
					print(1+" ");
				}
			}
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