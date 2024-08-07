

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class CodeForces {
    private final static Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

    public static void main(String[] args) {
        int t = in.nextInt();

        for(int i=0; i<t; i++) {
            int n = in.nextInt();
            in.nextLine();

            String wood = in.nextLine();

            printArr(solve(wood));
        }
    }

    private static void printArr(int[] arr) {
        StringBuilder str = new StringBuilder();
        for(int i=0; i<arr.length; i++) {
            str.append(arr[i]).append(" ");
        }
        str.setLength(str.length()-1);
        System.out.println(str.toString());
    }

    private static int[] solve(String wood) {
        int[] D = new int[wood.length()];
        int[] K = new int[wood.length()];
        int[] prefix = new int[wood.length()];
        Arrays.fill(prefix, 1);

        int countD = 0;
        int countK = 0;
        for(int i=0; i<wood.length(); i++) {
            if (wood.charAt(i) == 'D')
                countD++;
            else
                countK++;
            D[i] = countD;
            K[i] = countK;
        }

        for(int i=1; i<=Math.sqrt(wood.length()); i++) {
            int j=1;

            //first cell
            int cell = i*j-1;

            int DD=D[cell];
            int KK=K[cell];

            while (cell < wood.length()) {
                if(D[cell]/j == DD && K[cell]/j == KK) {
                    if(prefix[cell] < j)
                        prefix[cell] = j;
                } else {
                    break;
                }

                j++;
                cell = i*j-1;
            }
        }

        for(int i=1; i<=Math.sqrt(wood.length()); i++) {
            int i2 = wood.length()/i;
            int j=1;

            //first cell
            int cell = i2*j-1;

            int DD=D[cell];
            int KK=K[cell];

            while (cell < wood.length()) {
                if(D[cell]/j == DD && K[cell]/j == KK) {
                    if(prefix[cell] < j)
                        prefix[cell] = j;
                } else {
                    break;
                }

                j++;
                cell = i2*j-1;
            }
        }

        return prefix;
    }
}
