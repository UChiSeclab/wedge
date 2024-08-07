/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Scanner;

/**
 *
 * @author thaer
 */
public class JavaApplication5 {

    /**
     * @param args the command line arguments
     */
    static Scanner in=new  Scanner(System.in);
    public static void main(String[] args) {
        // TODO code application logic herelll
        int n=in.nextInt(),arr[]=new int[n],accomulator[]=new int[1500070],result=0;
        for (int i = 0; i < n; i++) {
            arr[i]=in.nextInt();
            accomulator[arr[i]]++;
        }
        for (int i = 1; i < 150001; i++) {
        if (accomulator[i-1]>0){
            result++;
        }else{
        if((accomulator[i]>0)){
        result++;accomulator[i]--;
        }else{
        if(accomulator[i+1]>0){
            result++;accomulator[i+1]--;
        }
        }
        }
        
        }
        System.out.println(result);
        
    }
    
}
