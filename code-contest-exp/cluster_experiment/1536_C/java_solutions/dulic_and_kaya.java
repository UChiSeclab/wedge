import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.HashMap;

public class dulic_and_kaya {
    public static int gcd(int a,int b){
        return a==0?b:gcd(b%a,a);
    }
    
	public static void main(String[] args) throws java.lang.Exception {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));
		int t=Integer.parseInt(br.readLine());
		while(t>0){
		    int n=Integer.parseInt(br.readLine());
		    String s=br.readLine();
		    Map<String,Integer> map=new HashMap<>();
		    int D=0,K=0;
		    for(int i=0;i<n;i++){
		        char c=s.charAt(i);
		        if(c=='D') D++;
		        else K++;
		        int g=gcd(D,K);
		        String key=(D/g)+"#"+(K/g);
		        int f=map.getOrDefault(key,0);
		        map.put(key,f+1);
		        bw.write((f+1)+" ");
		    }
		    bw.write("\n");
		    t--;
		}
		bw.close();
	}
}
