import java.io.*;
import java.util.*;
 
 
public class Main1{

	public static Reader sc = new Reader();
	//public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

   public static void main(String[] args) throws IOException{
	
	int t = sc.nextInt();
	while (t-->0){
		int n = sc.nextInt();
		int[] a = new int[n];
		boolean[] v = new boolean[n+1];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
			v[a[i]] = true;
		}
		int maxP = 0;
		for (int i = 1; i <= n; i++) {
			if (v[i]) maxP = i;
			else break;
		}
		int maxDec = 0;
		int cur = 1;
		for (int i = 1; i < n; i++) {
			if (a[i] >= a[i-1]) {
				maxDec = Integer.max(cur, maxDec);
				cur = 0;
			}
			cur++;
		}
		maxDec = Integer.max(cur, maxDec);
	//	System.out.println(maxP + "asdasd" + maxDec);
		for (int i = 0; i < n; i++) {
			if (i == 0) {
				if (maxP == n) out.print(1);
				else out.print(0);
				continue;
			}
			// final size is n-i;
			if (n-i > maxP) out.print(0);
			else {
				if (maxDec == 1) out.print(1);
				else out.print(maxDec >= (i+1) ? 0:1);
			}
		}
		out.println();
	}
	
	out.close();
   }
   static long ceil(long a, long b) {
		return (a+b-1)/b;
	}
  
   static long powMod(long base, long exp, long mod) {
	   if (base == 0 || base == 1) return base;
	   if (exp == 0) return 1;
	   if (exp == 1) return base % mod;
	   long R = powMod(base, exp/2, mod) % mod;
	   R *= R;
	   R %= mod;
	   if ((exp & 1) == 1) {
		   return base * R % mod;
	   }
	   else return R % mod;
   }
   
   static long pow(long base, long exp) {
	   if (base == 0 || base == 1) return base;
	   if (exp == 0) return 1;
	   if (exp == 1) return base;
	   long R = pow(base, exp/2);
	   if ((exp & 1) == 1) {
		   return R * R * base;
	   }
	   else return R * R;
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
               {
            	   break;
               }
               buf[cnt++] = (byte) c; 
           } 
           return new String(buf, 0, Integer.max(cnt-1, 0)); 
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

	public static PrintWriter out = new PrintWriter (new BufferedOutputStream(System.out));
}











