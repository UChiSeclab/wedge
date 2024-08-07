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

            boolean ans[]=new boolean[n+1];
            int l=0,r=n-1;
            for(i=1;i<n;i++)
            if(g[i].size()==0) break;
            else
            {
                ans[i]=true;
                if(g[i].size()>1) break;
                if(g[i].get(0)!=l && g[i].get(0)!=r) break;
                if(g[i].get(0)==l) l++;
                else r--;
            }

            ans[n]=true;
            for(i=1;i<=n;i++)
            if(g[i].size()==0) {ans[n]=false; break;}

            for(i=n;i>0;i--)
            if(ans[i]) sb.append(1);
            else sb.append(0);
            sb.append("\n");
        }
        System.out.print(sb);
    }
}