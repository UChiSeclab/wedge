/*package whatever //do not write package name here */

import java.io.*;
import java.util.*;

public class GFG {
    
    static StringBuffer str=new StringBuffer("");
    static int n;
    static Long v[];
    static int lower_bound(long val)
    {
        int l=0,r=v.length-1;
        int ans=-1;
        while(l<=r)
        {
            int mid=(l+r)>>>1;
            if(v[mid]>=val)
            {
                ans=mid;
                r=mid-1; 
            }
            else
                l=mid+1;
        } 
        return ans;
    }
    static class BIT
    {
        long bit[];
        int sz;
        BIT(int sz)
        {
          this.sz=sz;
          bit=new long[sz+1];
          Arrays.fill(bit,0);
        }
        void update(int inx,long val)
        {
            inx++;
            while(inx<=sz)
            {
                bit[inx]+=val;
                inx+=(inx&(-inx));
            }
        }
         long getSum(int inx)
        {
            inx++;
            long res=0;
            while(inx>0)
            {
                res+=bit[inx];
                inx-=(inx&(-inx));
            }
            return res;
        }
    }
    static class Pair implements Comparable<Pair>{
        long x,v;
        Pair(long x,long v)
        {
            this.x=x;
            this.v=v;
        }
        public int compareTo(Pair p)
        {
            return (int)(this.x-p.x);
        }
    }
    static Pair p[];
    static void solve()
    {
       Arrays.sort(p);
       Arrays.sort(v);
       long ans=0;
        BIT f1=new BIT(n);
        BIT f2=new BIT(n);
       for(int i=0;i<n;i++)
       {
          int inx=lower_bound(p[i].v);
          ans+=f1.getSum(inx)*p[i].x-f2.getSum(inx);
          f1.update(inx,1);
          f2.update(inx,p[i].x);
       }
       System.out.println(ans);
    }
	public static void main (String[] args) throws Exception {
		
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		
		    n=Integer.parseInt(bf.readLine());
		    String s[]=bf.readLine().trim().split("\\s+");
		    p=new Pair[n];
		    v=new Long[n];
	        String s1[]=bf.readLine().trim().split("\\s+");
		    for(int i=0;i<n;i++)
		    {
		      p[i]=new Pair(Long.parseLong(s[i]),Long.parseLong(s1[i]));
		      v[i]=Long.parseLong(s1[i]);
		    }
            solve();
		
	}
}