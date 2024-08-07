import java.io.*;
import java.util.*;

public class MainClass
{
    static int n = 0;
    static int[] A;
    static int[] ans;
    static ArrayList<Integer>[] adj;
    public static void main(String[] args)throws IOException
    {
        Reader in = new Reader();
        n = in.nextInt();
        A = new int[n];
        for (int i=0;i<n;i++)
            A[i] = in.nextInt();
        ans = new int[n];
        adj = new ArrayList[n];
        for (int i=0;i<n;i++)
            adj[i] = new ArrayList<>();
        for (int i=0;i<n;i++)
        {
            int left = i - A[i];
            int right = i + A[i];
            if (left >= 0)
                adj[left].add(i);
            if (right < n)
                adj[right].add(i);
        }
        Arrays.fill(ans, -1);
        Queue<Integer> queue = new LinkedList<>();
        Queue<Integer> dist = new LinkedList<>();
        for (int i=0;i<n;i++)
        {
            int[] dd = {-A[i], A[i]};
            if (A[i] % 2 == 0)
            {
                for (int j=0;j<2;j++)
                {
                    int to = i + dd[j];
                    if (to >= 0 && to < n && A[i] % 2 != A[to] % 2)
                    {
                        queue.add(i);
                        dist.add(1);
                    }
                }
            }
        }
        BFS(queue, dist);
        queue.clear();
        dist.clear();
        for (int i=0;i<n;i++)
        {
            int[] dd = {-A[i], A[i]};
            if (A[i] % 2 == 1)
            {
                for (int j=0;j<2;j++)
                {
                    int to = i + dd[j];
                    if (to >= 0 && to < n && A[i] % 2 != A[to] % 2)
                    {
                        queue.add(i);
                        dist.add(1);
                    }
                }
            }
        }
        BFS(queue, dist);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i: ans)
            stringBuilder.append(i).append(" ");
        System.out.println(stringBuilder);
    }
    public static void BFS(Queue<Integer> queue, Queue<Integer> dist)
    {
        boolean[] visited = new boolean[n];
        while (!queue.isEmpty())
        {
            int node = queue.poll();
            int dd = dist.poll();
            if (visited[node])
                continue;
            ans[node] = dd;
            visited[node] = true;
            for (int u: adj[node])
            {
                if (!visited[u] && A[u] % 2 == A[node] % 2)
                {
                    queue.add(u);
                    dist.add(dd + 1);
                }
            }
        }
    }
}
class Reader
{
    final private int BUFFER_SIZE = 1 << 16;
    private final DataInputStream din;
    private final byte[] buffer;
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