import java.util.Arrays;
import java.util.Scanner;
import java.util.Comparator;
 
 
public class kretanje {
	private static long zbir(long[] arr,int some) {
		long result=0;
		while (some>=0) {
			result+=arr[some];
			some=(some&(some+1))-1;
		}
		return result;
	}
	
	private static void poslije (long[] qqq,int some,int vv) {
		while (some<qqq.length) {
			qqq[some]+=vv;
			some=some|(some + 1);
		}
	}
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int n=in.nextInt();
		int[][] xx=new int[n][2];
		int[][] yy=new int[n][2];
		for (int i=0;i<n;i++)
			xx[i][0]=in.nextInt();
		for (int i=0;i<n;i++) {
			xx[i][1]=in.nextInt();
			yy[i][0]=xx[i][1];
			yy[i][1]=i;
		}
		in.close();
		Arrays.sort(yy, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});
		int somm=0;
		int[][] kraj=new int[n][2];
		for (int i=0;i<n;i++) {
			if (i>0 && yy[i][0]!=yy[i-1][0])
				somm++;
			kraj[i][0]=somm;
			kraj[i][1]=yy[i][1];
		}
		Arrays.sort(kraj,new Comparator<int[]>() {
			public int compare(int[] a,int[] b) {
				return a[1]-b[1];
				}});
		
		for (int i=0; i<n;i++) {
			xx[i][1] = kraj[i][0];
		}
		Arrays.sort(xx,new Comparator<int[]>() {
			public int compare(int[] a,int[] b) {
				return a[0]-b[0];
			}});
				
		long[] c=new long[n];
		long[] sooom=new long[n];
		long totalsum=0;
		
		for (int i=0;i<n;i++) {
			int some=xx[i][1];
			totalsum+=zbir(sooom,some)*xx[i][0]-zbir(c,some);
			poslije(sooom,some,1);
			//System.out.println();
			//System.out.println(totalsum);
			//System.out.println();
			//System.out.println("CRDT sum");
			//for (int j=0;j<c.length;j++) {
				//System.out.print(c[j]);
			//}
			poslije(c, some, xx[i][0]);
			//System.out.println();
			//System.out.println("Point sum");
			//for (int j=0;j<sooom.length;j++) {
				//System.out.print(sooom[j]);
			//}
		}
		//System.out.println();
		System.out.println(totalsum);
 
	}
}