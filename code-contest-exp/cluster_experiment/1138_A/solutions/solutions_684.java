import java.util.Scanner;

public class C{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();


        int best = 0, countsushi1 = 1, countsushi2 = 0;
        int first = sc.nextInt();

        for (int i = 1; i < size; i ++){
            int actual = sc.nextInt();

            if (actual != first) {
                best = Math.max(Math.min(countsushi1, countsushi2)*2, best);
                countsushi2 = countsushi1;
                countsushi1 = 0;
            }
            first = actual;
            countsushi1++;
        }

        System.out.println(Math.max(Math.min(countsushi1, countsushi2)*2, best));
    }

}
 	  			  					  		  			  	 			