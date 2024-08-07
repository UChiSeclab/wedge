import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
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

        public double nextDouble() throws IOException {
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

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    static class Words {
        String word;
        int cost;

        public Words(String word) {
            this.word = word;
        }
    }

    static class Node {
        int key;
        int edgeweight;
        int height;

        public Node(int key, int edgeweight) {
            this.key = key;
            this.edgeweight = edgeweight;
        }
    }

    static class Graph {
        int vertices;
        ArrayList<ArrayList<Node>> al;

        public Graph(int vertices) {
            this.vertices = vertices;
            al = new ArrayList<>(vertices);
            for (int i = 0; i < vertices; i++) {
                al.add(new ArrayList<Node>());
            }
        }

        public static void addEdgeUndirected(ArrayList<ArrayList<Node>> al, int source, int destination, int weight) {
            al.get(source).add(new Node(destination, weight));
            al.get(destination).add(new Node(destination, weight));
        }

        /* public static int bfs(ArrayList<ArrayList<Node>> al, int v, int root) {
             //here root means the starting vertex for bfs
             int count = 1;
             boolean[] visited = new boolean[v];
             Queue<Integer> q = new LinkedList<>();
             visited[root] = true;
             q.add(root);
             while (!q.isEmpty()) {
                 int u = q.poll();
                 for (int i = 0; i < al.get(u).size(); i++) {
                     if (!visited[al.get(u).get(i).key]) {
                         visited[al.get(u).get(i).key] = true;
                         q.add(al.get(u).get(i).key);
                         count++;
                     }
                 }
             }
             return count;
         }*/
        public static void bfs(ArrayList<ArrayList<Node>> al, int v, int root) {
            //here root means the starting vertex for bfs
            boolean[] visited = new boolean[v];
            Queue<Integer> q = new LinkedList<>();
            visited[root] = true;
            q.add(root);
            while (!q.isEmpty()) {
                int u = q.poll();
                System.out.print(u + " ");
                for (int i = 0; i < al.get(u).size(); i++) {
                    if (!visited[al.get(u).get(i).key]) {
                        visited[al.get(u).get(i).key] = true;
                        q.add(al.get(u).get(i).key);
                    }
                }
            }
        }

    }


    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        int n = r.nextInt();
        int[] a = new int[n];
        for(int i=0;i<n;i++) a[i]=r.nextInt();
        int one = 0;
        int two = 0;
        int temp1 = 0;
        int temp2 = 0;
        boolean flag1 = false;
        for(int i=0;i<n;i++){
            if(a[i]==1 && !flag1){
                flag1=true;
                temp1=1;
                one = Math.max(one,temp1);
            }else if(a[i]==1){
                temp1++;
                one = Math.max(one,temp1);
            }else if(a[i]==2 && flag1){
                flag1 = false;
                temp2=1;
                two=Math.max(two,temp2);
            }else{
                temp2++;
                two = Math.max(two,temp2);
            }
        }
        System.out.println(2*Math.min(one,two));
    }
}






