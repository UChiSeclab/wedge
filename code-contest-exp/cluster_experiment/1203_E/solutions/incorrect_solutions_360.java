import java.util.*;
import java.math.*;
public class Boxers{
	public static void main(String args[]){
		Scanner s=new Scanner(System.in);
		int n=s.nextInt();
		int[] arr=new int[n];
		for(int i=0;i<n;i++){
			arr[i]=s.nextInt();
		}
		HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
		for(int i=0;i<n;i++){
			if(map.containsKey(arr[i])){
				int w=map.get(arr[i]);
				if(arr[i]==1){
					if(w==1){
						map.put(arr[i],3);
						arr[i]=arr[i]+1;
					}
				}else{
					if(w==2){
						map.put(arr[i],3);
						arr[i]=arr[i]+1;
					}else if(w==1){
						map.put(arr[i],2);
						arr[i]=arr[i]-1;
					}
				}
			}else{
				map.put(arr[i],1);
			}
		}
		int count=0;
		HashSet<Integer> set=new HashSet<Integer>();
		for(int i=0;i<n;i++){
			//System.out.print(arr[i]+" ");
			if(!set.contains(arr[i])){
				set.add(arr[i]);
				count++;
			}
		}
		System.out.print(count);
	}
}