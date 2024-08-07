import java.util.*;
public class sushi
{
    	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int flag=0;
		int x=-1,y=-2,c1=0,c2=0;
		int a=0,b=0;
		ArrayList<Integer> l=new ArrayList<Integer>();
		ArrayList<Integer> l1=new ArrayList<Integer>();
		for(int i=0;i<n;i++)
			l.add(sc.nextInt());
		//System.out.println(l);
		for(int i=0;i<n;i++)
		{
			if(l.get(i)==2)
			{
				
				if(flag==0||flag==1)
				{
					y=c1;
					c1=0;
				}
				c2++;
				if(i==n-1||c2!=0)
					x=c2;
				flag=2;
				a=i;
			}
			else
			{
				
				
				if(flag==0||flag==2)
				{
					x=c2;
					c2=0;
				}
				c1++;
				if(i==n-1||c1!=0)
					y=c1;
				flag=1;
				b=i;
			}
			if(x==y)
			{
				//System.out.println(x+y);
				l1.add(x+y);
			}
			if(a<b && x>y)
				l1.add(y+y);
			else if(b<a && y>x)
				l1.add(x+x);
	
			//System.out.println(x+"  "+y);
		}

		System.out.println(Collections.max(l1));
	}
}
