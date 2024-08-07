/**
 * ******* Created  on 13/4/20 7:25 PM*******
 */

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class F1311 implements Runnable {

    private static final int MAX = 2*(int) (1E5 + 5);
    private static final int MOD = (int) (1E9 + 7);
    private static final long Inf = (long) (1E14 + 10);
    private static final double eps = (double) (1E-9);
    int SIZE =0;
    private void solve() throws IOException {
        int n = reader.nextInt();

        int[] v =  new int[MAX];
        List<Integer> list = new ArrayList<>();
        List<Integer> xs = new ArrayList<>();
        for(int i=0;i<n;i++) {
            xs.add(reader.nextInt());
        }
        for(int i=0;i<n;i++) {
            v[i] = reader.nextInt();
            list.add(v[i]);
        }
        Collections.sort(list);
        Collections.sort(xs);
        list = list.stream().distinct().collect(Collectors.toList());
        SIZE = list.size();
        List<Long> cnt = new ArrayList<>();
        List<Long> xp = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            cnt.add(0L);
            xp.add(0L);
        }
        long res =0;
        for(int i=0;i<n;i++){
            int pos = upperBound(list, v[i])-1;

            res += getSum(cnt,pos) * xs.get(i) - getSum(xp,pos);
            update(cnt, pos,1);
            update(xp, pos,xs.get(i));
        }
        writer.println(res);
    }

    private void update(List<Long> cnt, int pos, int val) {
        for(int i=pos;i<SIZE;i |= (i+1)){
            cnt.add(i, cnt.get(i) + val);
        }
    }

    private long getSum(List<Long> cnt, int pos) {
        long sum =0;
        for(int i=pos ; i>=0 ; i =(i &(i-1))-1 ){
            sum +=cnt.get(i);
        }
        return sum;
    }

    private int upperBound(List<Integer> list, int val) {
        int low =0, high = list.size();
        while(low < high){
            int mid =(low+high)/2;
            if(list.get(mid) <=val )
                low =mid+1;
            else
                high =mid;
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
    }
}
