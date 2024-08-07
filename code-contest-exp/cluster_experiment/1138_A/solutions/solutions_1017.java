					/* package codechef; // don't place package name! */
					import java.util.*;
					import java.lang.*;
					import java.io.*;
					
					/* Name of the class has to be "Main" only if the class is public. */
					public class IUPC
					{
						public static void main (String[] args) throws java.lang.Exception
						{
		
							Scanner sc=new Scanner(System.in);
						int n=sc.nextInt();
						Integer a[]=new Integer[n+1];
						for(int i=1;i<=n;i++) {
						a[i]=sc.nextInt();	
						}
						int max1=0;
						int max=0;
						int max2=0;
						int total=0;
						a[0]=a[1];
						for(int i=1;i<=n;i++) {
						if(a[i]==1&&a[i]==a[i-1]) {max1++;
						max=Math.min(max1,max2);

						if(total<=max)total=max;}
						else if(a[i]==2&&a[i]==a[i-1]) {max2++;
						max=Math.min(max1,max2);

						if(total<=max)total=max;
						}
						else {
							if(a[i]==1) {
								while(i<=n&&a[i]==1) {
								i++;
									max1++;
								}
								max=Math.min(max1,max2);
								i--;
								if(total<=max)total=max;
										max2=0;}				
							else {
								while(i<=n&&a[i]==2) {
									i++;
								max2++;
								}
								i--;
								max=Math.min(max1,max2);

								if(total<=max)total=max;
								max1=0;	
								
							}
							
						}
						}
						if(total==0)total=1;
							System.out.println(total*2);
						}
					}
					
					
						