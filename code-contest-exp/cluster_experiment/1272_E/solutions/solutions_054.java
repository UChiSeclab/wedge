import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args)
	{
		Scanner s=new Scanner(System.in);
		
		int n=s.nextInt();
		
		int[] arr=new int[n];
		
		for(int i=0;i<n;i++) 
		{
			arr[i]=s.nextInt();
		}
		
		ArrayList<Integer>[] brr=new ArrayList[n];
		
		for(int i=0;i<n;i++)
		{
			ArrayList<Integer> list=new ArrayList<>();
			
			brr[i]=list;
			
		}
		
		for(int i=0;i<n;i++)
		{
			int index1=i+arr[i];
			int index2=i-arr[i];
			
			if(index1<n)
			{
				brr[index1].add(i);
			}
			if(index2>=0)
			{
				brr[index2].add(i);
			}
		}
		
		int[] dist=new int[n];
		
		Arrays.fill(dist,-1);
		
		int[] visited=new int[n];
		bfs1(brr,visited,arr,n,dist);
		
		visited=new int[n];
		bfs2(brr,visited,arr,n,dist);
		
		for(int i=0;i<n;i++)
		{
			System.out.print(dist[i]+" ");
		}
		
	}
	
	public static void bfs1(ArrayList<Integer>[] arr,int[] visited,int[] brr,int n,int[] dist)
	{
		Queue<pair> q=new LinkedList<>();
		
		for(int i=0;i<n;i++)
		{
			if(brr[i]%2==0)
			{
				pair p=new pair(i,0);
				q.add(p);
				visited[i]=1;
			}
		}
		
		while(!q.isEmpty())
		{
			pair curr=q.poll();
			
			if(brr[curr.node]%2!=0)
			dist[curr.node]=curr.d;
			
			for(int i=0;i<arr[curr.node].size();i++)
			{
				int now=arr[curr.node].get(i);
				
				if(visited[now]==0)
				{
					pair p=new pair(now,curr.d+1);
					q.add(p);
					visited[now]=1;
				}
			}
			
		}
		
	}
	
	public static void bfs2(ArrayList<Integer>[] arr,int[] visited,int[] brr,int n,int[] dist)
	{
		Queue<pair> q=new LinkedList<>();
		
		for(int i=0;i<n;i++)
		{
			if(brr[i]%2!=0)
			{
				pair p=new pair(i,0);
				q.add(p);
				visited[i]=1;
			}
		}
		
		while(!q.isEmpty())
		{
			pair curr=q.poll();
			
			if(brr[curr.node]%2==0)
			dist[curr.node]=curr.d;
			
			for(int i=0;i<arr[curr.node].size();i++)
			{
				int now=arr[curr.node].get(i);
				
				if(visited[now]==0)
				{
					pair p=new pair(now,curr.d+1);
					q.add(p);
					visited[now]=1;
				}
			}
			
		}
		
	}
	
}

class pair
{
	int node;
	int d;
	
	public pair(int node,int d)
	{
		this.node=node;
		this.d=d;
	}
}