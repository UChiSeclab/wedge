
import static java.lang.Math.*;

import java.util.Scanner;


public class onebalgo {
	
	static long minsum=0;
	static int n;
	static dot[] overwrite=new dot[n];


	public static boolean samepace(dot i,dot j) {
		if(i.vel==j.vel)
			return true;
		else return false;
	}
	
	public static boolean waitornot(dot i,dot j) {
		if(i.vel>0 && j.vel<0 && i.pos>=0 && j.pos<0) 
			return false;
		if(i.vel>0 && j.vel<0 && i.pos<0 && j.pos>=0)
			return true;
		if(i.pos>j.pos && i.vel>j.vel)
			return false;
		if(i.pos>j.pos && i.vel<j.vel)
			return true;
		if(i.pos<j.pos && i.vel<j.vel)
			return false;
		if(i.pos<j.pos && i.vel>j.vel)
		    return true;
		return false;
	}
	
	static void enterhere(int x,int y)
	{
			minsum=minsum+Math.abs(x-y);
		
	}
	
	
	static void checker() {
		for(int i=0;i<n;i++) {
			dot doto2=overwrite[i];
			for(int j=0;j<n;j++) {
				if(i<=j && i==j) {
					System.out.println(overwrite[i].pos+""+overwrite[j].pos);
					dot doto3=overwrite[j];
					if(samepace(doto2,doto3)==true)
						enterhere(doto2.pos,doto3.pos);
					else if(waitornot(doto2, doto3)==false)
						enterhere(doto2.pos,doto3.pos);
					
				}
				
			}
		}
	}
	
	
	public static void main(String[] args) {
		Scanner isus= new Scanner(System.in);
		n=isus.nextInt();
		overwrite= new dot[n];
		for(int i=0;i<n;i++) {
			dot doto1=new dot();
			doto1.pos=isus.nextInt();
			overwrite[i]=doto1;

		}
		
		for(int i=0;i<n;i++) {
			int vel=isus.nextInt();
			overwrite[i].vel=vel;
		}
		checker();
		System.out.println(minsum);
}
	
}

class dot{
	int pos;
	int vel;
}