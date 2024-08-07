import java.util.*;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    Scanner input=new Scanner(System.in);
    while(input.hasNext()){
    	int n=input.nextInt();
    	int x[]=new int [n+1];
    	int s[]=new int [n+1];
    	for(int t=1;t<=n;t++){
    		s[t]=0;
    	}
    	for(int i=1;i<=n;i++){
    		x[i]=input.nextInt();
    	}
    	int k=1;
    	s[k]=1;
    	for(int j=2;j<=n;j++){
    		if(x[j]==x[j-1]){
    			s[k]++;
    		}else{
    			k++;
    			s[k]++;
    		}
    	}
    	int max=Math.min(s[1], s[2]);
    	for(int h=3;h<=k;h++){
    		if(Math.min(s[h-1],s[h])>max){
    			max=Math.min(s[h-1],s[h]);
    		}
    	}
    	System.out.println(max*2);
    }
	}

}