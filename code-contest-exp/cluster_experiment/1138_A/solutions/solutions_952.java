import java.io.*;
import java.util.*;

public class SushiforTwo {
    static class TypeCount {
        int type;
        int count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sushi[] = new int[n];
        for (int i = 0; i < n; i++) {
            sushi[i] = sc.nextInt();
        }
        sc.close();
        System.out.println(findTruth(sushi, n));
    }

    public static int findTruth(int sushi[], int n) {
        ArrayList<TypeCount> arr = new ArrayList<TypeCount>();
        int maxLen = 0;
        TypeCount tempTC = new TypeCount();
        tempTC.type = sushi[0];
        tempTC.count = 1;
        int j = 1;
        while (j < n) {
            if (tempTC.type == sushi[j]) {
                ++tempTC.count;
            } else {
                arr.add(tempTC);
                tempTC = null;
                tempTC = new TypeCount();
                tempTC.count = 1;
                tempTC.type = sushi[j];
            }
            ++j;
        }
        arr.add(tempTC);

        for (int i = 1; i < arr.size(); i++) {
            TypeCount tcNext = arr.get(i);
            TypeCount tcPrev = arr.get(i - 1);
            int minCount = Math.min(tcNext.count, tcPrev.count) * 2;
            if (maxLen < minCount) {
                maxLen = minCount;
            }
        }
        return maxLen;
    }
}