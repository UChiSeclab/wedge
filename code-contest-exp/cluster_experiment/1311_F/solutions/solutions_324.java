// package Quarantine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MovingPoints {
    public static void update(int tree[],int val,int ind,int size){
        while(ind<=size){
            tree[ind]+=val;
            ind+=(ind&(-ind));
        }
        return;
    }
    public static void update(long tree[],int val,int ind,int size){
        while(ind<=size){
            tree[ind]+=val;
            ind+=(ind&(-ind));
        }
        return;
    }
    public static long query(long tree[],int ind){
        long sum=0;
        while(ind>0){
            sum+=tree[ind];
            ind-=(ind&(-ind));
        }
        return sum;
    }
    public static long query(int tree[],int ind){
        long sum=0;
        while(ind>0){
            sum+=tree[ind];
            ind-=(ind&(-ind));
        }
        return sum;
    }
    public static void main(String[] args)throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        StringTokenizer st=new StringTokenizer(br.readLine());
        int x[]=new int[n+1];
        for(int i=1;i<=n;i++){
            x[i]=Integer.parseInt(st.nextToken());
        }
        st=new StringTokenizer(br.readLine());
        int v[]=new int[n+1];
        for(int i=1;i<=n;i++){
            v[i]=Integer.parseInt(st.nextToken());
        }
        ArrayList<Particle> particles=new ArrayList<>();
        TreeSet<Integer> set=new TreeSet<>();
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=1;i<=n;i++){
            particles.add(new Particle(x[i],v[i]));
            set.add(v[i]);
        }
        Collections.sort(particles);
        int count=1;
        for(int k:set){
            map.put(k,count++);
        }
        int numtree[]=new int[count];
        long coordi[]=new long[count];
        count--;
//        System.out.println(map.toString());
        long ans=0;
        for(int i=n-1;i>=0;i--){
            Particle curr=particles.get(i);
            int vt=map.get(curr.v);
            long num=query(numtree,count)-query(numtree,vt-1);
            long sum=query(coordi,count)-query(coordi,vt-1);
//            System.out.println(curr.x+" "+num+" "+sum);
            long temp=sum-num*curr.x;
            ans+=temp;
            update(numtree,1,vt,count);
            update(coordi,curr.x,vt,count);
        }
        System.out.println(ans);
    }
}


class Particle implements Comparable<Particle>{
    int x,v;
    public Particle(int x,int v){
        this.x=x;
        this.v=v;
    }

    @Override
    public int compareTo(Particle o) {
        return this.x-o.x;
    }
}
