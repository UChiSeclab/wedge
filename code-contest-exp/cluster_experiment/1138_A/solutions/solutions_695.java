

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class codeforcesDP {
    static int MOD = 1000000007;
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

      int n = sc.nextInt();
      int[] arr = takeInputInt(n);
      int prevLen = Integer.MIN_VALUE;
      int prevElem = arr[0];
      int max = 0;
      int currLen = 0;
      for(int i=0;i<n;i++){
          if(arr[i] == prevElem){
              currLen++;
          }
          else {
              if(max < Math.min(currLen,prevLen)){
                  max = Math.min(currLen,prevLen);
              }
              prevLen = currLen;
              currLen = 1;
          }
          prevElem = arr[i];
      }

        if(max < Math.min(currLen,prevLen)){
            max = Math.min(currLen,prevLen);
        }
        System.out.println(max*2);

    }


    static int binarySearch(int[] arr, int x){
        int low = 0;
        int high = arr.length-1;
        int mid;
        while(low < high){
            mid = (low + high)/2;
            if(arr[mid] == x)
                return mid;
            if(arr[mid] < x){
                low = mid + 1;
            }
            else {
                high = mid-1;
            }
        }
        if(arr[low] > x)
            return low-1;
        return low;
    }

    static int binarySearchUB(int[] arr,int x){
        int low = 0;
        int high = arr.length-1;
        int mid;
        int ans = 0;
        int upper = 0;
        while(low <= high){
            mid = (low + high)/2;
            if(arr[mid] == x){
                low = mid + 1;
                ans = mid;
            }
            else if(arr[mid] < x){
                low = mid+1;
                upper = low;
            }
            else {
                high = mid - 1;
            }
        }
        if(arr[ans] == x)
            return ans;
        return upper;
    }

    static int binarySearchLB(int[] arr,int x){
        int low = 0;
        int high = arr.length-1;
        int mid;
        int ans = 0;
        int lower = 0;
        while(low <= high){
            mid = (low + high)/2;
            if(arr[mid] == x){
                ans = mid;
                high = mid - 1;
            }
            else if(arr[mid] < x){
                low = mid + 1;
            }
            else {
                high = mid - 1;
                lower = high;
            }
        }
        if(arr[ans] == x)
            return ans;
        return lower;
    }


    static int[] prefixSum(int[] arr){
        int[] ans = new int[arr.length];
        ans[0] = arr[0];
        for(int  i = 1;i<arr.length;i++){
            ans[i] = ans[i-1] + arr[i];
        }
        return ans;
    }
    static int kadane(int[] arr){

        int max = Integer.MIN_VALUE;
        int tempMax = 0;

        for(int i = 0;i<arr.length;i++){
            if(tempMax + arr[i] < arr[i]){
                tempMax = arr[i];
            }
            else {
                tempMax += arr[i];
            }
            if(tempMax > max){
                max = tempMax;
            }
        }
        return max;
    }




    static void printArr(long[] arr){
        for(long i : arr)
            System.out.print(i + " ");
        System.out.println();
    }

    static void printDP(int[][] dp){
        for(int i=0;i<dp.length;i++){
            for(int j = 0;j<dp[0].length;j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }

    }

    static int helpMe(int index, char[] ch, int length, int[][] dp){

        char c = 'Q';
        if(length == 1)
            c = 'A';
        if(length == 3)
            return 1;
        if(index == ch.length)
            return 0;
        if(dp[index][length] != -1)
            return dp[index][length];
        int considering = 0;
        if(c == ch[index])
            considering = helpMe(index+1,ch,length+1,dp);
        int notConsidering = helpMe(index+1,ch,length,dp);
        dp[index][length] = considering+notConsidering;
        return considering+notConsidering;
    }

    static int[] takeInputInt(int n){
        int[] input = new int[n];
        for(int i=0;i<n;i++){
            input[i] = sc.nextInt();
        }
        return input;
    }
    static long[] takeInputLong(int n){
        long[] input = new long[n];
        for(int i=0;i<n;i++){
            input[i] = sc.nextInt();
        }
        return input;
    }

}
