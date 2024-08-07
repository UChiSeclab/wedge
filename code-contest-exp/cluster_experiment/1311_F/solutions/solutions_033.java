import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int [][] xi = new int[num][2];
        int i=0;
        while (i<num){
           xi[i][0]  = sc.nextInt();
           i++;
        }
        Integer [] vi= new Integer[num];
        i=0;
        while (i<num){
            xi[i][1]  = sc.nextInt();
            vi[i]= xi[i][1];
            i++;
        }
        sc.close();
        Arrays.sort(vi);
        Arrays.sort(xi, new Comparator<int[]>() {
                    public int compare(int[] a, int[] b) {
                        return a[0] - b[0];
                    }
        });
        long[] x = new long[num];
        long[] count = new long[num];
        long result = 0;
        i=0;
        while(i<num){
            int position = Arrays.binarySearch(vi, xi[i][1]);
            result = result + (distance(position, count) * xi[i][0]) - distance(position,x);
            update(position, count, 1);
            update(position, x, xi[i][0]);
            i++;
        }
        System.out.println(result);
    }
    public static long distance (int positon, long count[]){
        long dist = 0;
        while (positon >= 0) {
            dist = dist + count[positon];
            positon = (positon & (positon + 1)) - 1;
        }
        return dist;
    }
    private static void update  (int pos, long[] t, int delta) {
        while (pos < t.length) {
            t[pos] += delta;
            pos = pos | (pos + 1);
        }
    }
}
