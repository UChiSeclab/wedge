import java.io.*;
import java.util.*;
public class E
{
    public static void main(String args[]) throws Exception
    {
      BufferedReader br  =  new BufferedReader( new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int N= 150000;
        Set<Integer> set =  new HashSet<>();
        int arr[] =  new int[N+1];
        for(String s : br.readLine().split(" "))
        {
            arr[Integer.parseInt(s)]++;
        }
        for(int i=1;i<N+1;i++)
        {
            if(arr[i]==0)
                continue;
            else
            {
                while(arr[i]-->0)
                {
                    if(i==1)
                    {
                        if(set.add(i)==false)
                        {
                            set.add(i+1);
                        }
                    }
                    else
                    {
                        if(set.add(i-1)==false)
                        {
                            if(set.add(i)==false)
                            {
                                set.add(i+1);
                            }
                        }
                    }
                }
            }
        }
        System.out.print(set.size());
    }
}