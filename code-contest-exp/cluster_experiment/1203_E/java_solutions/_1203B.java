import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class _1203B {
    BufferedReader bf;
    InputStream is;

    public _1203B(InputStream is) {
        this.is = is;
        this.bf = new BufferedReader(new InputStreamReader(is));
    }

    public int[] ints(int size) throws Exception {
        String[] data = bf.readLine().split(" ");
        data = Arrays.stream(data).filter(s -> !s.trim().isEmpty()).toArray(String[]::new);
        int i = 0;
        int[] array = new int[size];
        for (String s : data) array[i++] = Integer.parseInt(s);
        return array;

    }

    public int getInt() throws Exception {
        return Integer.parseInt(bf.readLine().replace(" ", ""));
    }

    public long[] longs(int size) throws Exception {
        String[] data = bf.readLine().split(" ");
        int i = 0;
        long[] array = new long[size];
        for (String s : data) array[i++] = Long.parseLong(s);
        return array;
    }

    public long getLong() throws Exception {
        return Long.parseLong(bf.readLine());
    }

    public String intsToString(int[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int e : data) stringBuilder.append(e).append(" ");
        return stringBuilder.toString();
    }

    public String getString() throws Exception {
        return bf.readLine();
    }

    public static int computeOne(int[] data){
        int hash[] = new int[1500001];
        for(int e : data) hash[e]++;
        int count = 0;
        //System.out.println(Arrays.toString(hash));
        for(int i = 1,h=hash.length;i<h;i++){
            if(hash[i]>1){
                    if(i==1){
                        if(hash[i+1]==0){
                            hash[i+1] = 1;
                        }
                    }else {
                        if(hash[i-1]==0){
                            hash[i-1] = 1;
                            hash[i]--;
                        }
                        if(hash[i]>1&&hash[i+1]==0){
                            hash[i+1] = 1;
                            hash[i]--;
                        }
                    }
            }
        }
        //System.out.println(Arrays.toString(hash));
        for(int e : hash) if(e>0) count++;
        return count;
    }

    public void run() throws Exception{
        System.out.println(computeOne(ints(getInt())));
    }

    public static void main(String... args) throws Exception {
        new _1203B(System.in).run();
    }
}