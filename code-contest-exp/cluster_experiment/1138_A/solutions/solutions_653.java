import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class sushi {

    public static int nextInt(BufferedReader in) throws IOException {
        int neg = 1;
        int ch = in.read();
        while (ch != '-' && (ch < '0' || ch > '9')) {
            ch = in.read();
        }
        if (ch == '-') {
            neg = -1;
            ch = in.read();
        }
        int c = ch - '0';
        ch = in.read();
        while (ch >= '0' && ch <= '9') {
            c = c * 10 + ch - '0';
            ch = in.read();
        }
        return c * neg;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int countPieces = nextInt(input);
        int nOnes = 0, nTwos = 0, maxLen = 0, currentLen = 0;
        int lastPiece = nextInt(input);
        if (lastPiece == 1) {
            nOnes++;
        } else {
            nTwos++;
        }
        for (int i = 0; i < countPieces - 1; i++) {
            int s = nextInt(input);
            if (lastPiece == s) {
                if (s == 1) {
                    nOnes++;
                } else {
                    nTwos++;
                }
            } else {
                if (s == 1) {
                    nOnes = 1;
                    lastPiece = 1;
                } else {
                    nTwos = 1;
                    lastPiece = 2;
                }
            }
            if (nOnes == nTwos) {
                currentLen = nOnes + nTwos;
            } else {
                if (nOnes > nTwos) {
                    currentLen = nTwos * 2;
                } else {
                    currentLen = nOnes * 2;
                }
            }
            if (currentLen > maxLen) {
                maxLen = currentLen;

            }
        }
        System.out.println(maxLen);

    }

}
