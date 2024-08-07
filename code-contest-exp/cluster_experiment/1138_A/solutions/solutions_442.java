import java.io.*;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int n = scanner.nextInt();
        int count = 2;
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String[] sitems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        int eelCounter = 0;
        int tunaCounter = 0;
        int prev = 0;
        int prevElkCounter = 0;
        int prevTunaCounter = 0;
        for (int i = 0; i < n; i++) {
            int item = Integer.parseInt(sitems[i]);
            if (prev != item) {
                if (prev == 1) {
                    prevElkCounter = eelCounter;
                    eelCounter = 0;
                } else if (prev == 2) {
                    prevTunaCounter = tunaCounter;
                    tunaCounter = 0;
                }
            }


            if (item == 1) {
                eelCounter++;
            } else if (item == 2) {
                tunaCounter++;
            }

            if (prevTunaCounter >= eelCounter) {
                if (count < 2 * eelCounter) {
                    count = 2 * eelCounter;
                }
            }

            if (prevElkCounter >= tunaCounter) {
                if (count < 2 * tunaCounter) {
                    count = 2 * tunaCounter;
                }
            }

            prev = item;
        }

        System.out.print(count);

        scanner.close();
    }

}
