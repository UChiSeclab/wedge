import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by hapsidra on 22.10.2016.
 */
public class Main {
    public static void main(String args[]) {
        int n,k;
        int count[]=new int[10005];
        FastScanner scanner=new FastScanner();
        n=scanner.nextInt();
        k=scanner.nextInt();
        for(int i=0;i<n;i++){
            count[scanner.nextInt()]++;
        }
        long ans = 0;
        if (k == 0) {
            for (int i = 0; i < 10001; i++) {
                ans += count[i] * (count[i] - 1) / 2;
            }
        } else {
            for (int i = 0; i < 10001; i++) {
                for (int j = i + 1; j < 10001; j++) {
                    if (NumberOfSetBits(i ^ j) == k) {
                        ans += count[i] * count[j];
                    }
                }
            }
        }
        System.out.println(ans);
    }
    static int NumberOfSetBits(int i)
    {
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        return (((i + (i >>> 4)) & 0x0F0F0F0F) * 0x01010101) >>> 24;
    }
    static boolean check(int a,int b,int k){
        if(k==0){
            return a==b;
        }

        if(a<b){
            int t=a;
            a=b;
            b=t;
        }
        int s=0;
        while(b>0){
            if(b%2!=a%2){
                s++;
                if(s>k){
                    return false;
                }
            }
            b/=2;
            a/=2;
        }
        while(a>0){
            s+=a%2;
            if(s>k)
                return false;
            a/=2;
        }
        return s==k;
    }
    private static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(String fileName) {
            try {
                br = new BufferedReader(new FileReader(fileName));
            } catch (FileNotFoundException e) {
            }
        }

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String nextToken() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(nextToken());
        }

        long nextLong() {
            return Long.parseLong(nextToken());
        }

        double nextDouble() {
            return Double.parseDouble(nextToken());
        }
    }
}