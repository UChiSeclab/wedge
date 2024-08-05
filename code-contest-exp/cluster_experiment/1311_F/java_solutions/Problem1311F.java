


import java.util.*;
import java.util.stream.Collectors;

public class Problem1311F {

    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", x, y);
        }
    }


    static class PairComparator implements Comparator<Pair> {

        public int compare(Pair p1, Pair p2) {
            return p1.x - p2.x;
        }
    }


    static class Solver {

        int n;
        List<Integer> x;
        List<Integer> y;
        ArrayList<Pair> pairs;
        ArrayList<Integer> vs;

        public Solver() {
            this.pairs = new ArrayList<>();
            this.vs = new ArrayList<>();
            read();
        }

        long get(ArrayList<Long> input, int position) {
            long res = 0;
            for (int i = position; i >= 0; i = (i & (i + 1)) - 1) {
                res += input.get(i);
            }
            return res;
        }

        void update(ArrayList<Long> input, int position, int val) {
            for (int i = position; i < input.size(); i |= i + 1) {
                input.set(i, input.get(i) + (long) val);
            }
        }

        void generatePairs() {
            for (int i = 0; i < this.n; i++) {
                Pair p = new Pair(this.x.get(i), this.y.get(i));
                this.pairs.add(p);

            }
            this.pairs.sort(new PairComparator());
            for (Pair p : this.pairs) {
                this.vs.add(p.y);
            }
            Collections.sort(this.vs);
            List<Integer> listUnique = this.vs.stream().distinct().collect(Collectors.toList());
            long ans = 0;
            ArrayList<Long> cnt = new ArrayList<>(listUnique.size());
            ArrayList<Long> xs = new ArrayList<>(listUnique.size());
            for (int i = 0; i < listUnique.size(); i++) {
                cnt.add(0L);
                xs.add(0L);
            }

            for (Pair p : this.pairs) {

                int pos = Collections.binarySearch(listUnique, p.y);
                ans += this.get(cnt, pos) * p.x - this.get(xs, pos);
                this.update(cnt, pos, 1);
                this.update(xs, pos, p.x);
            }
            System.out.println(ans);

        }

        void read() {
            Scanner userInput = new Scanner(System.in);
            this.n = userInput.nextInt();
            userInput.nextLine();
            String first = userInput.nextLine();
            String second = userInput.nextLine();
            this.x = Arrays.stream(first.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            this.y = Arrays.stream(second.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            generatePairs();
        }
    }


    public static void main(String[] args) {
        Solver s = new Solver();
    }
}
