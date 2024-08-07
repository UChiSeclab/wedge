import java.util.Scanner;


public class Sample1 {

	static int poss;
	static void isTrue(int num, int k){
		int count=0;
		while(num>0){
			int temp=num%2;
			if(temp==1){
				count++;
			}
			num=num/2;
		}
		if(count==k){
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n=sc.nextInt();
		int[] series = new int[n];
		int[] multiple = new int[100001];
		int k=sc.nextInt();
		for(int i=0;i<n;i++){
			series[i]=sc.nextInt();
			multiple[series[i]]++;
		}
		if(k==0){
			poss=0;
			for(int i=0;i<n;i++){
				for(int j=i+1;j<n;j++){
					if(series[i]<=series[j]){
						isTrue(series[i]^series[j], k);
					}
				}
			}
			System.out.println(poss);
		}
		else{
			poss=0;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(series[i]<series[j]){
						isTrue(series[i]^series[j], k);
					}
				}
			}
			System.out.println(poss);
		}
	}

}
