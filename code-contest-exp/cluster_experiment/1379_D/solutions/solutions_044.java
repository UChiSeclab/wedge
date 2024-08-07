// package codeforces.round.n657;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author: nayix
 * @Date: 7/20/20 2:20 PM
 */
public class NewPassengerTrams {

    private int lastNotBigger(int[] arr, int target) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (arr[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return r;
    }

    private boolean inRange(int time, int leftRange, int rightRange) {
        if (leftRange < rightRange) {
            return leftRange < time && time < rightRange;
        } else {
            return leftRange < time || (0 <= time && time < rightRange);
        }
    }

    private void work(int n, int halfM, int k, int[] train) {
        int[] trainCopy = Arrays.copyOf(train, train.length);
        Arrays.sort(trainCopy);
        int departTime = 0;
        int reachTime = 0;
        int minCancel = n + 1;
        for (int rightRange = 0; rightRange < n; rightRange++) {
            int beginTime = (trainCopy[rightRange] - k + halfM) % halfM;
            int leftRange = lastNotBigger(trainCopy, beginTime);
            int cancelNum = (leftRange < rightRange? rightRange - leftRange - 1: rightRange + n - leftRange - 1);
            if (cancelNum < minCancel) {
                minCancel = cancelNum;
                departTime = trainCopy[rightRange];
                reachTime = beginTime;
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
