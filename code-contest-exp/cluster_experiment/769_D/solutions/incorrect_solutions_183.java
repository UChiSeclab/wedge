import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int t = 0;
        int[] s = new int[2];
        for (int i = 0; i < 2; i++)
            s[i] = in.nextInt();

        int[] m = new int[s[0]];
        for (int i = 0; i < s[0]; i++)
            m[i] = in.nextInt();

        for (int i = 0; i < s[0]; i++) {
            for (int j = i + 1; j < s[0]; j++) {
                if (Integer.bitCount(m[i] ^ m[j]) == s[1]) {
                    if (s[1] == 0) {
                        t += (m[i]) * (m[i] - 1);
                    } else {
                        t += m[i] * m[j];
                    }
                }
            }
        }
        System.out.println(t / 2);
}}

