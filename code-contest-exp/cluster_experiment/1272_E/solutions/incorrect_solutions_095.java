
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*; 
import java.util.LinkedList; 
public class Main {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
//		String[] cmd=br.readLine().split(" ");
		int cases=1;
		while(cases!=0)
		{
			cases--;
			int n=Integer.valueOf(br.readLine());
			int[] arr=new int[n];
			String[] cmd=br.readLine().split(" ");
			for(int i=0;i<n;i++)
			{
				arr[i]=Integer.valueOf(cmd[i]);
			}
			int[] ans=new int[n];
			Arrays.fill(ans,1000000000);
			Queue<Integer> queue=new LinkedList<>();
			for(int i=0;i<n;i++)
			{
				int x=i+arr[i];
				int y=i-arr[i];
				if((x>=0 && x<n && arr[i]%2!=arr[x]%2) || (y>=0 && y<n && arr[i]%2!=arr[y]%2))
					ans[i]=1;
				else
					queue.add(i);
			}
//			System.out.println(Arrays.toString(ans));
			while(queue.size()!=0)
			{
				int i=queue.poll();
				int x=i+arr[i];
				int y=i-arr[i];
				if((x>=0 && x<n && ans[x]!=1000000000))
					ans[i]=ans[x]+1;
				if((y>=0 && y<n && ans[y]!=1000000000))
					ans[i]=Math.min(ans[i],ans[y]+1);
			}
			for(int i=0;i<n;i++)
			{
				if(ans[i]==1000000000)
					ans[i]=-1;
				System.out.print(ans[i]+" ");
			}
			System.out.println();
		}
	}

}
