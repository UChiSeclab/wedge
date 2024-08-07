import java.util.*;
// import java.lang.*;
import java.io.*;

//           THIS TEMPLATE MADE BY AKSH BANSAL.

public class Solution {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader();
        // ________________________________

//        int t = sc.nextInt();
//        StringBuilder output = new StringBuilder();
//
//        while (t-- > 0) {
//
//            output.append(solver()).append("\n");
//        }
//
//        System.out.println(output);
        // _______________________________

        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }
        System.out.println(solver(arr));
        // ________________________________
    }

    public static int solver(int[] arr) {
        int res = 0;
        boolean[] vis = new boolean[150001];
        //Arrays.sort(arr);
        for(int e: arr){                
            if(vis[e]){
                if(e==1 || vis[e-1]){
                    if(e==150000 || vis[e+1]){
                        //System.out.println("nikal");
                        continue;
                    }else{
                        vis[e+1] = true;
                        res++;
                    }
                }else{
                    vis[e-1] = true;
                    res++;
                }
            }
            else {
                vis[e] = true;
                res++;
            }
        }
        return res;
    }
}