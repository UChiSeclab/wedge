import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
public class _p001138A {
    static public void main(final String[] args) throws IOException {
        p001138A._main(args);
    }
//begin p001138A.java
static private class p001138A extends Solver{public p001138A(){nameIn="in/900/p001138A.in"
;singleTest=true;}int n;int[]t;@Override protected void solve(){int res=0;int prev
=0;int[]prev_count=new int[2];int prev_selector=0;for(int i=0;i<n;i++){if(t[i]!=
prev){if(prev>0){prev_selector^=1;if(res<2*Math.min(prev_count[0],prev_count[1])
){res=2*Math.min(prev_count[0],prev_count[1]);}prev_count[prev_selector]=0;}prev
=t[i];}prev_count[prev_selector]++;}if(res<2*Math.min(prev_count[0],prev_count[1
])){res=2*Math.min(prev_count[0],prev_count[1]);}pw.println(res);}@Override public
void readInput()throws IOException{n=sc.nextInt();if(sc.hasNextLine()){sc.nextLine
();}t=Arrays.stream(sc.nextLine().trim().split("\\s+")).mapToInt(Integer::valueOf
).toArray();}static public void _main(String[]args)throws IOException{new p001138A
().run();}}
//end p001138A.java
//begin net/leksi/contest/Solver.java
static private abstract class Solver{protected String nameIn=null;protected String
nameOut=null;protected boolean singleTest=false;protected boolean preprocessDebug
=false;protected boolean doNotPreprocess=false;protected PrintStream debugPrintStream
=null;protected Scanner sc=null;protected PrintWriter pw=null;final String SPACE
=" ";final String SPACES="\\s+";private void process()throws IOException{if(!singleTest
){int t=lineToIntArray()[0];while(t-->0){readInput();solve();}}else{readInput();
solve();}}abstract protected void readInput()throws IOException;abstract protected
void solve()throws IOException;protected int[]lineToIntArray()throws IOException
{return Arrays.stream(sc.nextLine().trim().split(SPACES)).mapToInt(Integer::valueOf
).toArray();}protected long[]lineToLongArray()throws IOException{return Arrays.stream
(sc.nextLine().trim().split(SPACES)).mapToLong(Long::valueOf).toArray();}protected
String joinToString(final int[]a){return Arrays.stream(a).mapToObj(Integer::toString
).collect(Collectors.joining(SPACE));}protected String joinToString(final long[]
a){return Arrays.stream(a).mapToObj(Long::toString).collect(Collectors.joining(SPACE
));}protected<T>String joinToString(final T[]a){return Arrays.stream(a).map(v->Objects
.toString(v)).collect(Collectors.joining(SPACE));}protected<T>String joinToString
(final T[]a,final Function<T,String>toString){return Arrays.stream(a).map(v->toString
.apply(v)).collect(Collectors.joining(SPACE));}protected<T>String joinToString(final
Collection<T>a){return a.stream().map(v->Objects.toString(v)).collect(Collectors
.joining(SPACE));}protected<T>String joinToString(final Collection<T>a,final Function<T
,String>toString){return a.stream().map(v->toString.apply(v)).collect(Collectors
.joining(SPACE));}protected<T>String joinToString(final Stream<T>a){return a.map
(v->Objects.toString(v)).collect(Collectors.joining(SPACE));}protected<T>String joinToString
(final Stream<T>a,final Function<T,String>toString){return a.map(v->toString.apply
(v)).collect(Collectors.joining(SPACE));}protected<T>String joinToString(final IntStream
a){return a.mapToObj(Integer::toString).collect(Collectors.joining(SPACE));}protected
<T>String joinToString(final LongStream a){return a.mapToObj(Long::toString).collect
(Collectors.joining(SPACE));}protected List<Long>intArrayToLongList(final int[]a
){return Arrays.stream(a).mapToObj(Long::valueOf).collect(Collectors.toList());}
protected List<Integer>toList(final int[]a){return Arrays.stream(a).mapToObj(Integer
::valueOf).collect(Collectors.toList());}protected List<Integer>toList(final IntStream
a){return a.mapToObj(Integer::valueOf).collect(Collectors.toList());}protected List<Long>
toList(final long[]a){return Arrays.stream(a).mapToObj(Long::valueOf).collect(Collectors
.toList());}protected List<Long>toList(final LongStream a){return a.mapToObj(Long
::valueOf).collect(Collectors.toList());}protected<T>List<T>toList(final Stream<T>
a){return a.collect(Collectors.toList());}protected<T>List<T>toList(final T[]a){
return Arrays.stream(a).collect(Collectors.toList());}protected void run()throws
IOException{boolean done=false;try{if(nameIn !=null&&new File(nameIn).exists()){
try(FileInputStream fis=new FileInputStream(nameIn);PrintWriter pw0=select_output
();){done=true;sc=new Scanner(fis);pw=pw0;process();}}}catch(IOException ex){}catch
(AccessControlException ex){}if(!done){try(PrintWriter pw0=select_output();){sc=
new Scanner(System.in);pw=pw0;process();}}}private PrintWriter select_output()throws
FileNotFoundException{if(nameOut !=null){return new PrintWriter(nameOut);}return
new PrintWriter(System.out);}}
//end net/leksi/contest/Solver.java
}
