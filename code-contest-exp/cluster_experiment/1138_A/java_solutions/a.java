import java.util.*;
public class a{
    public static void main(String args[]){
    
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        
        int max=0,cnt=0,cnt1=0,pre=0,min=0;
        for(int i=0;i<n;i++){
            int t=sc.nextInt();
            if(t==1 && pre==1){
                cnt++;
            }
            else if(t==2 && pre==2){
                cnt1++;
            }
            else{
                if(t==1){
                    
                    if(cnt<cnt1)
                        min=cnt*2;
                    else
                        min=cnt1*2;
                    max=max<min?min:max;
                    cnt=1;
                }    
                else{
                    if(cnt<cnt1)
                        min=cnt*2;
                    else
                        min=cnt1*2;
                    max=max<min?min:max;
                    cnt1=1;
                }
            }
            
            pre=t;
        }
        if(pre==1){
                    
            if(cnt<cnt1)
                min=cnt*2;
            else
                min=cnt1*2;
                max=max<min?min:max;
                cnt=1;
        }    
        else{
            if(cnt<cnt1)
                min=cnt*2;
            else
                min=cnt1*2;
            max=max<min?min:max;
            cnt1=1;
        }
        System.out.println(max);
    }    
    
}