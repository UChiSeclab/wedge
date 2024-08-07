import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {
  private static int readInt() throws Exception {
    return Integer.parseInt(br.readLine());
  }

  private static long readLong() throws Exception {
    return Long.parseLong(br.readLine());
  }

  public static int[] readIntArray() throws Exception {
    String line = br.readLine();
    if (line == null || line.isEmpty()) return null;
    String[] lines = line.split(" ");
    int n = Integer.parseInt(lines[0]);
    int[] nums = new int[n];
    for (int i = 0; i < n; i++)
      nums[i] = Integer.parseInt(lines[i + 1]);
    return nums;
  }

  public static int[] readIntArray(int n) throws Exception {
    int[] nums = new int[n];
    String[] lines = br.readLine().split(" ");
    for (int i = 0; i < n; i++)
      nums[i] = Integer.parseInt(lines[i]);
    return nums;
  }

  public static long[] readLongArray(int n) throws Exception {
    long[] nums = new long[n];
    String[] lines = br.readLine().split(" ");
    for (int i = 0; i < n; i++)
      nums[i] = Long.parseLong(lines[i]);
    return nums;
  }

  public static String[] readStringArray(int n) throws Exception {
    String[] nums = new String[n];
    for (int i = 0; i < n; i++)
      nums[i] = br.readLine();
    return nums;
  }

  public static void printArrayInline(int[] nums) throws Exception {
    for (int i = 0; i < nums.length - 1; i++) {
      bw.write(nums[i] + " ");
    }
    bw.write(nums[nums.length - 1] + "\n");
  }

  public static void printArrayInline(long[] nums) throws Exception {
    for (int i = 0; i < nums.length - 1; i++) {
      bw.write(nums[i] + " ");
    }
    bw.write(nums[nums.length - 1] + "\n");
  }

  public static <T> void printListInLine(List<T> nums) throws Exception {
    for (int i = 0; i < nums.size() - 1; i++) {
      bw.write(nums.get(i).toString() + " ");
    }
    bw.write(nums.get(nums.size() - 1) + "\n");
  }

  public static <T> void printListMultiLine(List<T> nums) throws Exception {
    for (T num : nums) {
      bw.write(num.toString() + "\n");
    }
  }

  public static void printArrayMultiLine(int[] nums) throws Exception {
    for (int num : nums) {
      bw.write(num + "\n");
    }
  }

  public static int[] makeRandomArray(int size, int lower, int upper) {
    Random random = new Random(System.currentTimeMillis());
    int[] nums = new int[size];
    for (int i = 0; i < size; i++)
      nums[i] = random.nextInt(upper - lower + 1) + lower;
    return nums;
  }
  public static long[] makeRandomLongArray(int size, int lower, int upper) {
    Random random = new Random(System.currentTimeMillis());
    long[] nums = new long[size];
    for (int i = 0; i < size; i++)
      nums[i] = random.nextInt(upper - lower + 1) + lower;
    return nums;
  }

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static PrintWriter bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
//  static {
//    try {
//      String env = System.getProperty("env-jay");
//      if (env != null && env.equals("jay")) {
//        br = new BufferedReader(new FileReader("src/input.txt"));
//        bw = new PrintWriter(new BufferedWriter(new FileWriter("src/output.txt", false)));
//      }
//    } catch (Throwable e) {
//
//    }
//  }
//
//
//  private static void test() throws Exception {
//    try {
//      String env = System.getProperty("env-jay");
//      if (env != null && env.equals("jay")) {
//        var output = new BufferedReader(new FileReader("src/output.txt"));
//        var expected = new BufferedReader(new FileReader("src/expected.txt"));
//        String ans;
//        int line = 0;
//        while ((ans = expected.readLine()) != null && !ans.isEmpty()) {
//          line++;
//          String guess = output.readLine();
//          if (guess == null || guess.isEmpty()) {
//            System.err.println(String.format(
//                    "Line[%d] There is no guess.\nAnswer:[%s]"
//                    , line, ans));
//            System.exit(-1);
//          }
//          if (!ans.equals(guess)) {
//            System.err.println(String.format(
//                    "Line[%d], Wrong guess.\nAnswer:[%s]\nGuess:[%s]"
//                    , line, ans, guess));
//            System.exit(-2);
//          }
//        }
//      }
//    } catch (Throwable e) {
//
//    }
//  }

  public static void main(String[] args) throws Exception {
    int testCases = readInt();
    for (int iter = 1; iter <= testCases; iter++) {
      int n = readInt();
      String line = br.readLine();
      int[] ans = solve(line);
      printArrayInline(ans);
    }
    bw.flush();
    bw.close();
    br.close();
    //test();
  }
  private static int[] solve(String str) {
    int n = str.length();
    boolean[] isD = new boolean[n];
    for(int i = 0; i < n; i ++) isD[i] = str.charAt(i) == 'D';

    int[] ans = new int[n];
    ans[0] = 1;
    Map<String, Integer> memo = new HashMap<>();
    int dCount = 0;
    if(isD[0]) {
       memo.put(getKey(1, 0), 1);
       dCount ++;
    } else {
      memo.put(getKey(0, 1), 1);
    }
    for(int i = 1; i < n; i ++) {
      if(isD[i]) dCount ++;
      String key = getKey(dCount, i + 1 - dCount);
      memo.put(key, memo.getOrDefault(key, 0) + 1);
      ans[i] = memo.get(key);
    }
    return ans;
  }
  private static String getKey(int d, int k) {
    int gcd = gcd(d,k);
    d = d/gcd; k = k/gcd;
    return d + "-" + k;
  }
  private static int gcd(int a, int b) {
    if(b == 0) return a;
    return gcd(b, a % b);
  }

}
