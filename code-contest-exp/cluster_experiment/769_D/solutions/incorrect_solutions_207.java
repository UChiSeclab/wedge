import java.util.*;

public class Main
{
	public static String tenInTwo(int num){
		String num2="";
		int b;
		while(num!=0){
			b=num%2;
			num2+=b;
			num/=2;
		}
		if(num2.isEmpty()){num2="0";}
		System.out.println(new StringBuffer(num2).reverse().toString());
		return new StringBuffer(num2).reverse().toString();
	}
	public static boolean whyRavno(String num1,String num2, int k){
		int length,raz= 0;
		int size1= num1.length();
		int size2= num2.length();
		if(size1>size2){
			length= size2;
			raz= size1-size2;
		}else if(size1<size2){
			length = size1;
			raz =size2- size1;
		}else{
			length = size1;
		}
		for(int i = 0; i<length;i++){
			if(num1.charAt(i)!=num2.charAt(i)){
				raz++;
			}
		}
		if(num1.length()==0 && num1.charAt(0)=='0' && num2.charAt(num2.length()-1)=='0'){
			raz++;
		}else if(num2.length()==0&&num2.charAt(0)==0 && num1.charAt(num1.length()-1)=='0'){
			raz++;
		}
		return raz==k;
	}
	public static void main(String[] args)
	{
		int l=0,l2=1,col=0;
		ArrayList<String> list = new ArrayList<String>();
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		for(int i= 0;i<n;i++){
			list.add(tenInTwo(sc.nextInt()));
		}
		//System.out.println("end");
		while(true){
			if(whyRavno(list.get(l),list.get(l2),k)){
				col++;
				System.out.println("col++ " + l + " " + l2);
			}
			l2++;
			if(l2==n){
				l++;
				l2=l+1;
				//System.out.println("l++");
			}
			if(l==n-1){
				//System.out.println("break");
				break;
			}
		}
		System.out.println(col);
	}
}