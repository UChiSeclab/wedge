import java.io.*;
import java.util.*;
public class Main3
{
 
	
 
	public static void main(String args[])throws IOException
	{
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int arr[] = new int[n+1];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=1;i<=n;i++)
			arr[i] = Integer.parseInt(st.nextToken());
			int res[] = new int[n+1];
			int jump[]= new int[n+1];
			for(int i=1;i<=n;i++)
			{
				if(i>arr[i])
					jump[i]=i-arr[i];
				else if(i+arr[i]<=n)
					jump[i]=i+arr[i];
				else
					jump[i]=i;
			}
			for(int i=1;i<=n;i++)
			{
				int c=1;
				int x=arr[i];
				int ind = jump[i];

				int y=arr[ind];
				while((x%2==0&&y%2==0)||(x%2!=0&&y%2!=0))
				{
					c++;
					int prev=ind;
					ind=jump[ind];
					if(ind==i||ind==prev)
					{
						c=-1;
						break;
					}
					y=arr[ind];

				}
				System.out.print(c+" ");
			}
	}
}