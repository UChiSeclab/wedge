import java.io.*;
import java.util.*;

public class Main {
    public static void main(String args[]) throws IOException {
        Reader in = new Reader();
        Writer out = new Writer();
        int n = in.nextInt();
        int k = in.nextInt();
        int arr[][] = new int[10001][14];
        for (int i = 0; i < n; i++) {
            arr[in.nextInt()][0]++;
        }
        for (int i = 0; i < 10001; i++) {
            int a = i;
            int b = 2;
            for (int j = 0; j < 14; j++) {
                if (a % 2 == 1) {
                    for (int l = 1; l < 14; l++) {
                        arr[i][l] += arr[i - b / 2][l - 1];
                    }
                }
                b *= 2;
                a = a >> 1;
            }
        }
        int ans = 0;
        if (k != 0) {
            for (int i = 0; i < 10001; i++) {
                ans += arr[i][k] * arr[i][0];
            }
        } else {
            for (int i = 0; i < 10001; i++) {
                ans += arr[i][0];
            }
        }
        out.print(ans);
        out.close();
    }

}

class Reader {
    BufferedReader in;

    Reader(String a) throws IOException {
        in = new BufferedReader(new FileReader(a));
    }

    Reader() throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    StringTokenizer tokin = new StringTokenizer("");

    void updtok() throws IOException {
        if (!tokin.hasMoreTokens()) {
            String a = in.readLine();
            if (a != null) {
                tokin = new StringTokenizer(a);
            }
        }
    }


    String next() throws IOException {
        updtok();
        return tokin.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
}

class Writer {
    PrintWriter out;

    Writer() throws IOException {
        out = new PrintWriter(System.out);
    }

    Writer(String name) throws IOException {
        out = new PrintWriter(new FileWriter(name));
    }

    StringBuilder sout = new StringBuilder();

    void print(Object a) {
        sout.append(a);
    }

    void close() {
        out.print(sout.toString());
        out.close();
    }
}