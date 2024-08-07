import java.util.*;

public class Main{
    public static void main(String[] args){
    	Scanner sc=new Scanner(System.in);
    	int num=sc.nextInt();
    	int[] arr=new int[num];
    	int max=1;
    	
    	int temp=0;
    	int count=1;
    	arr[0]=sc.nextInt();
    	for(int i=1;i<num;i++){
    		arr[i]=sc.nextInt();
    		if(arr[i]==arr[i-1]){
    			count++;
    		}
    		else{
    			if(Math.min(temp,count)>max)
    				max=Math.min(temp,count);
    			temp=count;
    			count=1;
    		}
    	}
    	if(Math.min(temp,count)>max)
			max=Math.min(temp,count);
    	System.out.println(max*2);
    }
}