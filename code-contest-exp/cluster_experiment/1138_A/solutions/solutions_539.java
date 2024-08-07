import java.util.Scanner;
import java.util.ArrayList;
public class Sushi_For_Two {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ArrayList<Integer> cont = new ArrayList<Integer>();
		int max =0;
		int counter=1;
		for(int i = scan.nextInt();i>0;i--) {
			if(scan.nextInt()!=counter) {
				cont.add(max);
				max = 1;
				if(counter==1) {
					counter=2;
				}else {
					counter=1;
				}
			}else {
				max++;
			}
		}
		cont.add(max);
		max=0;
		for(int i = 0;i<cont.size()-1;i++) {
			if(cont.get(i)>=cont.get(i+1)&&max<cont.get(i+1))
				max=cont.get(i+1);
			if(cont.get(i)<=cont.get(i+1)&&max<cont.get(i))
				max=cont.get(i);
			
		}
		System.out.println(max*2);
		for(int a : cont) {
			//System.out.print(a+" ");
		}

	}

}
