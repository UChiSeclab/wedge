import java.util.Scanner;

public class z4 {

	public static void main(String[] args) {
		int n;
		int k;
		Scanner finput;
		int[] values;
		int[] values2;
		int[] app;
		int counter = 0;
		String delims = "[ ]+";
		finput = new Scanner(System.in);
		
		String fl;
		String sl;
		fl = finput.nextLine();
		String[] fls = fl.split(delims);
		n = Integer.parseInt(fls[0]);
		k = Integer.parseInt(fls[1]);
		sl = finput.nextLine();
		String[] sls = sl.split(delims);
		values = new int[n];
		values2 = new int[n];
		for(int i = 0; i < n; i++){
			values[i] = Integer.parseInt(sls[i]);
		}
		
		values2 = removeDuplicates(values);
		
		app = new int[values2.length];
		
		for(int i = 0; i < values2.length; i++){
			app[i] = rep(values2[i], values);
		}
		
		for(int i = 0; i < values2.length; i++){
			counter = counter + ((app[i] * (app[i] + 1)) / 2);
		}
		
		if(values2.length > 1){
			for(int i = 0; i < values2.length-1; i++){
				for(int j = i+1; j < values2.length; j++){
					String f = "";
					String s = "";
					for(int a = 0; a < 15 - Integer.toBinaryString(values[i]).length(); a++){
						f = f.concat("0");
					}
					String fn = f.concat(Integer.toBinaryString(values[i]));
					for(int b = 0; b < 15 - Integer.toBinaryString(values[j]).length(); b++){
						s = s.concat("0");
					}
					String sn = s.concat(Integer.toBinaryString(values[j]));
					
					if(checker(fn, sn, k))
						counter++;
				}
			}
		}
		
		counter = counter - n;
		
		System.out.println(counter);
		finput.close();
	}

	private static boolean checker(String fn, String sn, int k) {
		int counter = 0;
		char[] first = fn.toCharArray();
		char[] second = sn.toCharArray();
		for(int i = 0; i < 15; i++){
			if(first[i] != second[i])
				counter++;
		}
		return(counter == k);
	}
	
	public static int[] removeDuplicates(int[] arr) {

	    int end = arr.length;

	    for (int i = 0; i < end; i++) {
	        for (int j = i + 1; j < end; j++) {
	            if (arr[i] == arr[j]) {                  
	                int shiftLeft = j;
	                for (int k = j+1; k < end; k++, shiftLeft++) {
	                    arr[shiftLeft] = arr[k];
	                }
	                end--;
	                j--;
	            }
	        }
	    }

	    int[] whitelist = new int[end];
	    for(int i = 0; i < end; i++){
	        whitelist[i] = arr[i];
	    }
	    return whitelist;
	}
	
	public static int rep(int n, int[] ar){
		int counter = 0;
		for(int i = 0; i < ar.length; i++){
			if(ar[i] == n)
				counter++;
		}
		return counter;
	}

}
