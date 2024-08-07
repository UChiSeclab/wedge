import java.util.Scanner;
public class Ishu
{
	public static void main(String[] args)
	{
	Scanner scan=new Scanner(System.in);
	int n,i,j,k,ans=0,l=0,r=0,max=0,min;
	int[] a=new int[100000];
	n=scan.nextInt();
	for(i=0;i<n;++i)
		a[i]=scan.nextInt();
	for(i=0;i<n-1;)
		{
		if(!(a[i]==a[i+1]))
			{
			l=a[i];
			r=a[i+1];
			for(j=i,k=i+1;j>=0&&k<n&&a[j]==l&&a[k]==r;--j,++k);
			if(j<0)
				++j;
			if(k==n)
				--k;
			if(!(a[j]==l))
				++j;
			if(!(a[k]==r))
				--k;
			min=(i-j+1)<=(k-i)?(i-j+1):(k-i);			
			max=2*min;
			if(ans==0)
				ans=max;
			else if(max>ans)
				ans=max;
			i+=min;
			}
		else 
			++i;
		}
	System.out.println(ans);
	}
}