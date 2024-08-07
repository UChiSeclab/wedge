

import java.io.BufferedInputStream;
import java.util.*;

/**
 * @Author hello_kiss
 * @Date 2020/12/7 0:06
 */
public class D {
    public static void main(String[] args) {
        Scanner scan = new Scanner(new BufferedInputStream(System.in));
        int t=scan.nextInt();
        while(t-->0){
            int n=scan.nextInt();
            int []a=new int[n];
            for(int i=0;i<n;i++)a[i]=scan.nextInt();
            Stack<Integer>stack=new Stack<>();
            int []l=new int[n];
            int []r=new int[n];
            for(int i=0;i<n;i++){
                while(!stack.isEmpty()&&a[stack.peek()]>=a[i])stack.pop();
                if(stack.isEmpty())l[i]=0;
                else l[i]=stack.peek()+1;
                stack.push(i);
            }
            stack.clear();
            for(int i=n-1;i>=0;i--){
                while(!stack.isEmpty()&&a[stack.peek()]>=a[i])stack.pop();
                if(stack.isEmpty())r[i]=n-1;
                else r[i]=stack.peek()-1;
                stack.push(i);
            }
            int []flag=new int[n+1];
            for(int i=0;i<n;i++){
                flag[a[i]]=Math.max(flag[a[i]],r[i]-l[i]+1);
            }
            List<Node> list=new ArrayList<>();
            for(int i=1;i<=n;i++){
                if(flag[i]>0){
                    Node node=new Node(i,flag[i]);
                    list.add(node);
                }
            }
            Collections.sort(list,(x,y)->{
                if(x.gs==y.gs)return x.val-y.val;
                return y.gs-x.gs;
            });
            int ma[]=new int[n];
            for(int i=0;i<list.size();i++){
                ma[i]=list.get(i).val;
                if(i>0)
                ma[i]=Math.max(ma[i-1],list.get(i).val);
            }
            int sub=0;
            int ans[]=new int[n+1];
            int index=list.size()-1;
//            for(int i=0;i<=index;i++){
//                System.out.printf("%d %d\n",list.get(i).val,list.get(i).gs);
//            }
            for(int i=1;i<=n;i++){
                if(list.size()<n-i+1){
                    ans[i]=0;
                }
                else{
                    while(index>=0&&list.get(index).gs<=sub)index--;
                    if(index<0)ans[i]=0;
                    else{
                        if(index+1==n-i+1&&ma[index]==n-i+1){
                            ans[i]=1;
                        }
                        else ans[i]=0;
                    }
                }
                sub++;
            }
            for(int i=1;i<=n;i++) System.out.printf("%d",ans[i]);
            System.out.println();

        }
    }
    private static class Node{
        public int val,gs;
        public Node(int val,int gs){
            this.val=val;
            this.gs=gs;
        }
    }
}
