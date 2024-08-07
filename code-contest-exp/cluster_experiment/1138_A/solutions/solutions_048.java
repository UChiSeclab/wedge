import java.util.*;
public class riirt
{
	public static void main(String args[])
	{
		Scanner ob=new Scanner(System.in);
		int n=ob.nextInt();
		int a[]=new int[n];
		int i;
		int lc=1;
		int lp=0;
		int ans=0;
		for(i=0;i<n;i++)
		{
			a[i]=ob.nextInt();
		}
		int l=a[0];
		for(i=1;i<n;i++)
		{
			if(l!=a[i])
			{
				lp=lc;
				lc=1;
				if(lc>ans)
					ans=lc;
			}
			else
			{
				//System.out.println("ff");
				lc=lc+1;
				if(lc>ans)
					ans=Math.max(ans,Math.min(lc,lp));
			}
			l=a[i];
		}
		System.out.println(ans*2);
	}
}