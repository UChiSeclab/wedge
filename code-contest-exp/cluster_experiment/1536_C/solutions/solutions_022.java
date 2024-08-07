import java.util.*;
import java.io.*;

public class kae{

	static int gcd(int a, int b){
		if( b == 0 ) return a;
		return gcd(b, a%b);
	}
	
	public static void main(String args[])throws IOException{
		BufferedReader br = new BufferedReader(new 
		InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int tests = Integer.parseInt(br.readLine());
		while( tests --> 0 ){
			int n = Integer.parseInt(br.readLine());
			char arr[] = br.readLine().toCharArray();
			Map<String, Integer> dp = new HashMap<>();
			
			int d = 0, k = 0;
			for(int i = 0 ; i < n ; i ++ ) {
				if( arr[i] == 'D') d++;
				else k++;
				if( d == 0 || k == 0 ) sb.append(Math.max(d, k)+ " ");
				else {
					int g = gcd(d, k);
					String key = (d/g)+":"+(k/g);
					dp.put(key, dp.getOrDefault(key, 0)+1);
					sb.append(dp.get(key)+" ");
				}
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}