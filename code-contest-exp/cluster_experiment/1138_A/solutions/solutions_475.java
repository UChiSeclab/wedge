import java.util.Scanner;

public class C{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int t, last, m = 0, c1 = 1, c2 = 0;
        last = sc.nextInt();
        for(int i = 1; i < n; i ++){
            t = sc.nextInt();
            if (t != last) {
                m = Math.max(Math.min(c1, c2) * 2, m);
                c2 = c1;
                c1 = 0;
            }
            last = t;
            c1++;
        }

        System.out.println(Math.max(Math.min(c1, c2) * 2, m));
    }

}

			   	 			  	      			 	  				