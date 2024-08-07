import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution 
{
	static class InputReader
	{
	    private final InputStream stream;
	    private final byte[] buf = new byte[8192];
	    private int curChar, snumChars;

	    public InputReader(InputStream st) {
	      this.stream = st;
	    }

	    public int read() {
	      if (snumChars == -1)
	        throw new InputMismatchException();
	      if (curChar >= snumChars) {
	        curChar = 0;
	        try {
	          snumChars = stream.read(buf);
	        } catch (IOException e) {
	          throw new InputMismatchException();
	        }
	        if (snumChars <= 0)
	          return -1;
	      }
	      return buf[curChar++];
	    }

	    public int nextInt() {
	      int c = read();
	      while (isSpaceChar(c)) {
	        c = read();
	      }
	      int sgn = 1;
	      if (c == '-') {
	        sgn = -1;
	        c = read();
	      }
	      int res = 0;
	      do {
	        res *= 10;
	        res += c - '0';
	        c = read();
	      } while (!isSpaceChar(c));
	      return res * sgn;
	    }

	    public long nextLong() {
	      int c = read();
	      while (isSpaceChar(c)) {
	        c = read();
	      }
	      int sgn = 1;
	      if (c == '-') {
	        sgn = -1;
	        c = read();
	      }
	      long res = 0;
	      do {
	        res *= 10;
	        res += c - '0';
	        c = read();
	      } while (!isSpaceChar(c));
	      return res * sgn;
	    }

	    public int[] nextIntArray(int n) {
	      int a[] = new int[n];
	      for (int i = 0; i < n; i++) {
	        a[i] = nextInt();
	      }
	      return a;
	    }

	    public String readString() {
	      int c = read();
	      while (isSpaceChar(c)) {
	        c = read();
	      }
	      StringBuilder res = new StringBuilder();
	      do {
	        res.appendCodePoint(c);
	        c = read();
	      } while (!isSpaceChar(c));
	      return res.toString();
	    }

	    public String nextLine() {
	      int c = read();
	      while (isSpaceChar(c))
	        c = read();
	      StringBuilder res = new StringBuilder();
	      do {
	        res.appendCodePoint(c);
	        c = read();
	      } while (!isEndOfLine(c));
	      return res.toString();
	    }

	    public boolean isSpaceChar(int c) {
	      return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	    }

	    private boolean isEndOfLine(int c) {
	      return c == '\n' || c == '\r' || c == -1;
	    }
	}
	public static class DSU
    {
        int[] parent,rank;
        public DSU(int n)
        {
            parent = new int[n];
            rank = new int[n];
            for(int i=0;i<n;i++)
                parent[i] = -1;
        }
        public int find(int node)
        {
            if(parent[node] == -1)
                return node;
            parent[node] = find(parent[node]);
            return parent[node];
        }
        public void union(int x,int y)
        {
            int xroot = find(x);
            int yroot = find(y);
            if(rank[x] > rank[y])
                parent[yroot] = xroot;
            else if(rank[x] < parent[y])
                parent[xroot] = yroot;
            else
            {
                parent[yroot] = xroot;
                rank[xroot]++;
            }
        }
        public boolean sameset(int x,int y)
        {
            return find(x) == find(y);
        }
    }
     public static class pair implements Comparable<pair>
    {
        int val;
        int ind;
        public pair(int a,int b)
        {
            this.val = a;
            this.ind = b;
        }
        public int compareTo(pair a)
        {
            return val - a.val;
        }
    }
    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[] str = (br.readLine()).trim().split(" ");
        int[] arr = new int[n];
        for(int i=0;i<n;i++)
            arr[i] = Integer.parseInt(str[i]);
        if(arr[0] == 1)
        {
            for(int i=0;i<n;i++)
            {
                if(arr[i] == 1)
                    arr[i] = 2;
                else if(arr[i] == 2)
                    arr[i] = 1;
                    
            }
        }
        int i = 0;
        int c1 = 0,c2 = 0;
        int max = Integer.MIN_VALUE;
        while(i < n)
        {
            c1 = 0;
            while(i < n && arr[i] == 2)
            {
                c1++;
                i++;
            }
            int temp = 2*Math.min(c1,c2);
            if(temp > max)
                max = temp;
            c2 = 0;
            while(i < n && arr[i] == 1)
            {
                c2++;
                i++;
            }
            temp = 2*Math.min(c1,c2);
            if(temp > max)
                max = temp;
        }
        System.out.println(max);
    }
}
