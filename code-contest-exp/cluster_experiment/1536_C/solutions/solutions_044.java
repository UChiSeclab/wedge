import java.io.*;
import java.util.*;

/**
 * Problem Cf1536C
 */
public class Cf1536C {

    public void run() {
        int t = in.nextInt();
        while(t-->0){
            int n = in.nextInt();
            int d=0,k=0;
            char[] s = in.next().toCharArray();
            Map<String, Integer> map = new HashMap<>();
            for(int i = 0; i < n; ++i){
                if(s[i]=='D') d++;
                else k++;
                String key = key(d,k);
                out.print((map.getOrDefault(key, 0) + 1) + " ");
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
            out.println();
        }
    }

    public String key(int a, int b){
        if(b==0) return "1 0";
        if(a==0) return "0 1";
        int gcd = gcd(a,b);
        return "" + (a/gcd) + " " + (b/gcd);
    }

    public int gcd(int a, int b){
        if(a%b==0) return b;
        return gcd(b, a%b);
    }

    /***********************************************************
     *                      BOILERPLATE                        *
    /***********************************************************/
    public InputReader in = new InputReader(System.in);
    public PrintWriter out = new PrintWriter(System.out);
    public void close() {out.close();}
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokens = null;
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);}
        private boolean prime() {
            while (tokens == null || !tokens.hasMoreTokens()) {
                try {
                    String line = reader.readLine();
                    if(line==null) return false; // EOF
                    tokens = new StringTokenizer(line);
                } catch (IOException e) {throw new RuntimeException(e);}
            } return true;}
        public boolean hasNext() {return prime();}
        public String next() {prime(); return tokens.nextToken();}
        public int nextInt() {return Integer.parseInt(next());}
        public long nextLong() {return Long.parseLong(next());}
        public double nextDouble() {return Double.parseDouble(next());}
    }
    public static void main(String[] args) {
        Cf1536C task = new Cf1536C(); task.run(); task.close();}
}
