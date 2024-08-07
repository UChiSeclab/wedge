import java.io.*;
import java.util.*;


public class Solution {
  public static void solve(BufferedReader br) throws IOException {
    int n = Integer.parseInt(br.readLine());
    int[] arr = new int[n];
    String[] s = br.readLine().split(" ");
    for (int i=0; i<n; i++) {
      arr[i] = Integer.parseInt(s[i]) - 1; // use 0-index
    }
    int[] counter = new int[n];
    for (int num : arr) {
      counter[num]++;
    }

    int[] res = new int[n];
    // we first check whether the minimum value is 1 in the array
    if (counter[0] > 0) res[n-1]=1;
    // then we check if each element occurs exactly once
    res[0] = 1;
    for (int i=0; i<n; i++) {
      if (counter[i] != 1) {
        res[0] = 0;
        break;
      }
    }

    // now we check 0 < k < n, we know the minimum value should always be at the end of interval
    // so each time we check whether the minimum value is at the end and the counter of the min value
    // is 1, if so, we know k-compression forms a permutation, and we go on until we reach some k 
    // that cannot meet the condition
    int l=0, r=n-1;
    for (int i=n-1; i>0; i--) {
      int min = n-i-1; // the minimum value in the interval [l,r], and should be at the end
      if (--counter[min] == 0 && (arr[l]==min || arr[r]==min) && counter[min+1] > 0) {
        res[i-1] = 1;
        if (arr[l] == min) l++;
        if (arr[r] == min) r--;
      } else {
        break;
      }
    }
    
    StringBuilder sb = new StringBuilder();
    for (int i:res) {
      sb.append(i);
    }
    System.out.println(sb.toString());

  }
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(br.readLine());
    while (t-- > 0) {
      solve(br);
    }
  }
}
