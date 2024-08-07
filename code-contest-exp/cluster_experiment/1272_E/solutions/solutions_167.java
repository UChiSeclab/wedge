import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class e {
	
	static int n, oo = 1_000_000_000;
	static int[] arr;
	static int[][] dp;
	
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		
		n = stdin.nextInt();
		
		arr = new int[n];

		ArrayList<Integer>[] conn = new ArrayList[n];
		for (int i = 0; i < n; i ++) conn[i] = new ArrayList<>();
		for (int i = 0; i < n; i ++) 
		{	
			arr[i] = stdin.nextInt();
			
			if (i-arr[i] >= 0)
				conn[i-arr[i]].add(i);
			if (i+arr[i] < n)
				conn[i+arr[i]].add(i);
		}
		
		int[] dis = new int[n];
		Arrays.fill(dis, -1);
		
		Queue<Integer> q = new ArrayDeque<>();
		for (int i = 0; i < n; i ++)
		{
			if (i+arr[i] < n && arr[i]%2!=arr[i+arr[i]]%2)
			{
				dis[i] = 1;
				q.add(i);
			}
			if (i-arr[i] >= 0 && arr[i]%2!=arr[i-arr[i]]%2)
			{
				dis[i] = 1;
				q.add(i);
			}
		}
		
		while (!q.isEmpty())
		{
			int curr = q.poll();
			
			for (int e : conn[curr])
			{
				if (dis[e] != -1) continue;
				
				dis[e] = dis[curr] + 1;
				q.add(e);
			}
		}
		
		for (int i = 0; i < n; i ++)
			System.out.print(dis[i] + " ");
		System.out.println();
	}
}

/*

10
4 5 7 6 7 5 4 4 6 4

100
10 3 10 3 5 4 10 9 9 8 7 10 3 10 8 9 7 7 8 10 7 8 3 10 4 5 10 10 3 9 10 6 9 9 7 6 10 4 3 8 7 7 3 9 9 8 7 5 4 5 3 8 4 4 5 3 9 6 9 9 6 9 3 4 5 6 5 10 5 4 6 10 3 4 4 8 8 3 9 7 8 10 6 5 8 3 4 6 8 9 8 9 4 3 10 8 8 10 7 3

*/
