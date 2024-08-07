import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int T[] = new int[N];
		for(int i =0;i<N;i++)
			T[i]  =  sc.nextInt();
		int i =1;
		int courant  = T[0];
		int nbreCourant = 1;
		int nbreNonCourant = 0;
		int max  =0;
		while(i<N)
		{
			while(T[i]==courant&&i<N)
			{
				nbreCourant ++;i++;
			}
			if(i==N) break;
			courant=T[i];
			while(i<N&&T[i]==courant)
			{
				nbreNonCourant++;i++;
			}
			if(Math.min(nbreCourant,nbreNonCourant)>max)
				max  = (int)Math.min(nbreCourant,nbreNonCourant);
			nbreCourant = nbreNonCourant;
			nbreNonCourant =0;
			if(i==N) break;
			courant=T[i-1];
		}
		System.out.println(max*2);
		sc.close();
	}

}
 