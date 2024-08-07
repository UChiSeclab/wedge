import java.io.*;
import java.lang.reflect.Array;
import java.math.*;
import java.util.*;
import java.util.stream.*;
import java.util.regex.*;
import java.text.*;

public class Solution {

    public static void main(String[] args) throws Exception {
        MyReader reader = new MyReader(System.in);
        MyWriter writer = new MyWriter(System.out);
        int n = reader.nextInt();
        int k = reader.nextInt();
        long[] a = new long[1 << 14];
        for (int i = 0; i < n; i++) {
            a[reader.nextInt()]++;
        }
        int[] c = new int[k + 1];
        for (int i = 0; i < k; i++) {
            c[i] = i;
        }
        c[k] = 14;
        long sum = 0;
        while (true) {
            int b = 0;
            for (int i = 0; i < k; i++) {
                b |= 1 << c[i];
            }
            for (int i = 0; i <= 10000; i++) {
                sum += a[i] * a[i ^ b];
            }
            int j = k - 1;
            while (j >= 0 && c[j + 1] - c[j] == 1) {
                j--;
            }
            if (j < 0) {
                break;
            } else {
                c[j++]++;
                while (j < k) {
                    c[j] = c[j - 1] + 1;
                    j++;
                }
            }
        }
        if (k == 0) {
            for (int i = 0; i <= 10000; i++) {
                sum -= a[i];
            }
        }
        writer.print(sum / 2);
        writer.close();
    }

    static class MyReader {

        final BufferedInputStream in;
        final int bufSize = 1 << 16;
        final byte buf[] = new byte[bufSize];
        int i = bufSize;
        int k = bufSize;
        final StringBuilder str = new StringBuilder();

        MyReader(InputStream in) {
            this.in = new BufferedInputStream(in, bufSize);
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }

        int[] nextIntArray(int n) throws IOException {
            int[] m = new int[n];
            for (int i = 0; i < n; i++) {
                m[i] = nextInt();
            }
            return m;
        }

        int[][] nextIntMatrix(int n, int m) throws IOException {
            int[][] a = new int[n][0];
            for (int j = 0; j < n; j++) {
                a[j] = nextIntArray(m);
            }
            return a;
        }

        long nextLong() throws IOException {
            int c;
            long x = 0;
            boolean sign = true;
            while ((c = nextChar()) <= 32) ;
            if (c == '-') {
                sign = false;
                c = nextChar();
            }
            while (c >= '0') {
                x = x * 10 + (c - '0');
                c = nextChar();
            }
            return sign ? x : -x;
        }

        long[] nextLongArray(int n) throws IOException {
            long[] m = new long[n];
            for (int i = 0; i < n; i++) {
                m[i] = nextLong();
            }
            return m;
        }

        String nextString() throws IOException {
            int c;
            str.setLength(0);
            while ((c = nextChar()) <= 32 && c != -1) ;
            if (c == -1) {
                return null;
            }
            while (c > 32) {
                str.append((char) c);
                c = nextChar();
            }
            return str.toString();
        }

        String[] nextStringArray(int n) throws IOException {
            String[] m = new String[n];
            for (int i = 0; i < n; i++) {
                m[i] = nextString();
            }
            return m;
        }

        int nextChar() throws IOException {
            if (i == k) {
                k = in.read(buf, 0, bufSize);
                i = 0;
            }
            return i >= k ? -1 : buf[i++];
        }
    }

    static class MyWriter {

        final BufferedOutputStream out;
        final int bufSize = 1 << 16;
        final byte buf[] = new byte[bufSize];
        int i = 0;
        final byte c[] = new byte[30];
        static final String newLine = System.getProperty("line.separator");

        MyWriter(OutputStream out) {
            this.out = new BufferedOutputStream(out, bufSize);
        }

        void print(long x) throws IOException {
            int j = 0;
            if (i + 30 >= bufSize) {
                flush();
            }
            if (x < 0) {
                buf[i++] = (byte) ('-');
                x = -x;
            }
            while (j == 0 || x != 0) {
                c[j++] = (byte) (x % 10 + '0');
                x /= 10;
            }
            while (j-- > 0)
                buf[i++] = c[j];
        }

        void print(int[] m) throws Exception {
            for (int a : m) {
                print(a);
                print(' ');
            }
        }

        void print(long[] m) throws Exception {
            for (long a : m) {
                print(a);
                print(' ');
            }
        }

        void print(String s) throws IOException {
            for (int i = 0; i < s.length(); i++) {
                print(s.charAt(i));
            }
        }

        void print(char x) throws IOException {
            if (i == bufSize) {
                flush();
            }
            buf[i++] = (byte) x;
        }

        void print(char[] m) throws Exception {
            for (char c : m) {
                print(c);
            }
        }

        void println(String s) throws IOException {
            print(s);
            println();
        }

        void println() throws IOException {
            print(newLine);
        }

        void flush() throws IOException {
            out.write(buf, 0, i);
            i = 0;
        }

        void close() throws IOException {
            flush();
            out.close();
        }
    }
}