/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author is2ac
 */
public class D_CF {

    public static void main(String[] args) throws IOException {
        FastScanner58 fs = new FastScanner58();
        //Reader fs = new Reader();
        PrintWriter pw = new PrintWriter(System.out);
        //int t = fs.ni();
        int t = 1;
        for (int tc = 0; tc < t; tc++) {
            int n = fs.ni();
            long[] x = fs.longArray(n);
            long[] v = fs.longArray(n);
            BIT16 bit = new BIT16(200005);
            BIT16 people = new BIT16(200005);
            Set<Long> set = new HashSet();
            List<Long> list = new ArrayList();
            Map<Long,Integer> map = new HashMap();
            for (int i = 0; i < n; i++) {
                set.add(v[i]);
            }
            for (long val : set) {
                list.add(val);
            }
            Collections.sort(list);
            for (int i = 0; i < list.size(); i++) {
                map.put(list.get(i),i+1);
            } 
            long[][] matrix = new long[n][2];
            for (int i = 0; i < n; i++) {
                matrix[i][0] = x[i];
                matrix[i][1] = v[i];
            }
            Arrays.sort(matrix,new Comparator<long[]>(){
                public int compare(long[] a, long[] b) {
                    return Long.compare(a[0],b[0]);
                }
            });
            long res = 0;
            for (int i = 0; i < n; i++) {
                long cx = matrix[i][0];
                int cv = map.get(matrix[i][1]);
                long cur = (long)people.sum(cv) * (long)cx;
                cur -= (long)bit.sum(cv);
                res += cur;
                people.update(cv,1);
                bit.update(cv,cx);
            }
            pw.println(res);
        }
  
        pw.close();
    }
    public static long gcd(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcd(n2, n1 % n2);
    }
}
class GFG {
 
    // A utility function to get the
    // middle index of given range.
    static int getMid(int s, int e)
    {
        return s + (e - s) / 2;
    }
 
    /*
    * A recursive function to get the sum
    of values in given range of the array.
    * The following are parameters
      for this function.
    *
    * st -> Pointer to segment tree
    * node -> Index of current node in
    *         the segment tree.
    * ss & se -> Starting and ending indexes
    *         of the segment represented
    *         by current node, i.e., st[node]
    * l & r -> Starting and ending indexes
    *         of range query
    */
    static long MinUtil(long[] st, int ss,
                       int se, int l,
                       int r, int node)
    {
 
        // If segment of this node is completely
        // part of given range, then return
        // the max of segment
        if (l <= ss && r >= se)
            return st[node];
 
        // If segment of this node does not
        // belong to given range
        if (se < l || ss > r)
            return (long)(1e11);
 
        // If segment of this node is partially
        // the part of given range
        int mid = getMid(ss, se);
 
        return Math.min(
            MinUtil(st, ss, mid, l, r,
                     2 * node + 1),
            MinUtil(st, mid + 1, se, l, r,
                    2 * node + 2));
    }
 
    /*
    * A recursive function to update the
    nodes which have the given index in their
    * range. The following are parameters
    st, ss and se are same as defined above
    * index -> index of the element to be updated.
    */
    static void updateValue(long arr[], long[]
                            st, int ss,
                            int se, int index,
                            long value,
                            int node)
    {
        if (index < ss || index > se) {
            //System.out.println("Invalid Input");
            return;
        }
 
        if (ss == se) {
 
            // update value in array and in
            // segment tree
            arr[index] = value;
            st[node] = value;
        }
        else {
            int mid = getMid(ss, se);
 
            if (index >= ss && index <= mid)
                updateValue(arr, st, ss, mid,
                            index, value,
                            2 * node + 1);
            else
                updateValue(arr, st, mid + 1, se, index,
                            value, 2 * node + 2);
 
            st[node] = Math.min(st[2 * node + 1],
                                st[2 * node + 2]);
        }
        return;
    }
 
    // Return max of elements in range from
    // index l (query start) to r (query end).
    static long getMin(long[] st, int n, int l, int r)
    {
 
        // Check for erroneous input values
        if (l < 0 || r > n - 1 || l > r) {
            //System.out.printf("Invalid Input\n");
            return (long)(1e11);
        }
 
        return MinUtil(st, 0, n - 1, l, r, 0);
    }
 
    // A recursive function that constructs Segment
    // Tree for array[ss..se]. si is index of
    // current node in segment tree st
    static long constructSTUtil(long arr[],
                               int ss, int se,
                               long[] st, int si)
    {
 
        // If there is one element in array, store
        // it in current node of segment tree and return
        if (ss == se) {
            st[si] = arr[ss];
            return arr[ss];
        }
 
        // If there are more than one elements, then
        // recur for left and right subtrees and
        // store the max of values in this node
        int mid = getMid(ss, se);
 
        st[si] = Math.min(
            constructSTUtil(arr, ss, mid,
                            st, si * 2 + 1),
            constructSTUtil(arr, mid + 1,
                            se, st,
                            si * 2 + 2));
 
        return st[si];
    }
 
    /*
    * Function to construct segment tree from
    given array. This function allocates
    * memory for segment tree.
    */
    static long[] constructST(long arr[], int n)
    {
 
        // Height of segment tree
        int x = (int)Math.ceil(Math.log(n) / Math.log(2));
 
        // Maximum size of segment tree
        int max_size = 2 * (int)Math.pow(2, x) - 1;
 
        // Allocate memory
        long[] st = new long[max_size];
 
        // Fill the allocated memory st
        constructSTUtil(arr, 0, n - 1, st, 0);
 
        // Return the constructed segment tree
        return st;
    }
}
class BIT16 {

    long[] bit;

    public BIT16(int size) {
        bit = new long[size];
    }

    public void update(int ind, long delta) {
        while (ind < bit.length) {
            bit[ind] += delta;
            ind = ind + (ind & (-1 * ind));
        }
    }

    public long sum(int ind) {
        long s = 0;
        while (ind > 0) {
            s += bit[ind];
            ind = ind - (ind & (-1 * ind));
        }
        return s;
    }

    public long query(int l, int r) {
        return sum(r) - sum(l);
    }
}

class UnionFind18 {

    int[] id;

    public UnionFind18(int size) {
        id = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }

    public int find(int p) {
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }
        return root;
    }

    public void union(int p, int q) {
        int a = find(p), b = find(q);
        if (a == b) {
            return;
        }
        id[b] = a;
    }
}

class Reader {

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
        byte[] buf = new byte[64];
        int cnt = 0, c;
        while ((c = read()) != -1) {
            if (c == '\n') {
                break;
            }
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }

    public int ni() throws IOException {
        int ret = 0;
        byte c = read();
        while (c <= ' ') {
            c = read();
        }
        boolean neg = (c == '-');
        if (neg) {
            c = read();
        }
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');
        if (neg) {
            return -ret;
        }
        return ret;
    }

    public long nextLong() throws IOException {
        long ret = 0;
        byte c = read();
        while (c <= ' ') {
            c = read();
        }
        boolean neg = (c == '-');
        if (neg) {
            c = read();
        }
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');
        if (neg) {
            return -ret;
        }
        return ret;
    }

    public double nextDouble() throws IOException {
        double ret = 0, div = 1;
        byte c = read();
        while (c <= ' ') {
            c = read();
        }
        boolean neg = (c == '-');
        if (neg) {
            c = read();
        }
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');
        if (c == '.') {
            while ((c = read()) >= '0' && c <= '9') {
                ret += (c - '0') / (div *= 10);
            }
        }
        if (neg) {
            return -ret;
        }
        return ret;
    }

    private void fillBuffer() throws IOException {
        bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        if (bytesRead == -1) {
            buffer[0] = -1;
        }
    }

    private byte read() throws IOException {
        if (bufferPointer == bytesRead) {
            fillBuffer();
        }
        return buffer[bufferPointer++];
    }

    public void close() throws IOException {
        if (din == null) {
            return;
        }
        din.close();
    }
}

class FastScanner58 {

    BufferedReader br;
    StringTokenizer st;

    public FastScanner58() {
        br = new BufferedReader(new InputStreamReader(System.in), 32768);
        st = null;
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int ni() {
        return Integer.parseInt(next());
    }

    int[] intArray(int N) {
        int[] ret = new int[N];
        for (int i = 0; i < N; i++) {
            ret[i] = ni();
        }
        return ret;
    }

    long nl() {
        return Long.parseLong(next());
    }

    long[] longArray(int N) {
        long[] ret = new long[N];
        for (int i = 0; i < N; i++) {
            ret[i] = nl();
        }
        return ret;
    }

    double nd() {
        return Double.parseDouble(next());
    }

    String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
