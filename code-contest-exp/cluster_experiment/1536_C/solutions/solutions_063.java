import java.util.*;
import java.io.*;


public class C {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int t = Integer.parseInt(br.readLine());
		
		while(t --> 0) {
			
			int n = Integer.parseInt(br.readLine());
			String line = br.readLine();
						
			HashMap<Double, Long> map = new HashMap<Double, Long>();
			
			double dc, kc;
			dc = kc = 0;
						
			for(int i = 0; i < n; i++) {
				
				if(line.charAt(i) == 'D')
					dc++;
				else
					kc++;
				
				if(dc == 0) {
					
					bw.write((long)kc + " ");
					
				}else if(kc == 0) {
					
					bw.write((long)dc + " ");
					
				}else {
											
					if(map.containsKey(new Double(dc/kc))) {
						
						long val = map.get(new Double(dc/kc))+1;
						
						//System.out.println(val + " for " + dc + " " + kc);
						
						map.replace(new Double(dc/kc), val);
						
						bw.write(val + " ");
						
					}else {
						
						map.put(new Double(dc/kc), 1L);
						
						bw.write("1 ");
						
					}
										
				}
				
			}
			
			bw.write("\n");
			bw.flush();
			
		}
		
	}
	
	static int gcd(int a, int b) {
		
		while(b != 0) {
			
			a = a % b;
			int tmp = b;
			b = a;
			a = tmp;
			
		}
		
		return a;
		
	}

}