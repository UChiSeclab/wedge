import java.awt.Point;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.*;

public class D implements Runnable{
   
    final boolean ONLINE_JUDGE = System.getProperty("ONLINE_JUDGE")!=null;
    BufferedReader in;
    PrintWriter out;
    StringTokenizer tok = new StringTokenizer("");
   
    void init() throws FileNotFoundException{
        if (ONLINE_JUDGE){
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(System.out);
        }else{
            in = new BufferedReader(new FileReader("input.txt"));
            out = new PrintWriter("output.txt");
        }
    }
   
    String readString() throws IOException{
        while(!tok.hasMoreTokens()){
            tok = new StringTokenizer(in.readLine());
        }
        return tok.nextToken();
    }
   
    int readInt() throws IOException{
        return Integer.parseInt(readString());
    }
   
    long readLong() throws IOException{
        return Long.parseLong(readString());
    }
   
    double readDouble() throws IOException{
        return Double.parseDouble(readString());
    }
   
    public static void main(String[] args){
        new Thread(null, new D(), "", 256 * (1L << 20)).start();
    }
   
    public void run(){
        try{
            long t1 = System.currentTimeMillis();
            init();
            solve();
            out.close();
            long t2 = System.currentTimeMillis();
            System.err.println("Time = "+(t2-t1));
        }catch (Exception e){
            e.printStackTrace(System.err);
            System.exit(-1);
        }
    }
   
    void solve() throws IOException{
        char[] c = readString().toCharArray();
        long k = readInt();
        int n = c.length;
        long sum = 0;
        for (long i = 1; i <= n; i++) sum += i;
        if (sum < k) {
            out.print("No such line.");
            return;
        }
        int m = 26;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++){
            list.add(i);
        }
        long[] count = new long[26];
        ArrayList<Character> ans = new ArrayList<Character>();
        while (true){
            Arrays.fill(count, 0);
            for (int i: list){
                count[c[i]-'a'] += (long)(n - i);
            }
            int j = 0;
            while (k > 0 && j < m && count[j] < k){
                k -= count[j];
                j++;
            }
            ans.add((char)(j + 'a'));
            for (int i: list){
                if (c[i] == j + 'a') k--;
                if (k == 0) break;
            }
            if (k == 0){
                for (char ch: ans){
                    out.print(ch + "");
                }
                return;
            }
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            for (int i : list){
                if (c[i] == j + 'a' && i < n - 1) tmp.add(i + 1);
            }
            list = tmp;
        }
    }
   
    static public class Utils {
       
        private Utils() {}
       
        public static void swap(int x, int y){
            x ^= y;
            y ^= x;
            x ^= y;
        }

        public static long mergeSort(int[] a) { // возвращает кол-во инверсий в исходном массиве
            return mergeSort(a, 0, a.length - 1);
        }

        private static long mergeSort(int[] a, int leftIndex, int rightIndex) {
            final int MAGIC_VALUE = 50;
            if (leftIndex < rightIndex) {
                if (rightIndex - leftIndex <= MAGIC_VALUE) {
                    return insertionSort(a, leftIndex, rightIndex);
                } else {
                    int middleIndex = (leftIndex + rightIndex) / 2;
                    return mergeSort(a, leftIndex, middleIndex) +
                    mergeSort(a, middleIndex + 1, rightIndex) +
                    merge(a, leftIndex, middleIndex, rightIndex);
                }
            }
            return 0;
        }

        private static long merge(int[] a, int leftIndex, int middleIndex, int rightIndex) {
            int length1 = middleIndex - leftIndex + 1;
            int length2 = rightIndex - middleIndex;
            int[] leftArray = new int[length1+1];
            int[] rightArray = new int[length2+1];
            System.arraycopy(a, leftIndex, leftArray, 0, length1);
            System.arraycopy(a, middleIndex + 1, rightArray, 0, length2);
            leftArray[length1] = rightArray[length2] = Integer.MAX_VALUE;
            long res = 0;
            for (int k = leftIndex, i = 0, j = 0; k <= rightIndex; k++) {
                if (leftArray[i] > rightArray[j]){
                    a[k] = rightArray[j++];
                    res += length1 - i;
                }else a[k] = leftArray[i++];
            }
            return res;
        }

        private static long insertionSort(int[] a, int leftIndex, int rightIndex) {
            long res = 0;
            for (int i = leftIndex + 1; i <= rightIndex; i++) {
                int current = a[i];
                int j = i - 1;
                while (j >= leftIndex && a[j] > current) {
                    a[j + 1] = a[j];
                    j--;
                    res++;
                }
                a[j + 1] = current;
            }
            return res;
        }
       
        public static int median(int[] a){
            return select(a, 0, a.length - 1, a.length / 2 - 1);
        }
       
        public static int select(int[] a, int i){
            return select(a, 0, a.length - 1, i);
        }
       
        private static int select(int[] a, int l, int r, int i){
            if (l == r) return a[l];
            int len = (r - l) / 5 + 1;
            int[] x = new int[len];
            for (int j = 0; j < len; j++){
                int leftIndex = l + j * 5;
                int rightIndex = min(leftIndex + 4, r);
                insertionSort(a, leftIndex, rightIndex);
                x[j] = a[(rightIndex+leftIndex)/2];
            }
            int med = median(x);
            int m = partition(a, l, r, med);
            int k = m - l;
            if (i == k) return a[k+l];
            else return i < k? select(a, l, m - 1, i): select(a, m + 1, r, i - k - 1);
        }
       
        private static int partition(int[] a, int l, int r, int med){
            int i = l;
            for (int j = l; j <= r; j++){
                if (a[j] < med){
                    swap(a[i++], a[j]);
                }
            }
            return i;
        }
       
        public static int median(int[] x, int[] y){
            return median(x, 0, x.length - 1, y, 0, y.length - 1);
        }
       
        private static int median(int[] x, int xl, int xr, int[] y, int yl, int yr){ // поиск медианы двух отсортированных массивов одинаковой длины
            if (xl == xr){
                int ind = xl + yl;
                for (; ind < x.length - 1 && yl < yr; yl++, ind++);
                return ind == x.length - 1? min(x[xl], y[yl]): max(x[xl], y[yl]);
            }
            if (yl == yr){
                int ind = xl + yl;
                for (; ind < y.length - 1 && xl < xr; xl++, ind++);
                return ind == y.length - 1? min(x[xl], y[yl]): max(x[xl], y[yl]);
            }
            int mdx = (xl + xr) / 2;
            int mdy = (yl + yr) / 2;
            return x[mdx] < y[mdy]? median(x, mdx, xr, y, yl, mdy): median(x, xl, mdx, y, mdy, yr);
        }
       
        public static final int[][] step8 = {{-1, 0}, {1, 0}, {0, -1}, {0, 1},
                                    {-1, -1}, {1, -1}, {-1, 1}, {1, 1}};

        public static final int[][] stepKnight = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                                            {1, -2}, {1, 2}, {2, -1}, {2, 1}};
       
        public static long gcd(long a, long b){
            if (a == 0) return b;
            return gcd(b % a, a);
        }
       
        public static long lcm(long a, long b){
            return a / gcd(a, b)*b;
        }

        public static long[] gcdPlus(long a, long b){
            long[] d = new long[3];
            if (a == 0){
                d[0] = b;
                d[1] = 0;
                d[2] = 1;
            }else{
                d = gcdPlus(b % a, a);
                long r = d[1];
                d[1] = d[2] - b/a*d[1];
                d[2] = r;
            }
            return d;
        }
       
        public static long binpow(long a, int n){
            if (n == 0) return 1;
            if ((n & 1) == 0){
                long b = binpow(a, n/2);
                return b*b;
            }else return binpow(a, n - 1)*a;
        }
       
        public static long binpowmod(long a, int n, long m){
            if (m == 1) return 0;
            if (n == 0) return 1;
            if ((n & 1) == 0){
                long b = binpowmod(a, n/2, m);
                return (b*b) % m;
            }else return binpowmod(a, n - 1, m)*a % m;
        }
       
        public static long f(long n, int x, int k){ //Кол-во десятичных чисел (включая 0), содержащих в себе цифры от 0 до k-1
            if (n == 0) return 1;
            long b = binpow(10, x - 1);
            long c = n / b;
            return (c < k? c: k)*binpow(k, x - 1) + (c < k? 1: 0)*f(n % b, x - 1, k);
        }
       
        public static long fib(int n){
            if (n == 0) return 0;
            if ((n & 1) == 0){
                long f1 = fib(n/2 - 1);
                long f2 = fib(n/2 + 1);
                return f2*f2 - f1*f1;
            }else{
                long f1 = fib(n/2);
                long f2 = fib(n/2 + 1);
                return f1*f1 + f2*f2;
            }
        }
       
        public static BigInteger BigFib(int n){
            if (n == 0) return BigInteger.ZERO;
            if ((n & 1) == 0){
                BigInteger f1 = BigFib(n/2 - 1);
                f1 = f1.multiply(f1);
                BigInteger f2 = BigFib(n/2 + 1);
                f2 = f2.multiply(f2);
                return f2.subtract(f1);
            }else{
                BigInteger f1 = BigFib(n/2);
                f1 = f1.multiply(f1);
                BigInteger f2 = BigFib(n/2 + 1);
                f2 = f2.multiply(f2);
                return f2.add(f1);
            }
        }
       
        public static long phi(long n){
            int[] p = Sieve((int)ceil(sqrt(n)) + 2);
            long phi = 1;
            for (int i = 0; i < p.length; i++){
                long x = 1;
                while (n % p[i] == 0){
                    n /= p[i];
                    x *= p[i];
                }
                phi *= x - x/p[i];
            }
            if (n != 1) phi *= n - 1;
            return phi;
        }
       
        public static boolean IsProbablyPrimeSqrt(long n){
            if (n == 1) return false;
            if ((n & 1) == 0) return (n == 2);
            for (int j = 3; j < sqrt(n) + 1; j += 2){
                if (n % j == 0) return false;
            }
            return true;
        }

        public static int[] Sieve(int n){
            boolean[] b = new boolean[n+1];
            Arrays.fill(b, true);
            b[0] = false;
            b[1] = false;
            long nLong = n;
            int j=0;
            for (int i = 1; i <= n; i++) {
                if (b[i]){
                    j++;
                    if (((long)i)*i <= nLong) {
                        for (int k = i*i; k <= n; k += i) {
                            b[k] = false;
                        }
                    }
                }
            }
            int[] p = new int[j];
            Arrays.fill(p, 0);
            j=0;
            for (int i = 2; i <= n; i++) {
                if (b[i]){
                    p[j]=i;
                    j++;
                }
            }
            return p;
        }
       
        public static class PointD{
           
            double x, y;
           
            public PointD(double x, double y){
                this.x = x;
                this.y = y;
            }
           
            public double d(PointD p){
                return sqrt(d2(p));
            }
           
            public double d2(PointD p){
                return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y);
            }
        }
       
        public static class Point3D{
           
            double x, y, z;
           
            public Point3D(double x, double y, double z){
                this.x = x;
                this.y = y;
                this.z = z;
            }
           
            public double d(Point3D p){
                return sqrt(d2(p));
            }
           
            public double d2(Point3D p){
                return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) + (z - p.z) * (z - p.z);
            }
        }
       
        public static int[][] Palindromes(String s){ // Кол-во всех подстрок - палиндромов в строке
            char[] c = s.toCharArray();
            int n = c.length;
            int[][] d = new int[2][n];
            int l = 0, r = -1;
            for (int i = 0; i < n; i++){
                int j = (i > r? 0: min(d[0][l+r-i+1], r-i+1)) + 1;
                for (; i - j >= 0 && i + j - 1< n && c[i-j] == c[i+j-1]; j++);
                d[0][i] = --j;
                if (i + d[0][i] - 1 > r){
                    r = i + d[0][i] - 1;
                    l = i - d[0][i];
                }
            }
            l = 0;
            r = -1;
            for (int i = 0; i < n; i++){
                int j = (i > r? 0: min(d[1][l+r-i], r-i)) + 1;
                for (; i - j >= 0 && i + j < n && c[i-j] == c[i+j]; j++);
                d[1][i] = --j;
                if (i + d[1][i] > r){
                    r = i + d[1][i];
                    l = i - d[1][i];
                }
            }
            return d;
        }
       
        public static class Permutation {
           
            private int[] a;
            private int n;
           
            public Permutation(int n){
                this.n = n;
                a = new int[n];
                for (int i = 0; i < n; i++){
                    a[i] = i;
                }
            }
           
            public boolean nextPermutation(){ //Пишется с do{}while(nextPermutation(a));
                int i = n - 1;
                for (i = n - 2; i >= 0 && a[i] < a[i+1]; i--);
                if (i == -1) return false;
                int jMin = i + 1;
                for (int j = n - 1; j > i; j--){
                    if (a[i] < a[j] && a[j] < a[jMin]){
                        jMin = j;
                    }
                }
                swap(i, jMin);
                for (int j = 1; j <= (n - i)/2; j++){
                    swap(i + j, n - j);
                }
                return true;
            }
           
           
            public int get(int i){
                return a[i];
            }
           
            private void swap(int i, int j){
                a[i] ^= a[j];
                a[j] ^= a[i];
                a[i] ^= a[j];
            }
        }
       
        public static class Fraction implements Comparable<Fraction>, Cloneable{
           
            public final Fraction FRACTION_ZERO = new Fraction();
            public final Fraction FRACTION_ONE = new Fraction(1);
            public long numerator = 0;
            public long denominator = 1;
           
            public Fraction(){
                numerator = 0;
                denominator = 1;
            }
           
            public Fraction(long numerator){
                this.numerator = numerator;
                denominator = 1;
            }
           
            public Fraction(long numerator, long denominator){
                this.numerator = numerator;
                this.denominator = denominator;
                Cancellation();
            }
           
            public Fraction(double numerator, double denominator, int accuracy){
                this.numerator = (long)(numerator*pow(10,accuracy));
                this.denominator = (long)(denominator*pow(10,accuracy));
                Cancellation();
            }
           
            public Fraction(String s){
                if (s.charAt(0) == '-'){
                    denominator = -1;
                    s = s.substring(1);
                }
                if (s.indexOf("/") != -1){
                    denominator *= Integer.parseInt(s.substring(s.indexOf("/") + 1));
                }
                if (s.indexOf(" ") != -1){
                    numerator = Integer.parseInt(s.substring(0, s.indexOf(" "))) * abs(denominator) + Integer.parseInt(s.substring(s.indexOf(" ") + 1, s.indexOf("/")));
                }else{
                    if (s.indexOf("/") != -1){
                        numerator = Integer.parseInt(s.substring(0, s.indexOf("/")));
                    }else{
                        numerator = Integer.parseInt(s)*abs(denominator);
                    }
                }
                this.Cancellation();
            }
           
            private void Cancellation(){
                long g = gcd(abs(numerator), abs(denominator));
                numerator /= g;
                denominator /= g;
                if (denominator < 0){
                    numerator *= -1;
                    denominator *= -1;
                }
            }
           
            public String toString(){
                String s = "";
                if (numerator == 0){
                    return "0";
                }
                if (numerator < 0){
                    s += "-";
                }
                if (abs(numerator) >= denominator){
                    s += Long.toString(abs(numerator) / denominator) + " ";
                }
                if (abs(numerator) % denominator != 0){
                    s += Long.toString(abs(numerator) % denominator);
                }else{
                    s = s.substring(0, s.length()-1);
                }
                if (denominator != 1){
                    s += "/" + Long.toString(denominator);
                }
                return s;
            }
           
            public Fraction add(Fraction f){
                Fraction fResult = new Fraction();
                fResult.denominator = lcm(denominator, f.denominator);
                fResult.numerator = numerator * fResult.denominator / denominator + f.numerator * fResult.denominator / f.denominator;
                fResult.Cancellation();
                return fResult;
            }
           
            public Fraction subtract(Fraction f){
                Fraction fResult = new Fraction();
                fResult.denominator = lcm(denominator, f.denominator);
                fResult.numerator = numerator * fResult.denominator / denominator - f.numerator * fResult.denominator / f.denominator;
                fResult.Cancellation();
                return fResult;
            }
           
            public Fraction multiply(Fraction f){
                Fraction fResult = new Fraction();
                fResult.numerator = numerator * f.numerator;
                fResult.denominator = denominator * f.denominator;
                fResult.Cancellation();
                return fResult;
            }
           
            public Fraction divide(Fraction f){
                Fraction fResult = new Fraction();
                fResult.numerator = numerator * f.denominator;
                fResult.denominator = denominator * f.numerator;
                fResult.Cancellation();
                return fResult;
            }
           
            @Override
            public int compareTo(Fraction f){
                long g = gcd(denominator, f.denominator);
                long res = numerator * (f.denominator / g) - f.numerator * (denominator / g);
                if (res < 0){
                    return -1;
                }
                if (res > 0){
                    return 1;
                }
                return 0;
            }
           
            public Fraction clone(){
                Fraction fResult = new Fraction(numerator, denominator);
                return fResult;
            }
           
            public Fraction floor(){
                Fraction fResult = this.clone();
                fResult.numerator = (fResult.numerator / fResult.denominator) * fResult.denominator;
                return fResult;
            }
           
            public Fraction ceil(){
                Fraction fResult = this.clone();
                fResult.numerator = (fResult.numerator/fResult.denominator + 1) * fResult.denominator;
                return fResult;
            }
           
            public Fraction binpow(int n){
                if (n==0) return FRACTION_ONE;
                if ((n&1)==0){
                    Fraction f=this.binpow(n/2);
                    return f.multiply(f);
                }else return binpow(n-1).multiply(this);
            }
        }
       
        public static class FenwickTree_1{  //One-dimensional array
           
            private int n;
            private long[] a;
            private long[] tree;
           
            public FenwickTree_1(int n){
                this.n = n;
                tree = new long[n];
                a = new long[n];
            }
           
            public long sum(int xl, int xr){
                return sum(xr) - sum(xl);
            }
           
            private long sum(int x){
                long result = 0;
                for (int i = x; i >= 0; i = (i & (i + 1)) - 1){
                    result += tree[i];
                }
                return result;
            }
           
            public void update(int x, long new_value){
                long delta = new_value - a[x];
                a[x] = new_value;
                for (int i = x; i < n; i = (i | (i + 1))){
                    tree[i] += delta;
                }
            }
        }
       
        public static class FenwickTree_2{  //Two-dimensional array
           
            private int n, m;
            private long[][] a;
            private long[][] tree;
           
            public FenwickTree_2(int n, int m){
                this.n = n;
                this.m = m;
                tree = new long[n][m];
                a = new long[n][m];
            }
           
            public long sum(int xl, int yl, int xr, int yr){
                return sum(xr, yr) - sum(xl - 1, yr) - sum(xr, yl - 1) + sum(xl - 1, yl - 1);
            }
           
            private long sum(int x, int y){
                long result = 0;
                for (int i = x; i >= 0; i = (i & (i + 1)) - 1){
                    for (int j = y; j >= 0; j = (j & (j + 1)) - 1){
                        result+=tree[i][j];
                    }
                }
                return result;
            }
           
            public void update(int x, int y, long new_value){
                long delta = new_value - a[x][y];
                a[x][y] = new_value;
                for (int i = x; i < n; i = (i | (i + 1))){
                    for (int j = y; j < m; j = (j | (j + 1))){
                        tree[i][j] += delta;
                    }
                }
            }
        }
       
        public static class FenwickTree_3{  //Three-dimensional array
           
            private int n, m, l;
            private long[][][] a;
            private long[][][] tree;
           
            public FenwickTree_3(int n, int m, int l){
                this.n = n;
                this.m = m;
                this.l = l;
                tree = new long[n][m][l];
                a = new long[n][m][l];
            }
           
            public long sum(int xl, int yl, int zl, int xr, int yr, int zr){
                return sum(xr, yr, zr) - sum(xl - 1, yr, zr)
                + sum(xl - 1, yr, zl - 1) - sum(xr, yr, zl - 1)
                - sum(xr, yl - 1, zr) + sum(xl - 1, yl - 1, zr)
                - sum(xl - 1, yl - 1, zl - 1) + sum(xr, yl - 1, zl - 1);
            }
           
            private long sum(int x, int y, int z){
                long result = 0;
                for (int i = x; i >= 0; i = (i & (i + 1)) - 1){
                    for (int j = y; j >= 0; j = (j & (j + 1)) - 1){
                        for (int k = z; k >= 0; k = (k & (k + 1)) - 1){
                            result += tree[i][j][k];
                        }
                    }
                }
                return result;
            }
           
            public void update(int x, int y, int z, long new_value){
                long delta = new_value - a[x][y][z];
                a[x][y][z] = new_value;
                for (int i = x; i < n; i = (i | (i + 1))){
                    for (int j = y; j < m; j = (j | (j + 1))){
                        for (int k = z; k < l; k = (k | (k + 1))){
                            tree[i][j][k] += delta;
                        }
                    }
                }
            }
        }
       
        static public class YoungTable{
           
            private long[] table;
            private int n;
            private int countOfElements;
           
            public YoungTable(int nmax) {
                n = (int) floor(sqrt(nmax)) + 1;
                table = new long[n*n];
                Arrays.fill(table, Long.MAX_VALUE);
                countOfElements = 0;
            }
           
            public YoungTable(long[] a){
                n = (int) floor(sqrt(a.length)) + 1;
                table = new long[n*n];
                Arrays.fill(table, Long.MAX_VALUE);
                countOfElements = 0;
                for (int i = 0; i < a.length; i++){
                    put(a[i]);
                }
            }
           
            int size(){
                return countOfElements;
            }
           
            void put(long v) throws ArrayIndexOutOfBoundsException{
                if (countOfElements == table.length) throw new ArrayIndexOutOfBoundsException();
                table[countOfElements] = v;
                YoungTablefyUp(countOfElements++);
            }
           
            private void YoungTablefyUp(int ind){
                int maxInd = ind;
                if (ind >= n && table[maxInd] < table[ind-n]) maxInd = ind - n;
                if (ind % n > 0 && table[maxInd] < table[ind-1]) maxInd = ind - 1;
                if (maxInd != ind){
                    swap(maxInd, ind);
                    YoungTablefyUp(maxInd);
                }
            }
           
            private void swap(int i, int j){
                table[i] ^= table[j];
                table[j] ^= table[i];
                table[i] ^= table[j];
            }
           
            boolean contains(long v){
                int i = n - 1;
                int j = 0;
                int ind = i * n + j;
                boolean exit = true;
                do{
                    exit = true;
                    while (i > 0 && table[ind] > v){
                        i--;
                        ind -= n;
                        exit = false;
                    }
                    while (j < n - 1 && table[ind] < v){
                        j++;
                        ind++;
                        exit = false;
                    }
                }while (!exit);
                return table[ind] == v;
            }
           
            long peek() throws ArrayIndexOutOfBoundsException{
                if (countOfElements == 0) throw new ArrayIndexOutOfBoundsException();
                return table[0];
            }
           
            long poll() throws ArrayIndexOutOfBoundsException{
                if (countOfElements == 0) throw new ArrayIndexOutOfBoundsException();
                long res = table[0];
                table[0] = table[--countOfElements];
                table[countOfElements] = Long.MAX_VALUE;
                YoungTablefyDown(0);
                return res;
            }
           
            private void YoungTablefyDown(int ind){
                int minInd = ind;
                if (ind % n < n - 1 && table[minInd] > table[ind+1]) minInd = ind + 1;
                if (ind / n < n - 1 && table[minInd] > table[ind+n]) minInd = ind + n;
                if (minInd != ind){
                    swap(minInd, ind);
                    YoungTablefyDown(minInd);
                }
            }
           
            void sort(long[] a){
                YoungTable yt = new YoungTable(a);
                for (int i = 0; i < a.length; i++){
                    a[i] = yt.poll();
                }
            }
        }
    }
}