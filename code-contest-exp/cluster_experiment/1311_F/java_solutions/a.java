import java.util.Scanner;

public class a {



    public static void main(String[] args) {


        Scanner sc=new Scanner(System.in);

        int n=sc.nextInt();
        long[] pos=new long[n];
        long[] vel=new long[n];
         for(int i=0;i<n;i++){
             pos[i]=sc.nextLong();
         }
         for(int i=0;i<n;i++){
            vel[i]=sc.nextLong();
        }
        long sum=0;
        for(int i=0;i<n;i++){

            for(int j=i+1;j<n;j++){
                if(vel[i]==0 && vel[j]==0){
                    sum=sum+Math.abs(pos[i]-pos[j]);
                }

                else if(vel[i]==0 ){
                    if(pos[i]<pos[j] && vel[j]>0){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                    if(pos[i]>pos[j] && vel[j]<0){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                }

                else if(vel[j]==0){
                    if(pos[j]<pos[i] && vel[i]>0){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                    if(pos[j]>pos[i] && vel[i]<0){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }


                }

                else if(vel[i]>0 && vel[j]>0){
                    
                    if( pos[j]>pos[i] &&vel[j]>=vel[i]){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                    if( pos[j]<pos[i] &&vel[j]<=vel[i]){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                }
                else if(vel[i]<0 && vel[j]<0){
                    if( vel[i]>=vel[j] && pos[i]<pos[j]){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                    if( vel[i]<=vel[j] && pos[i]>pos[j]){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                }
                else if(vel[i]<0 && vel[j]>0 ){
                    if(pos[i]<pos[j]){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                 }
                 else if(vel[i]>0 && vel[j]<0 ){
                    if(pos[i]>pos[j]){
                        sum=sum+Math.abs(pos[i]-pos[j]);
                    }
                 }
             }

            
            }
            System.out.println(sum);



        
        
    }

}