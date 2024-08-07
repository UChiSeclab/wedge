import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
public class Main{
    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        String next() {
            while (!st.hasMoreTokens())
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] nextArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }
        long[] nextArray(long n) {
            long[] a = new long[(int) n];
            for (int i = 0; i < n; i++) a[i] = nextLong();
            return a;
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
    static class FastWriter extends PrintWriter {
        FastWriter(){
            super(System.out);
        }
        void println(int[] array) {
            for(int i=0; i<array.length; i++) {
                print(array[i]+" ");
            }
            println();
        }
        void println(long [] array) {
            for(int i=0; i<array.length; i++) {
                print(array[i]+" ");
            }
            println();
        }
    }
    static int MOD=998244353;
    public static void main(String[] args){
        FastScanner in = new FastScanner();
        FastWriter out = new FastWriter();
        int t=in.nextInt();
        //int t=1;
        while (t-->0){
            int n=in.nextInt();
            String str=in.next();
            HashMap<String,Integer> map=new HashMap<>();
            int d=0,k=0;
            for(char ch:str.toCharArray()){
                if(ch=='D'){
                    d++;
                }else {
                    k++;
                }
                int x=d,y=k;
                int gcd=gcd(x,y);
                x/=gcd;y/=gcd;
                map.put(x+" "+y,map.getOrDefault(x+" "+y,0)+1);
                out.print(map.get(x+" "+y)+" ");
            }
            out.println();
        }
        out.close();
    }
    //Arrays.sort(a, (o1, o2) -> (o1[0] - o2[0]));
    static char get(int i){
        return (char)(i+'a');
    }
    static boolean distinct(int num){
        HashSet<Integer> set=new HashSet<>();
        int len=String.valueOf(num).length();
        while (num!=0){
            set.add(num%10);
            num/=10;
        }
        return set.size()==len;
    }
    static int maxSubArraySum(int a[],int st,int en) {
        int max_s = Integer.MIN_VALUE, max_e = 0,ind=0,ind1=1;
        for (int i = st; i <= en; i++) {
            max_e+= a[i];
            if (max_s < max_e){
                max_s = max_e;
                ind=ind1;
            }
            if (max_e < 0){
                max_e = 0;
                ind1=i+1;
            }
        }
        if(st==0){
            return max_s;
        }
        if(ind==1){
            return a[0]+2*max_s;
        }else {
            return 2*max_s+maxSubArraySum(a,0,ind-1);
        }
        //return max_s;
    }
    static void segmentedSieve(ArrayList<Integer> res,int low, int high) {
        if(low < 2){
            low = 2;
        }
        ArrayList<Integer> prime = new ArrayList<>();
        simpleSieve(high, prime);
        boolean[] mark = new boolean[high - low + 1];
        for (int i = 0; i < mark.length; i++){
            mark[i] = true;
        }
        for (int i = 0; i < prime.size(); i++) {
            int loLim = (low / prime.get(i)) * prime.get(i);
            if (loLim < low){
                loLim += prime.get(i);
            }
            if (loLim == prime.get(i)){
                loLim += prime.get(i);
            }
            for (int j = loLim; j <= high; j += prime.get(i)) {
                mark[j - low] = false;
            }
        }
        for (int i = low; i <= high; i++) {
            if (mark[i - low]){
                res.add(i);
            }
        }
    }
    static void simpleSieve(int limit, ArrayList<Integer> prime) {
        int bound = (int)Math.sqrt(limit);
        boolean[] mark = new boolean[bound + 1];
        for (int i = 0; i <= bound; i++){
            mark[i] = true;
        }
        for (int i = 2; i * i <= bound; i++) {
            if (mark[i]) {
                for (int j = i * i; j <= bound; j += i){
                    mark[j] = false;
                }
            }
        }
        for (int i = 2; i <= bound; i++) {
            if (mark[i]){
                prime.add(i);
            }
        }
    }
    static int highestPowerOf2(int n) {
        if (n < 1){ return 0; }
        int res = 1;
        for (int i = 0; i < 8 * Integer.BYTES; i++) {
            int curr = 1 << i;
            if (curr > n){ break; }
            res = curr;
        }
        return res;
    }
    static int reduceFraction(int x, int y) {
        int d= gcd(x, y);
        return x/d+y/d;
    }
    static boolean subset(int[] ar,int n,int sum){
        if(sum==0){
            return true;
        }
        if(n<0||sum<0){
            return false;
        }
        return subset(ar,n-1,sum)||subset(ar,n-1,sum-ar[n]);
    }
    static boolean isPrime(int n){
        if(n<=1) return false;
        for(int i = 2;i<=Math.sqrt(n);i++){
            if (n % i == 0) return false;
        }
        return true;
    }
    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }
    static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }
    static boolean isPowerOfTwo(int n) {
        if(n==0||n==1){return false;}
        double v = Math.log(n) / Math.log(2);
        return ((int)(Math.ceil(v)) == (int)(Math.floor(v)));
    }
    static boolean isPerfectSquare(int x){
        if (x >= 0) {
            int sr = (int)Math.sqrt(x);
            return ((sr * sr) == x);
        }
        return false;
    }
    static int lower_bound(int[] arr, int x) {
        int low_limit = 0, high_limit = arr.length, mid = -1;
        while (low_limit < high_limit) {
            mid = (low_limit + high_limit) / 2;
            if (arr[mid] >= x){
                high_limit = mid;
            }else{
                low_limit = mid + 1;
            }
        }
        return low_limit;
    }
    static int upper_bound(int[] arr, int x) {
        int low_limit = 0, high_limit = arr.length, mid = -1;
        while (low_limit < high_limit) {
            mid = (low_limit + high_limit) / 2;
            if (arr[mid] > x){
                high_limit = mid;
            }else{
                low_limit = mid + 1;
            }
        }
        return low_limit;
    }
    static int binarySearch(int[] ar,int n,int num){
        int low=0,high=n-1;
        while (low<=high){
            int mid=(low+high)/2;
            if(ar[mid]==num){
                return mid;
            }else if(ar[mid]>num){
                high=mid-1;
            }else {
                low=mid+1;
            }
        }
        return -1;
    }
}