

import sun.font.FontRunIterator;

import java.awt.*;
import java.io.*;
import java.util.*;

public class Main {

    private static BufferedReader br;
    private static StringTokenizer st;
    private static PrintWriter out;
    public static void main(String[] args) throws IOException {
        smart_init("rec.in", "rec.out");
        int n = nextInt();
        int prev = -1;
        int count = 0;
        int count_prev = 0;
        int ans = 0;
        for (int i = 0; i < n+1; i++) {
            if (i == n){
                ans = Math.max(ans,Math.min(count , count_prev)*2);
                break;
            }
            int y = nextInt();
            if (y == prev){
                count++;
            }else {
                ans = Math.max(ans,Math.min(count , count_prev)*2);
                count_prev = count;
                count = 1;
                prev = y;
            }
        }
        out.println(ans);
        out.close();
    }

    private static long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    private static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    private static String next() throws IOException {
        if (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }
    private static void smart_init(String s, String s1) throws IOException{
        try {
            file_init(s , s1);
        }catch (Exception e){
            io_init();
        }
    }
    private static void io_init() throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
    }
    private static void file_init(String s, String s1) throws IOException{
        br = new BufferedReader(new FileReader(s));
        out = new PrintWriter(s1);
    }
}
