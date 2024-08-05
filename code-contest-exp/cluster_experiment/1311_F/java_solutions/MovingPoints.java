import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MovingPoints {
    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(f.readLine());
        StringTokenizer tokenizer = new StringTokenizer(f.readLine());
        ValueAndIndex[] speedAndIndices = new ValueAndIndex[n];
        ValueAndIndex[] positionAndIndex = new ValueAndIndex[n];
        int[] position = new int[n];
        for (int i = 0; i < n; i++) {
            int pos = Integer.parseInt(tokenizer.nextToken());
            position[i] = pos;
            positionAndIndex[i] = new ValueAndIndex(pos, i, pos);
        }
        Arrays.sort(positionAndIndex);
        tokenizer = new StringTokenizer(f.readLine());
        for (int i = 0; i < n; i++) {
            speedAndIndices[i] = new ValueAndIndex(Integer.parseInt(tokenizer.nextToken()), i, position[i]);
        }
        position = null;
        Arrays.sort(speedAndIndices);
        int[] sortedSpeedIndex = new int[n];
        for (int i = 0; i < n; i++) {
            ValueAndIndex speedAndIndex = speedAndIndices[i];
            sortedSpeedIndex[speedAndIndex.index] = i;
        }
        speedAndIndices = null;


        FenwickTree treePosition = new FenwickTree(n);
        FenwickTree treeNumOfDifferent = new FenwickTree(n);
        treePosition.add(sortedSpeedIndex[positionAndIndex[0].index], positionAndIndex[0].value);
        treeNumOfDifferent.add(sortedSpeedIndex[positionAndIndex[0].index], 1);
        long sum = 0;
        for (int i = 1; i < n; i++) {
            ValueAndIndex positionAndInd = positionAndIndex[i];
            //System.out.println(i + " " + positionAndInd.value + " " + sortedSpeedIndex[positionAndInd.index] + " " +  treePosition.getSum(0, sortedSpeedIndex[i]) + " " + ((long) positionAndInd.value * treeNumOfDifferent.getSum(0, sortedSpeedIndex[i])));
            //System.out.println(positionAndInd.value + " " + treeNumOfDifferent.getSum(0, sortedSpeedIndex[positionAndInd.index]));
            sum -= treePosition.getSum(0, sortedSpeedIndex[positionAndInd.index]);
            sum += (long) positionAndInd.value * treeNumOfDifferent.getSum(0, sortedSpeedIndex[positionAndInd.index]);
            treePosition.add(sortedSpeedIndex[positionAndInd.index], positionAndInd.value);
            treeNumOfDifferent.add(sortedSpeedIndex[positionAndInd.index], 1);
        }

        System.out.println(sum);
    }

    private static class ValueAndIndex implements Comparable<ValueAndIndex> {
        int value;
        int index;
        int comparingValue;

        public ValueAndIndex(int speed, int index, int comparingValue) {
            this.value = speed;
            this.index = index;
            this.comparingValue = comparingValue;
        }

        @Override
        public int compareTo(ValueAndIndex speedAndIndex) {
            if (this.value < speedAndIndex.value) {
                return -1;
            } else if (this.value > speedAndIndex.value) {
                return 1;
            }

            if (comparingValue < speedAndIndex.comparingValue) {
                return -1;
            } else if (comparingValue > speedAndIndex.comparingValue) {
                return 1;
            }

            return 0;
        }
    }

    private static class FenwickTree {
        int[] ar;

        public FenwickTree(int n) {
            this.ar = new int[n];
        }

        public long getSum(int i) {
            long sum = 0;
            while (i >= 0) {
                sum += ar[i];
                i = (i & (i + 1)) - 1;
            }

            return sum;
        }

        public long getSum(int a, int b) {
            return getSum(b) - getSum(a - 1);
        }

        public void add(int i, int num) {
            while (i < this.ar.length) {
                this.ar[i] += num;
                i = i | (i + 1);
            }
        }
    }
}
