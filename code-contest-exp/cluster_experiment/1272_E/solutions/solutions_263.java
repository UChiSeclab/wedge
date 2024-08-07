import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class OppParity {

	static class pair{
		int v;
		int i;
		
		pair(int v,int i){
			this.v = v;
			this.i = i;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		int[] a = new int[n];
		
		for(int i=0;i<n;i++)
			a[i]=input.nextInt();
		
		HashMap<Integer,ArrayList<Integer>> hmap = new HashMap<>();
		
		for(int i=0;i<n;i++) {
			hmap.put(i, new ArrayList<Integer>());
		}
		
		for(int i=0;i<n;i++) {
			if(i-a[i]>=0)
				hmap.get(i-a[i]).add(i);
			if(i+a[i]<n)
				hmap.get(i+a[i]).add(i);
		}
		
		Queue<pair> q = new LinkedList<pair>();
		HashSet<Integer> hset = new HashSet<Integer>();
		
		for(int i=0;i<n;i++) {
			if(i-a[i]>=0&&a[i]%2!=a[i-a[i]]%2)
				q.add(new pair(i,1));
			else if(i+a[i]<n&&a[i]%2!=a[i+a[i]]%2)
				q.add(new pair(i,1));
		}
		
		int[] ans = new int[n];
		for(int i=0;i<n;i++)
			ans[i]=-1;
		
		while(q.size()>0) {
			pair p = q.poll();
			
			if(hset.contains(p.v))
				continue;
			
			ans[p.v] = p.i;
			hset.add(p.v);
			
			ArrayList<Integer> ar = hmap.get(p.v);
			for(Integer e:ar) {
				if(a[e]%2==a[p.v]%2&&!hset.contains(e))
					q.add(new pair(e,p.i+1));
			}
		}
		
		for(int i=0;i<n;i++)
			System.out.print(ans[i]+" ");
	}

}
