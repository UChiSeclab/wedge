import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long x[] = new long[n];
        long v[] = new long[n];
        
        List<Point> points = new ArrayList<>(n+1);
        for (int i=0; i<n; i++) points.add(new Point(in.nextLong(), 0));
        for (int i=0; i<n; i++) points.get(i).v = in.nextLong();
        
        points.sort(Comparator.comparing(l -> l.x));
        System.out.println(Arrays.deepToString(points.toArray()));
        
        long sum = 0;
        for (int i=0; i<n-1; i++) {
            for (int j=i+1; j<n; j++) {
                Point l = points.get(i);
                Point r = points.get(j);
                if (l.v <= r.v) sum += r.x - l.x;
            }
        }
        System.out.println(sum);
    }
    
    static class Point{
        long x, v;
        Point(long x_, long v_) { x = x_; v = v_; }
        public String toString() { return "["+x+" "+v+"]"; }
    }
}