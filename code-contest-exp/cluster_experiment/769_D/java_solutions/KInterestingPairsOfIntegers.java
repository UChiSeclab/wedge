import java.io.*;
import java.util.*;
import java.util.stream.*;

public class KInterestingPairsOfIntegers {
    private static boolean debug = false;

    private static int N, K;
    private static int[] A;

    private static void solveProblem(InputStream instr) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(instr)));
        int testCount = 1;
        if (debug) {
            testCount = sc.nextInt();
        }

        for (int t = 1; t <= testCount; t++) {
            printDebug("------ " + t + " ------");
            N = sc.nextInt();
            K = sc.nextInt();
            A = readInts(sc, N);
            Object result = solveTestCase();
            System.out.println(result);
        }
    }

    public static String lpad(String str, int num, char ch) {
        return String.format("%1$" + num + "s", str).replace(' ', ch);
    }

    private static Object solveTestCase() {
        int count = 0;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                int zor = A[i] ^ A[j];
                if (checkCount(zor)) {
                    count++;
                    //      System.out.println(i + "\t" + j);
                }
            }
        }
        return count;
    }

    private static boolean checkCount(int num) {
        int count = 0;
        while (num != 0) {
            if ((num & 1) == 1) {
                count++;
            }
            if (count > num) {
                return false;
            }
            num = num >> 1;
        }
        return count == K;
    }

    private static int[] readInts(Scanner sc, int N) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        return arr;
    }

    private static String joinValues(List<? extends Object> list, String delim) {
        return list.stream().map(Object::toString).collect(Collectors.joining(delim));
    }

    public static void printDebug(Object str) {
        if (debug) {
            System.out.println("DEBUG: " + str);
        }
    }

    public static void main(String[] args) throws Exception {
        long currTime = System.currentTimeMillis();
        if (debug) {
            solveProblem(new FileInputStream(new File("input.in")));
            System.out.println("Time: " + (System.currentTimeMillis() - currTime));
        } else {
            solveProblem(System.in);
        }
    }

}
