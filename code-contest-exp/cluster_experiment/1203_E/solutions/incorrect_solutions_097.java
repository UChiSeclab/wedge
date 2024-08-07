import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    static class Reader {
        BufferedReader br;
        StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) {
        Reader reader = new Reader();
        int boxerNum = reader.nextInt();
        int[] boxers = new int[boxerNum];
        for (int i = 0; i < boxerNum; i++) { boxers[i] = reader.nextInt(); }

        sort(boxers, 0, boxerNum - 1);

        ArrayList<Integer> team = new ArrayList<>();
        team.add(boxers[0]);

        for (int i = 1; i < boxerNum; i++) {
            int boxer = boxers[i];

            if (Collections.binarySearch(team, boxer + 1) < 0) {
                team.add(boxer + 1);
            } else if (Collections.binarySearch(team, boxer) < 0) {
                team.add(boxer);
            } else if (boxer - 1 != 0 && Collections.binarySearch(team, boxer - 1) < 0) {
                team.add(boxer - 1);
            }
        }

        System.out.println(team.size());
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    static void sort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            sort(arr, low, pivot - 1);
            sort(arr, pivot + 1, high);
        }
    }
}

