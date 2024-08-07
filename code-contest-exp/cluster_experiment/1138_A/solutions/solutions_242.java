import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
public class temp {

	public static void main(String args[] ) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int a[] = new int[n];
        for(int i=0;i<n;i++) a[i] = sc.nextInt();
        int count = 1,max=0,temp=0;
        for(int i=1;i<n;i++) {
        	if(a[i]==a[i-1]) count++;
        	else {
        		max = Math.max(max, Math.min(temp,count));
        		temp = count;
        		count = 1;
        	}
        }
        max = Math.max(max, Math.min(temp,count));
        System.out.println(max*2);
    }
}