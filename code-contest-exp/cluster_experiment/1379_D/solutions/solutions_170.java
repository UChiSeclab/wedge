// package codeforces.round.n657;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author: nayix
 * @Date: 7/20/20 2:20 PM
 */
public class NewPassengerTrams {

    private boolean inRange(int time, int leftRange, int rightRange) {
        if (leftRange < rightRange) {
            return leftRange < time && time < rightRange;
        } else {
            return leftRange < time || (0 <= time && time < rightRange);
        }
    }

    private void work(int n, int halfM, int k, int[] train) {
        int[] trainCopy = new int[(train.length << 1) + 1];
        for (int i = 0; i < train.length; i++) {
            trainCopy[i] = train[i];
            trainCopy[i + train.length] = train[i] + halfM;
        }
        trainCopy[train.length << 1] = halfM << 1;
        Arrays.sort(trainCopy);

        int departTime = 0;
        int reachTime = 0;
        int minCancel = n + 1;

        for (int leftRange = 0, rightRange = 0; leftRange < n; leftRange++) {
            int beginTime = trainCopy[leftRange];
            int endTime = beginTime + k;

            while (trainCopy[rightRange] < endTime) {
                rightRange++;
            }

            int cancelNum = rightRange - leftRange - 1;
            if (cancelNum < minCancel) {
                reachTime = beginTime;
                departTime = endTime % halfM;
                minCancel = cancelNum;
            }
        }

        System.out.printf("%d %d\n", minCancel, departTime);
        for (int i = 0; i < n; i++) {
            if (inRange(train[i], reachTime, departTime)) {
                System.out.printf("%d ", i+1);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        int halfM = (m >> 1);
        int[] train = new int[n];
        for (int i = 0; i < n; i++) {
            in.nextInt();
            train[i] = in.nextInt() % halfM;
        }
        in.close();

        NewPassengerTrams newPassengerTrams = new NewPassengerTrams();
        newPassengerTrams.work(n, halfM, k, train);
    }
}
