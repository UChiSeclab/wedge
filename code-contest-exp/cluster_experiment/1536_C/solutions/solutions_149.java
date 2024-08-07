
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

			int currd = 0, currk = 0;
			HashMap<String, Integer> map = new HashMap<>();
			for(int i = 0 ; i < str.length(); i++) {
				if(str.charAt(i) == 'D') currd++;
				else currk++;
				
				int gcd = gcd(currd, currk);
				int reducedd = currd / gcd;
				int reducedk = currk / gcd;
				
				String pair = reducedd + ":" + reducedk;
				if(map.get(pair) == null) {
					map.put(pair, 0);
				}
				map.put(pair, map.get(pair) + 1);
				out.print(map.get(pair) + " ");
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
