/**
 * ******* Created  on 1/5/20 2:03 PM*******
 */

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class F1311 implements Runnable {

    private static final int MAX = (int) (1E5 + 5);
    private static final int MOD = (int) (1E9 + 7);
    private static final long Inf = (long) (1E14 + 10);
    private static final double eps = (double) (1E-9);
    class Pair{
        int a;
        int b;
    }
    private void solve() throws IOException {
        int  n= reader.nextInt();
        Pair[] p = new Pair[n];
        for(int i=0;i<n;i++)
            p[i] = new Pair();
        for(int i=0;i<n;i++)
            p[i].a = reader.nextInt();
        for(int i=0;i<n;i++)
            p[i].b = reader.nextInt();
        Arrays.sort(p, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o1.a-o2.a;
            }
        });
        List<Integer>  list = new ArrayList<>();
        for(int i=0;i<n;i++)
            list.add(p[i].b);
        Collections.sort(list);
        list = list.stream().distinct().collect(Collectors.toList());

        int[] cnt = new int[list.size()];
        int[] xs = new int[list.size()];
        long res =0;
        for(int i=0;i<n;i++){
            int pos = lowerBound(list, p[i].b);
            res +=get( pos,cnt) * 1L*p[i].a -get(pos,xs);
           
            upd(pos,1,cnt);
            upd(pos,p[i].a,xs);
        }
        writer.println(res);
    }

    void upd(int pos, int val, int[]  l){
        int n = l.length;
        for (; pos <n; pos |=(pos+1) ){
            l[pos] +=val;
        }
    }
    long get(int pos, int[] l){
        long res =0;
        while(pos >=0 ){
            res += l[pos];
            pos = (pos&(pos+1))-1;
        }
        return res;
    }

    public int lowerBound(List<Integer> list ,  int val){
        int low =0, high = list.size();
        while (low < high){
            int mid = (low + high)>>1;
            if(list.get(mid) >= val)
                high = mid;
            else
                low = mid +1;
        }
        return low;
    }
    public static void main(String[] args) throws IOException {
        try (Input reader = new StandardInput(); PrintWriter writer = new PrintWriter(System.out)) {
            new F1311().run();
        }
    }

    StandardInput reader;
    PrintWriter writer;

    @Override
    public void run() {
        try {
            reader = new StandardInput();
            writer = new PrintWriter(System.out);
            solve();
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    interface Input extends Closeable {
        String next() throws IOException;

        String nextLine() throws IOException;

        default int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        default long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        default double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        default int[] readIntArray() throws IOException {
            return readIntArray(nextInt());
        }

        default int[] readIntArray(int size) throws IOException {
            int[] array = new int[size];
            for (int i = 0; i < array.length; i++) {
                array[i] = nextInt();
            }
            return array;
        }

        default long[] readLongArray(int size) throws IOException {
            long[] array = new long[size];
            for (int i = 0; i < array.length; i++) {
                array[i] = nextLong();
            }
            return array;
        }
    }

    private static class StandardInput implements Input {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        private StringTokenizer stringTokenizer;

        @Override
        public void close() throws IOException {
            reader.close();
        }

        @Override
        public String next() throws IOException {
            if (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
                stringTokenizer = new StringTokenizer(reader.readLine());
            }
            return stringTokenizer.nextToken();
        }

        @Override
        public String nextLine() throws IOException {
            return reader.readLine();
        }
    }

}
