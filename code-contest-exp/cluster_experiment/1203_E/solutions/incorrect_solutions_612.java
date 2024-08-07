import java.util.*;
import java.math.*;
import java.io.*; 
 public class B{
static int[] dx={-1,1,0,0};
static int[] dy={0,0,1,-1};
    static FastReader scan=new FastReader();
   public static PrintWriter out = new PrintWriter (new BufferedOutputStream(System.out));
  static ArrayList<Pair>es;
  static LinkedList<Integer>edges[];
  static LinkedList<Integer>edges2[];

   static boolean prime[];
   static void sieve(int n) 
    { 
        
        prime = new boolean[n+1]; 
        for(int i=0;i<n;i++) 
            prime[i] = true; 
          
        for(int p = 2; p*p <=n; p++) 
        { 
           
            if(prime[p] == true) 
            { 
                
                for(int i = p*p; i <= n; i += p) 
                    prime[i] = false; 
            } 
        } 
          
        
    }      
     public static int lowerBound(long[] array, int length, long value) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = (low + high) / 2;
            //checks if the value is less than middle element of the array
            if (value <= array[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
    public static int upperBound(long[] array, int length, long value) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = low+(high-low) / 2;
            if ( array[mid]>value) {
                high = mid ;
            } else {
                low = mid+1;
            }
        }
        return low;
    }
static long mod(long x,long y)
    {
        if(x<0)
            x=x+(-x/y+1)*y;
        return x%y;
    }    
     static boolean isPowerOfTwo(long n) 
    { 
        if (n == 0) 
            return false; 
  
        while (n != 1) { 
            if (n % 2 != 0) 
                return false; 
            n = n / 2; 
        } 
        return true; 
    } 

  
   
static boolean isprime(long x)
           {
            for(long i=2;i*i<=x;i++)
                if(x%i==0)
                    return false;
                return true;
           }


static int dist(int x1,int y1,int x2,int y2){
return Math.abs(x1-x2)+Math.abs(y1-y2);
}


    static long cuberoot(long x)
    {
        long lo = 0, hi = 1000005;
        while(lo<hi)
        {
            long m = (lo+hi+1)/2;
            if(m*m*m>x)
                hi = m-1;
            else
                lo = m;
        }
        return lo;
    }
     public static int log2(int N) 
    { 
  
        // calculate log2 N indirectly 
        // using log() method 
        int result = (int)(Math.log(N) / Math.log(2)); 
  
        return result; 
    } 
  
    
   
       static long gcd(long a, long b) {
    if(a!=0&&b!=0)
        while((a%=b)!=0&&(b%=a)!=0);
    return a^b;
}
    static long LCM(long a,long b){
    return (Math.abs(a*b))/gcd(a,b);
   }
   static int mid;
   public static class comp1 implements Comparator<Pair>{  
public int compare(Pair o1,Pair o2){
    return (int)(o1.y-o2.y);
}
  }

     public static class comp2 implements Comparator<Pair>{  
public int compare(Pair o1,Pair o2){
 
return (int)((o1.x+o1.y*mid)-(o2.x+o2.y*mid));
}
  }
 static boolean can(int m,int s)
 {
    return (s>=0&&s<=m*9);
 }
   static boolean collinear(long x1, long y1, long x2,  
                          long y2, long x3, long y3) 
{
    long a = x1 * (y2 - y3) +  
                x2 * (y3 - y1) +  
                x3 * (y1 - y2); 
                if(a==0)
                    return true;
                return false;
}

 static void pythagoreanTriplets(int limit) 
    { 
   //ArrayList<Integer>res=new ArrayList<Integer>();
        // triplet: a^2 + b^2 = c^2 
        int a, b, c = 0; 
  
        // loop from 2 to max_limitit 
        int m = 2; 
  
        // Limiting c would limit 
        // all a, b and c 
        int cnt=0;
        int tmp=1;
        while (c < limit) { 
  
            // now loop on j from 1 to i-1 
            //System.out.println("FUCK");

            for ( int n=tmp; n < m; n++) { 
                // Evaluate and print 
                // triplets using 
                // the relation between 
                // a, b and c 
                a = m * m - n * n; 
                b = 2 * m * n; 
                c = m * m + n * n;
               // System.out.println("FUCK");



  
                if (c > limit) 
                    break; 
  
              // System.out.println(a + " " + b + " " + c); 
                if(c+b==c*c-b*b){
               //     out.println(n);
         /// out.println(a+" "+b+" "+c);
                cnt++;
            }
            }
        tmp++; 
            m++; 
        } 
        out.println(cnt);
    } 
  static int arr[];
  static int n,l;
  static boolean flag=false;
  static boolean vis[]=new boolean[(int)4e4+5];
  static void dfs(int x)
  {
    if(x==l)
        flag=true;
    vis[x]=true;
    for(int v:edges[x])
        if(!vis[v])
            dfs(v);
  }
public static void main(String[] args)  throws Exception 
{
    /*int xx=253;
    for(int i=1;i*i<=xx;i++)
    {
        if(xx%i==0)
        {
            System.out.println(i);
            System.out.println(xx/i);
        }
    }*/
//java.util.Scanner scan=new java.util.Scanner(new File("mootube.in"));
// PrintWriter out = new PrintWriter (new FileWriter("mootube.out"));
//Scannen=new FastReader("moocast.in");
//out = new PrintWriter ("moocast.out");
//System.out.println(3^2);
//System.out.println(19%4);
   //StringBuilder news=new StringBuilder("ab");
   //news.deleteCharAt(1);
   //news.insert(0,'c');
   //news.deleteCharAt(0);
   //System.out.println(news);
   //System.out.println(can(2,15));
    //System.out.println(LCM(2,2));
  // System.out.println(31^15);
 //System.out.println("");
   //System.out.println(824924296372176000L>(long)1e16);
int tt=1;
//rec(2020);

//int st=scan.nextInt();
//System.out.println(calc(91));
//sieve(21000);

//SNWNSENSNNSWNNW

   // System.out.println(set.remove(new Pair(1,1)));
//System.out.println(count("cccccccccccccccccccccccccooooooooooooooooooooooooodddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeffffffffffffforrrrrrrrrrrrrcesssssssssssss","codeforces"));
  //S0ystem.out.println(isPowerOfTwo(446265625L));
//System.out.println("daaa".compareTo("bccc"));
//System.out.println(2999000999L>1999982505L);
//tt=scan.nextInt();
  outer:while(tt-->0)
 { 
int n=scan.nextInt();
int arr[]=new int[n];
int orig[]=new int[n];
int cnt[]=new int[150000+5];
for(int i=0;i<n;i++)
{
    arr[i]=scan.nextInt();
    orig[i]=arr[i];
    cnt[arr[i]]++;
}
sort(arr);
sort(orig);
cnt[arr[n-1]]--;
arr[n-1]++;
cnt[arr[n-1]]++;
for(int i=0;i<n;i++)
{
    if(arr[i]==1)
    {
        
    }
    else 
    {
        
        if(cnt[arr[i]-1]==0)
        {
             cnt[arr[i]]--;
            arr[i]--;
            cnt[arr[i]]++;
        }

    }
}
for(int i=n-1;i>=0;i--)
{
    if(arr[i]==orig[i]&&cnt[arr[i]+1]==0)
        {
             cnt[arr[i]]--;
            arr[i]++;
            cnt[arr[i]]++;

        }
}
Set<Integer>set=new HashSet<Integer>();
//out.println(cnt[4]);
for(int i=0;i<n;i++){
//out.println(arr[i]);
set.add(arr[i]);
}
//out.println(set.size());
int ans=set.size();
out.println(ans);
}



out.close();
}    


static class dsu{
    static int id[]=new int[101];
    dsu()
    {
        for(int i=0;i<101;i++)
            id[i]=i;
    }
    static int find(int x)
    {
        if(x==id[x])
            return x;
        return find(id[x]);
    }
    static void connect(int i,int j)
    {
        i=find(i);
        j=find(j);
        id[i]=j;
    }
    static boolean is(int i,int j)
    {
        return find(i)==find(j);
    }
}
static long binexp(long a,long n,long mod)
{
    if(n==0)
       return 1;
    long res=binexp(a,n/2,mod)%mod;
    res=res*res;
    if(n%2==1)
        return (res*a)%mod;
    else 
        return res%mod;
}
static class special{
    Pair x;
    Pair y;
    special(Pair x,Pair y)
    {
      this.x=new Pair(x.x,x.y);
      this.y=new Pair(y.x,y.y);
    }   
      @Override
        public int hashCode() {
            return (int)(x.x + 31 * y.y);
        }
        public boolean equals(Object other)
    {
        if(other instanceof special)
        {
            special e =(special)other;
            return this.x.x==e.x.x && this.x.y==e.x.y && this.y.x==e.y.x && this.y.y==e.y.y;
        }
        else return false;
    }
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
 public static  long pow(long b, long e) {
            long r = 1;
            while (e > 0) {
                if (e % 2 == 1) r = r * b ;
                b = b * b;
                e >>= 1;
            }
            return r;
        }

private static void sort(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int object : arr) list.add(object);
        Collections.sort(list);
        for (int i = 0; i < list.size(); ++i) arr[i] = list.get(i);
    }
 public static class FastReader {
        BufferedReader br;
        StringTokenizer root;
        
 
        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
        FastReader(String filename)throws Exception
        {

            br=new BufferedReader(new FileReader(filename));
        }
 
        String next() {
            while (root == null || !root.hasMoreTokens()) {
                try {
                    root = new StringTokenizer(br.readLine());
                } catch (Exception addd) {
                    addd.printStackTrace();
                }
            }
            return root.nextToken();
        }
 
        int nextInt() {
            return Integer.parseInt(next());
        }
 
        double nextDouble() {
            return Double.parseDouble(next());
        }
 
        long nextLong() {
            return Long.parseLong(next());
        }
 
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (Exception addd) {
                addd.printStackTrace();
            }
            return str;
        }
         public int[] nextIntArray(int arraySize) {
            int array[] = new int[arraySize];
 
            for (int i = 0; i < arraySize; i++) {
                array[i] = nextInt();
            }
 
            return array;
        }
}
public static class Pair implements Comparable<Pair>{
         long x;
         long y;
         long ab;
        
        long z;
        public Pair(){}
        public Pair(long x1, long y1,long z) {
            x=x1;
            y=y1;
            this.z=z;
        }
        public Pair(long x1, long y1) {
            x=x1;
            y=y1;
            this.ab=x+y;
        
        }
        @Override
        public int hashCode() {
            return (int)(x + 31 * y);
        }
        public String toString() {
            return x + " " + y;
        }
        @Override
        public boolean equals(Object o){
            if (o == this) return true;
            if (o.getClass() != getClass()) return false;
            Pair t = (Pair)o;
            return t.x == x && t.y == y;
        }
        public  int compareTo(Pair o)
        {
            return (int)(x-o.x);
        }
}

}







