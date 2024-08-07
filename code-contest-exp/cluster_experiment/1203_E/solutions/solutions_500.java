import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

        if (boxers[0] == 1) {
            team.add(boxers[0]);
        } else {
            team.add(boxers[0] - 1);
        }

        for (int i = 1; i < boxerNum; i++) {
            int boxer = boxers[i];

            if (boxer - 1 != 0 && team.get(team.size() - 1) < boxer - 1) {
                team.add(boxer - 1);
            } else if (team.get(team.size() - 1) < boxer) {
                team.add(boxer);
            } else if (team.get(team.size() - 1) < boxer + 1) {
                team.add(boxer + 1);
            }
        }

        System.out.println(team.size());
    }

    static int[] partition(int[] arr, int left, int right) {
        int lt = left;
        int i = left;
        int gt = right;
        int pivot = arr[left];

        while (i <= gt) {
            if (arr[i] < pivot) {
                int temp = arr[i];
                arr[i] = arr[lt];
                arr[lt] = temp;
                lt++;
                i++;
            } else if (arr[i] > pivot) {
                int temp = arr[i];
                arr[i] = arr[gt];
                arr[gt] = temp;
                gt--;
            } else {
                i++;
            }
        }

        return new int[] {lt, gt};
    }

    static void sort(int arr[], int left, int right) {
        if (left >= right) { return; }

        int[] bounds = partition(arr, left, right);
        sort(arr, left, bounds[0] - 1);
        sort(arr, bounds[1] + 1, right);
    }
}

