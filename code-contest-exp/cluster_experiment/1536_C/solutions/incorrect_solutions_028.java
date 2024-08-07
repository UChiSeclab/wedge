import java.sql.SQLSyntaxErrorException;
import java.util.*;
import java.io.*;
import java.util.stream.StreamSupport;

public class Solution {
    static long mod = (long)Math.pow(10,9)+7l;
    public static void main(String str[]) throws IOException{
//        Reader sc = new Reader();
        BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(System.out));
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while(t-->0){
            int n = sc.nextInt();
            sc.nextLine();
            String s = sc.nextLine();
            int D[] = new int[n+1];
            int K[] = new int[n+1];
            for(int i=1;i<=n;i++){
                D[i] = D[i-1];
                K[i] = K[i-1];
                if(s.charAt(i-1)=='D'){
                    D[i]++;
                }
                else{
                    K[i]++;
                }
            }
            int dp[] = new int[n+1];
            Arrays.fill(dp,1);
            for(int i=1;i<=n/2;i++){
                int d = D[i];
                int k = K[i];
                int cc= 2;
                for(int j=2*i;j<=n;j+=i){
                    if(D[j]*k==d*K[j]){
                        dp[j] = Math.max(dp[j], cc);
                    }
                    else    break;
                    cc++;
                }
            }
            for(int i=1;i<=n;i++){
                output.write(dp[i]+" ");
            }
            output.write("\n");
        }

        output.flush();
    }
    static class My{
        int D =0;
        int K =0;
    }



    static void arri(int arr[], int n, Reader sc) throws IOException{
        for(int i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }
    }
    static void arrl(long arr[], int n, Reader sc) throws IOException{
        for(int i=0;i<n;i++){
            arr[i] = sc.nextLong();
        }
    }

    static long gcd(long a, long b)
    {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
    static long power(long x, long y, long p)
    {
        long res = 1; // Initialize result

        x = x % p; // Update x if it is more than or
        // equal to p

        if (x == 0)
            return 0; // In case x is divisible by p;

        while (y > 0)
        {

            // If y is odd, multiply x with result
            if ((y & 1) != 0)
                res = (res * x) % p;

            // y must be even now
            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }
        return res;
    }

//    static boolean dfs(Tree node, boolean[] visited, int parent, ArrayList<Tree> tt){
//        visited[node.a] = true;
//        boolean b = false;
//        for(int i: node.al){
//            if(tt.get(i).a!=parent){
//                if(visited[tt.get(i).a])    return true;
//                b|= dfs(tt.get(i), visited, node.a, tt);
//            }
//        }
//        return b;
//    }

    static class Pair{
        //        int ind;
        int a;
        int b;
        ArrayList<Integer> ii = new ArrayList<>();
        Pair( int a, int b){
//            this.ind = i;
            this.a = a;
            this.b = b;
        }

    }
    static class SortbyI implements Comparator<Pair> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Pair a, Pair b)
        {
            return a.a - b.a;
        }
    }
    static class SortbyD implements Comparator<Pair> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Pair a, Pair b)
        {
            return b.a - a.a;
        }
    }
    static int binarySearch(ArrayList<Pair> a, int x, int s, int e){
        if(s>=e){
            if(x<=a.get(s).b)  return s;
            else    return s+1;
        }
        int mid = (e+s)/2;
        if(a.get(mid).b<x){
            return binarySearch(a, x, mid+1, e);
        }
        else    return binarySearch(a,x,s, mid);
    }

    //    static class Edge{
//        int a;
//        int b;
//        int c;
//        int sec;
//        Edge(int a, int b, int c, int sec){
//            this.a = a;
//            this.b = b;
//            this.c = c;
//            this.sec = sec;
//        }
//
//    }
    static class Tree{
        int a;
        ArrayList<Integer> al = new ArrayList<>();
        Tree(int a){
            this.a = a;
        }

    }
    static class Reader {
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
            din = new DataInputStream(
                    new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                buf[cnt++] = (byte)c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
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
            } while ((c = read()) >= '0' && c <= '9');
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
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
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


    static boolean isPrime(int n)
    {
        if (n <= 1)
            return false;
        if (n <= 3)
            return true;
        if (n % 2 == 0 ||
                n % 3 == 0)
            return false;

        for (int i = 5;
             i * i <= n; i = i + 6)
            if (n % i == 0 ||
                    n % (i + 2) == 0)
                return false;

        return true;
    }
    static ArrayList<Integer> sieveOfEratosthenes(int n)
    {
        ArrayList<Integer> al = new ArrayList<>();
        // Create a boolean array
        // "prime[0..n]" and
        // initialize all entries
        // it as true. A value in
        // prime[i] will finally be
        // false if i is Not a
        // prime, else true.
        boolean prime[] = new boolean[n + 1];
        for (int i = 0; i <= n; i++)
            prime[i] = true;

        for (int p = 2; p * p <= n; p++)
        {
            // If prime[p] is not changed, then it is a
            // prime
            if (prime[p] == true)
            {
                // Update all multiples of p
                for (int i = p * p; i <= n; i += p)
                    prime[i] = false;
            }
        }

        // Print all prime numbers
        for (int i = 2; i <= n; i++)
        {
            if (prime[i] == true)
                al.add(i);
        }
        return al;
    }

}




