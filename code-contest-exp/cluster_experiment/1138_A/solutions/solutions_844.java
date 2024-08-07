/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
  
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int myArray[] = new int [n+1], myArray2[] = new int[n+1];
        boolean A = false,B = false;
        for (int i = 0 ; i < n ; i ++){
            myArray[i] = in.nextInt();
        
        }
        for(int i = 0 , j = 0; i < n ; i++){
            if(myArray[i] == 1){
                
               if(B){
                    j++;
                    B = false;
                }
                myArray2[j]++;
                A = true;
                
            }
            else if(myArray[i]==2){
                if(A){
                    j++;
                    A = false;
                }
                B = true;
                myArray2[j]++;
            }
            
        }
        
        
        int ans = -1;
        for(int i = 0 ; i < n ; i ++){
            
            ans = Math.max(Math.min(myArray2[i], myArray2[i+1]), ans);
            
            
        }
        System.out.println(ans * 2);
        
    }
    
}
