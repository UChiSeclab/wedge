import java.io.*;
import java.util.*;
 
public final class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n, f = 1, g = 0, re = 0, p = 0, t = 0, maxRe = 0;
        n = in.nextInt();
        
        for(int i = 0; i < n+1; i++){
          if(i < n){
          	p = t;
          	t = in.nextInt();
          }
        	
          if(i > 0 && i < n && t == p){
          	f++;
          } else {
          	re = Math.min(g, f) *2;
            maxRe = Math.max(maxRe, re);
          	g = f;
          	f = 1;
            
          }
        }
        System.out.println(maxRe);
    }
}