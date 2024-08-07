

import java.io.*;
import java.util.*;

/**
 *  
 * @author akash_2ehuikg
 * Coding is love <3!
 */

public class A {
    public static void main(String args[]) {
        try {
            int n=nextInt();
            int a[]=new int[n];
            int c1=0;
            int c2=0;
            for(int i=0;i<n;i++){
                a[i]=nextInt();
            }
            int ans=0;
            int flag=0;
            for (int i = 0; i < n; i++) {
                if(a[i]==1){
                    if(flag==2){
                        ans=Math.max(ans, Math.min(c1, c2));
                        c1=0;
                    }
                    c1++;
                    flag=1;
                }else{
                    
                    if(flag==1){
                        ans=Math.max(ans, Math.min(c1, c2));
                        c2=0;
                    }
                    c2++;
                    flag=2;
                }
            }
            ans=Math.max(ans, Math.min(c1, c2));
            bw.write(ans*2+"\n");
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //TEMPLATE
    
    //******************************************************************************
    // Fast I/O 
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st = null;
    static int mod=1000000007;
    static boolean prime[]=new boolean[10000001];

    static String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }
    
    static int nextInt() {
        return Integer.parseInt(next());
    }
    
    static long nextLong() {
        return Long.parseLong(next());
    }

    static double nextDouble() {
        return Double.parseDouble(next());
    }

    static int b_search(int l,int r,int A[],int key){
        int ans=0;
        l=0;r=1000000000;

        while(l<r){
            //System.out.println(ans);
            ans=(l+r)/2;
            long temp=0;
            for (int i = 0; i < A.length; i++) {
                temp+=(long)Math.ceil((double)A[i]/(double)ans);
            }

            if(temp<=key){
                r=ans;
            }
            else{
                l=ans+1;
                ans=r;
            }
        }

        return ans;
    }

//******************************************************************************
//Sieve
static void sieveOfEratosthenes(boolean prime[]){
        int n=10000000;
        for(int i=0;i<n;i++) 
            prime[i] = true; 
          
        for(int p = 2; p*p <=n; p++){ 
            if(prime[p] == true) 
            {
                for(int i = p*p; i <= n; i += p) 
                    prime[i] = false; 
            } 
        } 
    } 

//******************************************************************************
//Modulo and FastExpo
    static long modInverse(long a)
    {
       return power(a, mod - 2,mod);
    }
     
    // To compute x^y under modulo m
    static long power(long x, long y, int p)
    {
        // Initialize result
        long res = 1;     
        // Update x if it is more  
        // than or equal to p
        x = x % p; 
     while (y > 0)
        {
            // If y is odd, multiply x
            // with result
            if((y & 1)==1)
                res = (res * x) % p;
            // y must be even now
            // y = y / 2
            y = y >> 1; 
            x = (x * x) % p; 
        }
        return res;
    }
//******************************************************************************
//GCD
    static long gcd(long a, long b)
    {
        if (a == 0)
            return b;
         
        return gcd(b%a, a);
    }

    //******************************************************************************
    // Useful user datatype
    
    static class Pair{
        public long x;
        public long y;

        public Pair(long first, long second){
            this.x = first;
            this.y = second;
            }
        @Override
        public String toString() {
        return "(" + x + "," + y + ")";
        }
    }

    static Comparator csort=new Comparator<Pair>(){
                    public int compare(Pair a, Pair b) {
                        if(a.x < b.x)
                        return -1;
                        else if (a.x > b.x)
                        return 1;
                        else if(a.y < b.y)
                        return -1;
                        else if (a.y > b.y)
                        return 1;
                        else
                        return 0;
                    }
                };

    //******************************************************************************
    //N choose K
    public static long choose(long total, long choose){
        if(total < choose)
            return 0;
        if(choose == 0 || choose == total)
            return 1;
        return choose(total-1,choose-1)+choose(total-1,choose);
    }

    //******************************************************************************
    //Permutations
    // simply prints all permutation - to see how it works
    private static void printPermutations( Comparable[] c ) {
            System.out.println( Arrays.toString( c ) );
            while ( ( c = nextPermutation( c ) ) != null ) {
                    System.out.println( Arrays.toString( c ) );
            }
    }

    // modifies c to next permutation or returns null if such permutation does not exist
    private static Comparable[] nextPermutation( final Comparable[] c ) {
            // 1. finds the largest k, that c[k] < c[k+1]
            int first = getFirst( c );
            if ( first == -1 ) return null; // no greater permutation
            // 2. find last index toSwap, that c[k] < c[toSwap]
            int toSwap = c.length - 1;
            while ( c[ first ].compareTo( c[ toSwap ] ) >= 0 )
                    --toSwap;
            // 3. swap elements with indexes first and last
            swap( c, first++, toSwap );
            // 4. reverse sequence from k+1 to n (inclusive) 
            toSwap = c.length - 1;
            while ( first < toSwap )
                    swap( c, first++, toSwap-- );
            return c;
    }

    // finds the largest k, that c[k] < c[k+1]
    // if no such k exists (there is not greater permutation), return -1
    private static int getFirst( final Comparable[] c ) {
            for ( int i = c.length - 2; i >= 0; --i )
                    if ( c[ i ].compareTo( c[ i + 1 ] ) < 0 )
                            return i;
            return -1;
    }

    // swaps two elements (with indexes i and j) in array 
    private static void swap( final Comparable[] c, final int i, final int j ) {
            final Comparable tmp = c[ i ];
            c[ i ] = c[ j ];
            c[ j ] = tmp;
    }
    //******************************************************************************
    // DSU
    //Maintain an Array A with N elements and Array size for size of each set, intialize A[i]=i and size[i]=1

    static int root(int A[],int i){
        while(A[i]!=i){
            A[i]=A[A[i]];
            i=A[i];
        }
        return i;
    }

    static boolean find(int A[],int a,int b){
        if(root(A,a)==root(A,b))return true;
        else return false;
    }

    static void union(int A[],int size[],int a,int b){
        int ra=root(A,a);
        int rb=root(A,b);

        if(ra==rb)return;

        if(size[ra]<size[rb]){
            A[ra]=A[rb];
            size[rb]+=size[ra];
        }else{
            A[rb]=A[ra];
            size[ra]+=size[rb];
        }
    }

    //**************************************************************************
    //BinarySearch
    /* binary search for 5:
                v-- lower bound
         1 2 3 4 5 5 5 6 7 9
                      ^-- upper bound
        
            binary search for 8
                        v-- lower bound
       1 2 3 4 5 5 5 6 7 9
                        ^-- upper bound
    */
    //For finding greater than 
    static int upper_bound(int A[],int key){
        int first = 0;
        int last = A.length;
        int mid;

        while (first < last) {
            mid =first+(last - first)/2; 

            if (A[mid] <= key)  
                first = mid + 1; 
            else 
                last = mid;
        }

        return first;
    }
    
    //For finding greater than equal to
    static int lower_bound(int A[],int key){
        int first = 0;
        int last = A.length;
        int mid=0;
        
        while (first < last) {
            mid = first + ((last - first) >> 1); 
            if (A[mid] < key)  
                first = mid + 1; 
            else 
                last = mid;
        }
        
        return first;
    }
    //**************************************************************************
}
