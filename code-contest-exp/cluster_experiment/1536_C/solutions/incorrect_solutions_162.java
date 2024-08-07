import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class C {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            solve(sc, pw);
        }
        pw.close();
    }

    static void solve(Scanner in, PrintWriter out){
        int n = in.nextInt();
        char[] cs = in.next().toCharArray();
        int cd = 0, ck = 0;
        Set<String> setk = new HashSet<>();
        Set<String> setd = new HashSet<>();
        for(int i = 0; i < n; i++){
            if (cs[i] == 'K'){
                ck++;
            }else{
                cd++;
            }
            if (ck == 0){
                out.print(cd+" ");
            }else if (cd == 0){
                out.print(ck+" ");
            }else{
                if (cd % ck != 0 && ck % cd != 0){
                    out.print(1+" ");
                    if (ck < cd){
                        String sc = ck+":"+cd;
                        setk.add(sc);
                    }else{
                        String sc = cd+":"+ck;
                        setd.add(sc);
                    }
                }else{
                    if (ck < cd){
                        helper(out, setk, ck, cd);
                    }else{
                        helper(out, setd, cd, ck);
                    }
                }
            }
        }
        out.println();
    }
    static void helper(PrintWriter out, Set<String> set, int ck, int cd){
        int mut = cd / ck;
        boolean find = false;
        int qq = -1;
        String dm = "";
        Set<String> add = new HashSet<>();
        for (int j = 1; j <= Math.sqrt(ck); j++) {
            if (ck % j == 0){
                int vd = mut * j;
                String sc = j+":"+vd;
                if (set.contains(sc)){
                    out.print(ck / j+" ");
                    find = true;
                    break;
                }
                int op = ck / j;
                vd = mut * op;
                sc = op+":"+vd;
                if (set.contains(sc)){
                    qq = j;
                    dm = sc;
                }
            }
        }
        if (!find && qq == -1){
            String sc = ck+":"+cd;
            set.add(sc);
            out.print(1+" ");
        }else if (!find && qq != -1){
            set.add(dm);
            out.print(qq+" ");
        }
        for(int j = 1; j <= Math.sqrt(ck); j++){
            if (ck % j == 0){
                int vd = mut * j;
                String sc = j+":"+vd;
                add.add(sc);
                int op = ck / j;
                vd = mut * op;
                sc = op+":"+vd;
                add.add(sc);
            }
        }
        set.addAll(add);
    }
}
