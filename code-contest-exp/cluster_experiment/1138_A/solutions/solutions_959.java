import java.util.Scanner;

public class A{
	static Scanner fr = new Scanner(System.in);
	
	public static void main(String args[]){
		int n = fr.nextInt();
		int arr[] = nextArray(n);
		int cont_1 = 0,cont_2 = 0;
		int ans = 0;
		boolean b = false;
		int prev = 0;
		int temp = 0;
		for(int i=0;i<n;i++){
			if(arr[i] == 1){
				cont_1 ++;
				if(cont_2 != 0){
					prev = cont_2;
				}
				cont_2 = 0;
				temp = Math.min(cont_1, prev);
			}else if(arr[i] == 2){
				cont_2 ++;
				if(cont_1 != 0){
					prev = cont_1;
				}
				cont_1 = 0;
				temp = Math.min(cont_2, prev);
			}
			ans = temp * 2 > ans ? temp * 2 : ans;
		}
		
		System.out.println(ans);
	}
	
	static int[] nextArray(int n){
		int arr[] = new int[n];
		for(int i=0;i<n;i++){
			arr[i] = fr.nextInt();
		}
		return arr;
	}
}
