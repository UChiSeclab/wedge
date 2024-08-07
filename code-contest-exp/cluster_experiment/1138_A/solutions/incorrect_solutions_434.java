import java.util.Scanner;
	import java.lang.Math; 
  import java.lang.*;
  import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {


    Scanner scan = new Scanner(System.in);
    int cantidad = scan.nextInt();

    ArrayList<Integer> lista = new ArrayList<Integer>();
    int contador= 1;
    int aux = scan.nextInt();
    int aux2 = 0;
		for (int i=1; i < cantidad; i++) {
      aux2 = scan.nextInt();
      //System.out.println("aux1 "+ aux + "  aux2 " + aux2 + "  contador "+ contador + "  i " + i);
      
      if (i == cantidad -1 ) {
        if(aux == aux2) {
          //System.out.println("Entro al auxx");
          lista.add(contador + 1);
        }  
      } else if(aux == aux2 ) {
        contador++;
      } else {
        //System.out.println("Entro al else");
        aux = aux2;
        lista.add(contador);
        contador = 1;
      }
		}

    int max = 0;
    for (int i = 0; i < lista.size() -1; i++) {
      if (lista.get(i) > max && lista.get(i+1) > max) {
        if(lista.get(i) < lista.get(i + 1)) {
          max = lista.get(i);
        } else {
          max = lista.get(i + 1);
        }
      }
      
    }

    System.out.println(max*2);
    
  }
}