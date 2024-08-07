


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class A {

    public static void main(String[] args) {

        FastReader in = new FastReader(System.in);
        PrintWriter pw = new PrintWriter(System.out);

        int n = in.nextInt();


        int temp1 = 0;
        int temp2 = 0;
        int prev2 = 1;
        int prev1 = 1;
        int ans = 0;
        boolean past1 = false;
        boolean past2 = false;
        int t = 0;
        for(int i = 0;i<n;i++){
            t= in.nextInt();
            if(t ==  1){
                temp1++;


               if(!past1){
                   past1 = true;
                   past2 = false;
                   prev2 = temp2;
                   int tans = Math.min(prev1,prev2) *2;
//                   pw.println("i "+i+" tans: "+tans+" ans "+ans+" prev1: "+prev1+" prev2: "+prev2);
                   if(tans > ans){
                       ans = tans;
                   }


                   temp2 = 0;
               }

            }
            if(t == 2){
                temp2 ++;

                if(!past2){
                    past2 = true;
                    past1 = false;
                    prev1 = temp1;
                    int tans = Math.min(prev1,prev2) *2;
//                    pw.println("i "+i+" tans: "+tans+" ans "+ans+" prev1: "+prev1+" prev2: "+prev2);
                    if(tans > ans){
                        ans = tans;
                    }

                    temp1 = 0;
                }


            }

            if(t ==2 ){
                prev2 = temp2;
            }
            else
            {
                prev1 = temp1;
            }
            int tans = Math.min(prev1,prev2) *2;
            if(tans > ans){
                ans = tans;
            }



        }


        pw.println(ans);

        pw.close();


    }




    static class FastReader {
        InputStream is;
        private byte[] inbuf = new byte[1024];
        private int lenbuf = 0, ptrbuf = 0;

        public FastReader(InputStream is){
            this.is = is;
        }

        public int readByte(){
            if(lenbuf == -1)throw new InputMismatchException();
            if(ptrbuf >= lenbuf){
                ptrbuf = 0;
                try {
                    lenbuf = is.read(inbuf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if(lenbuf <= 0)return -1;
            }
            return inbuf[ptrbuf++];
        }

        public boolean isSpaceChar(int c) {
            return !(c >= 33 && c <= 126);
        }
        public int skip() {
            int b;
            while((b = readByte()) != -1 && isSpaceChar(b));
            return b;
        }

        public String next(){
            int b = skip();
            StringBuilder sb = new StringBuilder();
            while(!(isSpaceChar(b))){
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }

        public String nextLine(){
            int b = readByte();
            StringBuilder sb = new StringBuilder();
            while(b != '\n' || b != '\r'){
                sb.appendCodePoint(b);
            }
            return sb.toString();
        }

        public int nextInt(){
            int num = 0, b;
            boolean minus = false;
            while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
            if(b == '-'){
                minus = true;
                b = readByte();
            }
            while(true){
                if(b >= '0' && b <= '9'){
                    num = (num<<3) + (num<<1) + (b - '0');
                }else{
                    return minus ? -num : num;
                }
                b = readByte();
            }
        }

        public long nextLong() {
            long num = 0;
            int b;
            boolean minus = false;
            while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
            if(b == '-'){
                minus = true;
                b = readByte();
            }

            while(true){
                if(b >= '0' && b <= '9'){
                    num = (num<<3) + (num<<1) + (b - '0');
                }else{
                    return minus ? -num : num;
                }
                b = readByte();
            }
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] next(int n){
            char[] buf = new char[n];
            int b = skip(), p = 0;
            while(p < n && !(isSpaceChar(b))){
                buf[p++] = (char)b;
                b = readByte();
            }
            return n == p ? buf : Arrays.copyOf(buf, p);
        }

        public char nextChar() {
            return (char)skip();
        }
    }



}
