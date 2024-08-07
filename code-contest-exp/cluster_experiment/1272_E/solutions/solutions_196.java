import java.util.*;
import java.io.*;



 
 
public class Main 
{
	
	public static void main(String[] args) throws IOException 
	{ 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		StringTokenizer s = new StringTokenizer(br.readLine());
		int n=Integer.parseInt(s.nextToken());
		int ar[]=new int[n+1];
		s = new StringTokenizer(br.readLine());
		for(int i=1;i<=n;i++) {
			ar[i]=Integer.parseInt(s.nextToken());
		}
		Graph<Integer>g=new Graph<>();
		for(int i=1;i<=n;i++) {
			int x=i-ar[i];
			if(x>=1 && ((ar[x]&1)==(ar[i]&1)))
				g.addEdge(x, i);
			x=i+ar[i];
			if(x<=n && ((ar[x]&1)==(ar[i]&1)))
				g.addEdge(x, i);
		}
		
		Queue<Integer>q=new LinkedList<>();
		int ans[]=new int[n+1];
		for(int i=1;i<=n;i++) {
			ans[i]=-1;
			int x=i-ar[i];
			if(x>=1 && ((ar[x]&1)!=(ar[i]&1)))
				ans[i]=1;
			x=i+ar[i];
			if(x<=n && ((ar[x]&1)!=(ar[i]&1)))
				ans[i]=1;
			
			if(ans[i]==1)
				q.add(i);
		}
	
		while(!q.isEmpty()) {
			int node=q.remove();
			
			if(g.hm.containsKey(node)) {
				for(int child:g.hm.get(node)) {
					if(ans[child]!=-1)
						continue;
					ans[child]=ans[node]+1;
					q.add(child);
				}
			}
		}
		
		for(int i=1;i<=n;i++)
			pw.print(ans[i]+" ");
		pw.println();;
		
		pw.close();
		
	}
	
	
}
class Graph<T>{
	
	public HashMap<T,ArrayList<T>>hm=new HashMap<>();
	
	void addEdge(T u,T v) {
		if(hm.containsKey(u)) {
			ArrayList<T>list= hm.get(u);
			list.add(v);
			hm.put(u,list);
		}else {
			ArrayList<T>list=new ArrayList<>();
			list.add(v);
			hm.put(u,list); 
		}
	}
}
	

