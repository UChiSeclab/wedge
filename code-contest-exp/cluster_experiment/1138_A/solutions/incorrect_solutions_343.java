import java.util.Scanner;
public class ProblemC 
{
	
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		
		int desperdicio= scan.nextInt();
		int[] arr= new int[desperdicio];
		
		for(int i=0; i<desperdicio; i++)
		{
			arr[i]=scan.nextInt();
		}
		
		int flag=0;
		for(int i=1; i<desperdicio; i++)
		{
			if(arr[0]==arr[i])
			{
				flag=1;
				i=desperdicio;
			}
		}
		
		if(flag==0) {
			System.out.println("2");
			System.exit(0);
		}
		//hacia abajo
		flag=0;
		for(int i=desperdicio-1; i>0; i--)
		{
			if(arr[desperdicio-1]==arr[i-1])
			{
				flag=1;
				i=-10;
			}
		}
		
		if(flag==0) {
			System.out.println("2");
			System.exit(0);
		}
		
		
		
		
		int s1=1, s2=1;
		
		int s1m=0, s2m=0;
		
		int cambio=0;
		int bandera=0;
		for(int i=0; i<desperdicio-1; i++)
		{
			
			if(bandera==1) {
				cambio=1;
			}
	
			if(arr[i]==arr[i+1] && cambio==0) {
				s1=s1+1;
		
			}else {
				
				bandera=1;
				
				if(s1m<s1) {
					s1m=s1;	
					
				}
				s1=1;
			}
			
			
			if(arr[i]==arr[i+1]&& cambio ==1)
			{
				
				s2=s2+1;
			}else {
				if(cambio==1)
				{
					cambio=0;
					bandera=0;
					if(s2m<s2) {
						s2m=s2;	
						
					}
					
					s2=1;
				}
				
			}
			
		}
	
	
	
		
		
		
		if(s1m==0 || s2m==0) {
			System.out.println("0");
			System.exit(0);
		}

		
		
		if(s2m<s2) {
			s2m=s2;	
			
		}
		
		if(s1m<s1) {
			s1m=s1;	
			
		}
		
		if(s1m>s2m) System.out.println(s2m*2);
		else {
			System.out.println(s1m*2);
		}
		
	}

}
  					 	 	    	 	 	 	   	 		