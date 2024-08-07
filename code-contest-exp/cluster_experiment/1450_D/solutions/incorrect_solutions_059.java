

import java.util.Scanner;

public class Main {

    public static void main(String[]args) {
        int t;
        Scanner cin = new Scanner(System.in);
        t = cin.nextInt();
        while (t > 0) {
            t--;
            int n = cin.nextInt();
            int a[] = new int[n + 2];
            int b[] = new int[n + 2];
            for (int i = 1; i <= n; i++) {
                a[i] = cin.nextInt();
                b[a[i]]++;
            }
            int tmp = 0;
            String s = "";
            for (int i = 1; i <= n; i++) {
                if (b[i] != 1) {
                    tmp++;
                    break;
                }
            }
            int fir = 1, fin = n;
            if (tmp == 0) s += '1';
            else s += '0';
            tmp = 0;
            for (int i = 1; i <= n; i++) {
                tmp = i;
                if (b[i] != 1) break;
                if (a[fir] == i && b[i + 1] > 0 && b[i] == 1) fir++;
                else if (a[fin] == i&& b[i + 1] > 0 && b[i] == 1) fin--;
                else break;
            }
            for (int i = 2; i <= n - tmp ; i++){
                s += '0';
            }

            for (int i = n - (tmp - 1); i < n; i++) {
                s += '1';
            }
            if(b[1] > 0  && n > 1) s += '1';
            else if(n > 1) s += '0';
            System.out.println(s);

        }
    }

 }
