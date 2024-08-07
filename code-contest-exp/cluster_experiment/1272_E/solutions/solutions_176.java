


/*
 -> Written by <-
   -----------
  |J_O_B_E_E_L|
  |___________|
  |    ___    |
  |   (^_^)   |
  |  /( | )\  |
  |____|_|____|
*/

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.Map.*;

public class Test {
    static PrintWriter pw = new PrintWriter(System.out);
    static ArrayList<Integer> [] list;
    static int [] even,arr,odd;
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        int x=sc.nextInt();
        arr=new int [x];
        even=new int [x];
        odd=new int [x];
        Arrays.fill(even,-1);
        Arrays.fill(odd,-1);
        list=new ArrayList[x];
        Queue<Integer>q_even=new LinkedList<Integer>();
        Queue<Integer>q_odd=new LinkedList<Integer>();
        for(int i=0;i<x;i++) list[i]=new ArrayList<>();
        for(int i=0;i<x;i++) {
        	arr[i]=sc.nextInt();
        	int left=i-arr[i];
        	int right=i+arr[i];
        	if(left>=0) list[left].add(i);
        	if(right<x) list[right].add(i);
        	if((arr[i]&1)==0) {q_even.add(i); even[i]=0; }
        	else {q_odd.add(i); odd[i]=0;}
        }
        //pw.println(q_even);
        while(!q_even.isEmpty()) {
        	int u=q_even.poll();
        	for(int v:list[u]) {
        		if(even[v]==-1) {
        			even[v]=1+even[u];
        			q_even.add(v);
        		}
        	}
        }
        while(!q_odd.isEmpty()) {
        	int u=q_odd.poll();
        	for(int v:list[u]) {
        		if(odd[v]==-1) {
        			odd[v]=1+odd[u];
        			q_odd.add(v);
        		}
        	}
        }
       // pw.println(Arrays.toString(odd));
       // pw.println(Arrays.toString(even));
        for(int i=0;i<x;i++) {
        	if((arr[i]&1)==0) pw.print(odd[i]+" ");
        	else pw.print(even[i]+" ");
        }
        pw.flush();
        pw.close();
    }
    
    static  class pair implements Comparable <pair>{
        int x,y;
        public pair(int x,int y){this.x=x; this.y=y;}

        public int compareTo(pair o) {
           if(y==o.y) return  x-o.x;
            return  y-o.y;
        }
        public String toString(){
            return  x+" "+y;
        }
    }

    static class Scanner {

        private InputStream in;
        private byte[] buffer = new byte[1024];
        private int curbuf;
        private int lenbuf;

        public Scanner(InputStream in) {
            this.in = in;
            this.curbuf = this.lenbuf = 0;
        }

        public boolean hasNextByte() {
            if (curbuf >= lenbuf) {
                curbuf = 0;
                try {
                    lenbuf = in.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (lenbuf <= 0)
                    return false;
            }
            return true;
        }

        private int readByte() {
            if (hasNextByte())
                return buffer[curbuf++];
            else
                return -1;
        }

        private boolean isSpaceChar(int c) {
            return !(c >= 33 && c <= 126);
        }

        private void skip() {
            while (hasNextByte() && isSpaceChar(buffer[curbuf]))
                curbuf++;
        }

        public boolean hasNext() {
            skip();
            return hasNextByte();
        }

        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            StringBuilder sb = new StringBuilder();
            int b = readByte();
            while (!isSpaceChar(b)) {
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        public int nextInt() {
            if (!hasNext())
                throw new NoSuchElementException();
            int c = readByte();
            while (isSpaceChar(c))
                c = readByte();
            boolean minus = false;
            if (c == '-') {
                minus = true;
                c = readByte();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res = res * 10 + c - '0';
                c = readByte();
            } while (!isSpaceChar(c));
            return (minus) ? -res : res;
        }

        public long nextLong() {
            if (!hasNext())
                throw new NoSuchElementException();
            int c = readByte();
            while (isSpaceChar(c))
                c = readByte();
            boolean minus = false;
            if (c == '-') {
                minus = true;
                c = readByte();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res = res * 10 + c - '0';
                c = readByte();
            } while (!isSpaceChar(c));
            return (minus) ? -res : res;
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }

    }

}