

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner=new Scanner(System.in);
		int len=scanner.nextInt();
		int [] sushis=new int[len];
		for(int i=0;i<len;i++)
		{
			sushis[i]=scanner.nextInt();
		}
		int maxlen=0;
		for(int i=0;i<len-1;i++)
		{
			int maxl=0;
			if(sushis[i]==1&&sushis[i+1]==2||sushis[i]==2&&sushis[i+1]==1)
			{
				maxl=2;
				int left=sushis[i];
				int right=sushis[i+1];
				for(int j=1;j<(len-i-1);j++)
				{
					if((i-j)>0&&sushis[i-j]==left&&sushis[i+1+j]==right)
					{
						maxl+=2;
					}
					else {
						break;
					}
				}
				
			}
			if(maxlen<maxl)
				maxlen=maxl;
		}
		System.out.println(maxlen);
		
	}

}
