/*
 *created by Kraken on 13-05-2020 at 15:56
 */
//package com.kraken.cf.cf605;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class E {
  private static final int INF = (int) 1e8;

  private static int n;
  private static int[] a;
  private static int[][] dp;
  private static boolean[][] vis;

  private static int find(int idx, int eo) {
    if (idx < 1 || idx > n) return -1;
    if (dp[idx][eo] != INF) return dp[idx][eo];
    if (vis[idx][eo])
      return -1;
    vis[idx][eo] = true;
    if (a[idx] % 2 == 0) {
      dp[idx][0] = 0;
      int left = find(idx - a[idx], 1);
      int right = find(idx + a[idx], 1);
      if (left == -1 && right == -1) dp[idx][1] = -1;
      else if (left == -1) dp[idx][1] = right + 1;
      else if (right == -1) dp[idx][1] = left + 1;
      else dp[idx][1] = Math.min(left, right) + 1;
    } else {
      dp[idx][1] = 0;
      int left = find(idx - a[idx], 0);
      int right = find(idx + a[idx], 0);
      if (left == -1 && right == -1) dp[idx][0] = -1;
      else if (left == -1) dp[idx][0] = right + 1;
      else if (right == -1) dp[idx][0] = left + 1;
      else dp[idx][0] = Math.min(left, right) + 1;
    }
    return dp[idx][eo];
  }

  public static void main(String[] args) {
    FastReader sc = new FastReader();
    n = sc.nextInt();
    a = new int[n + 1];
    dp = new int[n + 1][2];
    vis = new boolean[n + 1][2];
    for (int[] i : dp)
      Arrays.fill(i, INF);
    for (int i = 1; i <= n; i++) {
      a[i] = sc.nextInt();
    }
    for (int i = 1; i <= n; i++) {
      if (a[i] % 2 == 0) {
        find(i, 1);
      } else {
        find(i, 0);
      }
//      System.out.printf("after %d:\n", a[i]);
//      for (int[] x : dp)
//        System.out.println(Arrays.toString(x));
//      System.out.println("------------------");
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= n; i++) {
      if (a[i] % 2 == 0) sb.append(dp[i][1]);
      else sb.append(dp[i][0]);
      sb.append(" ");
    }
    System.out.println(sb.toString());
  }

  static class FastReader {

    BufferedReader br;

    StringTokenizer st;

    public FastReader() {
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
      while (st == null || !st.hasMoreElements()) {
        try {
          st = new StringTokenizer(br.readLine());
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
      return st.nextToken();
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

    String nextLine() {
      String str = "";
      try {
        str = br.readLine();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      return str;
    }
  }
}
