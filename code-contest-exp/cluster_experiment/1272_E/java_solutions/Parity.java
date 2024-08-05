import java.util.Arrays;
import java.util.Scanner;

public class Parity {
    private static final int ZERO_DEPTH = 0;
    public static int[] findMinimumMoves(int[] arr) {
        int minmoves[] = new int[arr.length];
        for(int i=0; i<arr.length; i++) {
            boolean visited[] = new boolean[arr.length];
            fillMinMovesArray(arr, minmoves, visited, i);
        }
        return minmoves;
    }

    // Returns minimum moves for the given child
    private static int findMinMovesOnIndex(int[] arr, int[] minmoves, boolean[] visited, int curIndex, int newIndex) {
        if(newIndex >= 0 && newIndex < arr.length) {
            // Different parity found
            if(((arr[curIndex]+arr[newIndex]) & 1) != 0) {
                return 1;
            }
            boolean isCycleDetected = fillMinMovesArray(arr, minmoves, visited, newIndex);
            if(isCycleDetected == true) {
                minmoves[newIndex] = -1;
                return -1;
            }
            if(minmoves[newIndex] != -1) {
                return minmoves[newIndex] + 1;
            }
        }
        return -1;
    }

    // Returns 1 if cycle detected, 0 if no cycle detected and no value possible.
    // Also populates minmoves
    private static boolean fillMinMovesArray(int[] arr, int[] minmoves, boolean[] visited, int curIndex) {
        // Current index is already calculated
        if(minmoves[curIndex] != 0) {
            return false;
        }
        if(visited[curIndex]) {
            return true;
        }
        visited[curIndex] = true;


        int left = curIndex - arr[curIndex];
        int right = curIndex + arr[curIndex];

        int leftMoves = findMinMovesOnIndex(arr, minmoves, visited, curIndex, left);
        int rightMoves = findMinMovesOnIndex(arr, minmoves, visited, curIndex, right);

        if(leftMoves == -1 && rightMoves == -1) {
            minmoves[curIndex] = -1;
        } else if (leftMoves != -1 && rightMoves != -1) {
            minmoves[curIndex] = Math.min(leftMoves, rightMoves);
        } else {
            minmoves[curIndex] = leftMoves == -1 ? rightMoves : leftMoves;
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        int[] arr = new int[count];
        for (int i=0; i<count; i++) {
            arr[i] = scanner.nextInt();
        }
        int[] moves = findMinimumMoves(arr);
        for(int i=0; i<count; i++){
            System.out.print(moves[i] + " ");
        }
        System.out.println("");
    }

}
