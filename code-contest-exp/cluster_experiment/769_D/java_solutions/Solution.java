import java.util.*;
import java.io.*;
public class Solution
{
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
    static int MaxUtil(int[] st, int ss, 
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
            return -1;
 
        // If segment of this node is partially
        // the part of given range
        int mid = getMid(ss, se);
 
        return Math.max(
            MaxUtil(st, ss, mid, l, r,
                     2 * node + 1),
            MaxUtil(st, mid + 1, se, l, r,
                    2 * node + 2));
    }
 
    /*
    * A recursive function to update the
    nodes which have the given index in their
    * range. The following are parameters
    st, ss and se are same as defined above
    * index -> index of the element to be updated.
    */
    static void updateValue(int arr[], int[] 
                            st, int ss,
                            int se, int index, 
                            int value,
                            int node)
    {
        if (index < ss || index > se) {
            System.out.println("Invalid Input");
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
 
            st[node] = Math.max(st[2 * node + 1],
                                st[2 * node + 2]);
        }
        return;
    }
 
    // Return max of elements in range from
    // index l (query start) to r (query end).
    static int getMax(int[] st, int n, int l, int r)
    {
 
        // Check for erroneous input values
        if (l < 0 || r > n - 1 || l > r) {
            System.out.printf("Invalid Input\n");
            return -1;
        }
 
        return MaxUtil(st, 0, n - 1, l, r, 0);
    }
 
    // A recursive function that constructs Segment
    // Tree for array[ss..se]. si is index of
    // current node in segment tree st
    static int constructSTUtil(int arr[],
                               int ss, int se,
                               int[] st, int si)
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
 
        st[si] = Math.max(
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
    static int[] constructST(int arr[], int n)
    {
 
        // Height of segment tree
        int x = (int)Math.ceil(Math.log(n) / Math.log(2));
 
        // Maximum size of segment tree
        int max_size = 2 * (int)Math.pow(2, x) - 1;
 
        // Allocate memory
        int[] st = new int[max_size];
 
        // Fill the allocated memory st
        constructSTUtil(arr, 0, n - 1, st, 0);
 
        // Return the constructed segment tree
        return st;
    }
    
    static int dp[][];
    static int min(int pos , int side , String arr[] , int last)
    {
           if(pos == -1)
           return 0;
           
           if(dp[pos][side] != -1)
           return dp[pos][side];
           
           int x = 0;
           int m = arr[0].length();
           if(side == 0)
           {
                  for(int i = m-1 ; i >= 0 ; i--)
                  {
                         if(arr[pos].charAt(i) == '1')
                         {
                                x = i;
                                break;
                         }
                  }
           }
           
           else
           {
                  for(int i = 0 ; i < m ; i++)
                  {
                         if(arr[pos].charAt(i) == '1')
                         {
                                x = (m-1-i);
                                break;
                         }
                  }
           }
           
           if(pos != last)
           dp[pos][side] = Math.min(2*x+min(pos-1,side,arr,last)+1,m+min(pos-1,1-side,arr,last));
           
           else
           dp[pos][side] = x;
           
           return dp[pos][side];
    }
    public static void main(String []args) throws IOException
    {
           Reader sc = new Reader();
           
          int n = sc.nextInt();
          int k = sc.nextInt();
          int cnt[] = new int[10001];
          
          for(int i = 0 ; i < n ; i++)
          {
                 cnt[sc.nextInt()]++;
          }
          
          boolean is[] = new boolean[10001];
          
          for(int i = 0 ; i <= 10000 ; i++)
          {
                 int tot = 0;
                 for(int j = 0 ; j <= 14 ; j++)
                 {
                        if((i&(1<<j)) > 0)
                        tot++;
                 }
                 if(tot == k)
                 is[i] = true;
          }
          long ans = 0;
          for(int i = 0 ; i <= 10000 ; i++)
          {
                 for(int j = 0 ; j <= 10000 ; j++)
                 {
                        if((i^j) <= 10000  && is[i^j])
                        {
                               if(i != j)
                               ans += (long)cnt[i]*(long)cnt[j];
                               
                               else
                               ans += ((long)cnt[i]*(long)(cnt[i]-1));
                        }
                 }
          }
          System.out.println(ans/2);
    }
}