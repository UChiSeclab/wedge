import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Long.bitCount;

public class Main {

    public static void main(String[] args) {
        FastScanner in = new FastScanner();
        int n; n = in.nextInt();
        int k; k = in.nextInt();
        Integer []ar = new Integer[n];
        Integer []occur = new Integer[200100];
        Arrays.fill(occur, 0);
        for(int i=0; i<n; ++i){
            ar[i] = in.nextInt();
            occur[ar[i]]++;
        }
        if(k == 0){
            long ans = 0;
            for(int i=0; i<200100; ++i){
                long t = occur[i];
                ans += ((t-1) * t)/(long)2;
            }
            System.out.println(ans);
            System.exit(0);
        }
        Integer []dp = new Integer[(int)1e6];
        Arrays.fill(dp, 0);
        Integer []sb = new Integer[200100];
        for(int i=0; i<200100; ++i){
            sb[i] = bitCount(i);
        }
        HashSet<Integer> st = new HashSet<Integer>(Arrays.asList(ar).subList(0, n));
        ArrayList<Integer> a = new ArrayList<Integer>(st);

        n = a.size();
        long ans = 0;
        for(int i=0; i<n; ++i){
            for(int j=i+1; j<a.size(); ++j){
                int xor = a.get(i)^a.get(j);
                if(sb[xor] != k) continue;
                ans += occur[a.get(i)]*(long)occur[a.get(j)];
            }
        }

        System.out.println(ans);
    }

    static void sort(int[] a) {
        ArrayList<Integer> l=new ArrayList<>();
        for (int i:a) l.add(i);
        Collections.sort(l);
        for (int i=0; i<a.length; i++) a[i]=l.get(i);
    }

    static class FastScanner {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st=new StringTokenizer("");
        String next() {
            while (!st.hasMoreTokens())
                try {
                    st=new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
        int[] readArray(int n) {
            int[] a=new int[n];
            for (int i=0; i<n; i++) a[i]=nextInt();
            return a;
        }
        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}
