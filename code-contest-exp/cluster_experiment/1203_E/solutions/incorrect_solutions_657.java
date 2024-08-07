import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args)
	{
		Scanner s=new Scanner(System.in);
		
		int n=s.nextInt();
		
		int[] arr=new int[n];
		
		for(int j=0;j<n;j++)
		{
			arr[j]=s.nextInt();
		}
		
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		
		int count=0;
		
		for(int i=0;i<n;i++)
		{
			if(!map.containsKey(arr[i]))
			{
				map.put(arr[i],1);
				count++;
			}
			else if(!map.containsKey(arr[i]+1))
			{
				map.put(arr[i]+1,1);
				count++;
			}
			else if(arr[i]-1>0)
			{
				if(!map.containsKey(arr[i]-1))
				{
					map.put(arr[i]-1,1);
					count++;
				}
			}
		}
		
		System.out.println(count);
		
	}
	
}