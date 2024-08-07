import java.util.*;

public class Main{
    public static void main(String[] args){
    	Scanner sc=new Scanner(System.in);
    	int num=sc.nextInt();
    	int[] arr=new int[num];
    	int max=num/2;
    	
    	for(int i=0;i<num;i++){
    		arr[i]=sc.nextInt();
    	}
    	for(int i=max;i>0;i--){
    		for(int j=0;j<num-2*i;j++){
    			boolean ok=true;
    			for(int z=1;z<i;z++){
    				if(arr[j+z]!=arr[j+z-1]){
    					ok=false;
    					break;
    				}
    			}
    			if(ok&&arr[j+i]!=arr[j+i-1]){
    				for(int z=1;z<i;z++){
        				if(arr[j+z+i]!=arr[j+z+i-1]){
        					ok=false;
        					break;
        				}
        			}
    			}
    			if(ok){
    				System.out.println(i*2);
    				return;
    			}
    		}
    	}
    	System.out.println(0);
    }
}