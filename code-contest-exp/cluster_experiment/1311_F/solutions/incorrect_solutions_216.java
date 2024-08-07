//package CodeforcesJava;

import java.io.*;
import java.util.*;

public class Main {

    public void solve(InputProvider in, PrintWriter out) throws IOException {
        int pointCount = in.nextInt();
        MovingPoint[] points = new MovingPoint[pointCount];
        for (int i = 0; i < points.length; i++) {
            points[i] = new MovingPoint(in.nextInt());
        }
        for (MovingPoint point : points) {
            point.velocity = in.nextInt();
        }
        Arrays.sort(points, MovingPoint.POSITION_COMPARATOR);
        long totalDistance = 0L;
        TreeSet<MovingPoint> pointsOnTheLeftByVelocity = new TreeSet<>(MovingPoint.VELOCITY_COMPARATOR);
        for (MovingPoint point : points) {
            NavigableSet<MovingPoint> unreachablePoints = pointsOnTheLeftByVelocity.headSet(point, true);
            for (MovingPoint unreachablePoint : unreachablePoints) {
                totalDistance += Math.abs(point.position - unreachablePoint.position);
            }
            pointsOnTheLeftByVelocity.add(point);
        }
        out.print(totalDistance);
    }

    private static class MovingPoint {

        private final int position;
        private int velocity;

        private MovingPoint(int position) {
            this.position = position;
        }

        private static Comparator<MovingPoint> POSITION_COMPARATOR = Comparator
                .comparingInt(o -> o.position);

        private static Comparator<MovingPoint> VELOCITY_COMPARATOR = Comparator
                .<MovingPoint>comparingInt(o -> o.velocity)
                .thenComparingInt(Object::hashCode);

    }

    public static void main(String[] args) throws Exception {
        try (InputProvider input = new InputProvider(System.in);
             PrintWriter output = new PrintWriter(System.out)) {
            new Main().solve(input, output);
        }
    }

    public static class InputProvider implements AutoCloseable {

        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public InputProvider(Reader reader) {
            this.reader = new BufferedReader(reader);
        }

        public InputProvider(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
        }

        public String next() throws IOException {
            if (Objects.isNull(tokenizer) || !tokenizer.hasMoreTokens())
                tokenizer = new StringTokenizer(reader.readLine());
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public String nextLine() throws IOException {
            return reader.readLine();
        }

        @Override
        public void close() throws Exception {
            reader.close();
        }

    }

}