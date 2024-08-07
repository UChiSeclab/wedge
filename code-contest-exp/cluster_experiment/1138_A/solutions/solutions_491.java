import java.util.Scanner;

public class SushiForTwo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] freq = new int[3];

        int[] seq = new int[n];

        // Read first elem first
        seq[0] = sc.nextInt();
        freq[seq[0]]++;

        int maxSeqLength = 0;

        int temp = -1;

        for (int i = 1; i < seq.length; ++i) {
            seq[i] = sc.nextInt();

            if (seq[i] != seq[i - 1]) {
                freq[seq[i]] = 0;
            }

            freq[seq[i]]++;

            temp = Math.min(freq[1], freq[2]) * 2;

            maxSeqLength = Math.max(maxSeqLength, temp);
        }

        System.out.println(maxSeqLength);
    }
}
