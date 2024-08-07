/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Centrino
 */
public class pp {
    public static void main(String[] args) {
    Scanner sc = new Scanner (System.in);
   int n = sc.nextInt();
   int [] a = new int [n];
   HashMap<Integer,Integer> m = new HashMap<>();
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
            if(m.containsKey(a[i]))
                m.put(a[i], m.get(a[i])+1);
            else m.put(a[i], 1);
        }
        int c=0;
        for (Integer i :m.keySet()) {
            if (i==1 && m.get(i)>= 2)
                c+=2;
            else if (i==1)
                c++;
            else if(m.get(i)>=3 && !m.containsKey(i+1)&& !m.containsKey(i-1))
                c+=3;
            else if(m.get(i)>=2 && (m.containsKey(i+1)|| m.containsKey(i-1))){
                c+=2;
               
            }
            
            else
                c++;
        }
        System.out.println(c);
    
        
        
    }
}