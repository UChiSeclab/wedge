import java.util.Scanner;


public class sushi {
    public static Scanner sc = new Scanner(System.in);
    private static int[][] memo;
    private static String[] b;
    private static int[] res;

    private static int num = 0, longest = 1, temp = 0,m = 0;

    public static void main (String[] args){
 
        num = sc.nextInt();
        sc.nextLine();
        String a = sc.nextLine();
        b = a.split(" ");
        
        String eat = b[0];
        memo = new int[num][2];

        for(int i = 1; i < num ; i ++){
            if(b[i].equals(eat)){
                longest++;
                if(i == num -1){
                    memo[m][0] = Integer.parseInt(eat);
                    memo[m][1] = longest;
                    longest = 1;
                    m++;
                    eat = b[i];

                }
            } else {
                memo[m][0] = Integer.parseInt(eat);
                memo[m][1] = longest;
                longest = 1;
                m++;
                eat = b[i];
            }
        
        }
        /*for(int i = 0; i < m; i ++){
            System.out.print(memo[i][0]);
            System.out.print(memo[i][1]);
            System.out.println();
        }
        */
        res = new int[m];
        for(int i = 0; i < m - 1; i ++){
            if(memo[i][1] <= memo[i+1][1]){
                res[i] = memo[i][1] * 2;
            }else{
                res[i] = memo[i+1][1] * 2;
            }
        }
        //System.out.print("~~~~~~");

        for(int i = 0; i < m; i ++){
           // System.out.print(res[i]);
        }
        
        for(int i = 0; i < m; i ++){
            if (res[i] > temp){
                temp = res[i];
            }
        }
        System.out.print(temp);

    }
}

	 	 			  	 	  	 		 	 				   	 	