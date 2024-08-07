
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class A_SushiForTwo {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String line[]=br.readLine().split(" ");
		int n=Integer.parseInt(line[0]);
		
		line=br.readLine().split(" ");
		int maxCount=0;
		int count[]=new int[2];
		int before=Integer.parseInt(line[0])-1;
		count[before]++;
		for(int i=1;i<n;i++) {
			int cur=Integer.parseInt(line[i])-1;
			if(cur==before) {
				count[cur]++;
			}else {
				count[cur]=1;
				before=cur;
			}
			maxCount=Math.max(maxCount, Math.min(count[0], count[1]));
		}
		System.out.println(maxCount*2);
		br.close();
	}

}
