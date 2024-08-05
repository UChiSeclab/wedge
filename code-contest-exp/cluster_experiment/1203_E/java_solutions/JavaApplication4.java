/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.math.MathContext;
import java.util.Arrays;

/*
 *
 * @author thaer
 */
public class JavaApplication4 {
    /**
     * @param args the command line arguments
     */
   
    
    public static int[] freq(int[]boxers){
    int arr[]=new int[15001];
        for (int i = 0; i < boxers.length; i++) {
            arr[boxers[i]]++;
       // System.out.println(" "+boxers[i] );
        }
    return arr;}
    
    public static int wof(int freq[]){
    int cout=0;
        for (int i = 1; i < freq.length ; i++) {
            if(freq[i]>0){cout++;}
          //  System.out.println(" "+freq[i] );
        }
/*    6
1 1 1 4 4 4
*/
        
    return cout;}
    
    public static void main(String[] args) {
        // TODO code application logic here
       Scanner in=new Scanner(System.in);
       int n,boxers[],freq[];
       n=in.nextInt();
       int arr[]=new int[n];
       boxers = new int[n] ;
        for (int i = 0; i < boxers.length; i++) {
            boxers[i]=in.nextInt();
        }
       freq=new int[150005];
       freq=freq(boxers);
        for (int i = 1; i < boxers.length; i++) {
            if(freq[boxers[i]]/3>=1){
            freq[boxers[i]-1]++;
            freq[boxers[i]+1]++;
            
            }
            else if(freq[boxers[i]]==2){
            if(freq[boxers[i]+1]==0){freq[boxers[i]+1]++;}
            else{freq[boxers[i]-1]++;}
            }
        }
       
        //wof(freq);
      System.out.println(wof(freq));
    }
    
}