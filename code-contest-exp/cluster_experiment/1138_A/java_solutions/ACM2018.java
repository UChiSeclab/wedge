import java.util.Scanner;

public class ACM2018 {

	public static void main(String[] args) {
		int i = 1;
		Scanner in = new Scanner(System.in);
    	i = in.nextInt();
		int[] Myarray = new int[i];
        int ones = 0;
        int min1 = 10000000;
        int min2 = 10000000;
        int twos = 0;
        for(int k=0;k<i;k++) {Myarray[k] = in.nextInt();}
        in.close();
        for(int k=i-1;k>=1;k--) {
        	if(Myarray[k] == Myarray[k-1] && Myarray[k] == 1) {ones++;}
        	if(Myarray[k] == Myarray[k-1] && Myarray[k] == 2) {twos++;} 
        	if(Myarray[k] == 1 && Myarray[k-1] == 2) {if(ones<min1) {min1=ones; ones=0;}}
        	if(Myarray[k] == 2 && Myarray[k-1] == 1) {if(twos<min2 && twos != 0) {min2=twos; twos=0;}}
        }
        if(min1 == 10000000 || min2 == 10000000) {System.out.println(2);}
        else if(min1<=min2) {
        	System.out.println((min1+1)*2);
        }
        else if(min1>min2) {
        	System.out.println((min2+1)*2);
        }
        
      
	}

	}
