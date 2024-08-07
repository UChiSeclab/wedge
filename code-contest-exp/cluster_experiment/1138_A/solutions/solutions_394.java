import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private final static InputReader inputReader = new InputReader(System.in);

    private final static int MAX_SIZE = 100005;

    static int[] continousOneInLeft = new int[MAX_SIZE];
    static int[] continousOneInRight = new int[MAX_SIZE];
    static int[] continousTwoInLeft = new int[MAX_SIZE];
    static int[] continousTwoInRight = new int[MAX_SIZE];

    public static void main(String[] args) throws IOException {
        int numberOfShushi = inputReader.scanInt();
        final List<Integer> shushiTypes = new ArrayList<>();
        for (int loopCounter = 0; loopCounter < numberOfShushi; ++loopCounter) {
            shushiTypes.add(inputReader.scanInt());
        }

        Arrays.fill(continousOneInLeft, 0);
        Arrays.fill(continousOneInRight, 0);
        Arrays.fill(continousTwoInLeft, 0);
        Arrays.fill(continousTwoInRight, 0);

        for (int loopCounter = 0; loopCounter < numberOfShushi; ++loopCounter) {
            int leftSumForOne = 0;
            int leftSumForTwo = 0;
            if (loopCounter - 1 >= 0) {
                leftSumForOne = continousOneInLeft[loopCounter - 1];
                leftSumForTwo = continousTwoInLeft[loopCounter - 1];
            }

            if (shushiTypes.get(loopCounter) == 1) {
                continousOneInLeft[loopCounter] = leftSumForOne + 1;
                continousTwoInLeft[loopCounter] = 0;
            } else {
                continousOneInLeft[loopCounter] = 0;
                continousTwoInLeft[loopCounter] = leftSumForTwo + 1;
            }
        }

        for (int loopCounter = numberOfShushi - 1; loopCounter >= 0; --loopCounter) {
            int rightSumForOne = 0;
            int rightSumForTwo = 0;
            if (loopCounter + 1 < numberOfShushi) {
                rightSumForOne = continousOneInRight[loopCounter + 1];
                rightSumForTwo = continousTwoInRight[loopCounter + 1];
            }

            if (shushiTypes.get(loopCounter) == 1) {
                continousOneInRight[loopCounter] = rightSumForOne + 1;
                continousTwoInRight[loopCounter] = 0;
            } else {
                continousOneInRight[loopCounter] = 0;
                continousTwoInRight[loopCounter] = rightSumForTwo + 1;
            }
        }

        int answer = 0;
        for (int loopCounter = 0; loopCounter < numberOfShushi; ++loopCounter) {
            // First Assume [1,2]
            if (loopCounter + 1 < numberOfShushi) {
                answer = Math.max(answer, Math.min(continousOneInLeft[loopCounter], continousTwoInRight[loopCounter + 1]) * 2);
                answer = Math.max(answer, Math.min(continousTwoInLeft[loopCounter], continousOneInRight[loopCounter + 1]) * 2);
            }
        }

        System.out.println(answer);
    }

    static class InputReader {

        private final BufferedInputStream bufferedInputStream;

        private final static char NEGATIVE_CHAR = '-';
        private final static char SPACE_CHAR = ' ';
        private final static char NEW_LINE_CHAR = '\n';
        private final static int END_OF_STREAM_BYTE = -1;

        public InputReader(final InputStream inputStream) {
            bufferedInputStream = new BufferedInputStream(inputStream);
        }

        public int scanInt() throws IOException {
            int currentByte = findFirstUsefulByte();
            boolean isNegative = false;
            if (currentByte == NEGATIVE_CHAR) {
                isNegative = true;
                currentByte = bufferedInputStream.read();
            }

            int number = 0;
            while (isUsefulByte(currentByte)) {
                number = (number * 10) + (currentByte - '0');
                currentByte = bufferedInputStream.read();
            }

            return isNegative ? -number : number;
        }

        private int findFirstUsefulByte() throws IOException {
            int currentByte = bufferedInputStream.read();
            while (!isUsefulByte(currentByte)) {
                currentByte = bufferedInputStream.read();
            }

            return currentByte;
        }

        private boolean isUsefulByte(final int currentByte) {
            if (currentByte == SPACE_CHAR || currentByte == NEW_LINE_CHAR || currentByte == '\r' || currentByte == '\t' || currentByte == -1) {
                return false;
            } else {
                return true;
            }
        }
    }
}
