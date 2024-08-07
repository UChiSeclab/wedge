import java.util.Scanner;

public class C {
	
	public static <E extends Comparable<E>>int binarySearch(E valor, E[] datos) {
		int min=0,
			max=datos.length-1,
			avg;
		
		while (min<=max) {
			avg=(max+min)/2;
			if(datos[avg].equals(valor)) {
				return avg;
			}else if(datos[avg].compareTo(valor)<0) {
				min=avg+1;
			}else {
				max=avg-1;
			}
			
			
		}
		
		return -1;
		
	}
	
	
	public static int check(int[] arr) {
		for (int i : arr) {
			System.out.print(i+",");
		}
		System.out.println();
		return 0;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int size=Integer.parseInt(sc.nextLine());
		
		String[] temp=sc.nextLine().split(" ");
		
		int[] arr=new int[size];
		
		for (int i = 0; i < size; i++) {
			arr[i]=Integer.parseInt(temp[i]);
		}
		
		int ones=0;
		int twos=0;
		
		int mayor=0;
		int mayorGlobal=0;
		
		int checando=1;
		
		for (int i = 0; i < size; i++) {
			if (arr[i]!=checando) {
				if (checando==1) {
					twos=1;
					checando=2;
				}else {
					ones=1;
					checando=1;
				}
				
			}else {
				if (checando==1) {
					ones++;
				}else {
					twos++;
				}
			}
			if(ones<twos) {
				mayor=ones;
			}else {
				mayor=twos;
			}
			
			if (mayor*2>mayorGlobal) {
				mayorGlobal=mayor*2;
			}
		}
		System.out.println(mayorGlobal);
		
	}
	
}

   	 	 			 	 	  		  		  	   		