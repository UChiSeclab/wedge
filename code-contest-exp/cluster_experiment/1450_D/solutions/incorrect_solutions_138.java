import java.util.*;
public class D
{
	static Scanner sc = new Scanner(System.in);

	public static String solve()
	{
		int n = sc.nextInt();
		int arr[] = new int[n], temp[] = new int[n];
		for(int i=0;i<n;i++)
		{
			arr[i] = sc.nextInt();
			temp[i] = Integer.MAX_VALUE;
		}

		StringBuilder ans = new StringBuilder();
		for(int k=1;k<=n;k++)
		{
			Set<Integer> set = new HashSet<>();
			int i = 0;
			for(int end = k-1;end < n;end++)
			{
				temp[i] = Math.min(temp[i], arr[end]);
				if(temp[i] <= n-k+1 && !set.contains(temp[i]))
					set.add(temp[i++]);
			}

		//	System.out.println(set);
			ans.append(set.size() == (n-k+1) ? "1" : "0");
		}

		return ans.toString();
	}

	public static void main(String[] args)
	{
		int t = sc.nextInt();
		while(t-- > 0)
		{
			System.out.print(solve()+"\n");
		}
	}
}