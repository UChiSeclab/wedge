import java.util.*;
public class c724 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		System.out.println(2/0);
//		System.out.println(Math.max(2, 2));
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for(int o = 0 ; o<t;o++) {
			int n = sc.nextInt();
			String str = sc.next();
			int d = 0;
			int k = 0;
			int[][] arr = new int [2][n];
			for(int i = 0 ; i<n;i++) {
				if(str.charAt(i) == 'D') {
					d++;
				}else {
					k++;
				}
			arr[0][i] = d;
			arr[1][i] = k;
				int f = 0;
			int min = Math.min(d, k);
			int max = Math.max(d, k);
			if(min == 0) {
				System.out.print(max + " ");
			}else {
				if(max%min==0 && min!=1) {
					if(arr[1][min-1]==0){
						System.out.print("1 ");
					continue;
					}
					float x = 0;
					x = arr[0][min-1]/arr[1][min-1];
					
					for(int j = min-1; j<i;j+=min) {
						if(arr[0][j]/arr[1][j]!=x) {
							f = 1;
							 System.out.print("1 ");
						break;
						}
						
						
					}
					if(f==0) {
						System.out.print(min + " ");
//					continue;
					 }
					
					
				}else{
					System.out.print("1 ");
				}
			}
			
			
			}
			
			System.out.println();
		}
		
		
	}

}
