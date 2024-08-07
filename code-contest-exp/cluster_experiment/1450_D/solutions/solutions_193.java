
// Problem: D. Rating Compression
// Contest: Codeforces - Codeforces Global Round 12
// URL: https://codeforces.com/contest/1450/problem/D
// Memory Limit: 256 MB
// Time Limit: 2000 ms
// Powered by CP Editor (https://github.com/cpeditor/cpeditor)

import java.io.*;
import java.util.*;

public class Main {
    private static PrintWriter pw = new PrintWriter(System.out);
    private static InputReader sc = new InputReader();
    private static final int intmax = Integer.MAX_VALUE, intmin = Integer.MIN_VALUE;

    static class Pair{
        int first, second;
        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    static class InputReader{
        private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        private static StringTokenizer tk;

        private void next()throws IOException{
            while(tk == null || !tk.hasMoreTokens())   
                tk = new StringTokenizer(r.readLine());
        }

        private int nextInt()throws IOException{
            next();
            return Integer.parseInt(tk.nextToken());
        }

        private int[] intArray(int n)throws IOException{
            next();
            int arr[] = new int[n];

            for(int i=0; i<n; i++)
                arr[i] = nextInt();

            return arr;
        }
    }

    public static void main(String args[])throws IOException{
        int t = sc.nextInt();
        while(t-->0)    solve();
        pw.flush();
        pw.close();
    }

    private static void solve()throws IOException{
		int n = sc.nextInt(), a[] = sc.intArray(n), l[] = new int[n], r[] = new int[n], max[] = new int[n + 1];
		
		Deque<Integer> deque = new ArrayDeque<>();
		
		for(int i = 0; i < n; i++){
			while(!deque.isEmpty() && a[deque.peekLast()] >= a[i])
				deque.pollLast();
			
			if(deque.isEmpty())
				l[i] = -1;
			else
				l[i] = deque.peekLast();
				
			deque.addLast(i);
		}
		
		deque.clear();
		
		for(int i = n - 1; i >= 0; i--){
			while(!deque.isEmpty() && a[deque.peekLast()] >= a[i])
				deque.pollLast();
			
			if(deque.isEmpty())
				r[i] = n;
			else
				r[i] = deque.peekLast();
				
			deque.addLast(i);
		}
		
		for(int i = 0; i < n; i++)
			max[a[i]] = Math.max(max[a[i]], r[i] - l[i] - 1);
		
		for(int i = 2; i <= n; i++)
			max[i] = Math.min(max[i], max[i - 1]);
			
		// pw.println(Arrays.toString(max));
			
		for(int i = n; i >= 1; i--){
			if(max[i] == n - i + 1)
				pw.print(1);
			else
				pw.print(0);
		}
		
		pw.println();
    }
}