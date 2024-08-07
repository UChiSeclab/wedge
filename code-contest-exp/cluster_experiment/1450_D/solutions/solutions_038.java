//Some of the methods are copied from GeeksforGeeks Website
import java.util.*;
import java.lang.*;
import java.io.*;
public class Main
{ 
 public static void main (String[] args) throws java.lang.Exception
   {
	try{
	     //long n=sc.nextLong();
	     //String s=sc.next();
	     //FastReader sc=new FastReader(System.in);
	     Reader sc=new Reader();
	     PrintWriter out=new PrintWriter(System.out);
		  int t=sc.nextInt();
		  while(t-->0)
		    { int n=sc.nextInt();
		      int a[]=new int[n];
	Set<Integer> set=new HashSet<>();
    for(int i=0;i<n;i++)
      {a[i]=sc.nextInt();
      set.add(a[i]);}
    Map<Integer,Integer> map=new HashMap<>();
    for(int i=0;i<n;i++)
     {
        if(!map.containsKey(a[i]))
          map.put(a[i],1);
        else
          map.replace(a[i],map.get(a[i])+1);
     }
      int ans[]=new int[n];
      if(set.size()==n)
       ans[0]=1;
     int start=0,end=n-1;
     for(int i=0;i<n;i++)
       {
           if(map.containsKey(i+1))
             {
                 if(map.get(i+1)>0)
                  ans[n-i-1]=1;
                 else
                  break;
             }
            else
             break;
          if(map.containsKey(i+1))
             {
                 if(map.get(i+1)>1)
                  break;
             }
            if(a[start]==i+1)
                start++;
            else if(a[end]==i+1)
                end--;
            else
                 break;
       }
    
     
     for(int e : ans)
		out.print(e);
		out.println();
		      
		    }
	     out.flush();
	     out.close();
	   }     
	catch(Exception e)
		 {}
	}
	
	



//Soluntion Ends Here........................................................................................


	//Template
	
   // int a[]=new int[n];
   // for(int i=0;i<n;i++)
   //  a[i]=sc.nextInt();
   
   // Map<Integer,Integer> map=new HashMap<>();
   // for(int i=0;i<n;i++)
   //  {
   //     if(!map.containsKey(a[i]))
   //       map.put(a[i],1);
   //     else
   //       map.replace(a[i],map.get(a[i])+1);
   //  }
     
  //ArrayList<Integer> al=new ArrayList<>();
  
 static int gcd(int a, int b) 
    { 
        if (a == 0) 
            return b; 
          
        return gcd(b%a, a); 
    } 
    
static void calcsubarray(long a[],long x[], int n, int c) 
 { 
    for (int i=0; i<(1<<n); i++) 
    { 
        long s = 0; 
        for (int j=0; j<n; j++) 
            if ((i & (1<<j))==0)
                s += a[j+c]; 
        x[i] = s; 
    } 
 } 
 
   static class FastReader{
 
        byte[] buf = new byte[2048];
        int index, total;
        InputStream in;
 
        FastReader(InputStream is) {
            in = is;
        }
 
        int scan() throws IOException {
            if (index >= total) {
                index = 0;
                total = in.read(buf);
                if (total <= 0) {
                    return -1;
                }
            }
            return buf[index++];
        }
 
        String next() throws IOException {
            int c;
            for (c = scan(); c <= 32; c = scan());
            StringBuilder sb = new StringBuilder();
            for (; c > 32; c = scan()) {
                sb.append((char) c);
            }
            return sb.toString();
        }
 
        int nextInt() throws IOException {
            int c, val = 0;
            for (c = scan(); c <= 32; c = scan());
            boolean neg = c == '-';
            if (c == '-' || c == '+') {
                c = scan();
            }
            for (; c >= '0' && c <= '9'; c = scan()) {
                val = (val << 3) + (val << 1) + (c & 15);
            }
            return neg ? -val : val;
        }
 
        long nextLong() throws IOException {
            int c;
            long val = 0;
            for (c = scan(); c <= 32; c = scan());
            boolean neg = c == '-';
            if (c == '-' || c == '+') {
                c = scan();
            }
            for (; c >= '0' && c <= '9'; c = scan()) {
                val = (val << 3) + (val << 1) + (c & 15);
            }
            return neg ? -val : val;
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
// Thank You !