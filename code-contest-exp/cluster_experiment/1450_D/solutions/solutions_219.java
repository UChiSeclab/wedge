import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.abs;
import java.util.*;
import java.io.*;
import java.math.*;

public class RatingCompression2
{
    public static void main(String hi[]) throws Exception
    {
        BufferedReader infile = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(infile.readLine());
        int T = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder();
        while(T-->0)
        {
            st = new StringTokenizer(infile.readLine());
            int N = Integer.parseInt(st.nextToken());
            int[] arr = readArr(N, infile, st);
            ArrayDeque<Integer>[] locs = new ArrayDeque[N+1];
            for(int i=1; i <= N; i++)
                locs[i] = new ArrayDeque<Integer>();
            for(int i=0; i < N; i++)
                locs[arr[i]].add(i);
            int leftbound = 0;
            for(int i=1; i < N; i++)
            {
                if(arr[i] <= arr[i-1])
                    break;
                leftbound = i;
            }
            int rightbound = N-1;
            for(int i=N-2; i >= 0; i--)
            {
                if(arr[i] <= arr[i+1])
                    break;
                rightbound = i;
            }
            int[] res = new int[N];
            int min = 0;
            for(int v=1; v <= N; v++)
            {
                if(locs[v].size() == 0)
                    break;
                min = v;
            }
            int left = -1, right = -2;
            int sum = 0;
            //System.out.println(leftbound+" "+rightbound+" "+min);
            for(int v=N; v >= 1; v--)
            {
                for(int dex: locs[v])
                {
                    if(left == -1)
                        left = right = dex;
                    left = min(left, dex);
                    right = max(right, dex);
                }
                sum += locs[v].size();
                if(sum == right-left+1 && N-sum == v-1)
                {
                    if(left <= leftbound+1 && right >= rightbound-1 && min >= v)
                        res[N-v] = 1;
                }
            }
            HashSet<Integer> set = new HashSet<Integer>();
            for(int x: arr)
                set.add(x);
            if(set.size() == N)
                res[0] = 1;
            for(int x: res)
                sb.append(x);
            sb.append("\n");
        }
        System.out.print(sb);
    }
    public static int[] readArr(int N, BufferedReader infile, StringTokenizer st) throws Exception
    {
        int[] arr = new int[N];
        st = new StringTokenizer(infile.readLine());
        for(int i=0; i < N; i++)
            arr[i] = Integer.parseInt(st.nextToken());
        return arr;
    }
}