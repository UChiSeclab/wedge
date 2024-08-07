// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED
// CLASS BEGINS, THIS CLASS IS REQUIRED
public class Solution {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        boolean[] team = new boolean[n + 2];

        int total = 0;
        for (int i = 0; i < n; i++) {
            int boxer = sc.nextInt();
            if(!team[boxer]){
                team[boxer] = true;
                total++;
            }else if(boxer > 1 && !team[boxer-1]){
                team[boxer -1] = true;
                total++;
            } else if(!team[boxer +1]){
                team[boxer +1] = true;
                total++;
            }
        }

        System.out.println(total);
    }

    private static Collection<Long> divisors(long input){
        Set<Long> results = new HashSet<>();
        for(long l =1 ;l<=Math.sqrt(input); l++){
            if( input%l == 0){
                results.add(l);
                results.add(input/l);
            }
        }
        return results;
    }

    private static long gcd(long[] input){
        if(input.length == 1){
            return input[0];
        }
        long result = gcd(input[0], input[1]);
        for (int i = 2; i < input.length; i++) {
            result = gcd(result, input[i]);
        }
        return result;
    }
    private static long gcd(long a, long b)
    {
        while(a!=0 && b!=0) // until either one of them is 0
        {
            long c = b;
            b = a%b;
            a = c;
        }
        return a+b; // either one is 0, so return the non-zero value
    }
}