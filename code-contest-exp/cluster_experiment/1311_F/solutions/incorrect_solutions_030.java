import java.io.*;
import java.util.*;


/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Housni Abdellatif
 */
public class Main {
    public static void main(String[] args) throws IOException{
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Task.Reader in = new Task.Reader();
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(1, in, out);
        out.close();
    }

    static class Task {

        public void solve(int testNumber, Reader reader, PrintWriter out) throws IOException {

            int n = reader.nextInt();
            Point[] pts = new Point[n];
            List<Integer> vs = new ArrayList<>();

            for (int i = 0; i < n; ++i) {
                pts[i] = new Point();
                pts[i].x = reader.nextInt();
            }

            for (int i = 0; i < n; ++i) {
                int v = reader.nextInt();
                pts[i].v = v;
                vs.add(v);
            }

            Collections.sort(vs);
            Arrays.sort(pts, (p1, p2) -> p1.x - p2.x);

            long ans = 0;
            int[] bitV = new int[n];
            int[] bitX = new int[n];
            for (int i = 0; i < n; ++i) {
                int pos = getFirstIndex(vs, pts[i].v);
                long cnt = sum(bitV, pos);
                long s = sum(bitX, pos);
                //out.println(s);
                ans += cnt * pts[i].x - s;

                update(bitV, pos, 1);
                update(bitX, pos, pts[i].x);
            }
            out.println(ans);
        }

        private void update(int[] bit, int index, int delta) {
            while (index < bit.length) {
                bit[index] += delta;
                index |= (index + 1);
            }
        }

        private long sum(int[] bit, int r) {
            long res = 0;
            while (r >= 0) {
                res += bit[r];
                r = (r & (r + 1)) - 1;
            }
            return res;
        }

        private int getFirstIndex(List<Integer> list, int x) {
            int l = 0;
            int r = list.size() - 1;

            while ( l < r) {
                int m = (l + r) / 2;
                if (list.get(m) < x) {
                    l = m + 1;
                }else {
                    r = m;
                }
            }

            return l;
        }

        static class Point {
            int x;
            int v;
        }

        static class Reader
        {
            final private int BUFFER_SIZE = 1 << 16;
            private DataInputStream din;
            private byte[] buffer;
            private int bufferPointer, bytesRead;

            public Reader()
            {
                din = new DataInputStream(System.in);
                buffer = new byte[BUFFER_SIZE];
                bufferPointer = bytesRead = 0;
            }

            public Reader(String file_name) throws IOException
            {
                din = new DataInputStream(new FileInputStream(file_name));
                buffer = new byte[BUFFER_SIZE];
                bufferPointer = bytesRead = 0;
            }

            public String readLine() throws IOException
            {
                byte[] buf = new byte[64]; // line length
                int cnt = 0, c;
                while ((c = read()) != -1)
                {
                    if (c == '\n')
                        break;
                    buf[cnt++] = (byte) c;
                }
                return new String(buf, 0, cnt);
            }

            public int nextInt() throws IOException
            {
                int ret = 0;
                byte c = read();
                while (c <= ' ')
                    c = read();
                boolean neg = (c == '-');
                if (neg)
                    c = read();
                do
                {
                    ret = ret * 10 + c - '0';
                }  while ((c = read()) >= '0' && c <= '9');

                if (neg)
                    return -ret;
                return ret;
            }

            public long nextLong() throws IOException
            {
                long ret = 0;
                byte c = read();
                while (c <= ' ')
                    c = read();
                boolean neg = (c == '-');
                if (neg)
                    c = read();
                do {
                    ret = ret * 10 + c - '0';
                }
                while ((c = read()) >= '0' && c <= '9');
                if (neg)
                    return -ret;
                return ret;
            }

            public double nextDouble() throws IOException
            {
                double ret = 0, div = 1;
                byte c = read();
                while (c <= ' ')
                    c = read();
                boolean neg = (c == '-');
                if (neg)
                    c = read();

                do {
                    ret = ret * 10 + c - '0';
                }
                while ((c = read()) >= '0' && c <= '9');

                if (c == '.')
                {
                    while ((c = read()) >= '0' && c <= '9')
                    {
                        ret += (c - '0') / (div *= 10);
                    }
                }

                if (neg)
                    return -ret;
                return ret;
            }

            private void fillBuffer() throws IOException
            {
                bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
                if (bytesRead == -1)
                    buffer[0] = -1;
            }

            private byte read() throws IOException
            {
                if (bufferPointer == bytesRead)
                    fillBuffer();
                return buffer[bufferPointer++];
            }

            public void close() throws IOException
            {
                if (din == null)
                    return;
                din.close();
            }
        }

    }
}
