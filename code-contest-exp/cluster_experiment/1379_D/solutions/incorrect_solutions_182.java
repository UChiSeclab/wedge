import java.io.*;
import java.lang.Math;
import java.util.*;

public class Main  {

    public BufferedReader in;
    public PrintStream out;

    public boolean log_enabled = false;
    
    public boolean multiply_tests = false;

    private class TestCase {

        public Object solve() {
            
            int N = readInt(), H = readInt(), M = readInt(), K = readInt(), M2 = M / 2;
            
            
            int i,h,m;
            
            int[] inc = new int[N+1];
            int[] dec = new int[N+1];
            
            int[] _inc = new int[N];
            int[] _dec = new int[N];
            
            
            
            int z = 0;
            
            for (i=0; i<N; i++)
            {
                h = readInt();
                m = readInt() % M2;
                
                inc[i] = (m+1) % M2;
                dec[i] = (m+K) % M2;
                
                _inc[i] = inc[i];
                _dec[i] = dec[i];
                
                if ((inc[i] > dec[i])&&(dec[i]>0))
                {
                    z++;
                }
            }
            
            if (K==1) return "0 0";
            
            Arrays.sort(inc);
            Arrays.sort(dec);
            
            /*int inc_n = 0;
            for (i=1; i<N; i++)
            {
                if (inc[i]!=inc[inc_n])
                {
                    inc[++inc_n] = inc[i];
                }
            }
            inc_n ++;
            
            int dec_n = 0;
            for (i=1; i<N; i++)
            {
                if (dec[i]!=dec[dec_n])
                {
                    dec[++dec_n] = dec[i];
                }
            }
            dec_n ++;*/
           
            int inc_n = N;
            int dec_n = N;
            
            inc[inc_n] = M2;
            dec[dec_n] = M2;
            
            int mx = z, c, mx_t=0, t = 0;
            
            int inc_idx = 0;
            int dec_idx = 0;
            
            while ((inc_idx < inc_n)||(dec_idx<dec_n))
            {
                t = Math.min( inc[inc_idx], dec[dec_idx] );
                
                while (inc[inc_idx] == t)
                {
                    if (t>0)
                    {
                        z ++;
                    }
                    
                    inc_idx ++;
                }
                
                while (dec[dec_idx] == t)
                {
                    if (t>0)
                    {
                        z --;
                    }
                    dec_idx ++;
                }
                    
                if (z<mx)
                {
                    mx = z;
                    mx_t = t;
                }
                
            }
            
            out.printf("%d %d\n", mx, mx_t);
            boolean b,f = true;
            for (i=0; i<N; i++)
            {
                b = false;
                if (_inc[i] < _dec[i])
                {
                    b = (_inc[i]<=mx_t)&&(mx_t<_dec[i]);
                }
                else
                {
                    b = (mx_t >= _inc[i]) || (mx_t < _dec[i]);
                }
                
                if (b)
                {
                    if (f)
                    {
                        f = false;
                    }
                    else
                    {
                        out.print(" ");
                    }
                    
                    out.print(i+1);
                }
            }
            if (mx<0)
            {
                out.println();
            }
            
            return null;
            
            //return strf("%f", 0);
            
            //out.printf("Case #%d: \n", caseNumber);
            //return null;
        }
        
        public int caseNumber;
        
        TestCase(int number) {
            caseNumber = number;
        }
        
        public void run(){
            Object r = this.solve();
            
            if ((r != null))
            {
                //outputCaseNumber(r);
                out.println(r);
            }
        }
        
        public String impossible(){
            return "IMPOSSIBLE";
        }
        
        public String strf(String format, Object... args)
        {
            return String.format(format, args);
        }
        
//        public void outputCaseNumber(Object r){
//            //out.printf("Case #%d:", caseNumber);
//            if (r != null)
//            {
//              //  out.print(" ");
//                out.print(r);
//            }
//            out.print("\n");
//        }
    }

    public void run() {
        //while (true)
        {
            int t = multiply_tests ?  readInt() : 1;
            for (int i = 0; i < t; i++) {
                TestCase T = new TestCase(i + 1);
                T.run();
            }
        }
    }
    

    
    public Main(BufferedReader _in, PrintStream _out){
        in = _in;
        out = _out;
    }
    

    public static void main(String args[]) {
        Locale.setDefault(Locale.US);
        Main S;
        try {
            S = new Main(
                        new BufferedReader(new InputStreamReader(System.in)),
                        System.out
                );
        } catch (Exception e) {
            return;
        }
        
        S.run();
        
    }

    private StringTokenizer tokenizer = null;

    public int readInt() {
        return Integer.parseInt(readToken());
    }

    public long readLong() {
        return Long.parseLong(readToken());
    }

    public double readDouble() {
        return Double.parseDouble(readToken());
    }

    public String readLn() {
        try {
            String s;
            while ((s = in.readLine()).length() == 0);
            return s;
        } catch (Exception e) {
            return "";
        }
    }

    public String readToken() {
        try {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(in.readLine());
            }
            return tokenizer.nextToken();
        } catch (Exception e) {
            return "";
        }
    }

    public int[] readIntArray(int n) {
        int[] x = new int[n];
        readIntArray(x, n);
        return x;
    }

    public void readIntArray(int[] x, int n) {
        for (int i = 0; i < n; i++) {
            x[i] = readInt();
        }
    }
    
    public long[] readLongArray(int n) {
        long[] x = new long[n];
        readLongArray(x, n);
        return x;
    }

    public void readLongArray(long[] x, int n) {
        for (int i = 0; i < n; i++) {
            x[i] = readLong();
        }
    }

    public void logWrite(String format, Object... args) {
        if (!log_enabled) {
            return;
        }

        out.printf(format, args);
    }
}
