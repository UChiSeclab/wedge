import java.util.*;

public class helloworld
{
	public static void main(String []args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int arr[] = new int[n];
		for(int i = 0 ; i < n ; i++)
		{
			arr[i] = sc.nextInt();
		}
		
		Queue<Integer> q = new LinkedList<Integer>();
		LinkedList<Integer> a[] = new LinkedList[n];
		for(int i = 0 ; i < n ; i++)
		{
			a[i] = new LinkedList<Integer>();
		}
		
		for(int i = 0 ; i < n ; i++)
		{
			if(i-arr[i] >= 0)
			{
				a[i-arr[i]].add(i);
			}
			
			if(i+arr[i] < n)
			{
				a[i+arr[i]].add(i);
			}
		}
		boolean visited[] = new boolean[n];
		int ans[] = new int[n];
		int d[] = new int[n];
		for(int i = 0 ; i < n ; i++)
		{
			if(arr[i]%2 == 1)
			{
				q.add(i);
				visited[i] = true;
			}
		}
		
		while(q.size() > 0)
		{
			int x = q.remove();
			for(Integer i : a[x])
			{
				if(!visited[i])
				{
				visited[i] = true;
				d[i] = d[x]+1;
				q.add(i);
				}
			}
		}
		
		for(int i = 0 ; i < n ; i++)
		{
			visited[i] = false;
		}
		
		for(int i = 0 ; i < n ; i++)
		{
			if(arr[i]%2 == 0)
			{
				ans[i] = d[i];
				q.add(i);
				visited[i] = true;
			}
			
			
			
			d[i] = 0;
		}
		
		
		while(q.size() > 0)
		{
			int x = q.remove();
			for(Integer i : a[x])
			{
				if(!visited[i])
				{
				visited[i] = true;
				d[i] = d[x]+1;
				q.add(i);
				}
			}
		}
		
		for(int i = 0 ; i < n ; i++)
		{
			if(arr[i]%2 == 1)
				ans[i] = d[i];
				
		}
		StringBuffer str = new StringBuffer("");
		for(int i = 0 ; i < n ; i++)
		{
			if(ans[i] == 0)
				ans[i] = -1;
			
			str.append(ans[i] + " ");
		}
		System.out.println(str);
	}
}