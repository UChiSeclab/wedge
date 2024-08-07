//package codeforce.div2.r724;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import static java.lang.System.gc;
import static java.lang.System.out;
import static java.util.stream.Collectors.joining;


/**
 * @author pribic (Priyank Doshi)
 * @see <a href="https://codeforces.com/contest/1536/problem/C" target="_top">https://codeforces.com/contest/1536/problem/C</a>
 * @since 06/06/21 9:17 PM
 */
public class C {
  static FastScanner sc = new FastScanner(System.in);

  public static void main(String[] args) {
    /*
    long st = System.currentTimeMillis();
    int lim = 5 * 100000 + 1;
    Map<Integer, List<Integer>> divisors = new HashMap<>();
    for (int i = 1; i * i <= lim; i++) {
      for (int j = i; j <= lim; j += i) {
        divisors.putIfAbsent(j, new ArrayList<>());
        divisors.get(j).add(i);
        if (j / i != i)
          divisors.get(j).add(j / i);
      }
    }
    out.println(System.currentTimeMillis() - st); */
    try (PrintWriter out = new PrintWriter(System.out)) {
      int T = sc.nextInt();
      for (int tt = 1; tt <= T; tt++) {
        int n = sc.nextInt();
        String str = sc.next();
        Map<int[], Integer> ratioF = new HashMap<>();
        int cd = 0;
        int ck = 0;
        for (char c : str.toCharArray()) {
          int i = c == 'D' ? cd++ : ck++;
          int _gcd = gcd(cd, ck);
          int[] key = new int[]{cd / _gcd, ck / _gcd};
          ratioF.put(key, ratioF.getOrDefault(key, 0) + 1);
          out.print(ratioF.get(key) + " ");
        }
        out.println();
      }
    }
  }

  private static int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  static class Pair {
    int a, b;

    public Pair(int a, int b) {
      int _gcd = gcd(a, b);
      this.a = a / _gcd;
      this.b = b / _gcd;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Pair pair = (Pair) o;
      return a == pair.a && b == pair.b;
    }

    @Override
    public int hashCode() {
      return Objects.hash(a, b);
    }
  }

  static class FastScanner {
    BufferedReader br;
    StringTokenizer st;

    public FastScanner(File f) {
      try {
        br = new BufferedReader(new FileReader(f));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

    public FastScanner(InputStream f) {
      br = new BufferedReader(new InputStreamReader(f), 32768);
    }

    String next() {
      while (st == null || !st.hasMoreTokens()) {
        String s = null;
        try {
          s = br.readLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (s == null)
          return null;
        st = new StringTokenizer(s);
      }
      return st.nextToken();
    }

    boolean hasMoreTokens() {
      while (st == null || !st.hasMoreTokens()) {
        String s = null;
        try {
          s = br.readLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (s == null)
          return false;
        st = new StringTokenizer(s);
      }
      return true;
    }

    int nextInt() {
      return Integer.parseInt(next());
    }

    long nextLong() {
      return Long.parseLong(next());
    }

    double nextDouble() {
      return Double.parseDouble(next());
    }
  }
}