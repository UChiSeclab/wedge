import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class CodeforcesTestClass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] A = new int[n];
        for(int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }
        
        int max = 0;
        int onesNow = 0;
        int onesSaved = 0;
        int twosNow = 0;
        int twosSaved = 0;
        if(A[0] == 1) {
            onesNow = 1;
            onesSaved = 1;
        } else {
            twosNow = 1;
            twosSaved = twosNow;
        }
        
        for(int i = 1; i < A.length; i++) {
            if(A[i] == 1) {
                if(A[i-1] == 2) onesSaved = 0;
                onesNow++;
                onesSaved = onesNow;
                twosNow = 0;
            } else {
                if(A[i-1] == 1) twosSaved = 0;
                twosNow++;
                twosSaved = twosNow;
                onesNow = 0;
            }
            max = Math.min(onesSaved, twosSaved);
        //    System.out.println("i: " + i);
       //     System.out.println("OnesNow: " + onesNow);
       //     System.out.println("OnesSaved: " + onesSaved);
       //     System.out.println("TwosNow: " + twosNow);
       //     System.out.println("TwosSaved: " + twosSaved);
        //    System.out.println();
        }
        if(A[A.length - 1] == 1) onesSaved = onesNow;
        else twosSaved = twosNow;
        max = Math.min(onesSaved, twosSaved);

        System.out.println(max*2);
        
        scanner.close();
    }
}

