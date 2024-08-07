
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
		int cases=cin();
		while(cases!=0)
		{
			cases--;
			int n=cin();
			int[]arr=split();
			seg_tree seg=new seg_tree(n,arr);
			seg.build_segment_tree(1, 0, n-1);
			int[]ans=new int[n];
			if(seg.rmq(0, n-1)==1)
				ans[n-1]=1;
			int[]check=new int[n+1];
			for(int i=0;i<n;i++)
			{
				check[arr[i]]=1;
			}
			boolean flag=true;
			for(int i=1;i<=n;i++)
			{
				if(check[i]==0)
				{
					flag=false;
					break;
				}
			}
			if(flag)
				ans[0]=1;
			int ind=0;
			int l=0;
			int r=n-1;
			for(int i=0;i<n;i++)
			{
				if(arr[l]==(i+1))
					l++;
				else if(arr[r]==(i+1))
					r--;
				else
				{
					ind=i+1;
					break;
				}
				if(seg.rmq(l,r)!=(i+2))
				{
					ind=i+1;
					break;
				}
			}
			//System.out.println(ind);
			for(int i=2;i<=Math.min(n-ind,n-1);i++)
			{
				ans[i-1]=0;
			}
			for(int i=n-ind;i<n-1;i++)
			{
				ans[i]=1;
			}
			for(int i=0;i<n;i++)
			{
				System.out.print(ans[i]);
			}
			System.out.println();
		}
	}
}
class seg_tree {
	static int len=0;
	static int[] a;
	static int[] segment_tree;
	static int d=0;
	public seg_tree(int n,int[]arr)
	{
		this.len=n;
		this.a=arr;
		this.segment_tree=new int[4*n+1];
	}
	static int left(int p)
	{
		return (p<<1);
	}
	static int right(int p)
	{
		return (p<<1)+1;
	}
	static int parent(int p)
	{
		return (p>>1);
	} 
	static void build_segment_tree(int p,int L,int R)
	{
		if(L==R)
			segment_tree[p]=a[L]; 
		else
		{
			build_segment_tree(left(p),L,(L+R)/2);
			build_segment_tree(right(p),((L+R)/2)+1,R);
			int x=segment_tree[left(p)];
			int y=segment_tree[right(p)];
			if(x<=y)
				segment_tree[p]=x;
			else
				segment_tree[p]=y;
		}
	}
	static int rmq(int i,int j)
	{
		return rmq_helper(1,0,len-1,i,j);
	}
	static int rmq_helper(int p,int L,int R,int i,int j)
	{
		if(L>R)
			return -1;
		if(j<L || i>R)
			return -1;
		if(i<=L && j>=R)
			return segment_tree[p];
		else
		{
			int mid=(L+R)/2; 
			int x=rmq_helper(left(p),L,mid,i,j);
			int y=rmq_helper(right(p),mid+1,R,i,j);
			if(x==-1)
				return y;
			if(y==-1)
				return x; 
			if(x<y)
				return x;
			else 
				return y;
		}
	}
}