import java.util.*;
import java.lang.*;
import java.io.*;

public class Solution{
    public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int[] arr = new int[n];
		int[] arr1 = new int[n];
		for(int i = 0; i < n; i++){
		    arr[i] = scan.nextInt();
		}
		for(int i = 0; i < n - 1; i++){
		    arr1[i] = Math.abs(arr[i] - arr[i + 1]);
		}
		arr1[n-1] = 1;
		int max = 0;
		boolean first = true;
		int index = -1;
		int left = 0;
		int right = 0;
		for(int i = 0; i < n; i++){
		    if(arr1[i] == 1 && first == true){
		        index = i;
		        left = i;
		        first = false;
		        continue;
		    }
		    if(arr1[i] == 1 && first == false){
		        right = i - index - 1;
		        if(max < (Math.min(left,right)+1)*2){
		            max = (Math.min(left,right)+1)*2;
		        }
		        left = right;
		        index = i;
		    }
		}
		System.out.println(max);
	}
}