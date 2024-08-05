/*
ID: abdelra29
LANG: JAVA
TASK: skidesign
*/
import java.util.*;
import java.math.*;
import java.io.*; 
 public class B{
static int[] dx = new int[]{-1,0,1,0};
    static int[] dy = new int[]{0,1,0,-1};
    static FastReader scan=new FastReader();
   public static PrintWriter out = new PrintWriter (new BufferedOutputStream(System.out));
  static ArrayList<Pair>es;
  static LinkedList<Integer>edges[];
  static Set<Pair>edges2[][];

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
     public static int lowerBound(int[] array, int length, int value) {
        int low = 0;
        int high = length;
        while (low < high) {
            final int mid = (low + high) / 2;
            //checks if the value is less than middle element of the array
            if (value <= array[mid]) {
                high = mid;
            } else {
                low = mid+1 ;
            }
        }
        return low;
    }
    public static int upperBound(int[] array, int length, int value) {
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
   public static class comp1 implements Comparator<ArrayList<Integer>>{  
public int compare(ArrayList<Integer> o1,ArrayList<Integer> o2){
    return (int)(o2.size()-o1.size());
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

static int ceil(int a,int b)
{
    if(a%b!=0)
        return (a/b)+1;
    else return a/b;
}

static int cnt=0;
static int count1[];
 static void numberOfNodes(int s, int e) 
    { 
        count1[s] = 1; 
        for(Integer u: edges[s]) 
        { 
            // condition to omit reverse path 
            // path from children to parent 
            if(u == e) 
                continue; 
              
            // recursive call for DFS 
            numberOfNodes(u ,s); 
              
            // update count[] value of parent using  
            // its children 
            count1[s] += count1[u]; 
        } 
    } 
/*static int rec(int i)
{

}*/

static String orig;
static String res;
static void rec(StringBuilder s)
{
    if(s.length()==11)
        return;
  //System.out.println(res);
    if(!orig.contains(s.toString())){
   
        if(s.length()==res.length()){
            if(s.toString().compareTo(res)<0){
            res=s.toString();
        }
        }
        else if(s.length()<res.length())
        {
            res=s.toString();
        }
        return;
    }
    for(char c='a';c<='z';c++)
    {
        s.append(c);
        rec(s);
        s.deleteCharAt(s.length()-1);
    }
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
//scan=new FastReader("skidesign.in");
//out = new PrintWriter ("skidesign.out");
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
//System.out.println(Long.MAX_VALUE);
//System.out.println(Integer.MAX_VALUE);
//int st=scan.nextInt();
//System.out.println(calc(91));
//sieve(21000);

//SNWNSENSNNSWNNW

   // System.out.println(set.remove(new Pair(1,1)));
//System.out.println(count("cccccccccccccccccccccccccooooooooooooooooooooooooodddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeffffffffffffforrrrrrrrrrrrrcesssssssssssss","codeforces"));
  //S0ystem.out.println(isPowerOfTwo(446265625L));
//System.out.println("daaa".compareTo("bccc"));
//System.out.println(2999000999L>1999982505L);
//System.out.println("?bac?bac??a?a?a?abac????abacab?ca?a".length());
//StringBuilder s=new StringBuilder("");
//tt=scan.nextInt();
//String ok="ABDC";
//System.out.println(ok.substring(2));
//System.out.println(2000000000/2);
tt=scan.nextInt();
//System.out.println(LCM(4,6));
//System.out.println(isprime(23335));
//int T=1;

 StringBuilder news=new StringBuilder("");
   for(int i=0;i<1000;i++)
    news.append("z");
  outer:while(tt-->0)
 {
   int n=scan.nextInt();
   String s=scan.next();
   int arr[]=new int[n+1];
   for(int i=0;i<n;i++)
    if(s.charAt(i)=='D')
        arr[i+1]=1;
    for(int i=2;i<=n;i++)
        arr[i]+=arr[i-1];
    ArrayList<Integer>can[]=new ArrayList[n+1];
    for(int i=0;i<=n;i++)
        can[i]=new ArrayList();
    for(int i=1;i<=n;i++)
    {
        int prev=i;
        int should=-1;
        if(i*2<=n)
            should=arr[i*2]-arr[prev];

        if(should!=arr[i]){
            //if(i==3)
                   // out.println(arr[3]+" "+should);
            continue;
        }

        for(int j=i*2;j<=n;j+=i)
        {
            if(arr[j]-arr[prev]!=should){

                break;
            }
            prev=j;
            can[j].add(i);
        }
    }
    for(int i=1;i<=n;i++){
        if(can[i].size()==0)
            out.print(1+" ");
        else out.print((i/can[i].get(0))+" ");
        //out.println(can[i]);
    }
out.println();

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
            final int prime = 31;
            int result = 1;
            result = prime * result +(int) x;
            result = prime * result + (int)y;
            return result;
        }
        public String toString() {
            return x + " " + y;
        }
        @Override
        public boolean equals(Object o){
            if (o == this) return true;
            if(o==null)
                return false;
            if (o.getClass() != getClass()) return false;
            Pair t = (Pair)o;
            return t.x == x && t.y == y&&t.z==z;
        }
        public  int compareTo(Pair o)
        {
            return (int)(y-o.y);
        }
}

}







