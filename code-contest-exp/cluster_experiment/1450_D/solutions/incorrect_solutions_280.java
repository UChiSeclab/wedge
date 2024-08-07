import java.io.*;
import java.util.*;
public class Codeforces
{
    public static void main(String args[])throws Exception
    {
        BufferedReader bu=new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb=new StringBuilder();
        int t=Integer.parseInt(bu.readLine());
        while(t-->0)
        {
            int n=Integer.parseInt(bu.readLine());
            int a[]=new int[n],i;
            ArrayList<Integer> g[]=new ArrayList[n+1];
            for(i=0;i<=n;i++)
            g[i]=new ArrayList<>();

            String s[]=bu.readLine().split(" ");
            for(i=0;i<n;i++)
            {
                a[i]=Integer.parseInt(s[i]);
                g[a[i]].add(i);
            }

            int prev[]=new int[n];
            Stack<Integer> st=new Stack<>();
            for(i=0; i<n; i++)
            {
                while(!st.isEmpty() && a[st.peek()]>=a[i]) st.pop();
                if(st.empty()) prev[i]=-1;
                else  prev[i]=st.peek();
                st.push(i);
            }

            int next[]=new int[n];
            st=new Stack<>();
            for(i=n-1;i>=0;i--)
            {
                while(!st.isEmpty() && a[st.peek()]>=a[i]) st.pop();
                if(st.empty()) next[i]=n;
                else  next[i]=st.peek();
                st.push(i);
            }
            boolean ans[]=new boolean[n+1];
            for(i=1;i<=n;i++)
            if(g[i].size()==0) break;
            else
            {
                boolean found=false;
                for(int x:g[i])
                {
                    int dist=-prev[x]+next[x]-1;
                    //System.out.println(prev[x]+" "+next[x]+" "+x);
                    if(dist>=(n-i+1)) {found=true; break;}
                }
                if(found) ans[i]=true;
                if(g[i].size()>1) break;
            }

            for(i=n;i>0;i--)
            if(ans[i]) sb.append(1);
            else sb.append(0);
            sb.append("\n");
        }
        System.out.print(sb);
    }
}