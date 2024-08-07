import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class cf1379d {

    public static void main(String[] args) throws IOException {
        long n = rnl(), h = nl(), m = nl(), k = nl();
        Map<Long, Integer> res = new TreeMap<>();
        Map<Long, Set<Integer>> inds = new TreeMap<>();
        for(int i = 0; i < n; ++i) {
            long hi = rnl(), mi = nl();
            inds.computeIfAbsent(mi % (m / 2), key -> new HashSet<>()).add(i + 1);
            res.put(mi % (m / 2), res.getOrDefault(mi % (m / 2), 0) + 1);
            res.put(m / 2 + mi % (m / 2), res.get(mi % (m / 2)));
        }
        Iterator<Long> lo = res.keySet().iterator(), hi = res.keySet().iterator();
        long curlo = -1, cnt = 0, max = 0, maxlo = 0, curhi = 0, lasthi = 0, maxhi = 0;
        while(lo.hasNext()) {
            if(curlo >= 0) {
                cnt -= res.get(curlo);
            }
            curlo = lo.next();
            while(hi.hasNext() && (curhi = hi.next()) <= curlo + m / 2 - k) {
                cnt += res.get(curhi);
                lasthi = curhi;
            }
            if(!hi.hasNext() && curhi <= curlo + m / 2 - k) {
                lasthi = curhi;
            }
            if(cnt > max) {
                max = cnt;
                maxlo = curlo;
                maxhi = lasthi >= m / 2 ? lasthi - m / 2 : lasthi;
            }
        }
        prln(n - max, maxlo);
        List<Integer> cancel = new ArrayList<>();
        // prln(maxlo, maxhi);
        if(maxhi < maxlo) {
            for(long key : inds.keySet()) {
                if(key > maxhi && key < maxlo) {
                    cancel.addAll(inds.get(key));
                }
            }
        } else {
            for(long key : inds.keySet()) {
                if(key > maxhi || key < maxlo) {
                    cancel.addAll(inds.get(key));
                }
            }
        }
        prln(cancel);
        close();
    }

    static BufferedReader __in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter __out = new PrintWriter(new OutputStreamWriter(System.out));
    static StringTokenizer input;
    static Random rand = new Random();

    // references
    // IBIG = 1e9 + 7
    // IMAX ~= 2e10
    // LMAX ~= 9e18
    
    // constants
    static final int IBIG = 1000000007;
    static final int IMAX = 2147483647;
    static final int IMIN = -2147483648;
    static final long LMAX = 9223372036854775807L;
    static final long LMIN = -9223372036854775808L;
    // math util
    static int minof(int a, int b, int c) {return min(a, min(b, c));}
    static int minof(int... x) {if(x.length == 1) return x[0]; if(x.length == 2) return min(x[0], x[1]); if(x.length == 3) return min(x[0], min(x[1], x[2])); int min = x[0]; for(int i = 1; i < x.length; ++i) if(x[i] < min) min = x[i]; return min;}
    static long minof(long a, long b, long c) {return min(a, min(b, c));}
    static long minof(long... x) {if(x.length == 1) return x[0]; if(x.length == 2) return min(x[0], x[1]); if(x.length == 3) return min(x[0], min(x[1], x[2])); long min = x[0]; for(int i = 1; i < x.length; ++i) if(x[i] < min) min = x[i]; return min;}
    static int maxof(int a, int b, int c) {return max(a, max(b, c));}
    static int maxof(int... x) {if(x.length == 1) return x[0]; if(x.length == 2) return max(x[0], x[1]); if(x.length == 3) return max(x[0], max(x[1], x[2])); int max = x[0]; for(int i = 1; i < x.length; ++i) if(x[i] > max) max = x[i]; return max;}
    static long maxof(long a, long b, long c) {return max(a, max(b, c));}
    static long maxof(long... x) {if(x.length == 1) return x[0]; if(x.length == 2) return max(x[0], x[1]); if(x.length == 3) return max(x[0], max(x[1], x[2])); long max = x[0]; for(int i = 1; i < x.length; ++i) if(x[i] > max) max = x[i]; return max;}
    static int powi(int a, int b) {if(a == 0) return 0; int ans = 1; while(b > 0) {if((b & 1) > 0) ans *= a; a *= a; b >>= 1;} return ans;}
    static long powl(long a, int b) {if(a == 0) return 0; long ans = 1; while(b > 0) {if((b & 1) > 0) ans *= a; a *= a; b >>= 1;} return ans;}
    static int floori(double d) {return (int)d;}
    static int ceili(double d) {return (int)ceil(d);}
    static long floorl(double d) {return (long)d;}
    static long ceill(double d) {return (long)ceil(d);}
    static int randInt(int min, int max) {return rand.nextInt(max - min + 1) + min;}
    // array util
    static void reverse(int[] a) {for(int i = 0, n = a.length, half = n / 2; i < half; ++i) {int swap = a[i]; a[i] = a[n - i - 1]; a[n - i - 1] = swap;}}
    static void reverse(long[] a) {for(int i = 0, n = a.length, half = n / 2; i < half; ++i) {long swap = a[i]; a[i] = a[n - i - 1]; a[n - i - 1] = swap;}}
    static void reverse(double[] a) {for(int i = 0, n = a.length, half = n / 2; i < half; ++i) {double swap = a[i]; a[i] = a[n - i - 1]; a[n - i - 1] = swap;}}
    static void reverse(char[] a) {for(int i = 0, n = a.length, half = n / 2; i < half; ++i) {char swap = a[i]; a[i] = a[n - i - 1]; a[n - i - 1] = swap;}}
    static <T> void reverse(T[] a) {for(int i = 0, n = a.length, half = n / 2; i < half; ++i) {T swap = a[i]; a[i] = a[n - i - 1]; a[n - i - 1] = swap;}}
    static void shuffle(int[] a) {int n = a.length - 1; for(int i = 0; i < n; ++i) {int ind = randInt(i, n); int swap = a[i]; a[i] = a[ind]; a[ind] = swap;}}
    static void shuffle(long[] a) {int n = a.length - 1; for(int i = 0; i < n; ++i) {int ind = randInt(i, n); long swap = a[i]; a[i] = a[ind]; a[ind] = swap;}}
    static void shuffle(double[] a) {int n = a.length - 1; for(int i = 0; i < n; ++i) {int ind = randInt(i, n); double swap = a[i]; a[i] = a[ind]; a[ind] = swap;}}
    static void shuffle(char[] a) {int n = a.length - 1; for(int i = 0; i < n; ++i) {int ind = randInt(i, n); char swap = a[i]; a[i] = a[ind]; a[ind] = swap;}}
    static <T> void shuffle(T[] a) {int n = a.length - 1; for(int i = 0; i < n; ++i) {int ind = randInt(i, n); T swap = a[i]; a[i] = a[ind]; a[ind] = swap;}}
    static void rsort(int[] a) {shuffle(a); sort(a);}
    static void rsort(long[] a) {shuffle(a); sort(a);}
    static void rsort(double[] a) {shuffle(a); sort(a);}
    static void rsort(char[] a) {shuffle(a); sort(a);}
    static <T> void rsort(T[] a) {shuffle(a); sort(a);}
    // graph util
    static List<List<Integer>> graph(int n) {List<List<Integer>> g = new ArrayList<>(); for(int i = 0; i < n; ++i) g.add(new ArrayList<>()); return g;}
    static void connect(List<List<Integer>> g, int u, int v) {g.get(u).add(v); g.get(v).add(u);}
    static void connectDirected(List<List<Integer>> g, int u, int v) {g.get(u).add(v);};
    // input
    static void r() throws IOException {input = new StringTokenizer(__in.readLine());}
    static int ri() throws IOException {return Integer.parseInt(__in.readLine());}
    static long rl() throws IOException {return Long.parseLong(__in.readLine());}
    static int[] ria(int n) throws IOException {int[] a = new int[n]; input = new StringTokenizer(__in.readLine()); for(int i = 0; i < n; ++i) a[i] = Integer.parseInt(input.nextToken()); return a;}
    static int[] riam1(int n) throws IOException {int[] a = new int[n]; input = new StringTokenizer(__in.readLine()); for(int i = 0; i < n; ++i) a[i] = Integer.parseInt(input.nextToken()) - 1; return a;}
    static long[] rla(int n) throws IOException {long[] a = new long[n]; input = new StringTokenizer(__in.readLine()); for(int i = 0; i < n; ++i) a[i] = Long.parseLong(input.nextToken()); return a;}
    static char[] rcha() throws IOException {return __in.readLine().toCharArray();}
    static String rline() throws IOException {return __in.readLine();}
    static int rni() throws IOException {input = new StringTokenizer(__in.readLine()); return Integer.parseInt(input.nextToken());}
    static int ni() {return Integer.parseInt(input.nextToken());}
    static long rnl() throws IOException {input = new StringTokenizer(__in.readLine()); return Long.parseLong(input.nextToken());}
    static long nl() {return Long.parseLong(input.nextToken());}
    // output
    static void pr(int i) {__out.print(i);}
    static void prln(int i) {__out.println(i);}
    static void pr(long l) {__out.print(l);}
    static void prln(long l) {__out.println(l);}
    static void pr(double d) {__out.print(d);}
    static void prln(double d) {__out.println(d);}
    static void pr(char c) {__out.print(c);}
    static void prln(char c) {__out.println(c);}
    static void pr(char[] s) {__out.print(new String(s));}
    static void prln(char[] s) {__out.println(new String(s));}
    static void pr(String s) {__out.print(s);}
    static void prln(String s) {__out.println(s);}
    static void pr(Object o) {__out.print(o);}
    static void prln(Object o) {__out.println(o);}
    static void prln() {__out.println();}
    static void pryes() {__out.println("yes");}
    static void pry() {__out.println("Yes");}
    static void prY() {__out.println("YES");}
    static void prno() {__out.println("no");}
    static void prn() {__out.println("No");}
    static void prN() {__out.println("NO");}
    static void pryesno(boolean b) {__out.println(b ? "yes" : "no");};
    static void pryn(boolean b) {__out.println(b ? "Yes" : "No");}
    static void prYN(boolean b) {__out.println(b ? "YES" : "NO");}
    static void prln(int... a) {for(int i = 0, len = a.length - 1; i < len; __out.print(a[i]), __out.print(' '), ++i); __out.println(a[a.length - 1]);}
    static void prln(long... a) {for(int i = 0, len = a.length - 1; i < len; __out.print(a[i]), __out.print(' '), ++i); __out.println(a[a.length - 1]);}
    static <T> void prln(Collection<T> c) {int n = c.size() - 1; Iterator<T> iter = c.iterator(); for(int i = 0; i < n; __out.print(iter.next()), __out.print(' '), ++i); if(n >= 0) __out.println(iter.next());}
    static void h() {__out.println("hlfd");}
    static void flush() {__out.flush();}
    static void close() {__out.close();}
}