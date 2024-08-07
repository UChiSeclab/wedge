import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int numberOfTails = scan.nextInt();
        String enter = scan.next();
        String allTails = enter + scan.nextLine();

        String splitedTails[] = allTails.split(" ");

        int tailsAsIntegers[] = new int[splitedTails.length];

        for (int i = 0; i < splitedTails.length; i++) {

            tailsAsIntegers[i] = Integer.parseInt(splitedTails[i]);
        }

        System.out.println(maxShusi(tailsAsIntegers));
    }

    public static int maxShusi(int[] array) {
        int max = 0;
        int index =0 ;
        int firstMaxInRaw =0 ;
        int secondMaxInRaw =0 ;

        while (index <array.length){

                firstMaxInRaw = maxInRaw(array,index,array[index]);
                secondMaxInRaw = maxInRaw(array,index+firstMaxInRaw,3-array[index]);
                max = Math.max(max,Math.min(firstMaxInRaw,secondMaxInRaw));
                index = index+ firstMaxInRaw;

        }

        return 2*max;
    }

    public static int maxInRaw(int[] array, int startIndex, int number) {
        int max = 0;
        for (int i = startIndex; i < array.length; i++) {
            if (array[i] == number){
                max ++;
            }
            else{
                break;
            }
        }
        return max;
    }
}
