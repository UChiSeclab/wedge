import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MovingPoints {
    static int[] pointsTree;
    static int[] distTree;
    static int[] velocity;
    static int[] distance;
    static Point[] points;

    static class FastScanner {
        StringTokenizer st;
        BufferedReader br;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while(st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class Point {
        int distance;
        int velocity;
        int index;

        Point(int distance, int velocity, int index) {
            this.distance = distance;
            this.velocity = velocity;
            this.index = index;
        }
    }

    static class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point a, Point b) {
            if(a.velocity != b.velocity) {
                return (a.velocity < b.velocity) ? -1 : 1;
            }
            if(a.distance != b.distance) {
                return (a.distance < b.distance) ? -1 : 1;
            }
            if(a.index != b.index) {
                return (a.index < b.index) ? -1 : 1;
            }
            return 0;
        }
    }

    static void updateIndex(int index, int dist, int n) {
        while(index <= n) {
            distTree[index] += dist;
            pointsTree[index] += 1;
            index += (index & (-index));
        }
    }

    static long solve(int index, long dist) {
        int x = index;
        int nPoints = 0;
        while(x > 0) {
            nPoints += pointsTree[x];
            x -= (x & (-x));
        }
        long prefDist = 0;
        x = index;
        while(x > 0) {
            prefDist += distTree[x];
            x -= (x & (-x));
        }
        dist = (long) nPoints * dist;
        dist -= prefDist;
        return dist;
    }

    public static void main(String[] Args) {
        FastScanner sc = new FastScanner();
        int n;
        n = sc.nextInt();
        distTree = new int[n + 1];
        pointsTree = new int[n + 1];
        velocity = new int[n + 1];
        distance = new int[n + 1];
        points = new Point[n];

        ArrayList<Integer> distances = new ArrayList<>();
        TreeMap<Integer, Integer> treeMap = new TreeMap();

        for(int i = 0; i < n; i++) {
            distance[i] = sc.nextInt();
            distances.add(distance[i]);
        }

        Collections.sort(distances);
        int x = 0;
        for(int i = 0; i < distances.size(); i++) {
            if(i == 0 || distances.get(i) != distances.get(i - 1)) {
                treeMap.put(distances.get(i), ++x);
            }
        }
        for(int i = 0; i < n; i++) {
            velocity[i] = sc.nextInt();
        }

        for(int i = 0; i < n; i++) {
            points[i] = new Point(distance[i], velocity[i], treeMap.get(distance[i]));
        }

        Arrays.sort(points, new PointComparator());

        long sol = 0L;
        for(Point point: points) {
            sol += solve(point.index, point.distance);
            updateIndex(point.index, point.distance, treeMap.size());
        }

        System.out.println(sol);
    }
}