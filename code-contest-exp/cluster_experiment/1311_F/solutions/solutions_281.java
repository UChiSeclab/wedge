import java.util.Arrays;
import java.util.Scanner;
import java.util.Comparator;

public class MovingPoints {

		private static long sumaj(long[] arr,int index) {
			long result=0;
			while (index>=0) {
				result+=arr[index];
				index=(index&(index+1))-1;
			}
			return result;
		}
		
		private static void apdejt (long[] t,int index,int vv) {
			while (index<t.length) {
				t[index]+=vv;
				index=index|(index + 1);
			}
		}
		public static void main(String[] args) {
			Scanner in=new Scanner(System.in);
			int n=in.nextInt();
			int[][] pm=new int[n][2];
			int[][] vm=new int[n][2];
			for (int i=0;i<n;i++)
				pm[i][0]=in.nextInt();
			for (int i=0;i<n;i++) {
				pm[i][1]=in.nextInt();
				vm[i][0]=pm[i][1];
				vm[i][1]=i;
			}
			in.close();
			Arrays.sort(vm, new Comparator<int[]>() {
				public int compare(int[] a, int[] b) {
					return a[0] - b[0];
				}
			});
			int nss=0;
			int[][] finar=new int[n][2];
			for (int i=0;i<n;i++) {
				if (i>0 && vm[i][0]!=vm[i-1][0])
					nss++;
				finar[i][0]=nss;
				finar[i][1]=vm[i][1];
			}
			Arrays.sort(finar,new Comparator<int[]>() {
				public int compare(int[] a,int[] b) {
					return a[1]-b[1];
					}});
			
			for (int i=0; i<n;i++) {
				pm[i][1] = finar[i][0];
			}
			Arrays.sort(pm,new Comparator<int[]>() {
				public int compare(int[] a,int[] b) {
					return a[0]-b[0];
				}});
					
			long[] c=new long[n];
			long[] pts=new long[n];
			long totalsum=0;
			
			for (int i=0;i<n;i++) {
				int index=pm[i][1];
				totalsum+=sumaj(pts,index)*pm[i][0]-sumaj(c,index);
				apdejt(pts,index,1);
				//System.out.println();
				//System.out.println(totalsum);
				//System.out.println();
				//System.out.println("coordtsum");
				//for (int j=0;j<c.length;j++) {
					//System.out.print(c[j]);
				//}
				apdejt(c, index, pm[i][0]);
				//System.out.println();
				//System.out.println("pointsum");
				//for (int j=0;j<pts.length;j++) {
					//System.out.print(pts[j]);
				//}
			}
			//System.out.println();
			System.out.println(totalsum);
	 
		}
	}