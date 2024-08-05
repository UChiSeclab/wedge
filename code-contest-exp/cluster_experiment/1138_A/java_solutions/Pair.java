import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.*;

public class Pair {

    static class FastReader {
        private BufferedReader br;
        private StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main (String[] args) throws Exception{
        try{
            FastReader sc = new FastReader();
//            PrintWriter out = new PrintWriter(System.out);
            int n = sc.nextInt();
            int[]arr = new int[n];
            for(int i=0;i<arr.length;i++){
                arr[i] = sc.nextInt();
            }
            int count1=0;
            int count2=0;
            for(int i=0;i<arr.length;i++){
                if(arr[i]==1){
                    count1+=1;
                }
                else{
                    count2+=1;
                }
            }
            if(count1<count2){
                System.out.println(count1*2);
            }
            else if(count1>count2){
                System.out.println(count2*2);
            }
            else{
                System.out.println(count1-1);
            }
        }
        catch(Exception e){

        }
    }
}