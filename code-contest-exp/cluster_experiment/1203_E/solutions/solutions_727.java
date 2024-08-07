import java.util.*;
import java.io.*;
import java.math.*;
public class Main
{
    public static void main(String args[])throws Exception
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw=new PrintWriter(System.out);
        int n=Integer.parseInt(br.readLine());
        int arr[]=new int[n];
        boolean vis[]=new boolean[150002];
        int cntr[]=new int[150002];
        String str[]=br.readLine().split(" ");
        for(int i=0;i<n;i++)
        {
            arr[i]=Integer.parseInt(str[i]);
            cntr[arr[i]]++;
        }
        int cnt=0;
        for(int i=1;i<=150000;i++)
        {
            if(cntr[i]==1)
            {
                if(i>1&&!vis[i-1])
                {
                    cnt+=1;
                    vis[i-1]=true;
                    continue;
                }
                if(!vis[i])
                {
                    cnt+=1;
                    vis[i]=true;
                    continue;
                }
                if(!vis[i+1])
                {
                    cnt+=1;
                    vis[i+1]=true;
                }
            }
            else if(cntr[i]>=2)
            {
                if(i>1&&cntr[i]>=1&&!vis[i-1])
                {
                    cnt+=1;
                    vis[i-1]=true;
                    cntr[i]-=1;
                }
                if(cntr[i]>=1&&!vis[i])
                {
                    cnt+=1;
                    vis[i]=true;
                    cntr[i]-=1;
                }
                if(cntr[i]>=1&&!vis[i+1])
                {
                    cnt+=1;
                    vis[i+1]=true;
                }
            }
        }
        
        /*Arrays.sort(arr);
        int cntr=0;
        for(int i=0;i<n;i++)
        {
            if(arr[i]>1&&!vis[arr[i]-1])
            {
                vis[arr[i]-1]=true;
                cntr++;
            }
            else if(!vis[arr[i]])
            {
                vis[arr[i]]=true;
                cntr++;
            }
            else if(!vis[arr[i]+1])
            {
                vis[arr[i]+1]=true;
                cntr++;
            }
        }*/
        pw.println(cnt);
        pw.flush();
        pw.close();
    }
}