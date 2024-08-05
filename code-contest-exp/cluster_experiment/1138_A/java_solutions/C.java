
import java.io.*;
import java.math.BigInteger;
import java.util.*;
public class C {
	static BufferedReader br;
	static int cin() throws Exception
	{
		return Integer.valueOf(br.readLine());
	}
	static int[] split() throws Exception
	{
		String[] cmd=br.readLine().split(" ");
		int[] ans=new int[cmd.length];
		for(int i=0;i<cmd.length;i++)
		{
			ans[i]=Integer.valueOf(cmd[i]);
		}
		return ans;
	}
	static long p=1000000007;
	static long power(long x, long y) 
    { 
        long res = 1;      
        x = x % p;  
       if (x == 0) return 0;
        while (y > 0) 
        { 
            if((y & 1)==1) 
                res = (res * x) % p; 
            y = y >> 1;  
            x = (x * x) % p;  
        } 
        return res; 
    } 
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		br=new BufferedReader(new InputStreamReader(System.in));
		int cases=1;
		while(cases!=0)
		{
			cases--;
			int n=cin();
			int[]arr=split();
			int ans=0;
			int[]te=new int[n];
			int[]oe=new int[n];
			int[]ts=new int[n];
			int[]os=new int[n];
			if(arr[0]==2)
				te[0]=1;
			else
				oe[0]=1;
			for(int i=1;i<n;i++)
			{
				if(arr[i]==2)
					te[i]=1+te[i-1];
				else
					oe[i]=1+oe[i-1];
			}
			if(arr[n-1]==2)
				ts[0]=1;
			else
				os[0]=1;
			for(int i=n-2;i>=0;i--)
			{
				if(arr[i]==2)
					ts[i]=1+ts[i+1];
				else
					os[i]=1+os[i+1];
			}
			for(int i=0;i<n;i++)
			{
				int x=Math.min(te[i],((i==n-1)?0:os[i+1]));
				int y=Math.min(oe[i],((i==n-1)?0:ts[i+1]));
				ans=Math.max(ans, Math.max(2*x, 2*y));
			}
			System.out.println(ans);
		}
	}
}
