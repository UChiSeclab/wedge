import java.util.Scanner;

public class CParity {
    private static final int ZERO_DEPTH = 0;
    public static int[] findMinimumMoves(int[] arr) {
        int moves[] = new int[arr.length];
        for(int i=0; i<arr.length; i++) {
            int[] visited = new int[arr.length];
            fillMoves(arr, moves, i,visited);
        }
        return moves;
    }

    private static boolean isValidIndex(int[] arr, int index) {
        return index >= 0 && index < arr.length;
    }

    // Only valid indexes come
    private static boolean isSameParity(int n1, int n2, int[] arr) {
        return (arr[n1]+arr[n2])%2 == 0;
    }

    private static void fillMoves(int[] arr, int[] moves, int i, int[] visited) {
        if(!isValidIndex(arr, i)) {
            return;
        }
        if(moves[i] != 0){
            return;
        }
        if(visited[i] == 1){
            return;
        }
        visited[i] = 1;
        // Terminating condition needed
        int left = i-arr[i];
        int right = i+arr[i];

        //diff parity
        if(isValidIndex(arr, left) && !isSameParity(left, i, arr)){
            moves[i] = 1;
            return;
        }
        if(isValidIndex(arr, right) && !isSameParity(right, i, arr)) {
            moves[i] = 1;
            return;
        }

        //same parity
        fillMoves(arr, moves, left, visited);
        fillMoves(arr, moves, right, visited);

        int leftValue = isValidIndex(arr, left) ? moves[left] : -1;
        int rightValue = isValidIndex(arr, right) ? moves[right] : -1;

        if( leftValue <= 0 && rightValue <=0){
            moves[i] = -1;
            return;
        } else if(leftValue <= 0){
            moves[i] = rightValue + 1;
        } else if(rightValue <= 0){
            moves[i] = leftValue + 1;
        } else {
            moves[i] = Math.min(leftValue, rightValue) + 1;
        }
        return;
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
