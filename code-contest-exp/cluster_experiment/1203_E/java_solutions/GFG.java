//https://www.geeksforgeeks.org/counting-frequencies-of-array-elements
import java.util.*;
import java.io.*;
import java.math.BigInteger;
public class GFG {
    static void countFreq(int arr[], int n) 
    { 
        Map<Integer, Integer> mp = new HashMap<>(); 
  
        // Traverse through array elements and 
        // count frequencies 
        for (int i = 0; i < n; i++) 
        { 
            if (mp.containsKey(arr[i]))  
            { 
                mp.put(arr[i], mp.get(arr[i]) + 1); 
            }  
            else
            { 
                mp.put(arr[i], 1); 
            } 
        } 
        int sum=0;
        for (Map.Entry<Integer, Integer> entry : mp.entrySet()) 
        { 
            if(entry.getKey()==1){
            sum=sum+((entry.getValue()<3)?entry.getValue():2);continue;}
            sum=sum+((entry.getValue()<4)?entry.getValue():3);
        }
        System.out.println(sum);
    }
      public static void main (String[] args){
         Scanner sc=new Scanner(System.in); 
                int n=sc.nextInt();
                int arr[]=new int[n];
                for(int i=0;i<n;i++)
                arr[i]=sc.nextInt();
                countFreq(arr,n);
        }
      }