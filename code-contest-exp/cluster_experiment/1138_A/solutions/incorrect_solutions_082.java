import java.util.Scanner;

public class nextround {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		int sushi = scan.nextInt();
		int count = 1;
		int count2= 1;
		int ones = 0;
		int twos = 0;
		int max = 0;
		int[] list = new int[sushi];
		for(int i =0; i<sushi;i++){
			list[i] = scan.nextInt();
			if(list[i]==1){
				ones++;
			}
			else{ twos++;
		}}
			if(ones>twos){
				max=twos;
			}
			else{max = ones;}
		for(int x = 0;x<sushi-1;x++){

			if(count>count2){
				count2=count;
			}
			if(count2>max){
				count2=max;
			}
			if(list[x] == list[x+1]){
				count++;
			}
			else{
				count = 1;
			}

		}
		if(count2>sushi/2){
			System.out.println(sushi);
		}
		else{
			System.out.println(count2*2);
		}
		}
}
