import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int num = s.nextInt();
        int ans = 0;
        int numofone = 0, numoftwo = 0;
        boolean one = false;
        int first = s.nextInt();
        if (first == 1) {
            one = true;
            numofone++;
        } else {
            numoftwo++;
        }
        for (int i = 1; i < num; i++) {
            if (one) {
                if (s.nextInt() == 1) {
                    numofone++;
                } else {
                    one = false; 
                    int min = Math.min(numofone, numoftwo);
                    if (min > ans) {
                        ans = min;
                    }
                    numoftwo = 1;
                }
            } else {
                if (s.nextInt() == 2) {
                    numoftwo++;
                } else {
                    one = true;
                    int min = Math.min(numofone, numoftwo);
                    if (min > ans) {
                        ans = min;
                    }
                    numofone = 1;
                }
            }
            int min = Math.min(numofone, numoftwo);
                    if (min > ans) {
                        ans = min;
                    }
        }
        System.out.println(ans * 2);
    }
}
	 		        		  	 		  	 		 	 	