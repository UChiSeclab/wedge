import java.util.Arrays;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.io.BufferedReader;
import java.math.BigInteger;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author Vadim Semenov
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		TaskD solver = new TaskD();
		solver.solve(1, in, out);
		out.close();
	}
}

class TaskD {
    private static final int MODULO = 1_000_000_007;

    private List<int[]>[] graph;
    private int[] s1;
    private int[] s2;
    private int[] s1inSubTree;
    private int[] s2inSubTree;
    private int[] subTreeSize;

    // LCA
    private int[][] parent;
    private int[] level;
    private int[] distance;

    public void solve(int testNumber, InputReader in, PrintWriter out) {
        int vertices = in.nextInt();
        graph = new List[vertices];
        for (int i = 0; i < vertices; ++i) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i + 1 < vertices; ++i) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            int l = in.nextInt();
            graph[v].add(new int[]{u, l});
            graph[u].add(new int[]{v, l});
        }
        s1 = new int[vertices];
        s2 = new int[vertices];
        s1inSubTree = new int[vertices];
        s2inSubTree = new int[vertices];
        subTreeSize = new int[vertices];
        prepare(0, -1);
        anotherPrepare(0, -1, s1inSubTree[0], s2inSubTree[0]);
        level = new int[vertices];
        parent = new int[vertices][18];
        for (int[] p : parent) Arrays.fill(p, -1);
        distance = new int[vertices];
        prepareLCA(0, -1, 0, 0);

        int queries = in.nextInt();
        for (int q = 0; q < queries; ++q) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            int lca = lca(u, v);
            int dist = (distance[u] + distance[v]);
            if (dist >= MODULO) dist -= MODULO;
            dist -= 2 * distance[lca];
            while (dist < 0) dist += MODULO;
            if (v != lca) {
                long answer = s1inSubTree[v] * 2L * dist;
                answer += s2inSubTree[v];
                answer += dist * 1L * dist % MODULO * subTreeSize[v];
                answer *= 2;
                answer -= s2[u];
                answer %= MODULO;
                if (answer < 0) answer += MODULO;
                out.println(answer);
            } else {
                long answer = (s2[v] - s2inSubTree[v]) + (s1[v] - s1inSubTree[v]) * 2L * dist + dist * 1L * dist %
                        MODULO * (vertices - subTreeSize[v]);
                answer %= MODULO;
                answer = s2[u] - 2 * answer;
                answer %= MODULO;
                if (answer < 0) answer += MODULO;
                out.println(answer);
            }
        }
    }

    private int lca(int u, int v) {
        if (level[u] < level[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        int diff = level[u] - level[v];
        for (int i = 0; i < parent[u].length; ++i) if (((diff >> i) & 1) == 1) {
            u = parent[u][i];
        }
        if (level[v] != level[u]) throw new AssertionError(level[v] + " " + level[u]);
        if (u == v) return v;
        for (int i = parent[v].length; i >= 0; --i) if (i < parent[v].length && parent[v][i] != parent[u][i]) {
            v = parent[v][i];
            u = parent[u][i];
        }
        return parent[v][0];
    }

    private void prepareLCA(int vertex, int parent, int level, int distance) {
        this.distance[vertex] = distance;
        this.level[vertex] = level;
        this.parent[vertex][0] = parent;
        for (int i = 1; i < this.parent[vertex].length; ++i) {
            if (this.parent[vertex][i - 1] != -1) {
                this.parent[vertex][i] = this.parent[this.parent[vertex][i - 1]][i - 1];
            }
        }
        for (int[] e : graph[vertex]) if (e[0] != parent) {
            prepareLCA(e[0], vertex, level + 1, (distance + e[1]) % MODULO);
        }
    }

    private void anotherPrepare(int vertex, int parent, int s1, int s2) {
        this.s1[vertex] = s1;
        this.s2[vertex] = s2;
        for (int[] e : graph[vertex]) {
            int to = e[0];
            if (to == parent) continue;
            long l = e[1];
            anotherPrepare(to, vertex,
                    (int) ((s1 + l * (graph.length - 2 * subTreeSize[to])) % MODULO),
                    (int) ((((s2 + l * l % MODULO * (graph.length - 2 * subTreeSize[to]) +
                            2 * l * (s1 - l * subTreeSize[to] % MODULO - 2 * s1inSubTree[to]))) % MODULO + MODULO) %
                    MODULO));
        }
    }

    private void prepare(int vertex, int parent) {
        subTreeSize[vertex] = 1;
        long s1 = 0;
        long s2 = 0;
        for (int[] e : graph[vertex]) {
            int to = e[0];
            if (to == parent) continue;
            long l = e[1];
            prepare(to, vertex);
            subTreeSize[vertex] += subTreeSize[to];
            s1 += l * subTreeSize[to] + s1inSubTree[to];
            s1 %= MODULO;
            s2 += l * l % MODULO * subTreeSize[to] + s2inSubTree[to] + 2 * l * s1inSubTree[to];
            s2 %= MODULO;
        }
        s1inSubTree[vertex] = (int) s1;
        s2inSubTree[vertex] = (int) s2;
    }
}

class InputReader {
    private final BufferedReader reader;
    private StringTokenizer tokenizer;

    public InputReader(InputStream in) {
        reader = new BufferedReader(new InputStreamReader(in));
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(readLine());
        }
        return tokenizer.nextToken();
    }

    public String readLine() {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}

