import java.io.*;
import java.util.*;


public class Solution {

    private void solve() throws Exception {
        int n = nextInt();
        long h = nextLong();
        long m = nextLong();
        long mod = m/2;
        long k = nextLong();
        Train[] train = new Train[2*n];
        for(int i = 0; i < n; i++) {
            nextLong();
            train[i] = new Train(nextLong()%mod, i);
            train[n+i] = new Train(train[i].start+mod, i);
        }
        
        Arrays.sort(train, Comparator.comparing(tr -> tr.start));
        int j = 0;
        int best = n;
        int l = 0;
        int r = 0;
        long t = 0;
        for(int i = 0; i < n; i++) {
            while(train[j].start < train[i].start+k ) {
                j++;
            }
            int ans = j-i-1;
            if(ans < best) {
                best = ans;
                t = train[i].start;
                l = i+1;
                r = j-1;
            }
        }
        System.out.println(best + " " + t);
        for(int i = l; i <= r; i++) System.out.print((i+1) + " ");
        System.out.println();
    }
        
    class Train{
        long start;
        int idx;
        public Train(long start, int idx) {
            this.start = start;
            this.idx = idx;
        }
    }

    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            public void run() {
                new Solution().run();
            }
        }, "1", 1 << 27).start();
    }

    private BufferedReader in;
    private PrintWriter out;
    private StringTokenizer tokenizer;

    public void run() {
        try {
//            in = new BufferedReader(new FileReader("ks_10000_0"));
            in = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
            // out = new PrintWriter(new File("outputPQ.txt"));
            out = new PrintWriter(System.out);
            solve();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    private long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    private float nextFloat() throws IOException {
        return Float.parseFloat(nextToken());
    }

    private String nextLine() throws IOException {
        return new String(in.readLine());
    }

    private String nextToken() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(in.readLine());
        }
        return tokenizer.nextToken();
    }

}

class Node {
    Node left;
    Node right;
}