import java.util.Scanner;

public class ACM2018 {

	public static void main(String[] args) {
		int i = 1;
		Scanner in = new Scanner(System.in);
    	i = in.nextInt();
		int[] Myarray = new int[i];
        int ones = 0;
        int twos = 0;
        for(int k=0;k<i;k++) {Myarray[k] = in.nextInt();}
        in.close();
        for(int k=i-1;k>=1;k--) {
        	if(Myarray[k] == Myarray[k-1] && Myarray[k] == 1) {ones++;}
        	if(Myarray[k] == Myarray[k-1] && Myarray[k] == 2) {twos++;} 	
        }
        if(twos<ones) {
        	System.out.println((twos+1)*2);
        }
        if(twos>ones) {
        	System.out.println((ones+1)*2);
        }
        else
        	System.out.println((ones+1)*2);
        
	}

}
