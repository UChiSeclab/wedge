import java.util.*;
import java.io.*;
public class Solution
{
    public static void main(String []ks) throws Exception
    {
       BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
       int n=Integer.parseInt(bf.readLine());
       String s[]=bf.readLine().trim().split("\\s+");
       int a[]=new int[n];
       for(int i=0;i<n;i++)
         a[i]=Integer.parseInt(s[i]);
       
       Arrays.sort(a);
       a[n-1]+=1;
       HashSet<Integer> set1=new HashSet<>();
       HashSet<Integer> set=new HashSet<>();
       set1.add(a[n-1]);
       for(int i=n-2;i>=0;i--)
       {
           if(!set1.contains(a[i]+1))
             a[i]++;
       }
       set.add(a[n-1]);
       for(int i=0;i<n-1;i++)
       {
           if(a[i]!=1&&(!set.contains(a[i]-1)))
             a[i]-=1;
             
             set.add(a[i]);
       }
       System.out.println(set.size());
    }
}