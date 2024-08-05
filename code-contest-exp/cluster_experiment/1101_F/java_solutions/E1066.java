import java.io.*;
import java.util.*;

public class E1066 {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader inp = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Solver1066E solver = new Solver1066E();
        solver.solve(inp, out);
        out.close();
    }

    static class InputReader {
        StringTokenizer tokenizer;
        InputStreamReader sReader;
        BufferedReader reader;

        InputReader(InputStream stream) {
            sReader = new InputStreamReader(stream);
            reader = new BufferedReader(sReader, 32768);
            tokenizer = null;
        }

        String next() {
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

        public List<Integer> intLine(int size) {
            List<Integer> ls = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                ls.add(nextInt());
            }
            return ls;
        }

        int nextC() {
            try {
                return reader.read();
            } catch (IOException e) {
                return -1;
            }
        }

        public int[] intArr(int size) {
            int[] ls = new int[size];
            for (int i = 0; i < size; i++) ls[i] = nextInt();
            return ls;
        }
    }
static class Solver1066E {
        int[] dists;
        public void solve(InputReader inp, PrintWriter out) {
            int n = inp.nextInt(), m = inp.nextInt(), prev = 0;
            dists = new int[n];
            for (int i=0; i < n; i++){
                int loc = inp.nextInt();
                dists[i] = loc - prev;
                prev = loc;
            }
            long currmin = 0;
            long bsmax = prev+1-dists[0];
            for (int i=0; i < m; i++){
                int start = inp.nextInt(), end = inp.nextInt(), usage = inp.nextInt(), refuels = inp.nextInt();
                // if (check(currmin, start, end, usage, refuels)) continue;
                long right = bsmax*usage;
                currmin = bs(currmin+1, right, start, end, usage, refuels);
            }
            out.println(currmin);
        }

    public long bs(long left, long right, int start, int end, int usage, int refuels) {
        while (true) {
//            System.out.println(left + ", " + right);
            if (left >= right) return right;
            long mid = (left + right) >> 1;
            boolean ok = check(mid, start, end, usage, refuels);
            if (ok) right = mid;
            else left = mid + 1;
        }
    }

        public boolean check(long size, int start, int end, int usage, int refuels){
            long left = size;
            long usag = usage;
            for (int curr =start; curr < end; curr++){
                left -= usag*dists[curr];
                if (left < 0){
                    refuels -= 1;
                    left = size - usag*dists[curr];
                    if (left < 0) return false;
                }
            }
            return refuels > -1;
        }
    }
}
