/*package whatever //do not write package name here */

import java.io.*;
import java.util.*;
public class GFG {
	public static void main (String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();
		while(t-- > 0){
		    int n = scn.nextInt();
		    scn.nextLine();
		    String str = scn.nextLine();
		    int d_arr[] = new int[n];
		    int k_arr[] = new int[n];
		    
		    if(str.charAt(0) == 'K'){
		        k_arr[0] = 1;
		    }
		    if(str.charAt(0) == 'D'){
		        d_arr[0] = 1;
		    }
		    
		    HashMap<Double, Integer> map = new HashMap<>();
		    for(int i=1; i<n; i++){
		        if(str.charAt(i) == 'K'){
		            k_arr[i] = k_arr[i-1] + 1;
		        }
		        else{
		            k_arr[i] = k_arr[i-1];
		        }
		        
		        if(str.charAt(i) == 'D'){
		            d_arr[i] = d_arr[i-1] + 1;
		        }
		        else{
		            d_arr[i] = d_arr[i-1];
		        }
		        
		        int num = gcd(k_arr[i], d_arr[i]);
		        
		        int k = k_arr[i] / num;
		        int d = d_arr[i] / num;
		        
		        double temp;
		        if(k == 0){
		            temp = Integer.MIN_VALUE;
		        }
		        else{
		            temp = (1.0 * d)/k;
		        }
		        
		      //  String temp = k + d;
		        
		        if(map.containsKey(temp) == false){
		            map.put(temp, 1);
		        }
		        else{
		            map.put(temp,  map.get(temp) + 1);
		        }
		        
		        System.out.print(map.get(temp) + " ");
		    }
		    

		    
		    for(int i=0; i<n; i++){
		        
		    }
		    
		  //  System.out.println(map);
		  System.out.println();;
		    
		    
		}
		
	}
	
	public static int gcd(int a, int b){
	    if(b ==0){
	        return a;
	    }
	    
	    return gcd(b, a%b);
	}
}