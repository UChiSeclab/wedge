
import java.io.*;
import java.util.*;

public class f_624_Div3
{


    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt()
        {
            return Integer.parseInt(next());
        }

        long nextLong()
        {
            return Long.parseLong(next());
        }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }



    public static void print(int[] arr){
        int len = arr.length;
        for(int i=0;i<len;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.print('\n');
    }

    static int sorted_size;
    static long[] prefixsum;
    static long[] prefixcount;
    static int[] points;
    static int[] speeds;
    static int n;
    static int n_speeds;
    public static void main(String[] args)
    {
        FastReader io = new FastReader();
        n = io.nextInt();

        points = new int[n];
        speeds = new int[n];
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            points[i] = io.nextInt();
        }
        for(int i=0;i<n;i++){
            speeds[i] = io.nextInt();
            map.putIfAbsent(points[i],speeds[i]);
        }
        Arrays.sort(points);
        for(int i=0;i<n;i++){
            speeds[i] = map.get(points[i]);
        }
        map.clear();
        //now we need to sort
        int [] sorted_speeds = speeds.clone();
        Arrays.sort(sorted_speeds);
        HashSet<Integer> hs = new HashSet<>();
        for(int i=0;i<n;i++){
            hs.add(sorted_speeds[i]);
        }
        sorted_speeds = new int[hs.size()];
        Iterator<Integer> itr = hs.iterator();
        int i=0;
        while (itr.hasNext()){
            sorted_speeds[i++] = itr.next();
        }
        Arrays.sort(sorted_speeds);
        sorted_size = sorted_speeds.length;
        n_speeds = sorted_size;
        //now we have sorted speeds we need to create a prefixsum and prefix count
        prefixsum = new long[sorted_size+1];
        prefixcount = new long[sorted_size+1];
//        print(sorted_speeds);
        long answer =0;
        for(i=0;i<n;i++){
            int pos = Arrays.binarySearch(sorted_speeds,speeds[i]);
            int count = query_count(pos);
            int sum = query_sum(pos);
            answer+=((count*points[i]*1L)-sum);
            update(pos,points[i]);
        }
        System.out.println(answer);
    }

    static void update(int x, int delta)
    {   x++;
        for(; x <= n_speeds; x += x&-x){
            prefixsum[x] += delta;
            prefixcount[x]+=1;
        }

    }
    static int query_count(int x)
    {    x++;
        int sum = 0;
        for(; x > 0; x -= x&-x)
            sum += prefixcount[x];
        return sum;
    }
    static int query_sum(int x)
    {    x++;
        int sum = 0;
        for(; x > 0; x -= x&-x)
            sum += prefixsum[x];
        return sum;
    }








//    OutputStream out = new BufferedOutputStream( System.out );
//        for(int i=1;i<n;i++){
//    out.write((max_divisor[i]+" ").getBytes());
//}
//        out.flush();



}
