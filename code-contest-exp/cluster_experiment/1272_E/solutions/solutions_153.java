import java.io.*;
import java.util.*;

public class ProblemE {

    public static InputStream inputStream = System.in;

    public static OutputStream outputStream = System.out;

    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

        int n = scanner.nextInt();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n ; i++) {
            list.add(scanner.nextInt());
        }

        Graph graph = new Graph(n);

        for (int i = 0; i < n; i ++) {
            int x = i - list.get(i);
            if (x >= 0) {
                graph.addEdge(x, i);
            }
            int y = i + list.get(i);
            if (y < n) {
                graph.addEdge(y, i);
            }
        }

        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (list.get(i) % 2 == 0) {
                evens.add(i);
            } else {
                odds.add(i);
            }
        }

        int[] ans = new int[n];

        graph.bfs(odds);
        for (int i = 0; i < n; i++) {
            if (list.get(i) % 2 == 0) {
                ans[i] = graph.nodes.get(i).dist;
            }
        }

        graph.clean();
        graph.bfs(evens);
        for (int i = 0; i < n; i++) {
            if (list.get(i) % 2 == 1) {
                ans[i] = graph.nodes.get(i).dist;
            }
        }

        for (int i = 0; i < n ; i++) {
            if (ans[i] == 0) {
                ans[i] = -1;
            }
            out.print(ans[i] + " ");
        }


        out.flush();
    }

    private static class Graph {

        private Map<Integer, Node> nodes = new HashMap<>();

        private Graph(int n) {
            for (int i = 0; i < n; i++) {
                nodes.put(i, new Node(i));
            }
        }

        private void addEdge(int x, int y) {
            nodes.get(x).nodes.add(nodes.get(y));
        }

        private void bfs(List<Integer> src) {
            LinkedList<Node> toVisit = new LinkedList<>();
            for (int x : src) {
                Node node = nodes.get(x);
                node.visited = true;
                toVisit.add(node);
            }

            while (!toVisit.isEmpty()) {
                Node node = toVisit.poll();
                for (Node adj : node.nodes) {
                    if (!adj.visited) {
                        adj.dist = node.dist + 1;
                        adj.visited = true;
                        toVisit.add(adj);
                    }
                }
            }
        }

        private void clean() {
            for (Node node : nodes.values()) {
                node.clean();
            }
        }
    }

    private static class Node {

        private int label;

        private int dist;

        private boolean visited;

        private List<Node> nodes = new ArrayList<>();

        private Node(int label) {
            this.label = label;
        }

        private void clean() {
            dist = 0;
            visited = false;
        }
    }

    private static class MyScanner {
        private BufferedReader bufferedReader;
        private StringTokenizer stringTokenizer;

        private MyScanner(InputStream inputStream) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        private String next() {
            while (stringTokenizer == null || !stringTokenizer.hasMoreElements()) {
                try {
                    stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringTokenizer.nextToken();
        }

        private int nextInt() {
            return Integer.parseInt(next());
        }

        private long nextLong() {
            return Long.parseLong(next());
        }

        private double nextDouble() {
            return Double.parseDouble(next());
        }

        private String nextLine() {
            String str = "";
            try {
                str = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    private static class Pair<F, S> {

        private F first;

        private S second;

        public Pair() {}

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}
