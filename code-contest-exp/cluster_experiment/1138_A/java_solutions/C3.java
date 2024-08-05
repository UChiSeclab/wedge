import java.util.Scanner;
import java.util.ArrayList;
public class C3 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		ArrayList <Integer> list = new ArrayList <Integer>();
		String n_temporal = sc.nextLine();
		int n = Integer.parseInt(n_temporal);
		String cadena = sc.nextLine();
		int aux = cadena.length();
		String [] a_temporal = new String[n];
		int [] a_temporal_2 = new int[n];
		int [] a = new int[n];
		int aux_2 = 0;
		char signo = '-';
		int [] combinaciones = new int[n];
		
		for (int i = 0; i < n; i++) {
			a_temporal[i] = "";
		}
		for (int i = 0; i < aux; i++) {
			if(Character.isDigit(cadena.charAt(i)) || (cadena.charAt(i)) == signo)
				a_temporal[aux_2] = a_temporal[aux_2] + cadena.charAt(i);
			else
				aux_2++;
		}
		for (int i = 0; i < n; i++) {
			 a[i] = Integer.parseInt(a_temporal[i]);	
			 a_temporal_2[i] = Integer.parseInt(a_temporal[i]);
		}

		int cont_1 = 1;
		int cont_2 = 1;
		int bandera = 0;
		int max = 0;
		int i = 0;
		int j = 0;
		int temp_i = 0;
		aux = 0;

		while (i < n-1) {
			while (a_temporal_2[j] == a_temporal_2[j + 1]) {
				cont_1++;
				j++;
			}
			i = j + 1;
			temp_i = i;
			while (i < n - 1 && bandera == 0) {
				if (a_temporal_2[i] == a_temporal_2[i + 1]) {
					cont_2++;
					i++;
				}
				else
					bandera++;				
			}
			bandera = 0;
			if (cont_1 == cont_2)
				max = cont_1 + cont_2;
			else {
				aux = Math.min(cont_1, cont_2);
				if ((aux*2) > max)
					max = aux*2;
			}
			j = temp_i;
			cont_1 = 1;
			cont_2 = 1;
		}
		System.out.println(max);				
	}
}
 				    		  	 		 	 	  			   	