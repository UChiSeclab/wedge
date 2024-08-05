import java.util.Scanner;
public class ProblemaCsecondtry 
{
private static long arr[];
 private static int arr1[];
 private static int arr2[];
 private static int length;
 static Scanner scan = new Scanner(System.in);
 private static int theBiggestMean;
 

 
	public static void main(String[] args) 
	{
		
		
		length = scan.nextInt();
		arr = new long [length];
		arr1 = new int [length+10];
		arr2 = new int [length+10];
		
		readMatrix();
		
		if(length==2)
		{
			System.out.println("2");
			System.exit(0);
		}
		getarr1();
		getarr2();
		
	
		int theOneWithZero = getTheOneWithZero();
		
		if(theOneWithZero==1) getTheLargestArr1();
		if(theOneWithZero==2) getTheLargestArr2();
		
		
		
	
			//System.out.println(theBiggestMean*2);
		
	
		/*
		  
		  int c=0;
	
		
		while(arr1[c]!=-1)
		{
			System.out.println(arr1[c]);
			c++;
		}
		
		
		System.out.println("\n\n");
		
		 c=0;
		
		while(arr2[c]!=-1)
		{
			System.out.println(arr2[c]);
			c++;
		}
			
		*/
		
		System.out.println(theBiggestMean*2);
		
	}
	
	
	
	
	
	public static void readMatrix() 
	{
		for(int i = 0; i < length ; i++)
		{
			arr[i] = scan.nextInt();
		}
	}
	
	public static void getarr1()
	{
		
		int matrix1 = 0, matrix2 = 0 ;
	    int counter1=0,counter2=0;
		int flagtoSave=0;
		
		for(int i=0 ; i<length ; i++)
		{
			if(arr[i]==1)
			{
				flagtoSave=0;
				matrix1++;
			}
			else {
				if(flagtoSave==0) 
				{
					arr1[counter1]= matrix1;
					matrix1=0;
					flagtoSave=1;
					counter1++;
				}
			
			}
		}
		
	

		
		if(matrix1>0)
		{
			arr1[counter1]=matrix1;
			counter1++;
		}
		
		arr1[counter1]=-1;
		
		
	}

	public static void getarr2()
	{
		
		int matrix1 = 0, matrix2 = 0 ;
	    int counter1=0,counter2=0;
		int flagtoSave=0;
		
		for(int i=0 ; i<length ; i++)
		{
			if(arr[i]==2)
			{
				flagtoSave=0;
				matrix1++;
			}
			else {
				if(flagtoSave==0) 
				{
					arr2[counter1]= matrix1;
					matrix1=0;
					flagtoSave=1;
					counter1++;
				}
			
			}
		}
		


		
		if(matrix1>0)
		{
			arr2[counter1]=matrix1;
			counter1++;
		}
		
		arr2[counter1]=-1;
		
		
	}

	public static int getTheOneWithZero() 

	
	{
		/*
		 * El que tengaa el cero se comparará con uno hacia abajo
		 * Después preguntará si existe igual a él, si es así se compara. Esto antes que se llegué a -1
		*/
		
		//Identify who has 0
		
		int flag = 0;
		
		if(arr1[0]==0) flag=1;
		if(arr2[0]==0) flag=2;
		
		return flag;
		
		
		
		
		
	}
	
	public static void getTheLargestArr1()
	{
		
		
	
		int c=1;
		int tempMean=0;
		theBiggestMean=0;
		while(arr1[c] != -1)
		{
			//tempMean = ( arr1[c] + arr2[c-1] ) / 2;
			
			if(arr1[c]>arr2[c-1]) tempMean=arr1[c-1];
			else tempMean=arr1[c];
			
			if(theBiggestMean < tempMean)
			{
				theBiggestMean= tempMean;
			}
	
			
			if(arr2[c]!=-1)
			{
				//tempMean = ( arr1[c] + arr2[c] ) / 2;
				
				if(arr2[c]>arr1[c]) tempMean=arr1[c];
				else tempMean=arr2[c];
				
				if(theBiggestMean < tempMean)
				{
					theBiggestMean= tempMean;
				}
			}
			c++;
		}
		
		
	}
	
	public static void getTheLargestArr2()
	{
	
		
		int c=1;
		int tempMean=0;
		theBiggestMean=0;
		while(arr2[c] != -1)
		{
			//tempMean =( arr2[c] + arr1[c-1] ) / 2;
			
			if(arr2[c]>arr1[c-1]) tempMean=arr1[c-1];
			else tempMean=arr2[c];
			
			if(theBiggestMean < tempMean)
			{
				theBiggestMean= tempMean;
			}
	
			
			if(arr1[c]!=-1)
			{
				//tempMean = ( arr2[c] + arr1[c] ) / 2;
				
				if(arr2[c]>arr1[c]) tempMean=arr1[c];
				else tempMean=arr2[c];
				
				if(theBiggestMean < tempMean)
				{
					theBiggestMean = tempMean;
				}
			}
			c++;
		}
	}
	
}