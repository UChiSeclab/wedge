import java.util.*;
import java.io.*;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			int n = sc.nextInt();
			int [] a = new int [n];
			for(int i=0;i<n;i++) {
				a[i]=sc.nextInt();
			}
			int temp = a[0];
			int max = 0;
			int [] b = new int [n];
			int k=0;
			for(int i=0;i<n;i++) {
				if(a[i]==temp) {
					b[k]++;
				}
				else {
					temp=a[i];
					k++;
					b[k]++;
				}
			}
			for(int i=0;i<k;i++) {
				if(s(b[i],b[i+1])>max) {
					max=s(b[i],b[i+1]);
				}
			}
			System.out.println(2*max);
		}
	}
	public static int s(int s1,int s2) {
		if(s1>s2) {
			return s2;
		}
		else {
			return s1;
		}
	}
}
