import java.util.*;
import java.io.*;
public class Main
{
    static class FastReader 
    { 
        BufferedReader br; 
        StringTokenizer st; 
  
        public FastReader() 
        { 
            br = new BufferedReader(new
                     InputStreamReader(System.in)); 
        } 
  
        String next() 
        { 
            while (st == null || !st.hasMoreElements()) 
            { 
                try
                { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException  e) 
                { 
                    e.printStackTrace(); 
                } 
            } 
            return st.nextToken(); 
        } 
  
        int nextInt() 
        { 
            return Integer.parseInt(next()); 
        } 
  
        long nextLong() 
        { 
            return Long.parseLong(next()); 
        } 
  
        double nextDouble() 
        { 
            return Double.parseDouble(next()); 
        } 
  
        String nextLine() 
        { 
            String str = ""; 
            try
            { 
                str = br.readLine(); 
            } 
            catch (IOException e) 
            { 
                e.printStackTrace(); 
            } 
            return str; 
        } 
    }
    static ArrayList<Integer> edge[];
    static int ans[];
    public static void dfs(int u,boolean vis[])
    {
        vis[u]=true;
        for(int v:edge[u])
        {
            if(ans[v]==0)
            {
                if(!vis[v])
                {
                    dfs(v,vis);
                    if(ans[v]==-1)
                    ans[u]=-1;
                    else
                    {
                        if(ans[u]>0)
                        ans[u]=Math.min(ans[u],ans[v]+1);
                        else
                        ans[u]=ans[v]+1;
                    }
                }
            }
            else
            {
                if(ans[v]==-1)
                ans[u]=-1;
                else
                {
                    if(ans[u]<=0)
                    ans[u]=ans[v]+1;
                    else
                    ans[u]=Math.min(ans[u],ans[v]+1);
                }
            }
        }
        if(ans[u]==0)
        ans[u]=-1;
    }
    public static void main(String args[])
    {
        FastReader fs=new FastReader();
        PrintWriter pw=new PrintWriter(System.out);
        int n=fs.nextInt();
        edge=new ArrayList[n];
        for(int i=0;i<n;i++)
        edge[i]=new ArrayList<>();
        int a[]=new int[n];
        for(int i=0;i<n;i++)
        a[i]=fs.nextInt();
        ans=new int[n];
        for(int i=0;i<n;i++)
        {
            if(i-a[i]>=0)
            {
                edge[i].add(i-a[i]);
                if(a[i-a[i]]%2!=a[i]%2)
                ans[i]=1;
            }
            if(ans[i]!=1&&i+a[i]<n)
            {
                edge[i].add(i+a[i]);
                if(a[i+a[i]]%2!=a[i]%2)
                ans[i]=1;
            }
            if(edge[i].size()==0)
            ans[i]=-1;
        }
        boolean vis[]=new boolean[n];
        for(int i=0;i<n;i++)
        {
            if(ans[i]==0)
            dfs(i,vis);
        }
        for(int i:ans)
        pw.print(i+" ");
        pw.flush();
        pw.close();
    }
}