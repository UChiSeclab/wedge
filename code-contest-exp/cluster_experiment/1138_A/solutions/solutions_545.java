import java.util.*;
import java.io.*;
import java.util.Arrays; 
import java.util.HashSet; 
import java.util.Set; 

public class EjerC3 {


  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    

    int n, sushi = 0;
    
    

    n = sc.nextInt();
    
    int[] rollo = new int[n+1];

    for (int i = 0; i < n; i++) {
        sushi = sc.nextInt();
        rollo[i] = sushi;
    }
    
    int ans=0,act1=0,act2=0,cont1=0,cont2=0;

    if (n == 2) {
        if (rollo[0] != rollo[1]) {
            System.out.println("2");
            System.exit(0);
        }else{
            System.out.println("0");
            System.exit(0);
        }
    }
    

    for(int i=1;i<=n;i++)
	{
		if(rollo[i]==rollo[i-1]){
			if(rollo[i]==1){
				act1=i;
                cont1++;
            }
			else{
				act2=i;
				cont2++;
			}
		}
		else{
			ans=Math.max(ans,Math.min(cont1,cont2));
			if(rollo[i]==1){
				cont1=1;
				
			}
			else{
				cont2=1;
			}
		}
	}
	ans=(Math.max(ans,Math.min(cont1,cont2)))*2;


    System.out.println(ans);



    
    
  }
}