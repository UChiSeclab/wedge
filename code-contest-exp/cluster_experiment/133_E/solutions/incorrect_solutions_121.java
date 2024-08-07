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
    public static void main(String[] args) throws IOException {
        Task133E task = new Task133E();
        task.resolve();
    }

    private void resolve() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String commands = bufferedReader.readLine();
        int needChange = Integer.parseInt(bufferedReader.readLine());
        int startPosition = commands.length() + 1;
        int maxRowLength = commands.length() * 2 + 1;
        boolean[][][][] isPossibleToGet = new boolean[startPosition][needChange + 1][maxRowLength + 2][2];
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

        for (int i = 1; i < isPossibleToGet.length; i++) {
            for (int j = 1; j < isPossibleToGet[i].length; j++) {
                for (int k = 1; k < maxRowLength + 1; k++) {
                    if (commands.charAt(i - 1) == 'T') {
                        isPossibleToGet[i][j][k][0] = isPossibleToGet[i - 1][j][k][1] || isPossibleToGet[i - 1][j - 1][k - 1][0];
                        isPossibleToGet[i][j][k][1] = isPossibleToGet[i - 1][j][k][0] || isPossibleToGet[i - 1][j - 1][k + 1][1];
                    } else {
                        isPossibleToGet[i][j][k][0] = isPossibleToGet[i - 1][j - 1][k][1] || isPossibleToGet[i - 1][j][k - 1][0];
                        isPossibleToGet[i][j][k][1] = isPossibleToGet[i - 1][j - 1][k][0] || isPossibleToGet[i - 1][j][k + 1][1];
                    }
                }
            }
        }

        int maxDistance = Integer.MIN_VALUE;
        for (int i = 0; i <= startPosition; i++) {
            if (isPossibleToGet[isPossibleToGet.length - 1][isPossibleToGet[0].length - 1][i][0] ||
                    isPossibleToGet[isPossibleToGet.length - 1][isPossibleToGet[0].length - 1][i][1]) {
                maxDistance = startPosition - i;
                break;
            }
        }

        for (int i = maxRowLength; i > startPosition; i--) {
            if (isPossibleToGet[isPossibleToGet.length - 1][isPossibleToGet[0].length - 1][i][0] ||
                    isPossibleToGet[isPossibleToGet.length - 1][isPossibleToGet[0].length - 1][i][1]) {
                maxDistance = i - startPosition;
                break;
            }
        }

        System.out.println(maxDistance);
    }
}
