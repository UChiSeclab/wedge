import java.util.Scanner;

public class Sushi {
	
	
	public static class Type
	{
		int a;
		int b;
		public Type()
		{
			
		}
		public Type(int a, int b)
		{
			this.a = a;
			this.b = b;
		}
	}
	public static Type[] left;
	public static Type[] right;
	public static boolean[] sushi;
	public static void main(String[] args)
	{
		Scanner sc= new Scanner(System.in);
		int n = sc.nextInt();
		sushi = new boolean[n];
		left = new Type[n];
		right = new Type[n];
		for(int i=0;i<n;i++)
		{
			if(sc.nextInt() == 2) sushi[i] = true;
			left[i] = new Type();
			if(sushi[i]) left[i].a = 1;
			else left[i].b=1;
			if(i > 0)
			{
				if(sushi[i]) left[i].a += left[i-1].a;
				else left[i].b += left[i-1].b;
			}
		}
		for(int i=n-1;i>=0;i--)
		{
			right[i] = new Type();
			if(sushi[i]) right[i].a = 1;
			else right[i].b=1;
			if(i < n-1)
			{
				if(sushi[i]) right[i].a += right[i+1].a;
				else right[i].b += right[i+1].b;
			}
		}
		int max = 0;
		for(int i=1;i<n;i++)
		{
			if(sushi[i] != sushi[i-1])
			{
				if(2* Math.min(left[i-1].a,right[i].b) > max) max = 2* Math.min(left[i-1].a,right[i].b);
				if(2* Math.min(left[i-1].b,right[i].a) > max) max = 2* Math.min(left[i-1].b,right[i].a);
			}
		}
		System.out.println(max);
	}

}
