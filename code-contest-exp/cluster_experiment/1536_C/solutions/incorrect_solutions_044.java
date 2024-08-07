import java.io.PrintWriter;
import java.util.*;

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
//        Set<String> setk = new HashSet<>();
        Map<String, Integer> mp1 = new HashMap<>();
        Map<String, Integer> mp2 = new HashMap<>();
//        Set<String> setd = new HashSet<>();
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
//                        setk.add(sc);
                        mp1.put(sc, 1);
                    }else{
                        String sc = cd+":"+ck;
//                        setd.add(sc);
                        mp2.put(sc, 1);
                    }
                }else{
                    if (ck < cd){
                        helper(out, mp1, ck, cd);
                    }else{
                        helper(out, mp2, cd, ck);
                    }
                }
            }
        }
        out.println();
    }
    static void helper(PrintWriter out, Map<String, Integer> mp, int ck, int cd){
        int mut = cd / ck;
        boolean find = false;
        int qq = -1;
        String dm = "";
        Set<String> add = new HashSet<>();
        for (int j = 1; j <= Math.sqrt(ck); j++) {
            if (ck % j == 0){
                int vd = mut * j;
                String sc = j+":"+vd;
                if (mp.containsKey(sc) && (mp.get(sc) + 1 == ck / j)){
                    out.print(ck / j+" ");
                    find = true;
                    break;
                }
                int op = ck / j;
                vd = mut * op;
                sc = op+":"+vd;
                if (mp.containsKey(sc) && (mp.get(sc) + 1 == j)){
                    qq = j;
                    dm = sc;
                }
            }
        }
        if (!find && qq == -1){
            String sc = ck+":"+cd;
//            set.add(sc);
            mp.put(sc, 1);
            out.print(1+" ");
        }else if (!find && qq != -1){
//            set.add(dm);
            mp.put(dm, 1);
            out.print(qq+" ");
        }
        for(int j = 1; j <= Math.sqrt(ck); j++){
            if (ck % j == 0){
                int vd = mut * j;
                String sc = j+":"+vd;
                if (mp.containsKey(sc) && (mp.get(sc) + 1 == ck / j)){
                    mp.put(sc, ck /j);
                }else{
                    mp.put(sc, 1);
                }
                int op = ck / j;
                vd = mut * op;
                sc = op+":"+vd;
                if (mp.containsKey(sc) && (mp.get(sc) + 1 == j)){
                    mp.put(sc, ck /j);
                }else{
                    mp.put(sc, 1);
                }
            }
        }

    }

}
