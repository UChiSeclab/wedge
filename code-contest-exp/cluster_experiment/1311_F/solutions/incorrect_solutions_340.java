import java.util.Scanner;
public class Movingpoints {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
Scanner s = new Scanner(System.in);
int n=s.nextInt();
while(n<2||n>200000) {
n=s.nextInt();
}
int [] pt = new int[n];
int [] vl = new int [pt.length];

for (int i=0;i<pt.length;i++) {
int m = s.nextInt();

while(m<1||m>100000000||duplicate(pt,m)==true) {
	m=s.nextInt();
}

pt[i]=m;
}
int temp1=0;
int temp2=0;

for(int j=0;j<vl.length;j++) {
	int k=s.nextInt();
	while(k<-100000000||k>100000000) {
		k=s.nextInt();
	}
	vl[j]=k;
}
for (int i = 0; i < pt.length; i++) 
{
    for (int j = i + 1; j < pt.length; j++) { 
        if (pt[i] > pt[j]) 
        {
            temp1 = pt[i];
            pt[i] = pt[j];
            pt[j] = temp1;
            temp2=vl[i];
            vl[i]=vl[j];
            vl[j]=temp2;
        }
    }
}
int sum=0;
for(int l=0;l<pt.length;l++) {
	for(int x=l+1;x<pt.length;x++) {
		if(vl[l]<=vl[x]) {
			
			sum=sum+distance(pt[l],pt[x]);
		}
		
	}
	
}

System.out.println(sum);


	}

	
	public static boolean duplicate (int [] a , int b) {
		int i =0;
		boolean t=false;
		while(a[i]!=0 && t==false) {
	
			if (a[i]==b) {
				t=true;
			}
		i++;
		}
		return t;
	}
	public static int distance(int a ,int b ) {
		int  d=Math.abs(b-a);
		return d;
	}
}





