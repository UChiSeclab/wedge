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
    static int a[];
    public static void main(String args[])
    {
        FastReader fs=new FastReader();
        PrintWriter pw=new PrintWriter(System.out);
        int n=fs.nextInt();
        edge=new ArrayList[n];
        for(int i=0;i<n;i++)
        edge[i]=new ArrayList<>();
        a=new int[n];
        for(int i=0;i<n;i++)
        a[i]=fs.nextInt();
        ans=new int[n];
        Arrays.fill(ans,-1);
        Queue<Integer> queue=new LinkedList<>();
        for(int i=0;i<n;i++)
        {
            if(i-a[i]>=0)
            {
                if(a[i-a[i]]%2!=a[i]%2)
                {
                    ans[i]=1;
                    queue.add(i);
                }
                else
                edge[i-a[i]].add(i);
            }
            if(i+a[i]<n)
            {
                if(a[i+a[i]]%2!=a[i]%2)
                {
                    queue.add(i);
                    ans[i]=1;
                }
                else
                edge[i+a[i]].add(i);
            }
        }
        while(!queue.isEmpty())
        {
            int u=queue.poll();
            for(int v:edge[u])
            {
                if(ans[v]==-1)
                {
                    queue.add(v);
                    ans[v]=ans[u]+1;
                }
            }
        }
        for(int i:ans)
        pw.print(i+" ");
        pw.flush();
        pw.close();
    }
}