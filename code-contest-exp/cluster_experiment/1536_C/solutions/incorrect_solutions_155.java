/*input
5
3
DDK
6
DDDDDD
4
DKDK
1
D
6
KKKDDD
*/
import java.io.*;
import java.util.*;

public class Main{
	 public static int hashCode2(long u,long v) {
            int hu = (int)(u ^ (u >>> 32));
            int hv =  (int)(v ^ (v >>> 32));
            return 31 * hu + hv;
	 	// return (u << 16) + v;
        }

public static void main(String[] args) throws Exception {
        MyScanner scn = new MyScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
         /*
      int n      = sc.nextInt();        // read input as integer
      long k     = sc.nextLong();       // read input as long
      double d   = sc.nextDouble();     // read input as double
      String str = sc.next();           // read input as String
      String s   = sc.nextLine();       // read whole line as String
 
      int result = 3*n;
      out.println(result);                    // print via PrintWriter
      */

      //The Code Starts here
      int t = scn.nextInt();
      while(t-- > 0){
      	int n = scn.nextInt();
      	String s = scn.next();
      	
      	int cntd = 0;
      	int cntk = 0;
      	int hashArray[] = new int[n];
      	for(int i=0;i<n;i++){
                 char ch = s.charAt(i);
                 if(ch == 'D'){
                 	cntd++;
                 }else{
                 	cntk++;
                 }

      			int x = gcd(cntk,cntd);
      			int y = cntk/x;
      			int z = cntd/x;

      			  hashArray[i] = hashCode2((long)y,(long)z);

      		

      	}
      	HashMap<Integer,Integer> map = new HashMap<>();
      	// HashMap<Pair,Integer> map = new HashMap<>();

      	for(int i=0;i<n;i++){

      		if(map.containsKey(hashArray[i])){
      			System.out.print(map.get(hashArray[i])+1 + " ");
      			map.put(hashArray[i],map.get(hashArray[i])+1);
      		}else{
      			System.out.print(1 + " ");
      			map.put(hashArray[i],1);
      		}
      	}
      	System.out.println();
      	
      	


      	
      }



      //The Code Ends here
        out.close();

    

    
 }





   //-----------PrintWriter for faster output---------------------------------
    public static PrintWriter out;
 
    //-----------MyScanner class for faster input----------
    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;
 
        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
 
        int nextInt() {
            return Integer.parseInt(next());
        }
 
        long nextLong() {
            return Long.parseLong(next());
        }
 
        double nextDouble() {
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
 
        public int[] nextIntArray(int n) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextInt();
            }
            return arr;
        }
 
        public long[] nextLongArray(int n) {
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextLong();
            }
            return arr;
        }
 
        public int[][] nextInt2DArray(int m, int n) {
            int[][] arr = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++)
                    arr[i][j] = nextInt();
            }
            return arr;
        }
 
        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
 
        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
        }
 
    }
 
    public static void debug(Object... o) {
        System.out.println(Arrays.deepToString(o));
    }
 
    public static class Pair implements Comparable<Pair> {
        long u;
        long v;
 
        public Pair(long u, long v) {
            this.u = u;
            this.v = v;
        }
 
 
        public int hashCode() {
            int hu = (int) (u ^ (u >>> 32));
            int hv = (int) (v ^ (v >>> 32));
            return 31 * hu + hv;
        }
 
        public boolean equals(Object o) {
            Pair other = (Pair) o;
            return u == other.u && v == other.v;
        }
 
        public int compareTo(Pair other) {
            return Long.compare(u, other.u) != 0 ? Long.compare(u, other.u) : Long.compare(v, other.v);
        }
 
        public String toString() {
            return "[u=" + u + ", v=" + v + "]";
        }
    }
       
       //is number prime upto N numbers
       static int N = 10;
       static boolean[] prime_seive = new boolean[N+1];
	public static void seive(){
		Arrays.fill(prime_seive, true);

		prime_seive[0] = false;
		prime_seive[1] = false;
		for(int i=2;i*i<N;i++){
				if(prime_seive[i] == true){

			for(int j=i*i;j<=N;j+=i){
					prime_seive[j] = false;
				}
			}

		}
	}

	public static int gcd(int a,int b)
{
    if (b == 0)
        return a;
    return gcd(b, a % b);
     
}
       //
    //--------------------------------------------------------
}




	



	
