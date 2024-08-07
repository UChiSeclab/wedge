import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Arles
 */
public class Main {

    public static void main(String[] args) throws IOException {
        int n, h, m, k;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Pair> li = new ArrayList<>();
        int[] data = Arrays.stream(br.readLine().split(" ")).mapToInt(num -> Integer.parseInt(num)).toArray();
        n = data[0];
        h = data[1];
        m = data[2];
        k = data[3];
        m /= 2;
        for (int i = 1; i <= n; i++) {
            int x, y;
            data = Arrays.stream(br.readLine().split(" ")).mapToInt(num -> Integer.parseInt(num)).toArray();
            x = data[0];
            y = data[1];
            li.add(new Pair(y % m, i));
            li.add(new Pair(y % m + m, i));
        }
        Collections.sort(li);
        Pair ans = new Pair((int) 1e9, -1);
        int j = 0;
        for (int i = 0; i < 2 * n; i++) {
            while (li.get(i).x - li.get(j).x >= k) {
                j++;
            }
            if (i >= n) {
                Pair aux = new Pair(i - j, li.get(i).x);
                if (ans.compareTo(aux) > 0) {
                    ans = aux;
                }
            }
        }
        System.out.println(ans.x + " " + ans.y % m);
        for (int i = 0; i < 2*n; i++) {
            int l = ans.y - k;
            int r = ans.y;
            if(li.get(i).x < r && li.get(i).x > l){
                System.out.print(li.get(i).y + " ");
            }            
        }
        System.out.println("");
    }

}

class Pair implements Comparable<Pair> {

    int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Pair o) {
        int res = this.x - o.x;
        if (res == 0) {
            res = this.y - o.y;
        }
        return res;
    }

}
