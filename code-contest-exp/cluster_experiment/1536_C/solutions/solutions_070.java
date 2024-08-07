    import java.io.*;
    import java.util.*;
    import java.lang.Math;


    public class Main {

          static PrintWriter pw;
          static Scanner sc;
          static StringBuilder ans;
          static long mod = 1000000000+7;

          static void pn(final Object arg) {
               pw.print(arg);          
               pw.flush();      
          }     

          /*-------------- for input in an value ---------------------*/
          static int ni()     { return sc.nextInt();       }
          static long nl()    { return sc.nextLong();      }
          static double nd()  { return sc.nextDouble();    }
          static String ns()  { return sc.next();          }


          static void ap(int arg)           { ans.append(arg); }
          static void ap(long arg)          { ans.append(arg); }
          static void ap(String arg)        { ans.append(arg); }
          static void ap(StringBuilder arg) { ans.append(arg); }        


          /*-------------- for input in an array ---------------------*/
          static void inputIntegerArray(int arr[]){
               for(int i=0; i<arr.length; i++)arr[i] = ni();
          }
          static void inputLongArray(long arr[]){
               for(int i=0; i<arr.length; i++)arr[i] = nl(); 
          }
          static void inputStringArray(String arr[]){
               for(int i=0; i<arr.length; i++)arr[i] = ns(); 
          }    
          static void inputDoubleArray(double arr[]){
               for(int i=0; i<arr.length; i++)arr[i] = nd();
          }


          /*-------------- File vs Input ---------------------*/
          static void runFile() throws Exception {
               sc = new Scanner(new FileReader("input.txt"));
               pw = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
          }
          static void runIo() throws Exception  {
               pw =new PrintWriter(System.out);
               sc = new Scanner(System.in);
          }

          static boolean isPowerOfTwo (long x) { return x!=0 && ((x&(x-1)) == 0);}

          static int gcd(int a, int b) { if (b == 0) return a;     return gcd(b, a % b); }
          
          static long nCr(long n, long r) { // Combinations
               if (n < r)
                    return 0;
               if (r > n - r) { // because nCr(n, r) == nCr(n, n - r)
                    r = n - r;
               }
               long ans = 1L;
               for (long i = 0; i < r; i++) {
                    ans *= (n - i);
                    ans /= (i + 1);
               }
               return ans;
          }          
          
          static int countDigit(long n){return (int)Math.floor(Math.log10(n) + 1);} 
          
          static boolean isPrime(int n) { 
               if (n <= 1) return false; 
               if (n <= 3) return true; 
               if (n % 2 == 0 || n % 3 == 0) return false; 

               for (int i = 5; i * i <= n; i = i + 6) 
               if (n % i == 0 || n % (i + 2) == 0) 
                    return false; 

               return true; 
          }         

          static boolean sv[] = new boolean[10002];
           

          static void seive() {

                 //true -> not prime
               // false->prime

               sv[0] = sv[1] = true; 
               sv[2] = false;
               for(int i = 0; i< sv.length; i++) {
                    if( !sv[i] && (long)i*(long)i < sv.length ) {
                         for ( int j = i*i; j<sv.length ; j += i ) {                
                          sv[j] = true;
                         }
                    }
               }
 
          }


          static long kadensAlgo(int ar[]) {

               int n = ar.length;
    
               long pre = ar[0];
               long ans = ar[0];                

               for(int i = 1; i<n; i++) {

                    pre = Math.max(pre + ar[i], ar[i]);
                    ans = Math.max(pre, ans);

               }

               return ans;
          }


          static long  binpow( long a,   long b) {
               long res = 1;
               while (b > 0) {
                    if ( (b & 1) > 0){
                    res = (res * a)%mod;
               }
               a = (a * a)%mod;
               b >>= 1;
               }

               return res;
          }

          static long factorial(long n)  {
               long res = 1, i;
               for (i = 2; i <= n; i++){
                    res  =  ((res%mod) * (i%mod))%mod;
               }          
               return res;
          }
  
          static class Pair {
               
               int i, c, d;
               Pair(int i,int c, int d){
                    this.i = i;
                    this.c = c; 
                    this.d = d;
               }
          }
     
          public static void main(String[] args)  throws Exception {

               // runFile();  
  
               runIo();              

               int t;
               t = 1;
  
               t =  sc.nextInt();
                
               ans = new StringBuilder();
          
               while( t-- > 0 ) {   
                    solve();
               }                
          
               pn(ans+""); 
 
          } 
 
          public static void solve() {          
               
               int n = ni();
               String s = ns() ;
               
               HashMap<String, Integer> map = new HashMap();

               int d = 0, k = 0;

               for(char ch:s.toCharArray()) {

                    if( ch == 'D')
                         d++;
                    else
                         k++;

                    int g = gcd(k,d);

                    // if( g < 1 ) {
                    //      ap("1 ");
                    // }

                    int dd = d;
                    int kk = k;

                    if( g > 0 ) { 
                    
                         dd = d/g;
                         kk = k/g;
                    }

                    String key = dd +"-"+ kk;

                    if( map.containsKey(key)) {
                         int ans = map.get(key);

                         ap((ans+1)+" ");
                         map.put(key, ans+1);
                    }
                    else {
                         ap("1 ");
                         map.put(key, 1);
                    }


               }


               ap("\n");

          } 
           

     }
   