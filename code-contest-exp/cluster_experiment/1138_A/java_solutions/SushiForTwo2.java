import java.util.*;
public class SushiForTwo2 {
    static String str1="";
    static String str2="";
	public static void find(int a)
	{
		
		for(int i=0;i<a;i++)
		{
			str1=str1+'1';
			str2=str2+'2';
		}
		String temp=str1;
		str1=str1+str2;
		str2=str2+temp;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
          Scanner sc=new Scanner(System.in);
          int x=sc.nextInt();
          int arr[]=new int[x],j=0;
          String str="";
          for(int i=0;i<x;i++)
          {
        	  int a=sc.nextInt();
        	  str=str+String.valueOf(a);
          }
          for(int i=1;i<x/2;i++)
          {
        	  find(i);
        	  if(str.contains(str1)||str.contains(str2))
        	  {
        		  int len=i*2;
        		  arr[j]=len;
        		  j++;
                                               str1="";
		str2="";
        	  }
          }
          Arrays.sort(arr);

System.out.println(arr[x-1]);
          
	}

}














