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
			bw.close();
	
}

// public static Pair(){
	//    int x;
   //    int y;
	
   //    public
   // }
	
	public static void solve() throws IOException{
		// StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(br.readLine());
		// int n = Integer.parseInt(st.nextToken());
		
		// int[] arr = new int[];
      String s = br.readLine();
		StringBuilder sb = new StringBuilder();
      int cntd=0,cntk=0;
		
      HashMap<String,Integer> map = new HashMap<>();
		
      for(int i=0;i<s.length();i++){
			char ch = s.charAt(i);
			
         if(ch == 'D'){
				cntd++;
         }else{
				cntk++;
         }
         int hcf = gcd(cntd,cntk);
			
         String str = (cntd/hcf)+"#"+(cntk/hcf);
         int val = map.getOrDefault(str, 0);
         val++;
         map.put(str,val);
			// sb.append(val + " ");
         // System.out.print(val + " ");
			bw.write(val + " ");
			
      }
		
		bw.write("\n");
		// bw.close();
		
		// sb.append("\n");
      // System.out.print(sb);
		
		
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