import java.util.*;
import java.io.*;
public class test
{
    public static void main (String[] args) throws NumberFormatException, IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Integer[] arr = new Integer[n];
        Set<Integer> hs = new HashSet<Integer>();
        String[] inp = br.readLine().split(" ");
        for(int i=0;i<n;i++)
        {
            arr[i] = Integer.parseInt(inp[i]);
        }
        Arrays.sort(arr);
        for(int i=0;i<n;i++)
        {
            if(!hs.contains(arr[i]-1) && arr[i]>1)
            hs.add(arr[i]-1);
        else if(!hs.contains(arr[i]))
            hs.add(arr[i]);
        else if(!hs.contains(arr[i]+1))
            hs.add(arr[i]+1);
        }
        System.out.println(hs.size());
    }
}
