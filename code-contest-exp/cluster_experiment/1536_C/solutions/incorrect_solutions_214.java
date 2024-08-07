import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class contest1536 {
    public static void main(String[] args) {
//        omkarAndBadStory();
        dilucAndKaeya();
        pw.close();
    }

    static void dilucAndKaeya() {
        int T = readInt();
        while (T-- > 0) {
            int n = readInt();
            char[] cs = readLine().toCharArray();
            int[] pCount = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                pCount[i] = pCount[i - 1] + (cs[i - 1] == 'D' ? 1 : 0);
            }
            int[] maxSplit = new int[n + 1];
            Arrays.fill(maxSplit, 1);
            for (int i = 1; i <= n / 2; i++) {
                int iCount = pCount[i];
                for (int s = 2, len = s * i; len <= n; s++, len += i) {
                    if (iCount * s == pCount[len] && maxSplit[len] == 1) maxSplit[len] = s;
                }
            }
            for (int i = 1; i <= n; i++) {
                pw.print(maxSplit[i] + " ");
            }
            pw.println();
        }
    }

    static void prinzessinderVerurteilung() {
        int T = readInt();
        NEXT_TEST:
        while (T-- > 0) {
            int n = readInt();
            int[] cs = readLine().chars().map(x -> x - 'a').toArray();
            boolean[] allChars = new boolean[26];
            for (int i = 0; i < n; i++) allChars[cs[i]] = true;
            for (int i = 0; i < 26; i++) {
                if (!allChars[i]) {
                    pw.println((char) ('a' + i));
                    continue NEXT_TEST;
                }
            }
            boolean[][] all2Chars = new boolean[26][26];
            for (int i = 1; i < n; i++) {
                all2Chars[cs[i - 1]][cs[i]] = true;
            }
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    if (!all2Chars[i][j]) {
                        pw.println((char) ('a' + i) + "" + (char) ('a' + j));
                        continue NEXT_TEST;
                    }
                }
            }
            boolean[][][] all3Chars = new boolean[2][26][26];
            for (int i = 2; i < n; i++) {
                if (cs[i - 2] < 2) {
                    all3Chars[cs[i - 2]][cs[i - 1]][cs[i]] = true;
                }
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 26; j++) {
                    for (int k = 0; k < 26; k++) {
                        if (!all3Chars[i][j][k]) {
                            pw.println((char) ('a' + i) + "" + (char) ('a' + j) + "" + (char) ('a' + k));
                            continue NEXT_TEST;
                        }
                    }
                }
            }
        }
    }

    static void omkarAndBadStory() {
        int T = readInt();
        NEXT_TEST:
        while (T-- > 0) {
            int n = readInt();
            int[] s = readIntArray(n);
            int max = -1;
            for (int i = 0; i < n; i++) {
                if (s[i] < 0) {
                    pw.println("NO");
                    continue NEXT_TEST;
                }
                max = Math.max(max, s[i]);
            }
            pw.println("YES");
            pw.println(max + 1);
            for (int i = 0; i <= max; i++) {
                pw.print(i + " ");
            }
            pw.println();
        }
    }

    // @formatter:off
    static final BufferedReader br=new BufferedReader(new InputStreamReader(System.in));static final PrintWriter pw=new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));static StringTokenizer st;static final String READ_ERROR="Error Reading.";static String readToken(){try{while(st==null||!st.hasMoreTokens())st=new StringTokenizer(br.readLine());return st.nextToken();}catch(IOException e){throw new RuntimeException(READ_ERROR);}}static int readInt(){return Integer.parseInt(readToken());}static int[]readIntArray(final int n){int[]a=new int[n];for(int i=0;i<n;i++)a[i]=readInt();return a;}static long readLong(){return Long.parseLong(readToken());}static char[]readLineAsCharArray(){try{return br.readLine().toCharArray();}catch(IOException e){throw new RuntimeException(READ_ERROR);}}static String readLine(){try{return br.readLine();}catch(IOException e){throw new RuntimeException(READ_ERROR);}}
}
