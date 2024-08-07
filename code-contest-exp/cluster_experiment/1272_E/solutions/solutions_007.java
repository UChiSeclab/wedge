
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class NearestOppositeParity {
    static int inf = (int) 1e9;

    public static void main(String[] args) throws IOException {
        Scan in = new Scan();
        PrintWriter out = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] arr = new int[n + 1];
        ArrayList<Integer> adj[] = new ArrayList[n + 1];
        for (int i = 0; i < n + 1; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        Deque<Integer> odd = new ArrayDeque<>();
        Deque<Integer> even = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            arr[i] = in.nextInt();
            if(arr[i] % 2 == 0){
                even.add(i);
            }else{
                odd.add(i);
            }
        }
        for (int i = 1; i <= n; i++) {
            int l = i - arr[i];
            int r = i + arr[i];
            if(l > 0){
                adj[l].add(i);
            }
            if(r <= n){
                adj[r].add(i);
            }
        }
        int[] d = new int[n + 1];
        int[] ans = new int[n + 1];
        Arrays.fill(d, inf);
        Arrays.fill(ans, -1);
        Deque<Integer> q = new ArrayDeque<>();
        for (Integer x : even) {
            d[x] = 0;
            q.add(x);
        }
        while(q.size() != 0){
            int tmp = q.poll();
            for (int i = 0; i < adj[tmp].size(); i++) {
                if(d[adj[tmp].get(i)] == inf){
                    d[adj[tmp].get(i)] = d[tmp] + 1;
                    q.add(adj[tmp].get(i));
                }
            }
        }
        for (Integer x : odd) {
            if(d[x] != inf){
                ans[x] = d[x];
            }
        }
        Arrays.fill(d, inf);
        for (Integer x : odd) {
            d[x] = 0;
            q.add(x);
        }
        while(q.size() != 0){
            int tmp = q.poll();
            for (int i = 0; i < adj[tmp].size(); i++) {
                if(d[adj[tmp].get(i)] == inf){
                    d[adj[tmp].get(i)] = d[tmp] + 1;
                    q.add(adj[tmp].get(i));
                }
            }
        }
        for (Integer x : even) {
            if(d[x] != inf){
                ans[x] = d[x];
            }
        }
        for (int i = 1; i < n; i++) {
            out.print(ans[i] + " ");
        }
        out.println(ans[n]);
        out.close();
    }


    static class Scan {

        public java.io.InputStream stream = System.in;
        private final static byte EOF = -1, NL = '\n', D = '-', SPC = ' ', buffer[] = new byte[0xFFFF];
        private char cBuff[] = new char[0xFF];
        public int $index, $readCount, itr;

        private void inc() {
            cBuff = java.util.Arrays.copyOf(cBuff, cBuff.length << 1);
        }

        private boolean readLINE() throws IOException {
            if ($readCount == EOF) {
                return false;
            }
            for (itr = 0;;) {
                while ($index < $readCount) {
                    if (buffer[$index] != NL) {
                        if (itr == cBuff.length) {
                            inc();
                        }
                        cBuff[itr++] = (char) buffer[$index++];
                    } else {
                        $index++;
                        return true;
                    }
                }
                $index = 0;
                $readCount = stream.read(buffer);
                if ($readCount == EOF) {
                    return true;
                }
            }
        }

        private boolean readPRT() throws IOException {
            if ($readCount == EOF) {
                return false;
            }
            T:
            for (;;) {
                while ($index < $readCount) {
                    if (buffer[$index] > SPC) {
                        break T;
                    } else {
                        $index++;
                    }
                }
                $index = 0;
                $readCount = stream.read(buffer);
                if ($readCount == EOF) {
                    return false;
                }
            }
            for (itr = 0;;) {
                while ($index < $readCount) {
                    if (buffer[$index] > SPC) {
                        if (itr == cBuff.length) {
                            inc();
                        }
                        cBuff[itr++] = (char) buffer[$index++];
                    } else {
                        return true;
                    }
                }
                $index = 0;
                $readCount = stream.read(buffer);
                if ($readCount == EOF) {
                    return true;
                }
            }
        }

        public int nextInt() throws IOException {
            if (!readPRT()) {
                throw new IOException();
            } else {
                int v = 0, i = 0;
                boolean neg;
                if (cBuff[i] == D) {
                    neg = true;
                    i++;
                } else {
                    neg = false;
                }
                while (i < itr) {
                    v = (v << 3) + (v << 1) + cBuff[i++] - '0';
                }
                return neg ? -v : v;
            }
        }

        public long nextLong() throws IOException {
            if (!readPRT()) {
                throw new IOException();
            } else {
                long v = 0;
                int i = 0;
                boolean neg;
                if (cBuff[i] == D) {
                    neg = true;
                    i++;
                } else {
                    neg = false;
                }
                while (i < itr) {
                    v = (v << 3L) + (v << 1L) + cBuff[i++] - '0';
                }
                return neg ? -v : v;
            }
        }

        public char[] buffer() throws IOException {
            return readPRT() ? cBuff : null;
        }

        public String next() throws IOException {
            return readPRT() ? new String(cBuff, 0, itr) : null;
        }

        public char[] nextArr() throws IOException {
            return readPRT() ? java.util.Arrays.copyOf(cBuff, itr) : null;
        }

        public String nextLine() throws IOException {
            return readLINE() ? new String(cBuff, 0, itr) : null;
        }

        public char[] nextLineArr() throws IOException {
            return readLINE() ? java.util.Arrays.copyOf(cBuff, itr) : null;
        }

        public float nextFloat() throws IOException {
            return Float.parseFloat(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }
    }
}
