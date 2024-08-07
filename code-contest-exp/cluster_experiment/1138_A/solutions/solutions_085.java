import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();

        String[] numbers = reader.readLine().split("\\s");
        int last = 0, max = 0, curr = 1;
        int[] sizes = new int[2];

        for (int i = 0; i < numbers.length; i++) {

            int val = Integer.parseInt(numbers[i]);
            if(val != last){

                int temp = Math.abs(curr-1);
                max = Math.max(max, Math.min(sizes[curr], sizes[temp]));

                curr = temp;
                sizes[curr] = 0;

            }

            last = val;
            sizes[curr]++;
        }
        max = Math.max(max, Math.min(sizes[0], sizes[1]));

        System.out.println(max*2);
    }

}
