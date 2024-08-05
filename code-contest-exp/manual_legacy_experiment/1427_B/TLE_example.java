

import java.util.Scanner;

public class B1427 {
    private final static Scanner scanner = new Scanner(System.in);

    private static int size;
    private static int times;

    public static void main(String[] args) {
        int groups = scanner.nextInt();
        for (int i = 0; i < groups; i++) {
            inner();
        }
    }

    private static void inner(){
        size = scanner.nextInt();
        times = scanner.nextInt();
        String result = scanner.next();
        char[] game = new char[size];
        for (int i = 0; i < size; i++) {
            game[i] = result.charAt(i);
        }
        math(game, result);

        int count = 0;
        for (int i = size-1; i > 0; i--) {
            if (game[i] == 'W' && game[i-1] == 'W'){
                count += 2;
            }else if (game[i] == 'W' && game[i-1] == 'L'){
                count += 1;
            }
        }
        if (game[0] == 'W'){
            count += 1;
        }
        System.out.println(count);
    }

    private static void math(char[] game, String string){
        int flag = 0;
        StringBuilder pd1 = new StringBuilder("W");
        String pd2 = "W";
        for (int  a = 1; a < size; a++) {
            pd1.append("L");
            if (string.contains(pd1+pd2)){
                flag = pd1.length();
            }
        }
        int distence = 1;
        while (times > 0) {
            distence++;
            if (flag >= distence){
                for (int j = 0; j < size-distence; j++) {
                    if (game[j] == 'W' && game[j+distence] == 'W'){
                        for (int k = j+1; k < j+distence; k++) {
                            if (game[k] == 'L'){
                                game[k] = 'W';
                                flag = distence + 1;
                                if (--times == 0) return;
                            }
                        }
                    }
                }
            }else{
                for (int j = size/2; j <size ; j++) {
                    if (game[j] == 'L'){
                        game[j] = 'W';
                        if (--times == 0) return;
                    }
                }
                for (int j = size/2; j >=0 ; j--) {
                    if (game[j] == 'L'){
                        game[j] = 'W';
                        if (--times == 0) return;
                    }
                }
                return;
            }
        }
    }
}

