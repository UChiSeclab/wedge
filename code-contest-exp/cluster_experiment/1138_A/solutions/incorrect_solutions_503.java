import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		while(in.hasNext()) {
			int n=in.nextInt();
			int[] a=new int[100005];
			int[] b=new int[100005];
			for(int i=0;i<n;i++) {
				a[i]=in.nextInt();
			}
			int k=0;
			for(int i=1;i<n;i++) {
				b[i]=1;
				if(a[i]==a[i-1]) {
					b[k]++;
				}else {
					k++;
				}
			}
			int sum=0;
			for(int q=1;q<=k;q++) {
				int m=Math.min(b[q], b[q-1]);
				sum=Math.max(sum,m);
			}
			System.out.println(sum*2);
		}
	}

}
