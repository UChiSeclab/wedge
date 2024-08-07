import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.abs;
import java.util.*;
import java.io.*;
import java.math.*;

public class RatingCompression
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
            TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
            DSU union = new DSU(N);
            ArrayDeque<Integer>[] locs = new ArrayDeque[N+1];
            for(int i=1; i <= N; i++)
                locs[i] = new ArrayDeque<Integer>();
            boolean hasOne = false;
            for(int i=0; i < N; i++)
            {
                push(map, arr[i]);
                locs[arr[i]].add(i);
                if(arr[i] == 1)
                    hasOne = true;
            }
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
            if(map.size() == N)
                res[0] = 1;
            int left = -1, right = -1;
            for(int max=N; max >= 1; max--)
            {
                for(int loc: locs[max])
                {
                    if(loc > 0 && arr[loc-1] >= max && union.find(loc-1) != union.find(loc))
                        union.merge(loc-1, loc);
                    if(loc+1 < N && arr[loc+1] >= max && union.find(loc) != union.find(loc+1))
                        union.merge(loc, loc+1);
                    if(left == -1)
                        left = right = loc;
                    left = min(left, loc);
                    right = max(right, loc);
                    pull(map, max);
                }
                boolean lol = false;
                if(map.size() == 0 && max == 1)
                    lol = true;
                if(map.size() > 0 && map.lastKey() == max-1 && map.size() == max-1)
                    lol = true;
                if((left <= leftbound+1 && right >= rightbound-1) && hasOne && lol && right-left == N-max)
                {
                    res[N-max] = 1;
                }
            }
            for(int x: res)
                sb.append(x);
            sb.append("\n");
        }
        System.out.print(sb);
    }
    public static void push(TreeMap<Integer, Integer> map, int k)
    {
        if(!map.containsKey(k))
            map.put(k, 1);
        else
            map.put(k, map.get(k)+1);
    }
    public static void pull(TreeMap<Integer, Integer> map, int k)
    {
        int lol = map.get(k);
        if(lol == 1)
            map.remove(k);
        else
            map.put(k, lol-1);
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

class DSU
{
    public int[] dsu;

    public DSU(int N)
    {
        dsu = new int[N+1];
        for(int i=0; i <= N; i++)
            dsu[i] = i;
    }
    public int find(int x)
    {
        return dsu[x] == x ? x : (dsu[x] = find(dsu[x]));
    }
    public void merge(int x, int y)
    {
        int fx = find(x);
        int fy = find(y);
        dsu[fx] = fy;
    }
}
