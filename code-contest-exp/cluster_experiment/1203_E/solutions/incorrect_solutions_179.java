// Created by Whiplash99
import java.io.*;
import java.util.*;
public class D
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        int i,N;

        N=Integer.parseInt(br.readLine().trim());
        String s[]=br.readLine().trim().split(" ");
        int a[]=new int[N];
        for(i=0;i<N;i++)
          a[i]=Integer.parseInt(s[i]);

        HashSet<Integer> set=new HashSet<>();
        for(i=0;i<N;i++)
        {
            if(!set.contains(a[i]))
                set.add(a[i]);
            else
            {
                if(!set.contains(a[i]+1))
                    set.add(a[i]+1);
                else if(a[i]-1>0&&!set.contains(a[i]-1))
                    set.add(a[i]-1);
            }
        }
        System.out.println(set.size());
    }
}