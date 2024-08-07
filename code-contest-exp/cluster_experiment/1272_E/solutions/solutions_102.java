/**
 * BaZ :D
 */
import java.util.*;
import java.io.*;
import static java.lang.Math.*;

public class ACMIND
{
    static FastReader scan;
    static PrintWriter pw;
    static long MOD = 998244353 ;
    static long INF = 1_000_000_000_000_000_000L;
    static long inf = 2_000_000_000;
    public static void main(String[] args) {
        new Thread(null,null,"BaZ",1<<27)
        {
            public void run()
            {
                try
                {
                    solve();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }.start();
    }

    static void solve() throws IOException
    {
        scan = new FastReader();
        pw = new PrintWriter(System.out,true);
        StringBuilder sb = new StringBuilder();
        int n  = ni();
        int arr[] = new int[n];
        ArrayList<Integer> odd_edges[] = new ArrayList[n];
        ArrayList<Integer> even_edges[] = new ArrayList[n];
        HashSet<Integer> even_sources = new HashSet<>();
        HashSet<Integer> odd_sources = new HashSet<>();
        for(int i=0;i<n;++i) {
            arr[i] = ni();
            odd_edges[i] = new ArrayList<>();
            even_edges[i] = new ArrayList<>();
        }
        for(int i=0;i<n;++i) {
            if(i-arr[i]>=0 && (arr[i] + arr[i-arr[i]])%2==0) {
                if(arr[i]%2==0) {
                    even_edges[i-arr[i]].add(i);
                }
                else {
                    odd_edges[i-arr[i]].add(i);
                }
            }
            else if(i-arr[i]>=0) {
                if(arr[i]%2==0) {
                    even_sources.add(i);
                }
                else {
                    odd_sources.add(i);
                }
            }

            if(i+arr[i]<n && (arr[i] + arr[i+arr[i]])%2==0) {
                if(arr[i]%2==0) {
                    even_edges[i+arr[i]].add(i);
                }
                else {
                    odd_edges[i+arr[i]].add(i);
                }
            }
            else if(i+arr[i]<n) {
                if(arr[i]%2==0) {
                    even_sources.add(i);
                }
                else {
                    odd_sources.add(i);
                }
            }
        }
//        pl("Even sources");
//        for(int e : even_sources) {
//            p(e);
//        }
//        pl();
//        pl("Odd sources");
//        for(int e : odd_sources) {
//            p(e);
//        }
//        pl();
        int ans[] = new int[n];
        int even_min_dis[] = BFS(n, even_sources, even_edges);
        int odd_min_dis[] = BFS(n, odd_sources, odd_edges);
        for(int i=0;i<n;++i) {
            if(arr[i]%2==1) {
                ans[i] = odd_min_dis[i];
            }
            else {
                ans[i] = even_min_dis[i];
            }
        }
        for(int e : ans) {
            p(e);
        }
        pl();
        pw.flush();
        pw.close();
    }
    static int[] BFS(int n, HashSet<Integer> sources, ArrayList<Integer> adj[]) {
        int min_dis[] = new int[n];
        Arrays.fill(min_dis, -1);
        LinkedList<Integer> queue = new LinkedList<>();
        for(int e : sources) {
            queue.addLast(e);
            min_dis[e] = 1;
        }
        while(!queue.isEmpty()) {
            int curr = queue.pollFirst();
            for(int e : adj[curr]) {
                if(min_dis[e] == -1) {
                    min_dis[e] = 1+min_dis[curr];
                    queue.addLast(e);
                }
            }
        }
        return min_dis;
    }
    static int ni() throws IOException
    {
        return scan.nextInt();
    }
    static long nl() throws IOException
    {
        return scan.nextLong();
    }
    static double nd() throws IOException
    {
        return scan.nextDouble();
    }
    static void pl()
    {
        pw.println();
    }
    static void p(Object o)
    {
        pw.print(o+" ");
    }
    static void pl(Object o)
    {
        pw.println(o);
    }
    static void psb(StringBuilder sb)
    {
        pw.print(sb);
    }
    static void pa(String arrayName, Object arr[])
    {
        pl(arrayName+" : ");
        for(Object o : arr)
            p(o);
        pl();
    }
    static void pa(String arrayName, int arr[])
    {
        pl(arrayName+" : ");
        for(int o : arr)
            p(o);
        pl();
    }
    static void pa(String arrayName, long arr[])
    {
        pl(arrayName+" : ");
        for(long o : arr)
            p(o);
        pl();
    }
    static void pa(String arrayName, double arr[])
    {
        pl(arrayName+" : ");
        for(double o : arr)
            p(o);
        pl();
    }
    static void pa(String arrayName, char arr[])
    {
        pl(arrayName+" : ");
        for(char o : arr)
            p(o);
        pl();
    }
    static void pa(String listName, List list)
    {
        pl(listName+" : ");
        for(Object o : list)
            p(o);
        pl();
    }
    static void pa(String arrayName, Object[][] arr) {
        pl(arrayName+" : ");
        for(int i=0;i<arr.length;++i) {
            for(Object o : arr[i])
                p(o);
            pl();
        }
    }
    static void pa(String arrayName, int[][] arr) {
        pl(arrayName+" : ");
        for(int i=0;i<arr.length;++i) {
            for(int o : arr[i])
                p(o);
            pl();
        }
    }
    static void pa(String arrayName, long[][] arr) {
        pl(arrayName+" : ");
        for(int i=0;i<arr.length;++i) {
            for(long o : arr[i])
                p(o);
            pl();
        }
    }
    static void pa(String arrayName, char[][] arr) {
        pl(arrayName+" : ");
        for(int i=0;i<arr.length;++i) {
            for(char o : arr[i])
                p(o);
            pl();
        }
    }
    static void pa(String arrayName, double[][] arr) {
        pl(arrayName+" : ");
        for(int i=0;i<arr.length;++i) {
            for(double o : arr[i])
                p(o);
            pl();
        }
    }
    static class FastReader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public FastReader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[1000000];
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') c = read();
            boolean neg = (c == '-');
            if (neg) c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg) return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ') c = read();
            boolean neg = (c == '-');
            if (neg) c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg) return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ') c = read();
            boolean neg = (c == '-');
            if (neg) c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (c == '.') while ((c = read()) >= '0' && c <= '9') ret += (c - '0') / (div *= 10);
            if (neg) return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1) buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null) return;
            din.close();
        }
    }
}