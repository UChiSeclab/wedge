import java.util.*;
import java.io.*;

public class CodeForces1203E {


    static BufferedReader f = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        /*
        Idea:
        put all the numbers in an array (frequency array)
        for all elements in the array:
        if the frequency for i-1 is 0; i = i-1, do the same for i and i+1;
         */

        int numOfBoxers = Integer.parseInt(f.readLine());
        int[] freqArray = new int[150003];
        StringTokenizer tok = new StringTokenizer(f.readLine());
        for (int i = 0; i < numOfBoxers; i++) {
            freqArray[Integer.parseInt(tok.nextToken())]++;
        }

        for (int i = 1; i < freqArray.length - 1; i++) {
            if (freqArray[i] > 0) {
                if (freqArray[i] == 1) { //--one weight as hope the next term can compensate
                    if (i != 1 & freqArray[i - 1] == 0) {
                        freqArray[i - 1]++;
                        freqArray[i]--;
                    }
                    //stay
                } else if (freqArray[i] == 2) {
                    if (i != 1 & freqArray[i - 1] == 0) {
                        freqArray[i - 1]++;
                        freqArray[i]--;
                    } //the one behind is at least one, so i,i-1 are all covered
                    else {
                        freqArray[i + 1]++;
                        freqArray[i]--;
                    }//next add one so i, i+1 are all covered
                } else if (freqArray[i] >= 3) {
                    freqArray[i - 1]++;
                    freqArray[i + 1]++;
                    freqArray[i] -= 2;
                }


            }
        }

        int count = 0;
        for (int i = 1; i < 150002; i++) {
            if (freqArray[i] > 0) {
                count++;
            }
        }

        System.out.println(count);
        f.close();


    }

}
