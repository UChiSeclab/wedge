import java.util.*;
import java.lang.*;
import java.io.*;

public class C_Diluc_and_Kaeya{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static void main (String[] args) throws java.lang.Exception
	{
    	
		int t = Integer.parseInt(br.readLine());

		for(int i=0;i<t;i++){
		solve();
		}
	}

	public static void solve() throws IOException{
		// StringTokenizer st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(br.readLine());
		// int n = Integer.parseInt(st.nextToken());

		// int[] arr = new int[];
      String s = br.readLine();
      int cntd=0,cntk=0;

      for(int i=0;i<s.length();i++){
         char ch = s.charAt(i);
         if(ch == 'D'){
            cntd++;
         }else{
            cntk++;
         }

         // System.out.println(cntk + " " + cntd);
         if(cntd == 0){
            System.out.print(cntk + " ");
         }else if(cntk==0){
            System.out.print(cntd + " ");
         }else{
            int hcf = gcd(cntd,cntk);
            if(hcf == 1){
               System.out.print(hcf + " ");
            }else{
               int small = Math.min(cntk,cntd);
               // int large = Math.max(cntk,cntd);
               // int total = small+large;

               // int packs = total/small;

               // for(int i=2;i<=small;i++){
               //    if(cntk%i == 0 && cntd%i == 0){
               //       System.out.println();
               //    }
               // }
               System.out.print(small + " ");
            }
         }
         
      }
      System.out.println();
		// for(int i=0;i<arr.length;i++){
		// 	arr[i] = Integer.parseInt(st.nextToken());
		// }

	}

   public  static int gcd(int a, int b)
   {
     if (b == 0)
       return a;
     return gcd(b, a % b);
   }

	// public static void printArr(int[] arr){
	// 	for(int i = 0;i<arr.length;i++){
	// 		System.out.print(arr[i] + " ");
	// 	}
	// 	System.out.println();
	// }

	// public static void print2DArr(int[][] arr){
	// 	for(int i = 0;i<arr.length;i++){
	// 		for(int j=0;j<arr[0].length;j++){
	// 			System.out.print(arr[i][j] + " ");
	// 		}
	// 		System.out.println();
	// 	}
	// }
}