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
        int myArray[] = new int[n+1];
        int tuna[] = new int [n+1],Eel[] = new int[n+1],k=0,j=0;
        
        for (int i = 0 ; i < n ; i ++){
            myArray[i] = in.nextInt();
            if(myArray[i]==1)
            {
                tuna[j]++;
                k++;
                
            }
            else{
                Eel[k]++;
                j++;
            }
            
        }
        int max1=-1,max2=-1;
        for(int i = 0 ; i < n ; i ++){
            max1 = Math.max(max1, tuna[i]);
            max2 = Math.max(max2, tuna[i]);
            
            
        }
        System.out.println(Math.min(max1, max2) * 2);
        
    }
    
}
