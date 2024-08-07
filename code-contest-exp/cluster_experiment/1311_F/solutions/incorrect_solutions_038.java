import java.util.*;
import java.lang.*;
import java.io.*;

public class ct1{

    static class Point{
        int ip, speed;
    }

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        int a = inp.nextInt();
        Point[] points = new Point[a];
        for (int l=0; l<a; l++){
//            int a = inp.nextInt();
            for (int i=0; i<a; i++){
                points[i] = new Point();
            }
            for (int i=0; i<a; i++){
                if (inp.hasNextInt())
                    points[i].ip = inp.nextInt();
            }
            for (int i=0; i<a; i++){
                if (inp.hasNextInt())
                    points[i].speed = inp.nextInt();
            }
            int ans=0;
            for (int i=0; i<a-1; i++){
                for (int j=i+1; j<a; j++){
                    if (points[i].ip < points[j].ip){
                        if(points[i].speed <= points[j].speed){
                            ans+=points[j].ip - points[i].ip;
                        }
                    }
                    else if(points[i].ip == points[j].ip)
                        ans+=0;
                    else {
                        if(points[i].speed >= points[j].speed){
                            ans+=points[i].ip - points[j].ip;
                        }
                    }
                }
            }
            System.out.println(ans);

        }
    }






//    static class ArrivalBurst{
//        public int arrivalTime, burstTime, waitingTime, turnAroundTime, completionTime;
//        ArrivalBurst(){
//            arrivalTime =0;
//            burstTime =0;
//            waitingTime =0;
//            turnAroundTime =0;
//            completionTime =0;
//        }
//    }
//
//    public static void main(String[] args) {
//        Scanner inp = new Scanner(System.in);
//        System.out.println("Enter the number of entries you want to enter: ");
//        int n = inp.nextInt();
//        ArrivalBurst[] arrivalBursts = new ArrivalBurst[n];
//        for(int i=0; i<n; i++){
//            arrivalBursts[i] = new ArrivalBurst();
//            arrivalBursts[i].arrivalTime = inp.nextInt();
//            arrivalBursts[i].burstTime = inp.nextInt();
//        }
//        getAverageTime(arrivalBursts);
//        displayOutput(arrivalBursts);
//    }
//
//    public static void displayOutput(ArrivalBurst[] arrivalBursts){
//        System.out.println("AT\tBT\tCT\tTAT\tWT");
//        double avgWt=0, avgTat=0;
//        for (int i=0; i<arrivalBursts.length; i++){
//            System.out.println(arrivalBursts[i].arrivalTime + " \t" + arrivalBursts[i].burstTime + " \t" + arrivalBursts[i].completionTime + " \t" +
//                    arrivalBursts[i].turnAroundTime + " \t" + arrivalBursts[i].waitingTime);
//            avgTat += arrivalBursts[i].turnAroundTime;
//            avgWt += arrivalBursts[i].waitingTime;
//        }
//        System.out.println("Average Waiting time: " + avgWt/arrivalBursts.length + "\nAverage Turnaroud Time: " + avgTat/arrivalBursts.length);
//    }
//
//    public static void getAverageTime(ArrivalBurst[] arrivalBursts){
//        arrivalBursts[0].completionTime = arrivalBursts[0].arrivalTime + arrivalBursts[0].burstTime;
//        for(int i=1; i<arrivalBursts.length; i++){
//            if(arrivalBursts[i].arrivalTime < arrivalBursts[i-1].completionTime)
//                arrivalBursts[i].completionTime = arrivalBursts[i-1].completionTime + arrivalBursts[i].burstTime;
//            else
//                arrivalBursts[i].completionTime = arrivalBursts[i].arrivalTime + arrivalBursts[i].burstTime;
//        }
//        getAvgWaitingTime(arrivalBursts);
//        getAvgTurnaroundTime(arrivalBursts);
//    }
//
//    public static void getAvgWaitingTime(ArrivalBurst[] arrivalBursts){
//        arrivalBursts[0].waitingTime = 0;
//        for (int i=1; i<arrivalBursts.length; i++){
//            arrivalBursts[i].waitingTime = arrivalBursts[i-1].completionTime - arrivalBursts[i].arrivalTime;
//        }
//    }
//
//    public static void getAvgTurnaroundTime(ArrivalBurst[] arrivalBursts){
//        for(int i=0; i<arrivalBursts.length; i++){
//            arrivalBursts[i].turnAroundTime = arrivalBursts[i].completionTime - arrivalBursts[i].arrivalTime;
//        }
//    }
}