//package codeforces.round545;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class A {
  public static void main(String[] args) throws IOException {
    A a = new A();
    a.run();
  }

  public void run() throws IOException {
    Solution solution = new Solution();
    solution.solve();
  }

  public class Solution {

    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

    public void solve() throws IOException {
      solveNextTest();
    }

    private void solveNextTest() throws IOException {
      int n = Integer.parseInt(sc.readLine());
      String allValues = sc.readLine();
      String[] possibleValues = allValues.split(" ");
      int c = 0, p=0, f, pf;
      f = 0;
      pf = 0;
      int max = 0;
      for (int j = 0; j < n; j++) {
        int a = Integer.parseInt(possibleValues[j]);
        if (c == 0) {
          c = a;
          f = 1;
          continue;
        }
        if (a == c) {
          f++;
          max = Math.max(max, Math.min(f, pf)*2);
        } else {
          p = c;
          c = a;
          pf = f;
          f = 1;
          max = Math.max(max, Math.min(f, pf)*2);
        }
      }
      System.out.println(max);
    }


  }

}
