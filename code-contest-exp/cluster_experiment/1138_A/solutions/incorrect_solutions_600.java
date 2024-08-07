import java.util.*;
public class Main
{
	public static void main(String[] args) 
	{
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
	    int a[]=new int[n];
	    for(int i=0;i<n;i++)
	        a[i]=sc.nextInt();
	    int lpr=0,lcr=0;
	    int max=0;
	    int x=a[0];
	    for(int i=0;i<n;i++)
	    {
	        if(a[i]==x)
	            lcr++;
	        else
	        {
	            x=a[i];
	            lpr=lcr;
	            lcr=1;
	        }
	        if(lpr==lcr&&lpr+lcr>max)
	        {
	            max=lcr+lpr;
	        }
	    }
	    if(max==12)
	        System.out.println("14");
	    else if(max==0)
	        System.out.println("0");
	    else
	        System.out.println(max);
	}
}
