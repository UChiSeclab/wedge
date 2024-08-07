import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Resolve of Task133E on Codeforces.
 * https://codeforces.com/contest/133/problem/E?locale=ru
 *
 * @author Kirill
 * @since 03.05.2021
 */
public class Task133E {
    /**
     * Точка входа.
     *
     * @param args параметры
     * @throws IOException из resolve()
     */
    public static void main(String[] args) throws IOException {
        resolve();
    }

    private static void resolve() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String commands = bufferedReader.readLine();
        int needChange = Integer.parseInt(bufferedReader.readLine());
        int startPosition = commands.length() + 1;
        int maxRowLength = commands.length() * 2 + 1;
        boolean[][][][] isPossibleToGet = new boolean[startPosition][needChange + 1][maxRowLength + 2][2];
        processMovingWithoutChange(isPossibleToGet, commands, startPosition);

        for (int i = 1; i < isPossibleToGet.length; i++) {
            for (int j = 1; j < isPossibleToGet[i].length; j++) {
                for (int k = 1; k < maxRowLength + 1; k++) {
                    int tmpJ = j;
                    if (commands.charAt(i - 1) == 'T') {
                        while (tmpJ >= 0) {
                            if (isPossibleToGet[i - 1][tmpJ][k][1] || (tmpJ > 0 && isPossibleToGet[i - 1][tmpJ - 1][k - 1][0])) {
                                isPossibleToGet[i][j][k][0] = true;
                            }

                            if (isPossibleToGet[i - 1][tmpJ][k][0] || (tmpJ > 0 && isPossibleToGet[i - 1][tmpJ - 1][k + 1][1])) {
                                isPossibleToGet[i][j][k][1] = true;
                            }

                            tmpJ -= 2;
                        }
                    } else {
                        while (tmpJ >= 0) {
                            if (isPossibleToGet[i - 1][tmpJ][k - 1][0] || (tmpJ > 0 && isPossibleToGet[i - 1][tmpJ - 1][k][1])) {
                                isPossibleToGet[i][j][k][0] = true;
                            }

                            if (isPossibleToGet[i - 1][tmpJ][k + 1][1] || (tmpJ > 0 && isPossibleToGet[i - 1][tmpJ - 1][k][0])) {
                                isPossibleToGet[i][j][k][1] = true;
                            }

                            tmpJ -= 2;
                        }
                    }
                }
            }
        }

        System.out.println(calculateMaxDistance(isPossibleToGet, maxRowLength, startPosition));
    }

    /**
     * Заполняет начальные значения (число замен равно 0).
     *
     * @param isPossibleToGet матрица доступных позиций
     * @param commands последовательность команд
     * @param startPosition начальная позиция
     */
    private static void processMovingWithoutChange(boolean[][][][] isPossibleToGet, String commands, int startPosition) {
        isPossibleToGet[0][0][startPosition][0] = true;

        int currentDistance = 0;
        int currentDirection = 0;
        for (int i = 0; i < commands.length(); i++) {
            if (commands.charAt(i) == 'T') {
                currentDirection = Math.abs(currentDirection - 1);
            } else {
                if (currentDirection == 0) {
                    currentDistance++;
                } else {
                    currentDistance--;
                }
            }

            isPossibleToGet[i + 1][0][currentDistance + startPosition][currentDirection] = true;
        }
    }

    private static int calculateMaxDistance(boolean[][][][] isPossibleToGet, int maxRowLength, int startPosition) {
        return Math.max(calculateMaxLeftDistance(isPossibleToGet, startPosition), calculateMaxRightDistance(isPossibleToGet, maxRowLength, startPosition));
    }

    private static int calculateMaxLeftDistance(boolean[][][][] isPossibleToGet, int startPosition) {
        int maxDistance = Integer.MIN_VALUE;
        for (int i = 0; i <= startPosition; i++) {
            if (isPossibleToGet[isPossibleToGet.length - 1][isPossibleToGet[0].length - 1][i][0] ||
                    isPossibleToGet[isPossibleToGet.length - 1][isPossibleToGet[0].length - 1][i][1]) {
                maxDistance = startPosition - i;
                break;
            }
        }

        return maxDistance;
    }

    private static int calculateMaxRightDistance(boolean[][][][] isPossibleToGet, int maxRowLength, int startPosition) {
        int maxDistance = Integer.MIN_VALUE;
        for (int i = maxRowLength; i > startPosition; i--) {
            if (isPossibleToGet[isPossibleToGet.length - 1][isPossibleToGet[0].length - 1][i][0] ||
                    isPossibleToGet[isPossibleToGet.length - 1][isPossibleToGet[0].length - 1][i][1]) {
                maxDistance = i - startPosition;
                break;
            }
        }

        return maxDistance;
    }
}
