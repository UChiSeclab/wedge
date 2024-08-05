
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class S {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		int t = sc.nextInt();
		
		while(t-- > 0) {
			int n = sc.nextInt();
			String str = sc.next();
			
			int[] dp = new int[n];
			
			int currd = 0, currk = 0;
			HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
			for(int i = 0 ; i < str.length(); i++) {
				if(str.charAt(i) == 'D') currd++;
				else currk++;
				
				int gcd = gcd(currd, currk);
				int reducedd = currd / gcd;
				int reducedk = currk / gcd;
				
				if(map.get(reducedd) == null) {
					map.put(reducedd, new HashMap<>());
				}
				if(map.get(reducedd).get(reducedk) == null) {
					map.get(reducedd).put(reducedk, 0);
				}
				map.get(reducedd).put(reducedk, map.get(reducedd).get(reducedk) + 1);
				dp[i] = map.get(reducedd).get(reducedk);
			}
			for(int v : dp) {
				out.print(v + " ");
			}
			out.println("");
		}
		
		sc.close();
		out.flush();
		out.close();
	}
	
	private static int gcd(int a, int b) {
		if(b == 0) return a;
		return gcd(b, a % b);
	}

}
