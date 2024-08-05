import java.util.Scanner;

public class NumberD {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		
		int count = 0;
		
		int[] a = new int[n];
		long start = System.currentTimeMillis();
		for(int i = 0; i < n ; i++){
			a[i] = sc.nextInt();
		}
		
		sc.close();
		
		for(int i = 0; i < n ; i++){
			for(int j = i + 1; j < n; j++){
				if(check(a[i], a[j], k)){
					count+=1;
				}
			}
		}
			
		long finish = System.currentTimeMillis();
		System.out.println(finish-start);
		System.out.println(count);
		
	}
	
	public static boolean check(int x, int y, int k){
		int count = 0;
		int delta;
		String buf = "";
		String str1 = Integer.toBinaryString(x);
		String str2 = Integer.toBinaryString(y);
		
		if(str1.length() > str2.length()) {
			 delta = str1.length() - str2.length();
			
			buf="";
			for(int i = 0 ; i<delta; i++){
				buf=buf+0;
			}
			str2 = buf + str2;
		}
		if(str1.length() < str2.length()) {
			 delta = str2.length() - str1.length();
			
			buf="";
			for(int i = 0 ; i<delta; i++){
				buf=buf+0;
			}
			str1 = buf + str1;
		}
		
		
		for(int i = 0; i < str1.length(); i++){
			if(str1.charAt(i) != str2.charAt(i)) count+=1;
		}
		if(count == k) return true;
		
		return false;
	}

}
