import java.util.Scanner;

public class SushiForTwo {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int piecesCount = in.nextInt(),
                prevPiece, currPiece = in.nextInt(), counter_1 = 1, counter_2 = 0, max = 0;

        for (int i = 1; i < piecesCount; i++) {
            prevPiece = currPiece;
            currPiece = in.nextInt();

            if (currPiece == prevPiece) {
                counter_1++;
                if (counter_1 == counter_2) {
                    if (counter_2 * 2 > max) {
                        max = counter_2 * 2;
                    }
                }
            } else {
                if (max == 0) {
                    max = 2;
                }
                counter_2 = counter_1;
                counter_1 = 1;
            }
        }

        System.out.println(max);
    }

}
