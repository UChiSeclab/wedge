
//package com.my.package1;
import java.util.Scanner;
import java.io.*;
import java.util.*;
public class HellWorld {

    public static void main(String[]args) throws IOException {
        int t;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        t = Integer.parseInt(br.readLine());
        while (t > 0) {
            t--;
            int n = Integer.parseInt(br.readLine());
            String[] st = br.readLine().split(" ");
            int a[] = new int[n + 2];
            int b[] = new int[n + 2];
            for (int i = 1; i <= n; i++) {
                //a[i] = cin.nextInt();
                a[i] = Integer.parseInt(st[i - 1]);
                b[a[i]]++;
            }
            StringBuilder sb = new StringBuilder();
            int tmp = 0;
            String s = "";
            for (int i = 1; i <= n; i++) {
                if (b[i] != 1) {
                    tmp++;
                    break;
                }
            }
            int fir = 1, fin = n;
            if (tmp == 0) sb.append(1);//s += '1';
            else sb.append(0);//s += '0';
            tmp = 0;
            for (int i = 1; i <= n; i++) {
                tmp = i;
                if (b[i] != 1) break;
                if (a[fir] == i && b[i + 1] > 0 && b[i] == 1) fir++;
                else if (a[fin] == i&& b[i + 1] > 0 && b[i] == 1) fin--;
                else break;
            }
            //System.out.println(tmp);
            if(tmp == n) {
                for(int i = 2; i <= n; i++){
                    sb.append(1);
                    //System.out.print('1');
                }
                // System.out.print('\n');
                // continue;
            }

            else {

                for (int i = 2; i <= n - tmp ; i++){
                    sb.append(0);
                    //s += '0';
                }
                for (int i = n - (tmp - 1); i < n; i++) {
                    //s += '1';
                    sb.append(1);
                }
                if (b[1] > 0 && n > 1) sb.append(1);//s += '1';
                else if (n > 1) sb.append(0);//s += '0;
            }
            System.out.println(sb.toString());

        }
    }

 }
