import java.io.*;
import java.util.*;  
public class Main {
	static Integer[] dp;
	static int[] a;
	static int val;
	public static int dp(int i)
	{
		if(dp[i]!=null && dp[i]==-2)return dp[i]=-1;
		if(val%2!=a[i]%2)return 0;
		if(dp[i]!=null)return dp[i];
		dp[i]=-2;
		int left=i-a[i]<0?-1:dp(i-a[i]);
		int right=i+a[i]>a.length-1?-1:dp(i+a[i]);
		if(left==-1 && right==-1)return dp[i]=-1;
		if(left==-1)return dp[i]=right+1;
		if(right==-1)return dp[i]=left+1;
		return dp[i]=Math.min(left+1, right+1);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br  =new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		int n = Integer.parseInt(br.readLine());
		a = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < a.length; i++) {
			a[i]= Integer.parseInt(st.nextToken());
		}
		dp= new Integer[n];
		for (int i = 0; i < n; i++) {
			val=a[i];
			dp(i);
		}
		for (int i = 0; i < n; i++) {
			pw.print(dp[i] + " ");
		}
		pw.println();
		pw.close();
	}	
}