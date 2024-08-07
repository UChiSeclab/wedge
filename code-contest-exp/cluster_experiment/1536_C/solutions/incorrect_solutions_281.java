import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class C {
    static long mod=(long)1e9+7;
    static long mod1=998244353;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);


        int t= in.nextInt();
        while(t-->0) {

            int n=in.nextInt();
            char[] arr=in.next().toCharArray();
            int[] d_count=new int[n];
            int[] k_count=new int[n];
            int prev_d=0;
            int prev_k=0;
            for(int i=0;i<n;i++){
                if(arr[i]=='D'){
                    d_count[i]=prev_d+1;
                    k_count[i]=prev_k;
                    prev_d++;
                }else{
                    k_count[i]=prev_k+1;
                    d_count[i]=prev_d;
                    prev_k++;
                }
            }
//            out.println(Arrays.toString(d_count));
//            out.println(Arrays.toString(k_count));

            for(int i=0;i<n;i++){
                ArrayList<Integer> div=getDivisors(i+1);
                for(int j:div){
                    boolean flag=true;
                    int sum=j;
                    int d=-1;
                    int k=-1;
                    while(sum<=(i+1)){
                        if(d==-1 && k==-1){
                            d=d_count[sum-1];
                            k=k_count[sum-1];
                        }else {
                            int n_d=d_count[sum-1]-d_count[sum-j-1];
                            int n_k=k_count[sum-1]-k_count[sum-j-1];
//                            if(j==3)
//                            out.println(n_d+" "+n_k);
                            if(n_d!=d || n_k!=k){
                                flag=false;
                                break;
                            }
                        }
                        sum+=j;
                    }
                    if(flag) {
                        out.print((i + 1) / j + " ");
                        break;
                    }
                }
            }
            out.println();

        }
        out.close();
    }

    static ArrayList<Integer> getDivisors(int n)
    {
        // Note that this loop runs till square root
        ArrayList<Integer> ll=new ArrayList<>();
        for (int i=1; i<=Math.sqrt(n); i++)
        {
            if (n%i==0)
            {
                // If divisors are equal, print only one
                if (n/i == i)
                    ll.add(i);

                else // Otherwise print both
                {
                    ll.add(i);
                    ll.add(n/i);
                }
            }
        }
        Collections.sort(ll);
        return ll;
    }


    static final Random random=new Random();

    static void ruffleSort(int[] a) {
        int n=a.length;//shuffle, then sort
        for (int i=0; i<n; i++) {
            int oi=random.nextInt(n), temp=a[oi];
            a[oi]=a[i]; a[i]=temp;
        }
        Arrays.sort(a);
    }
    static long gcd(long x, long y){
        if(x==0)
            return y;
        if(y==0)
            return x;
        long r=0, a, b;
        a = Math.max(x, y);
        b = Math.min(x, y);
        r = b;
        while(a % b != 0){
            r = a % b;
            a = b;
            b = r;
        }
        return r;
    }
    static long modulo(long a,long b,long c){
        long x=1,y=a%c;
        while(b > 0){
            if(b%2 == 1)
                x=(x*y)%c;
            y = (y*y)%c;
            b = b>>1;
        }
        return  x%c;
    }
    public static void debug(Object... o){
        System.err.println(Arrays.deepToString(o));
    }

    static int upper_bound(int[] arr,int n,int x){
        int mid;
        int low=0;
        int high=n;
        while(low<high){
            mid=low+(high-low)/2;
            if(x>=arr[mid])
                low=mid+1;
            else
                high=mid;
        }
        return low;
    }

    static int lower_bound(int[] arr,int n,int x){
        int mid;
        int low=0;
        int high=n;
        while(low<high){
            mid=low+(high-low)/2;
            if(x<=arr[mid])
                high=mid;
            else
                low=mid+1;
        }
        return low;
    }
    static String printPrecision(double d){
        DecimalFormat ft = new DecimalFormat("0.00000000000");
        return String.valueOf(ft.format(d));
    }
    static int countBit(long mask){
        int ans=0;
        while(mask!=0){
            mask&=(mask-1);
            ans++;
        }
        return ans;
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
        public long nextLong() {
            return Long.parseLong(next());
        }
        public double nextDouble() {
            return Double.parseDouble(next());
        }
        public int[] readArray(int n)
        {
            int[] arr=new int[n];
            for(int i=0;i<n;i++) arr[i]=nextInt();
            return arr;
        }
    }
}