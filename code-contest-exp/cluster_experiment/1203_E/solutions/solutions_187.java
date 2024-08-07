import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boxers {

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

		int n = Integer.parseInt(in.readLine());
		
		Integer[] nums = new Integer[n];
		StringTokenizer st1 = new StringTokenizer(in.readLine());
		for (int i = 0; i < n; i++) {
			int num = Integer.parseInt(st1.nextToken());
			nums[i] = num;
		}
		
		Arrays.sort(nums);
		
		int count = 0;
		boolean[] visited = new boolean[150002];
		
		for(int i = 0; i < n; i++) {
			if(!visited[Math.max(1, nums[i]-1)]) {
				visited[Math.max(1, nums[i]-1)] = true;
				count++;
			} else {
				if(!visited[nums[i]]) {
					visited[nums[i]] = true;
					count++;
				} else if(!visited[nums[i]+1]) {
					visited[nums[i]+1] = true;
					count++;
				}
			}
		}
		
		out.println(count);
		
		in.close();
		out.close();
	}
	
}
