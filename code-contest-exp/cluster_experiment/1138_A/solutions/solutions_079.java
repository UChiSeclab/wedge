import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	public Main() {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String[]temp ;
        String bfRead;
        try {
			bfRead = bf.readLine();
			temp=bfRead.split(" ");
			int n= Integer.parseInt(temp[0]);
			bfRead = bf.readLine();
			temp=bfRead.split(" ");
			int id=0;
			int cantidad=1;
			nodo cabeza=null;
			ArrayList<nodo> lista = new ArrayList<nodo>();
			for (int i = 0; i < n; i++) {
				if(i+1<temp.length && temp[i+1].equals(temp[i])) {
					cantidad++;
				}else {
					lista.add(new nodo(id, temp[i], cantidad));
					cantidad=1;
					id++;
				}
			}
			int mayor=0;
			for (int i = 0; i < lista.size(); i++) {
				if( i+1<lista.size() && !lista.get(i).tipo.equals(lista.get(i+1))) {
					if(lista.get(i).cantidad<=lista.get(i+1).cantidad &&  lista.get(i).cantidad>mayor) {
						mayor=lista.get(i).cantidad;
					}else if(lista.get(i+1).cantidad>mayor && lista.get(i+1).cantidad<=lista.get(i).cantidad) {
						mayor=lista.get(i+1).cantidad;
					}
				}
			}
			System.out.print(mayor*2);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public class nodo{
		int id;
		String tipo;
		int cantidad;

		
		public nodo(int id,String tipo,int cantidad){
			this.id=id;
			this.tipo=tipo;
			this.cantidad=cantidad;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}

	}
	public static void main(String[] args) {
		
		Main m = new Main();	

	}
}
	   					  	 	 	 			  	     			