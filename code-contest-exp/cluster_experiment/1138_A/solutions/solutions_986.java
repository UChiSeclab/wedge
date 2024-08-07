import java.io.BufferedReader;

import java.io.PrintWriter;

import java.io.InputStreamReader;

import java.io.IOException;

import java.util.StringTokenizer;

public class Main {
    
    static Scanner in = new Scanner();
    static PrintWriter out = new PrintWriter(System.out);
    
    public static void main(String[] args) throws IOException {
        int n = in.nextInt(), k = 0, t = 0, max = 0, l[] = new int[n+1];
        for(int i = 0; i < n; i++) {
            int a = in.nextInt();
            if(a % 2 == k % 2)
                t++;
            else {
                l[k++] = t;
                t = 1;
            }
        }
        l[k++] = t;
        for(int i = 0, j = 1; j < k; i = j++)
            max = Math.max(max, Math.min(l[i], l[j]));
        out.print(max * 2);
        out.close();
    }
    
    static class Scanner {
        BufferedReader br;
        StringTokenizer st;
        
        public Scanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
        }
        
        public String next() throws IOException {
            if(!st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }
        
        public String nextLine() throws IOException {
            if(!st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            String r = st.nextToken("\n");
            st = new StringTokenizer(br.readLine(), " ");
            return r;
        }
        
        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}