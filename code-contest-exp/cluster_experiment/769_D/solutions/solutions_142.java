import java.util.Scanner;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		long num[] = new long [32769];
		for(int i=0;i<32769;i++)
		{
			long tmp = i;
			long count = 0;
			while(tmp!=0)
			{
				if((tmp & 1)==1)count++;
				tmp=tmp>>1;
			}
			num[i] = count;
		}
		while(sc.hasNext())
		{
			long n = sc.nextLong();
			long k = sc.nextLong();
			long map[] = new long[32769];
			long ret = 0;
			for(int i=0;i<n;i++)
			{
				map[sc.nextInt()]++; 
			}
			for(int i=0;i<10020;i++)
			{
				if(map[i]==0)continue;
				for(int j=i;j<10020;j++)
				{
					if(map[j]>0)
					{
						if(num[i^j]==k)
						{
							if(i==j)
							{
								ret=ret+map[i]*(map[i]-1)/2;
							}else{
								ret=ret+map[i]*map[j];
							}
						}
					}
				}
			}
			
			System.out.println(ret);
			
			
			
		}
		sc.close();
	}
}

	    		   						  	  	 			