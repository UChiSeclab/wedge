import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
 
 
public class A1 { 
 
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
            byte[] buf = new byte[1000000]; // line length 
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
 
    static void oracle(int graph[][], int dist[][], int V) 
    { 
        int i, j, k; 
  
        for (i = 1; i < V; i++) 
            for (j = 1; j < V; j++) 
                dist[i][j] = graph[i][j]; 
  
        for (k = 1; k < V; k++) 
        { 
            for (i = 1; i < V; i++) 
            { 
                for (j = 1; j < V; j++) 
                { 
                    if (dist[i][k] + dist[k][j] < dist[i][j]) 
                        dist[i][j] = dist[i][k] + dist[k][j]; 
                } 
            } 
        } 
    } 
 
    static long fast_pow(long base,long n,long M)
    {
        if(n==0)
           return 1;
        if(n==1)
        return base;
        long halfn=fast_pow(base,n/2,M);
        if(n%2==0)
            return ( halfn * halfn ) % M;
        else
            return ( ( ( halfn * halfn ) % M ) * base ) % M;
    }
 
    static long finextDoubleMMI_fermat(long n,int M)
    {
        return fast_pow(n,M-2,M);
    }
 
    static long nCrModPFermat(int n, int r, int p) 
    { 
        if (r == 0) 
            return 1; 
        long[] fac = new long[n+1]; 
        fac[0] = 1;           
        for (int i = 1 ;i <= n; i++) 
            fac[i] = fac[i-1] * i % p;       
        return (fac[n]* finextDoubleMMI_fermat(fac[r], p)% p * finextDoubleMMI_fermat(fac[n-r], p) % p) % p; 
    } 
 
    static void merge(int arr[], int l, int m, int r) 
    { 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        int L[] = new int [n1]; 
        int R[] = new int [n2]; 
  
        for (int i=0; i<n1; ++i) 
            L[i] = arr[l + i]; 
        for (int j=0; j<n2; ++j) 
            R[j] = arr[m + 1+ j]; 
        int i = 0, j = 0; 
        
        int k = l; 
        while (i < n1 && j < n2) 
        { 
            if (L[i] <= R[j]) 
            { 
                arr[k] = L[i]; 
                i++; 
            } 
            else
            { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
  
        while (i < n1) 
        { 
            arr[k] = L[i]; 
            i++; 
            k++; 
        } 
  
        while (j < n2) 
        { 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 
  
    static void sort(int arr[], int l, int r) 
    { 
        if (l < r) 
        { 
            int m = (l+r)/2; 
            sort(arr, l, m); 
            sort(arr , m+1, r); 
            merge(arr, l, m, r); 
        } 
    } 
 
    static void sort(int arr[]) 
    { 
        int l=0;
        int r=arr.length-1;
        if (l < r) 
        { 
            int m = (l+r)/2; 
            sort(arr, l, m); 
            sort(arr , m+1, r); 
            merge(arr, l, m, r); 
        } 
    } 
 
    static int[] arint(int n)throws IOException{
        int i;
        int a[]=new int[n];
        for(i=0;i<n;i++)
            a[i]=sc.nextInt();
        return a;
    }
 
 
    static long[] arlong(int n)throws IOException{
        int i;
        long a[]=new long[n];
        for(i=0;i<n;i++)
            a[i]=sc.nextLong();
        return a;
    }

    static long sum(long n){
        int len=(""+n).length();
        int mod=1000000007;
        int d=0;
        long sum1=d=(""+n).charAt(0)-48;
        for(int i=1;i<len;i++){
            sum1=(sum1*10)%mod;
            int ch=(""+n).charAt(i)-48;
            if(ch==d)
                continue;
            sum1=(sum1+ch)%mod;
            d=ch;
        }
        return sum1;
    }

    // static Reader sc=new Reader(); 
    static Scanner sc=new Scanner(System.in);
    static PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
 
    public static void main(String args[])throws IOException{
    	int i,j;
        int n=sc.nextInt();
        int a[]=new int[n];
        int b[]=new int[150002];
        for(i=0;i<n;i++){
            a[i]=sc.nextInt();
        }
        sort(a);
        int c=0;
        for(i=0;i<n;i++){
            if(a[i]>1&&(b[a[i]-1]==0)){
                b[a[i]-1]=1;
                c++;
            }
            else if(b[a[i]]==0){
                b[a[i]]=1;
                c++;
            }
            else if(b[a[i]+1]==0)
            {
                b[a[i]+1]=1;
                c++;
            }
        }
        pw.println(c);
        pw.close();
    }
}	




