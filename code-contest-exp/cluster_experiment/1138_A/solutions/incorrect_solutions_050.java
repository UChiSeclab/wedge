import java.util.Scanner;

public class Practice {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int a, b; //a and b
		int longest = 1;
		int last = 1;
		int current = 1;
		int result;
		
		int[] arr = new int[n];
		for(int i = 0; i < n; i++)
			arr[i] = scan.nextInt();
		
		
		
		for(int i = 0; i < n-1; i++) {
			a = arr[i];
			b = arr[i+1];
			if(a == b)
				current ++;
			else {
				if(current > last) 
					result = last;
				else 
					result = current;
				if(result > longest)
					longest = result;
					last = current;
					current = 1;
				
			}
		}
		if(current > last) {
			result = last;
			if(result > longest)
				longest = result;
			last = current;
			current = 1;
		}
		
		System.out.println(longest * 2);
		scan.close();
		

	}

}
