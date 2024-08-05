import java.util.Scanner;

public class exe {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        boolean firstTimeOne = true;
        boolean firstTimeTwo = true;
        int numberOfOnes = 0;
        int numberOfTwos = 0;
        int firstIndexOne = -1;
        int firstIndexTwo = -1;

        int[] arr = new int[n];

        for(int i = 0; i < n; i++){
            arr[i] = scan.nextInt();

            numberOfOnes += (arr[i] == 1) ? 1 : 0;
            numberOfTwos += (arr[i] == 2) ? 1 : 0;

            if(arr[i] == 1 && firstTimeOne){
                firstTimeOne = false;
                firstIndexOne = i;
            }
            if(arr[i] == 2 && firstTimeTwo){
                firstTimeTwo = false;
                firstIndexTwo = i;
            }
        }

        boolean isOne = (numberOfOnes <= numberOfTwos) ? true : false;
        if(numberOfOnes <= numberOfTwos){
            int to = numberOfOnes + firstIndexOne;
            numberOfOnes = 0;
            int max = Integer.MIN_VALUE;
            for(int i = firstIndexOne; i < to; i++){
                if(arr[i] == 1) numberOfOnes++;
                if(arr[i] == 2){
                    max = Math.max(max, numberOfOnes);
                    numberOfOnes = 0;
                }
            }
            numberOfOnes = max;
        }else{
            int to = numberOfTwos + firstIndexTwo;
            numberOfTwos = 0;
            int max = Integer.MIN_VALUE;
            for(int i = firstIndexTwo; i < to; i++){
                if(arr[i] == 2) numberOfTwos++;
                if(arr[i] == 1){
                    max = Math.max(max, numberOfTwos);
                    numberOfTwos = 0;
                }
            }
            numberOfTwos = max;
        }

        System.out.println((isOne) ? numberOfOnes * 2 : numberOfTwos * 2);
    }
}
