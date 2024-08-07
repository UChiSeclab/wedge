import javafx.util.Pair;
import sun.misc.IOUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

public class Main {
    private static int F(int x) {
        return 2*(x*x-4)*(x*x-4)+7;
    }




    public static void main(String[] argv) throws IOException {
        Parser in = new Parser(System.in);
        PrintWriter out = new PrintWriter(System.out);
        //Scanner in = new Scanner(new File("point2.in"));
        //PrintWriter out = new PrintWriter(new File("point2.out"));
        int n = in.nextInt();
        int k = in.nextInt();
        int[] mas = new int[10005];
        for(int i = 0; i < n; i++) {
           mas[in.nextInt()]++;
        }
        int sum = 0;
        if(k == 0){
            for(int i = 0; i <= 10000; i++)
                if(mas[i] != 0) sum += (mas[i]*(mas[i]-1))/2;
        }
        for(int i = 0; i <= 10000; i++)
            for(int j = i+1; j <= 10000; j++) {
                if (mas[i] == 0) break;
                if (mas[j] != 0) {
                    if (Integer.bitCount(i^j) == k) sum += mas[i] * mas[j];
                }
            }
            out.println(sum);
        out.close();
    }
}
class Parser {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public Parser(InputStream in) {
        din = new DataInputStream(in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead =  0;
    }
    public String nextString(int maxSize) {
        byte[] ch = new byte[maxSize];
        int point =  0;
        try {
            byte c = read();
            while (c == ' ' || c == '\n' || c=='\r')
                c = read();
            while (c != ' ' && c != '\n' && c!='\r') {
                ch[point++] = c;
                c = read();
            }
        } catch (Exception e) {}
        return new String(ch,0,point);
    }
    public int nextInt() {
        int ret =  0;
        boolean neg;
        try {
            byte c = read();
            while (c <= ' ')
                c = read();
            neg = c == '-';
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
                c = read();
            } while (c > ' ');

            if (neg) return -ret;
        } catch (Exception e) {}
        return ret;
    }
    public long nextLong() {
        long ret =  0;
        boolean neg;
        try {
            byte c = read();
            while (c <= ' ')
                c = read();
            neg = c == '-';
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
                c = read();
            } while (c > ' ');

            if (neg) return -ret;
        } catch (Exception e) {}
        return ret;
    }
    private void fillBuffer() {
        try {
            bytesRead = din.read(buffer, bufferPointer =  0, BUFFER_SIZE);
        } catch (Exception e) {}
        if (bytesRead == -1) buffer[ 0] = -1;
    }

    private byte read() {
        if (bufferPointer == bytesRead) fillBuffer();
        return buffer[bufferPointer++];
    }
}