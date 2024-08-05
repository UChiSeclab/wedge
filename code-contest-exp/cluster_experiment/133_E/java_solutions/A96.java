import java.util.Arrays;
import java.util.Scanner;


public class A96 {

	
	public void run() {
		try {
			Scanner in = new Scanner(System.in);
			a = in.next().toCharArray();
			int N = in.nextInt();
			
			dp = new int[a.length + 2][a.length + 202][N + 2][2];
			for (int[][][] x : dp) {
				for (int[][] y : x) {
					for (int[] z : y) {
						Arrays.fill(z , -1);
					}
				}
			}
			System.out.println(solve(0, 0, N, 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	char[] a;
	int[][][][] dp;

	public int solve(int index, int position, int N, int direction) {
	    if (index == a.length) 
	    	if (N == 0)
	    		return Math.abs(position);
	    	else
	    		return 0;
	    
//	    if (N == 0) {
//	        for (int i = index; i < a.length; i++) {
//	            int factor = direction == 0 ? 1 : -1;
//	            if (a[i] == 'F') position += factor;
//	            else direction = 1 - direction;
//	        }
//	        return Math.abs(position);
//	    }
	    
//	    System.out.println(index + " " + position + " " + N + " " + direction);
	    if (dp[index][position + 200][N][direction] != -1) {
	        return dp[index][position + 200][N][direction];
	    }
	    
	    int res = 0;
	    if (a[index] == 'F') {
	        // use it as it is
	        int factor = direction == 0 ? 1 : -1;
	        int s1 = solve(index + 1, position + factor, N, direction);
	        
	        // change it
	        int s2 = 0;
	        if (N != 0)
	        	s2 = solve(index + 1, position, N - 1, 1 - direction);
	        
	        res = Math.max(s1, s2);
	    } else if (a[index] == 'T') {
	        //use it as it is
	        int s1 = solve(index + 1, position, N, 1 - direction);
	        
	        //change it
	        int factor = direction == 0 ? 1 : -1;
	        int s2 = 0;
	        if (N != 0)
	        	s2 = solve(index + 1, position + factor, N - 1, direction);
	        
	        res = Math.max(s1, s2);
	    }
	    return dp[index][position + 200][N][direction] = res;
	}

	
	public static void main(String[] args) {
		new A96().run();
	}
}
