import java.util.Scanner;

public class Sushi {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numberPieces = sc.nextInt();
		int[] array = new int[numberPieces];
		boolean check = false;
		int eelCount = 0;
		int tunaCount = 0;
		int max = 0;
		for(int i =0;i<numberPieces;i++) {
			array[i] = sc.nextInt();
			
			if(array[i]==1) {
				if(check) {
					tunaCount = 1;
					check = false;
				}
				else {
					tunaCount++;
				}
			} else {
				if(!check) {
					eelCount = 1;
					check = true;
				} else {
					eelCount++;
				}
			}
			
			int get = 0;
			if(tunaCount<eelCount) {
				get = 2*tunaCount;
			} else {
				get = 2*eelCount;
			}
			
			if(!(get<=max)) {
				max = get;
			}
			
		}
		
		System.out.println(max);
	}

}
  	     	  	 		   				 			 				