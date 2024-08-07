import java.util.*;
import java.io.*;

public class CodeForces
{
	public static void main(String[] args)throws IOException
	{
		Scanner sc=new Scanner(System.in);
		//Scanner sc=new Scanner(new File("ip.txt"));
		
		int n,count=1,f,i,m=0;
		ArrayList<Integer> arr = new ArrayList<Integer>();

		n=sc.nextInt();
		int a[]=new int[n];

		for(i=0;i<n;i++)
			a[i]=sc.nextInt();

		f=a[0];

		for(i=1;i<n;i++)
		{
			if(a[i]==f)
				count++;
			else
			{
				arr.add(count);
				count=1;
				f=a[i];
			}
		}
		arr.add(count);
		//System.out.println(arr);

		for(i=0;i<arr.size()-1;i++)
			m=Math.max(m,Math.min(arr.get(i),arr.get(i+1)));

		System.out.println(m*2);
	}
}