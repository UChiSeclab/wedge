import java.util.*;

/**
 * Created by Mill on 04.03.2017.
 */
public class N42 {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        int n, k;
        n = in.nextInt();
        k = in.nextInt();
        int[] seq = new int[n];

        for(int i = 0; i < n; i++) {
            seq[i] = in.nextInt();
        }

        int totalPairs = 0;
        int sum, xor,x;
        Map<Integer, Boolean> mapa = new HashMap<>();

        for(int i = 0; i < n - 1; i++) {
            for(int j = i + 1; j < n; j++) {
                    x = seq[i] ^ seq[j];
                    if(mapa.containsKey(x)) {
                        if(mapa.get(x)) {
                            totalPairs++;
                        }
                    }
                    else {
                        xor = x;
                        sum = xor % 2;
                        while (xor > 0 && sum <= k) {
                            xor = xor >> 2;
                            sum += xor % 2;
                        }

                        if (sum == k) {
                            totalPairs++;
                            mapa.put(x,true);
                        }
                        else mapa.put(x,false);
                    }
                }

            }

        System.out.print(totalPairs);
        }

    }

