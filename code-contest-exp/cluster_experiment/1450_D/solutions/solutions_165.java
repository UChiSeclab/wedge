import java.io.*;
import java.util.*;
public class Main {
    InputReader in;
    final long mod=1000000007;
    StringBuilder sb;
    public static void main(String[] args) throws java.lang.Exception {
        new Main().run();
    }
    void run() throws Exception {
        in=new InputReader(System.in);
        sb = new  StringBuilder();        
        int t=i();
        for(int i=1;i<=t;i++)
            solve();
        System.out.print(sb);
    }
    void solve() {
        int i, j, k=0;
        int n=i();
        int a[]=new int[n];
        int cnt[]=new int[n+5];
        int ans[]=new int[n+1];
        for(i=0;i<n;i++) {
            a[i]=i();
            a[i]--;
            cnt[a[i]]++;
        }
        int x=0;
        while(cnt[x]==1)
            x++;
        if(x==n)
            ans[0]=1;
        int l=0, r=n-1;
        if(cnt[0]>0)
            ans[n-1]=1;
        for(i=n-1;i>0;i--) {
            if(ans[n-1]==0)
                break;
            ans[i]=1;
            int next=n-i-1;
            if(cnt[next]==1 && (a[l]==next || a[r]==next) && cnt[next+1]>0) {
                if(a[l]==next)
                    l++;
                if(a[r]==next)
                    r--;
                continue;
            }       
            break;
        }
        for(i=0;i<n;i++)
            sb.append(ans[i]);
        sb.append("\n");
    }
    /*int moreq(long a[], long k) {
        int l=0, r=a.length-1, ans=-1;
        while(l<=r) {
            int mid=(l+r)/2;
            if(a[mid]>=k) {
                r=mid-1;
                ans=mid;
            }
            else
                l=mid+1;
        }
        return ans;
    }
    int leseq(long a[], long k) {
        int l=0, r=a.length-1, ans=-1;
        while(l<=r) {
            int mid=(l+r)/2;
            if(a[mid]>k)
                r=mid-1;
            else {
                l=mid+1;
                ans=mid;
            }
        }
        return ans;
    }
    void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    void sort(int arr[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }
    void reverse(long a[]) {
        for(int i=0;i<a.length/2;i++) {
            long z=a[i];
            a[i]=a[a.length-i-1];
            a[a.length-i-1]=z;
        }
    }
    long mul(long x, long y) {long ans = x*y;return (ans>=mod ? ans%mod : ans);}
    long power(long x, long y, long p) {  
        long res = 1;       
        x = x % p;  
        if (x == 0) return 0;   
        while (y > 0) {
            if((y & 1)==1) 
                res = (res * x) % p;  
            y = y >> 1;  
            x = (x * x) % p;  
        } 
        return res%p; 
    } 
    long gcd(long a, long b) { return (b==0)?a:gcd(b,a%b); }
    int gcd(int a, int b) { return (b==0)?a:gcd(b,a%b); }
    String s() { return in.next(); }*/
    int i() { return in.nextInt(); }
    long l() {return in.nextLong();}
    class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        public InputReader(InputStream stream) {
            this.stream = stream;
        }
        public int read() {
            if (numChars == -1)
                throw new UnknownError();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new UnknownError();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }
        public int peek() {
            if (numChars == -1)
                return -1;
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    return -1;
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar];
        }
        public void skip(int x) {
            while (x-->0)
                read();
        }
        public int nextInt() {
            return Integer.parseInt(next());
        }
        public long nextLong() {
            return Long.parseLong(next());
        }
        public String nextString() {
            return next();
        }
        public String next() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuffer res = new StringBuffer();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
 
            return res.toString();
        }
        public String nextLine() {
            StringBuffer buf = new StringBuffer();
            int c = read();
            while (c != '\n' && c != -1) {
                if (c != '\r')
                    buf.appendCodePoint(c);
                c = read();
            }
            return buf.toString();
        }
        public double nextDouble() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                if (c == 'e' || c == 'E')
                    return res * Math.pow(10, nextInt());
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.') {
                c = read();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E')
                        return res * Math.pow(10, nextInt());
                    if (c < '0' || c > '9')
                        throw new InputMismatchException();
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }
        public boolean hasNext() {
            int value;
            while (isSpaceChar(value = peek()) && value != -1)
                read();
            return value != -1;
        }
        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }
    }
}