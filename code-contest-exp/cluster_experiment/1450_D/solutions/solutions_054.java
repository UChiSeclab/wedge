//package global12;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// What's the smallest input?
// Check * for int/long overflow.
// Check / for accidental rounding.
// Are you using doubles? Can you avoid it?
// Never compare after taking mod.
// Mod the final result.
// Initialize globals in solve() unless they are independent of the problem input.
// Check for local/global name conflicts (n?).
// Check initial values for max/min (not Integer.MIN_VALUE for long).
public class D {
    static Random rand;
    static boolean MULTIPLE_CASES = true;
    static boolean CONSTRUCTIVE = false;
    static long MOD = (long) 1e9 + 7;
    static long BIG = (long) 2e9 + 10;

    static class Survival implements Comparable<Survival> {
        int index;
        int value;
        int maxK;

        public Survival(int index, int value, int maxK) {
            this.index = index;
            this.value = value;
            this.maxK = maxK;
        }

        @Override
        public int compareTo(Survival survival) {
            return Long.compare(BIG * maxK + index, BIG * survival.maxK + survival.index);
        }
    }
    
    static int computeMaxK(int i, int n, int smallestSince, int smallestUntil) {
        if (smallestSince == -1 && smallestUntil == -1) {
            return n;
        }

        if (smallestUntil == -1) {
            return n - smallestSince - 1;
        }

        if (smallestSince == -1) {
            return smallestUntil;
        }

        return smallestUntil - smallestSince - 1;
    }

    public static void solve(Reader in, PrintWriter out) {
        int n = in.ni();
        int[] arr = in.nis(n);

        int[] smallestSince = new int[n];
        // increasing value
        Stack<Integer> indices = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!indices.empty() && arr[indices.peek()] > arr[i]) indices.pop();
            smallestSince[i] = indices.isEmpty() ? -1 : indices.peek();
            indices.add(i);
        }

        int[] smallestUntil = new int[n];
        indices = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!indices.empty() && arr[indices.peek()] >= arr[i]) indices.pop();
            smallestUntil[i] = indices.isEmpty() ? -1 : indices.peek();
            indices.add(i);
        }

        PriorityQueue<Survival> q = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            q.add(new Survival(i, arr[i], computeMaxK(i, n, smallestSince[i], smallestUntil[i])));
        }

        SortedMap<Integer, Integer> map = new TreeMap<>();
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        int[] ans = new int[n];
        for (int k = 1; k <= n; k++) {
            insist(!q.isEmpty());
            while (q.peek().maxK < k) {
                Survival survival = q.poll();
                int freq = map.get(survival.value);
                if (freq == 1) {
                    map.remove(survival.value);
                } else {
                    map.put(survival.value, freq - 1);
                }
            }

            if (map.firstKey() == 1 && map.lastKey() == n - k + 1 && map.size() == n - k + 1) {
                ans[k-1] = 1;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i : ans) {
            sb.append(i);
        }
        out.println(sb.toString());
    }

    public static void main(String[] args) throws IOException {
        Reader in = null;
        PrintWriter out = null;
        for (String arg : args) {
            if (arg.startsWith("input")) {
                in = new Reader(arg);
            } else if (arg.startsWith("output")) {
                out = new PrintWriter(new FileWriter(arg));
            }
        }
        if (in == null) in = new Reader();
        if (out == null) out = new PrintWriter(new OutputStreamWriter(System.out));

        if (MULTIPLE_CASES) {
            int tests = in.ni();
            for (int t = 0; t < tests; t++) {
                solve(in, out);
            }
        } else {
            solve(in, out);
        }

        out.flush();
    }

    static class Tester {
        public static void main(String[] args) throws IOException {
            Validator validator = new Validator();

            File currentDir = new File(".");
            long before;
            long after;
            for (File inputFile : Arrays.stream(currentDir.listFiles())
                    .sorted(Comparator.comparing(File::getName))
                    .collect(Collectors.toList())) {
                if (!inputFile.getName().startsWith("input")
                        || inputFile.getName().contains("sight")
                        || inputFile.getName().contains("big")) continue;
                System.out.println("Test file: " + inputFile.getName());

                before = System.nanoTime();
                String outputFileName = "output" + inputFile.getName().substring(5);
                D.main(new String[]{inputFile.getName(), outputFileName});
                after = System.nanoTime();

                File outputFile = new File(outputFileName);
                if (!outputFile.exists()) {
                    throw new IllegalStateException("Missing output file " + outputFile);
                }

                // TODO if verifier is implemented, remove this
                if (CONSTRUCTIVE) {
                    System.out.println("INPUT:");
                    printFile(inputFile);
                    System.out.printf("\nOUTPUT: (%d milliseconds)\n", (after - before) / 1000000L);
                    printFile(outputFile);
                    continue;
                }

                if (validator.validate(inputFile, outputFile)) {
                    System.out.printf("OK (%d milliseconds)\n", (after - before) / 1000000L);
                } else {
                    System.out.println("FAILED");
                    System.out.println("\nInput: ");
                    printFile(inputFile);
                    System.out.println("\nIncorrect Output: ");
                    printFile(outputFile);
                    return;
                }
            }
            System.out.println("\n-----------------\n");

            File sightTestInput = new File("input-sight");
            if (sightTestInput.exists()) {
                System.out.println("Running sight test... Input:");
                printFile(sightTestInput);
                before = System.nanoTime();
                System.out.println("\nOutput: ");
                D.main(new String[]{"input-sight"});
                after = System.nanoTime();
                System.out.printf("(%d milliseconds)\n", (after - before) / 1000000L);
                System.out.println("\n-----------------\n");
            }

            File bigTestInput = new File("input-big");
            if (bigTestInput.exists()) {
                System.out.println("Running big test...");
                before = System.nanoTime();
                D.main(new String[]{"input-big"});
                after = System.nanoTime();
                System.out.printf("(%d milliseconds)\n", (after - before) / 1000000L);
            }
        }

        static void printFile(File file) throws FileNotFoundException {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.lines().forEach(System.out::println);
        }
    }

    static class Validator {
        boolean validate(File inputFile, File outputFile) throws IOException {
            File expectedOutputFile = new File("expected-output" + inputFile.getName().substring(5));
            if (CONSTRUCTIVE || !expectedOutputFile.exists()) {
                return validateManual(inputFile, outputFile);
            }
            return areSame(outputFile, expectedOutputFile);
        }

        private boolean validateManual(File inputFile, File outputFile) throws IOException {
            if (CONSTRUCTIVE) {
                // Validate output against input
                Reader inputReader = new Reader(inputFile.getName());
                Reader outputReader = new Reader(outputFile.getName());

                // TODO implement manual validation

                throw new IllegalStateException("Missing expected output file");
            } else {
                File naiveOutput = new File("naive-" + outputFile.getName());
                Naive.main(new String[]{inputFile.getName(), naiveOutput.getName()});

                return areSame(outputFile, naiveOutput);
            }
        }

        private boolean areSame(File file1, File file2) throws IOException {
            BufferedReader reader1 = new BufferedReader(new FileReader(file1));
            BufferedReader reader2 = new BufferedReader(new FileReader(file2));

            String line1;
            while ((line1 = reader1.readLine()) != null) {
                String line2 = reader2.readLine();
                if (line2 == null) line2 = ""; // ok if one has an extra newline
                if (!line1.trim().equals(line2.trim())) {
                    return false;
                }
            }
            String line2;
            while ((line2 = reader2.readLine()) != null) {
                if (!line2.trim().isEmpty()) {
                    return false;
                }
            }
            return true;
        }

    }

    static class Naive {
        public static void solveNaive(Reader in, PrintWriter out) {
            throw new IllegalStateException("missing expected output file");
        }

        public static void main(String[] args) throws IOException {
            Reader in = null;
            PrintWriter out = null;
            for (String arg : args) {
                if (arg.startsWith("input")) {
                    in = new Reader(arg);
                } else if (arg.startsWith("naive-output")) {
                    out = new PrintWriter(new FileWriter(arg));
                }
            }
            if (in == null) in = new Reader();
            if (out == null) out = new PrintWriter(new OutputStreamWriter(System.out));

            int tests = in.ni();
            for (int t = 0; t < tests; t++) {
                solveNaive(in, out);
            }

            out.flush();
            out.close();
        }
    }

    static boolean isPowerOfTwo(int x) {
        return x > 0 & (x & (x - 1)) == 0;
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static void ruffleSort(int[] a) {
        if (rand == null) rand = new Random();
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int oi = rand.nextInt(n), temp = a[oi];
            a[oi] = a[i];
            a[i] = temp;
        }
        Arrays.sort(a);
    }

    static void ruffleSort(long[] a) {
        if (rand == null) rand = new Random();
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int oi = rand.nextInt(n);
            long temp = a[oi];
            a[oi] = a[i];
            a[i] = temp;
        }
        Arrays.sort(a);
    }

    static int divRoundUp(int num, int denom) {
        return (num + denom - 1) / denom;
    }

    static long divRoundUp(long num, long denom) {
        return (num + denom - 1) / denom;
    }

    ///////////////////////////////////
    //////////   FAST PAIR   //////////
    ///////////////////////////////////

    // Works on -1e9 <= x, y <= 1e9
    static long pair(int x, int y) {
        x = (int) BIG / 2 - x;
        y = (int) BIG / 2 - y;
        return x * BIG + y;
    }

    static int x(long pair) {
        return (int) BIG / 2 - (int) (pair / BIG);
    }

    static int y(long pair) {
        return (int) BIG / 2 - (int) (pair % BIG);
    }

    static String str(long pair) {
        return String.format("(%d, %d)", x(pair), y(pair));
    }

    ///////////////////////////////////
    ////////// BINARY SEARCH //////////
    ///////////////////////////////////

    // return highest in range that still returns true
    // T T T F F F F
    // invariant:
    // -    indicator(min) = true
    // -    indicator(max+1) = false
    static int binarySearchHighest(int min, int max, Function<Integer, Boolean> indicator) {
        int a = min;
        int b = max;
        while (a != b) {
            int mid = (a % 2 != b % 2) ? 1 + (a + b) / 2 : (a + b) / 2;
            if (indicator.apply(mid)) {
                a = mid;
            } else {
                b = mid - 1;
            }
        }
        return a;
    }

    // return lowest in range that still returns true
    // F F F F T T T
    // invariant:
    // -    indicator(min-1) = false
    // -    indicator(max) = true
    static int binarySearchLowest(int min, int max, Function<Integer, Boolean> indicator) {
        int a = min;
        int b = max;
        while (a != b) {
            int mid = (a + b) / 2;
            if (indicator.apply(mid)) {
                b = mid;
            } else {
                a = mid + 1;
            }
        }
        return a;
    }

    static void insist(boolean bool) {
        if (!bool) throw new IllegalStateException();
    }

    static class Reader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public Reader() {
            reader = new BufferedReader(new InputStreamReader(System.in), 32768);
            tokenizer = null;
        }

        public Reader(String fileName) throws FileNotFoundException {
            reader = new BufferedReader(new FileReader(fileName));
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int ni() {
            return Integer.parseInt(next());
        }

        public long nl() {
            return Long.parseLong(next());
        }

        public int[] nis(int n) {
            int[] out = new int[n];
            for (int i = 0; i < n; i++) {
                out[i] = ni();
            }
            return out;
        }

        public long[] nls(int n) {
            long[] out = new long[n];
            for (int i = 0; i < n; i++) {
                out[i] = nl();
            }
            return out;
        }

        // 0-indexed
        public int[] stringAsIntArray(char firstChar) {
            return next().chars().map(i -> i - firstChar).toArray();
        }
    }
}
