import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException  {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskA solver = new TaskA();
        solver.solve(in, out);
        out.close();
    }
    static class Truck{
        private int st, fin, consumption , cntRefueling;
        Truck (int st, int fin, int consumption, int cntRefueling){
            this.st = st;
            this.fin = fin;
            this.consumption = consumption;
            this.cntRefueling = cntRefueling;
        }
        public boolean check(long tankVolume, int[] arr){
            long cntRefCopy = cntRefueling, volumeNow = tankVolume;
            boolean ans = true;
            for(int i = st - 1; i < fin - 1; ++i){
                long tmp = consumption * (arr[i + 1] - arr[i]);
                if(tmp > tankVolume) {ans = false; break;}
                if(tmp > volumeNow){
                    if(cntRefCopy > 0){
                        cntRefCopy--;
                        volumeNow = tankVolume - tmp;
                    }
                    else {ans = false; break;}
                }
                else volumeNow -= tmp;
            }
            return ans;
        }
    }
    static class TaskA {
        int[] arr;
        Truck[] def;
        public long binSearchM(long left, long right, int indx){
            long midd = 0;
            while(left + 1 < right) {
                midd = (left + right) / 2;
                if(def[indx].check(midd, arr))
                    right = midd;
                else
                    left = midd ;
            }
            //while(!def[indx].check(midd, arr)) midd++;
            return right;
        }
        static void shuffleArray(Truck[] ar)
        {
            Random rnd = ThreadLocalRandom.current();
            for (int i = ar.length - 1; i > 0; i--) {
                int index = rnd.nextInt(i + 1);
                Truck a = ar[index];
                ar[index] = ar[i];
                ar[i] = a;
            }
        }
        public void solve(InputReader in, PrintWriter out) throws IOException  {
            int n = in.nextInt(), m = in.nextInt();
            arr  = new int[n];
            def = new Truck[m];
            for(int i = 0; i < n; ++i)
                arr[i] = in.nextInt();
            for(int i = 0; i < m; ++i)
                def[i] = new Truck(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
            //shuffleArray(def);
            long minV = 0;
            for(int i = 0; i < m; ++i){
                if(!def[i].check(minV, arr))
                    minV = binSearchM(minV, (long)1e18, i);

            }
            out.println(minV);
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

    }
}
