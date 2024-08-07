import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Firеfly on 1/1/2017.
 */
public class Task {
    public static void main(String[] args) throws IOException {
        Emaxx emaxx = new Emaxx();
    }
}


class Emaxx {
    FScanner fs;
    PrintWriter pw;
    Emaxx() throws IOException {
        fs = new FScanner(new InputStreamReader(System.in));
        // pw = new PrintWriter(new FileWriter("arithnumbers.out"));
        //  fs = new FScanner(new FileReader("C:\\Users\\Firеfly\\Desktop\\New Text Document.txt"));
        //     fs = new FScanner(new FileReader("input.txt"));;
        //       pw = new PrintWriter("output.txt");
        pw = new PrintWriter(System.out); //PABOTAI, BITARD
        int n = fs.nextInt();
        int k =fs.nextInt();
        int[] mas = new int[n];
        for (int i =0 ; i<n; i++)
            mas[i] = fs.nextInt();
        Arrays.sort(mas);
        int uniq = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i<n; i++) {
            if (map.containsKey(mas[i]))
                map.put(mas[i], map.get(mas[i]) + 1);
            else {
                map.put(mas[i], 1);
                uniq++;
            }
        }
        int[] copy = new int[uniq];
        copy[0] = mas[0];
        int j = 1;
        long answer = 0;
        for (int i = 1; i<n; i++) {
            if (mas[i]!=mas[i-1])
                copy[j++] = mas[i];
        }
        for (int i = 0; i<uniq; i++) {
            for (int g = i+1; g<uniq; g++) {
                if (Integer.bitCount(copy[g]^copy[i])==k)
                    answer+=map.get(copy[g])*map.get(copy[i]);
            }
            if (k == 0) {
                answer+=map.get(copy[i])*(map.get(copy[i])-1)/2;;
            }
        }
        pw.println(answer);
        pw.close();
    }
    char addPoint(Integer count, int x, int y, int k, char[][] field, char[] answer) {
        if ((count+1)*2>k)
            return 'E';
        else {
            if (x !=field.length-1 && field[x+1][y] == '.') {
                answer[count] = 'D';
                answer[answer.length-count-1] = 'U';
                return 'D';
            }
            else if (y !=0 && field[x][y-1] == '.') {
                answer[count] = 'L';
                answer[answer.length-1-count] = 'R';
                return 'L';
            } else if (y !=field[0].length-1 && field[x][y+1] == '.') {
                answer[count] = 'R';
                answer[answer.length-1-count] = 'L';
                return 'R';
            } else if (x !=0 && field[x-1][y] == '.') {
                answer[count] = 'U';
                answer[answer.length-1-count] = 'D';
                return 'U';
            } else {
                return 'E';
            }
        }
    }
}


class FScanner {
    BufferedReader br;
    StringTokenizer st;
    FScanner(InputStreamReader isr) {
        br = new BufferedReader(isr);
    }

    FScanner(FileReader fr) {
        br = new BufferedReader(fr);
    }

    String nextToken() throws IOException{
        while (st == null || !st.hasMoreTokens()) {
            String s = br.readLine();
            if (s == null)
                return null;
            st = new StringTokenizer(s);
        }
        return st.nextToken();
    }

    int nextInt() throws IOException {
        return Integer.parseInt(nextToken());
    }

    long nextLong() throws IOException {
        return Long.parseLong(nextToken());
    }

    double nextDouble() throws IOException {
        return Double.parseDouble(nextToken());
    }

    String nextLine() throws IOException {
        return br.readLine();
    }

    char nextChar() throws IOException {
        return (char) br.read();
    }

}

class Pair implements Comparable<Pair> {
    int x, y;
    Pair(int x1, int y1) {
        x = x1;
        y = y1;
    }

    @Override
    public int compareTo(Pair o) {
        return Integer.compare(x, o.x);
    }
}