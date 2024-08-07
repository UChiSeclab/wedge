import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class ProblemE {

    public static void main(String[] args) throws IOException{
        //Reader sc = new Reader();
        PrintWriter pw = new PrintWriter(System.out);
        Scanner sc = new Scanner(System.in);
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numsNum = sc.nextInt();
        int[] nums = new int[numsNum];
        HashSet<Integer>[] canGo = new HashSet[numsNum];
        for (int i = 0; i < numsNum; i++) canGo[i] = new HashSet<>();
        for (int i = 0; i < numsNum; i++) {
            int aux = sc.nextInt();
            nums[i] = aux;
            if (i-aux>=0)canGo[i-aux].add(i);
            if (i+aux<numsNum)canGo[i+aux].add(i);
        }
        int[] sol = new int[numsNum];
        {
            boolean[] visited = new boolean[numsNum];
            LinkedList<Integer> que = new LinkedList<>();
            for (int i = 0; i < numsNum; i++) {
                if (nums[i]%2==0)continue;
                que.add(i);
                visited[i] = true;
            }
            que.add(null);
            int depth = 0;
            while (que.size() > 1){
                Integer aux = que.removeFirst();
                if (aux == null){
                    depth++;que.add(null);continue;
                }
                if (nums[aux]%2==0)sol[aux] = depth;
                for (Integer next : canGo[aux]) {
                    if (visited[next])continue;
                    que.add(next);
                    visited[next] = true;
                }
            }
        }
        {
            boolean[] visited = new boolean[numsNum];
            LinkedList<Integer> que = new LinkedList<>();
            for (int i = 0; i < numsNum; i++) {
                if (nums[i]%2==1)continue;
                que.add(i);
                visited[i] = true;
            }
            que.add(null);
            int depth = 0;
            while (que.size() > 1){
                Integer aux = que.removeFirst();
                if (aux == null){
                    depth++;que.add(null);continue;
                }
                if (nums[aux]%2==1)sol[aux] = depth;
                for (Integer next : canGo[aux]) {
                    if (visited[next])continue;
                    que.add(next);
                    visited[next] = true;
                }
            }
        }

        pw.print(sol[0]);
        for (int i = 1; i < numsNum; i++) {
            pw.print(" ");
            pw.print(sol[i]==0?-1:sol[i]);
        }
        pw.println();


        pw.flush();
    }

    static class State {
        public int depth,from;
        public boolean even;

        public State(int depth, boolean even, int from) {
            this.depth = depth;
            this.even = even;
        }
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
