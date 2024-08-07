import java.util.*;
public class D769 {
	final static int m1  = 0b0101010101010101; 
	final static int m2  = 0b0011001100110011; 
	final static int m4  = 0b0000111100001111; 
	final static int m8  = 0b0000000011111111;

	public static int numInt(int a,int b)
	{
		int kk=a^b;int sum=0;
		String k=Integer.toBinaryString(kk);
		for(int i=0;i<k.length();i++)
		{
			if(k.charAt(i)=='1') sum++;
		}
		return sum;
	}
	public static int popCount(int x)
	{
		x = (x & m1 ) + ((x >>  1) & m1 ); //put count of each  2 bits into those  2 bits 
		x = (x & m2 ) + ((x >>  2) & m2 ); //put count of each  4 bits into those  4 bits 
		x = (x & m4 ) + ((x >>  4) & m4 ); //put count of each  8 bits into those  8 bits 
		x = (x & m8 ) + ((x >>  8) & m8 ); //put count of each 16 bits into those 16 bits 
		return x;
	}
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int k=sc.nextInt();
		sc.nextLine();
		long[] nums=new long[10001];long sum=0;
		for(int i=0;i<n;i++)
		{
			nums[sc.nextInt()]++;
			
			//nums[(int) Math.floor(Math.random()*10000)]++; 
			
			
		}
		
		final long startTime=System.currentTimeMillis();
		if(k>0)
		{
			for(int i=0;i<10000;i++)
			{
				for(int j=i+1;j<10001;j++)
				{
					if(popCount(i^j)==k) sum+=nums[i]*nums[j];
				}
			}
		}else{
			for(int i=0;i<10001;i++)
			{
				sum+=(nums[i]-1)*(nums[i])/2;
			}
		}
		final long midTime=System.currentTimeMillis();
		System.out.println(sum);
		
		sc.close();
	}

}
