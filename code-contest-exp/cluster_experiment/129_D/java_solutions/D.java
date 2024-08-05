
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class D {
    public static void main(String[] args) throws NumberFormatException,
            IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s = in.readLine();
        int k = Integer.parseInt(in.readLine()) - 1;
        if (k >= (s.length() * (s.length() + 1)) / 2) {
            System.out.println("No such line.");
            return;
        }
        PriorityQueue<sub> P = new PriorityQueue<sub>();
        for (int i = 0; i < s.length(); i++)
            P.add(new sub("" + s.charAt(i), i + 1));
        for (int i = 0; i < k; i++) {
            sub temp = P.poll();
            if (temp.index < s.length()) {
                temp.s += s.charAt(temp.index);
                temp.index++;
                P.add(temp);
            }
        }
        System.out.println(P.poll().s);
    }
}

class sub implements Comparable<sub> {
    String s;
    int index;

    public sub(String i, int j) {
        s = i;
        index = j;
    }

    public int compareTo(sub o) {
        return s.compareTo(o.s);
    }

}